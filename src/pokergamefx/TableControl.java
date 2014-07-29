/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pokergamefx;

import com.xfinity.poker.Card;
import com.xfinity.poker.CardDeck;
import com.xfinity.poker.ComputerPlayer;
import com.xfinity.poker.Dealer;
import com.xfinity.poker.Dealer.Round;
import static com.xfinity.poker.DealerRules.PLAYER_HAND_SIZE;
import static com.xfinity.poker.GameRules.GAME_PLAYERS;
import static com.xfinity.poker.GameRules.PLAYER_REAWARD_FACTOR;
import static com.xfinity.poker.GameRules.PLAYER_TIME_OUT_SECONDS;
import com.xfinity.poker.HumanPlayer;
import com.xfinity.poker.Player;
import com.xfinity.poker.Player.PlayerAction;
import com.xfinity.poker.PlayerBestHighHand;
import com.xfinity.poker.PlayerBestLowHand;
import com.xfinity.poker.Table;
import static com.xfinity.poker.TableRules.NUMBER_OF_FLOP_CARDS;
import static com.xfinity.poker.TableRules.NUMBER_OF_RIVER_CARDS;
import static com.xfinity.poker.TableRules.NUMBER_OF_TURN_CARDS;
import static com.xfinity.poker.TableRules.RIVER_CARD_POS;
import static com.xfinity.poker.TableRules.TURN_CARD_POS;
import com.xfinity.poker.Value;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Administrator
 */
public class TableControl extends AnchorPane implements Initializable {
    
    @FXML
    private AnchorPane tableAnchor;
    
    @FXML
    private HBox singlePlayerCards,playerCards1,playerCards2,playerCards3,playerCards4,tableCards;
    
    @FXML
    private Text singlePlayerName,player1Name,player2Name,player3Name,player4Name;
    
    @FXML
    private Text singlePlayerChips,player1Chips,player2Chips,player3Chips,player4Chips;
    
    @FXML
    private Rectangle singlePlayerMessageBox,player1MessageBox,player2MessageBox,player3MessageBox,player4MessageBox;
    
    @FXML
    private Text singlePlayerMessageText,player1MessageText,player2MessageText,player3MessageText,player4MessageText;
    
    @FXML
    private Text roundMessageText;
    
    @FXML
    private Rectangle roundMessageBox;
    
    @FXML
    private Text singlePlayerPot,player1Pot,player2Pot,player3Pot,player4Pot;
    
    @FXML
    private Pane playerActionsPane;
    
    @FXML
    private ProgressIndicator playerTimeIndicatior;   
    
    
    @FXML
    private Button callButton,raiseButton,checkButton,foldButton,allInButton;
    
    @FXML
    private AnchorPane winnersAnchorPane;
    
    @FXML
    private Text highWinnerName,lowWinnerName,highHandType;
    
    @FXML
    private HBox highWinningCards,lowWinnngCards;
    
    @FXML 
    private Text highWinnings,lowWinnings;
    
    @FXML
    private Button playAgainButton,exitGameButton;
    
    @FXML
    private Group dealerChip,smallBlindChip,bigBlindChip;
    
    @FXML
    private ImageView playNowChip;
    
    @FXML
    private Text gameNameMain,howToPlayText;
    
    @FXML
    private AnchorPane startScreen;
    
    @FXML
    private AnchorPane tutorialAnchorPane;
    
    @FXML
    private Button tutorialBackButton;
    
    
    private List<HBox> playerCardsList;
    
    private Dealer dealer;
    
    private PokerGameFX application;
    
    private Table table;
    
    private PlayerAction playerSelectedAction;
    
    private Timeline timelinePlayerActionAnimation;
    
    private int roundsCount = 0;
    
    public TableControl(Dealer dealerRef,final PokerGameFX application){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLTable(1).fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        this.dealer = dealerRef;
        table = dealerRef.getTable();
        this.application = application;
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        
        setUpWelcomeScreen();
        
        setUpPlayerActions();
        
        setUpWinnerScreenActions();
        
        initializeTableControls();      

    }
    
    private void setUpWelcomeScreen(){
        setUpPlayNowChipAnimation();        
        welcomeScreenTextAnimations();
        
        howToPlayText.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent e) {
                tutorialAnchorPane.setVisible(true);
                startScreen.setVisible(false);
            }   
        });
        
        tutorialBackButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                tutorialAnchorPane.setVisible(false);
                startScreen.setVisible(true);
            }
        });
    }
    
    private void initializeTableControls(){
        playerCardsList = new ArrayList<HBox>();        
        playerCardsList.add(singlePlayerCards);
        playerCardsList.add(playerCards1);
        playerCardsList.add(playerCards2);
        playerCardsList.add(playerCards3);
        playerCardsList.add(playerCards4);
        playerSelectedAction = PlayerAction.FOLD;  
    }
    
    private void setUpPlayerActions(){
        callButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                hidePlayerControls();
                playerSelectedAction = PlayerAction.CALL;
                if(timelinePlayerActionAnimation != null){
                    timelinePlayerActionAnimation.jumpTo(Duration.millis(PLAYER_TIME_OUT_SECONDS * 1000));
                }
                
                setPlayerActionText(playerSelectedAction);
                dealer.getCallFrom(0);
            }

            

            
        });
        
        raiseButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                hidePlayerControls();
                playerSelectedAction = PlayerAction.RAISE;
                if(timelinePlayerActionAnimation != null){
                    timelinePlayerActionAnimation.jumpTo(Duration.millis(PLAYER_TIME_OUT_SECONDS * 1000));
                }
                setPlayerActionText(playerSelectedAction);
                dealer.getRaiseFrom(0);
                
            }
        });
        
        checkButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                hidePlayerControls();
                playerSelectedAction = PlayerAction.CHECK;
                if(timelinePlayerActionAnimation != null){
                    timelinePlayerActionAnimation.jumpTo(Duration.millis(PLAYER_TIME_OUT_SECONDS * 1000));
                }
                
                setPlayerActionText(playerSelectedAction);
                
            }
        });
        
        foldButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                hidePlayerControls();
                playerSelectedAction = PlayerAction.FOLD;
                if(timelinePlayerActionAnimation != null){
                    timelinePlayerActionAnimation.jumpTo(Duration.millis(PLAYER_TIME_OUT_SECONDS * 1000));
                }
                
                setPlayerActionText(playerSelectedAction);
                dealer.setFlod(0);
            }
        });
        
        allInButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                hidePlayerControls();
                playerSelectedAction = PlayerAction.ALL_IN;
                if(timelinePlayerActionAnimation != null){
                    timelinePlayerActionAnimation.jumpTo(Duration.millis(PLAYER_TIME_OUT_SECONDS * 1000));
                }
                
                setPlayerActionText(playerSelectedAction);
                dealer.getAllInFrom(0);
            }
        });
    }
    
    private void setUpWinnerScreenActions(){
        playAgainButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                winnersAnchorPane.setVisible(false);
                winnersAnchorPane.setLayoutY(winnersAnchorPane.getLayoutY()-10);
                lowWinnings.setText("$ 0");
                resetWinners();
                clearTable();
                adjustDealer();
                adjustTable();
                if(adjustPlayers()){
                    changeChipsPositionsAnimations();
                    startGame();
                }else{
                    //showGameOver();
                }
            }
        });
        
        exitGameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                Platform.exit();
            }
        });
    }
    
    private void welcomeScreenTextAnimations(){
        KeyValue valueFill = new KeyValue(gameNameMain.fillProperty(), Paint.valueOf("86060f"), Interpolator.EASE_OUT);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(800), valueFill);
        final Timeline timeline = new Timeline();
        timeline.setDelay(Duration.millis(300));
        timeline.setAutoReverse(true);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
        
        final RotateTransition rotateChip = new RotateTransition(Duration.millis(1000), playNowChip);
        rotateChip.setByAngle(720f);
        rotateChip.setDelay(Duration.millis(2500));
        rotateChip.setAxis(Rotate.Y_AXIS);
        rotateChip.setCycleCount(2);
        rotateChip.play();
        rotateChip.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                rotateChip.play();
            }    
        });
    }
    
    private void setUpPlayNowChipAnimation(){
        final RotateTransition rotateChip = new RotateTransition(Duration.millis(1000), playNowChip);
        rotateChip.setByAngle(720f);
        rotateChip.setDelay(Duration.millis(3500));
        rotateChip.setAxis(Rotate.Y_AXIS);
        rotateChip.setCycleCount(2);
        
        KeyValue valueOpacity = new KeyValue(startScreen.opacityProperty(), 0, Interpolator.EASE_OUT);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(1000), valueOpacity);
        final Timeline timeline = new Timeline();
        timeline.setDelay(Duration.millis(300));
        timeline.getKeyFrames().add(keyFrame);
        
        timeline.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                startScreen.setVisible(false);
                startGame();
            }    
        });        
        
        playNowChip.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent e) {
                timeline.play();
            }   
        });
    }
    
    private void resetWinners(){
        application.setHighHandWinner(null);
        application.setLowHandWinner(null);
    }
    
    private void clearTable(){
        for(HBox cardList : playerCardsList){
            cardList.getChildren().clear();
        }        
        tableCards.getChildren().clear();
    }
    
    private void adjustDealer(){
        dealer.incrementDealingPlayerOrder();
        CardDeck newDeck = new CardDeck();
        newDeck.shuffle();
        dealer.setDeck(newDeck);
        dealer.setRound(Round.PRE_FLOP);
        dealer.setRoundCount(0);
        dealer.clear();
    }
    
    public void adjustTable(){
        table.clear();
        table.incrementBlinds();
        table.setHighestPotValue(0);
    }
    
    private boolean adjustPlayers(){
        int foldCount = 0;
        for(Player player : table.getPlayers()){
            player.setFolded(false);
            if(player.getPlayerChips() < table.getBigBlind())
            {
                if(player instanceof HumanPlayer){
                    //player.awardChips(PLAYER_INITIAL_CHIPS/2);
                    return false;
                }else{
                    double awardingChips = table.getBigBlind() * PLAYER_REAWARD_FACTOR;                    
                    player.awardChips(awardingChips);
                    
                    foldCount++;
                }
            }            
            player.getPlayerHand().clear();
            player.clearWinningHands();
            player.setAllIn(false); 
            player.setCalledForAllIn(false);
        }
        if(foldCount > 4){
            return false;
        }
        
        return true;
    }
    
    private void changeChipsPositionsAnimations(){
        int[] xCordinates = {106,106,345,486,492};
        int[] yCordinates = {253,-40,-70,-40,253};
        
        int dealingPlayerOrder = dealer.getDealingPlayerOrder();
        
        int dealingChipPosition = (dealingPlayerOrder+4) % GAME_PLAYERS;
        int smallBlindChipPosition = dealingPlayerOrder;
        int bigBlindChipPosition = (dealingPlayerOrder + 1) % GAME_PLAYERS;
        
        KeyValue valueDealerX = new KeyValue(dealerChip.layoutXProperty(), xCordinates[dealingChipPosition], Interpolator.EASE_OUT);
        KeyValue valueDealerY = new KeyValue(dealerChip.layoutYProperty(), yCordinates[dealingChipPosition], Interpolator.EASE_OUT);
        KeyValue valueSmallBlindX = new KeyValue(smallBlindChip.layoutXProperty(), xCordinates[smallBlindChipPosition], Interpolator.EASE_OUT);
        KeyValue valueSmallBlindY = new KeyValue(smallBlindChip.layoutYProperty(), yCordinates[smallBlindChipPosition], Interpolator.EASE_OUT);
        KeyValue valueBigBlindX = new KeyValue(bigBlindChip.layoutXProperty(), xCordinates[bigBlindChipPosition], Interpolator.EASE_OUT);
        KeyValue valueBigBlindY = new KeyValue(bigBlindChip.layoutYProperty(), yCordinates[bigBlindChipPosition], Interpolator.EASE_OUT);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(1000), valueDealerX, valueDealerY,valueSmallBlindX,valueSmallBlindY,valueBigBlindX,valueBigBlindY);
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(keyFrame);
        
        RotateTransition rotateDealer = new RotateTransition(Duration.millis(1000), dealerChip);
        rotateDealer.setByAngle(720f); 
        
         RotateTransition rotateSmallBlind = new RotateTransition(Duration.millis(1000), smallBlindChip);
        rotateSmallBlind.setByAngle(720f); 
        
        RotateTransition rotateBigBlind = new RotateTransition(Duration.millis(1000), bigBlindChip);
        rotateBigBlind.setByAngle(720f); 
        
        ParallelTransition trans = new ParallelTransition();
        trans.getChildren().addAll(timeline,rotateDealer,rotateSmallBlind,rotateBigBlind);
        
        trans.play();
    }
    
    private void hidePlayerControls() {
        playerActionsPane.setOpacity(0);
        playerTimeIndicatior.setVisible(false);
    }
    
    private void setPlayerActionText(PlayerAction playerSelectedAction) {
        singlePlayerMessageText.setText(playerSelectedAction.toString());
    }
    
    public void startGame(){        
        roundsCount++;
        bindPlayers(table.getPlayers());
        dealer.collectBlinds();
        showPreFlopRoundAnimation();
        int dealingOrder = dealer.getDealingPlayerOrder();
        dealer.dealToPlayers();
        showDealingToPlayerAnimation(dealingOrder);    
        
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void showDealingToPlayerAnimation(int dealingOrder) {
        clearPlayerCards();
        final CardControl showingCard = new CardControl("DIAMONDS",Value.CardValue.TWO);
        final Pane card = showingCard.getCard();        
        card.setLayoutX(354);
        card.setLayoutY(530);        
        tableAnchor.getChildren().add(card);
        showingCard.faceDown();
        
        List<Transition> transitionSequence = new ArrayList<Transition>();
        
        for(int i=0,j=dealingOrder; i< GAME_PLAYERS*PLAYER_HAND_SIZE;i++,j++){
			//players.get(j%players.size()).giveCard(deck.getTopCard());
                        transitionSequence.add(dealToPlayerAnimation(j%5));
		}
        
        SequentialTransition transition = new SequentialTransition();
        transition.getChildren().addAll(transitionSequence);
        transition.play();
        
        transition.setOnFinished(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                adjustCardLists();
                showStartBettingAnimation("Pre Flop Round");                
            }    
        });
                
                
    }
    
    private Transition dealToPlayerAnimation(final int i) {
        final CardControl showingCard = new CardControl("DIAMONDS",Value.CardValue.TWO);
        final Pane card = showingCard.getCard();        
        card.setLayoutX(354);
        card.setLayoutY(530);
        
        tableAnchor.getChildren().add(card);
        showingCard.faceDown();        
        double destinationX = 354,destinationY = 530;
        
        
        
        switch(i){
            case 0:
                destinationX = singlePlayerCards.getLayoutX();
                destinationY = singlePlayerCards.getLayoutY();
                break;
            case 1:
                destinationX = playerCards1.getLayoutX();
                destinationY = playerCards1.getLayoutY();
                break;
            case 2:
                destinationX = playerCards2.getLayoutX();
                destinationY = playerCards2.getLayoutY();
                break;
            case 3:
                destinationX = playerCards3.getLayoutX();
                destinationY = playerCards3.getLayoutY();
                break;
            case 4:
                destinationX = playerCards4.getLayoutX();
                destinationY = playerCards4.getLayoutY();
                break;
            default:
                break;
        }
        
       KeyValue valueX = new KeyValue(card.layoutXProperty(),destinationX,Interpolator.EASE_OUT);       
       KeyValue valueY = new KeyValue(card.layoutYProperty(),destinationY,Interpolator.EASE_OUT);       
       KeyFrame keyFrame = new KeyFrame(Duration.millis(500), valueX, valueY);       
       Timeline timeline = new Timeline();
       timeline.getKeyFrames().add(keyFrame);
       
       RotateTransition rotateTransition = new RotateTransition(Duration.millis(500), card);
       rotateTransition.setByAngle(720f);        
       ParallelTransition parallel =  new ParallelTransition();        
       parallel.getChildren().addAll(timeline,rotateTransition);
       
       
       parallel.setOnFinished(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                addCardToCardList(card,i);
            }

            
        });
       
       return parallel;
       
       
    }
    synchronized private void addCardToCardList(Pane testCard, int i) {
        switch(i){
            case 0:    
                singlePlayerCards.getChildren().add(testCard);
                break;
            case 1:
                playerCards1.getChildren().add(testCard);
                break;
            case 2:
                playerCards2.getChildren().add(testCard);
                break;
            case 3:
                playerCards3.getChildren().add(testCard);
                break;
            case 4:
                playerCards4.getChildren().add(testCard);
                break;
            default:                
                break;
        }
    }

    private void clearPlayerCards() {
        
        for(HBox playerCards: playerCardsList)
            playerCards.getChildren().clear();
    }
    
    private void adjustCardLists(){
        
        List<Timeline> cardAjustTimeLines = new ArrayList<>();
        
        for(HBox playerCards: playerCardsList)
        {
            KeyValue valuePosAdjustment = null;
            if(playerCards == playerCards3 || playerCards == playerCards4){
                valuePosAdjustment = new KeyValue(playerCards.layoutXProperty(),playerCards.getLayoutX()+50);
            }
            KeyValue valueX = new KeyValue(playerCards.prefWidthProperty(),50,Interpolator.EASE_OUT);
            KeyFrame keyFrame = null; 
            if(valuePosAdjustment != null)
               keyFrame = new KeyFrame(Duration.millis(500), valueX,valuePosAdjustment); 
            else
                keyFrame =  new KeyFrame(Duration.millis(500), valueX); 
            Timeline timeline = new Timeline();
            timeline.getKeyFrames().add(keyFrame);          
            cardAjustTimeLines.add(timeline);
            
        }
        
        ParallelTransition transition = new ParallelTransition();
        transition.getChildren().addAll(cardAjustTimeLines);
        transition.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    showPlayerCards(0);
                }

            });
        transition.play();
        
    }

    void bindPlayers(List<Player> allPlayers) {
        
        List<Text> playerNamesControlList = new ArrayList<>();
        playerNamesControlList.add(singlePlayerName);
        playerNamesControlList.add(player1Name);
        playerNamesControlList.add(player2Name);
        playerNamesControlList.add(player3Name);
        playerNamesControlList.add(player4Name);
        
        for(int i=0;i<allPlayers.size();i++){
            playerNamesControlList.get(i).textProperty().bind(allPlayers.get(i).nameProperty());
        }
        
        bindPlayersChips(allPlayers);
        
        bindPlayerPots(allPlayers);
        
    }
    
    void showPreFlopRoundAnimation(){
        clearTableCards();
    }

    void showDealFlopCardsAnimation() {
        
    }

    private void clearTableCards() {
        tableCards.getChildren().clear();
    }

    private void bindPlayersChips(List<Player> allPlayers) {
        
        List<Text> playerChipsControlList = new ArrayList<>();
        playerChipsControlList.add(singlePlayerChips);
        playerChipsControlList.add(player1Chips);
        playerChipsControlList.add(player2Chips);
        playerChipsControlList.add(player3Chips);
        playerChipsControlList.add(player4Chips);
        for(int i=0;i<allPlayers.size();i++){
            playerChipsControlList.get(i).textProperty().bind(allPlayers.get(i).getPlayerChipsProperty().asString());
        }
        
    }    

    private void bindPlayerPots(List<Player> allPlayers) {
        List<Text> playerPotControlList = new ArrayList<>(allPlayers.size());
        playerPotControlList.add(singlePlayerPot);
        playerPotControlList.add(player1Pot);
        playerPotControlList.add(player2Pot);
        playerPotControlList.add(player3Pot);
        playerPotControlList.add(player4Pot);

        for(int i=0;i<allPlayers.size();i++){
            final int j = i;
            playerPotControlList.get(i).textProperty().bind(table.getTablePot().getPlayerPots().get(i).playerContributionProperty().asString("%.0f"));           
            playerPotControlList.get(i).textProperty().addListener( new ChangeListener<String>(){
                @Override
                public void changed(ObservableValue<? extends String> ov, String oldVal, String newVal) {
                    Text playerPot = null;
                    
                    switch(j){
                        case 0:
                            playerPot = singlePlayerPot;
                            break;
                        case 1:
                            playerPot = player1Pot;
                            break;
                        case 2:
                            playerPot = player2Pot;
                            break;
                        case 3:
                            playerPot = player3Pot;
                            break;
                        case 4: 
                            playerPot = player4Pot;
                            break;
                    }
                    
                    KeyValue valueFill = new KeyValue(playerPot.fillProperty(),Paint.valueOf("ff0000"),Interpolator.EASE_IN);
                     KeyFrame keyFrame = new KeyFrame(Duration.millis(1000), valueFill); 
                     Timeline timeline = new Timeline();
                    timeline.getKeyFrames().add(keyFrame);
                    timeline.setCycleCount(6);
                    timeline.setAutoReverse(true);
                    timeline.play();
                }
            });
            
        }
    }
    
    private void showStartBettingAnimation(String message) {
        roundMessageText.setText(message);
        roundMessageBox.setOpacity(0);
       KeyValue valueOpacity = new KeyValue(roundMessageText.opacityProperty(),1,Interpolator.EASE_OUT);       
       KeyValue valueBoxOpacity = new KeyValue(roundMessageBox.opacityProperty(),1,Interpolator.EASE_OUT);       
       KeyFrame keyFrame = new KeyFrame(Duration.millis(2000), valueOpacity,valueBoxOpacity);       
       Timeline timeline = new Timeline();
       timeline.getKeyFrames().add(keyFrame);
       timeline.setAutoReverse(true);
       timeline.setCycleCount(2);
       timeline.play();
       
        //playAudio("pre_flop_start.mp3");
       
    }
    
    private void playAudio(String sourceFile){
        String source = new File("audio/"+sourceFile).toURI().toString();
        Media media = new Media(source);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
    }
    
    private HBox getPlayerCardsHBox(int player){
        HBox cardsList = null;       
        switch(player){
            case 0:
                cardsList = singlePlayerCards;
                break;
            case 1:
                cardsList = playerCards1;
                break;
            case 2:
                cardsList = playerCards2;
                break;
            case 3:
                cardsList = playerCards3;
                break;
            case 4:
                cardsList = playerCards4;
                break;
        }       
        
        return cardsList;
    }
    
    private void showPlayerCards(int player) {
        //List<Pane> playerCards = getPlayerCardsPanes(player);
        HBox playerCards = getPlayerCardsHBox(player); 
        playerCards.getChildren().clear();
        
        
        
        List<Card> cards = table.getPlayers().get(player).getPlayerHand().getCards();
        
        List<Pane> playerCardPanes = new ArrayList<Pane>(cards.size());
            
        for(Card card: cards){
            CardControl cardControl =new CardControl(card.getSuit().getSuitType().toString(),card.getValue().getCardValue());
            
            playerCardPanes.add(cardControl.getCard());
            cardControl.getCard().setRotationAxis(Rotate.Y_AXIS);
            cardControl.getCard().setRotate(180);
            
            RotateTransition rt = new RotateTransition(Duration.millis(1000), cardControl.getCard());
            rt.setAxis(Rotate.Y_AXIS);
            rt.setByAngle(180);
            rt.play();
            
        }
        
       playerCards.getChildren().addAll(playerCardPanes);
       
       KeyValue valueSize = new KeyValue(playerCards.prefWidthProperty(),180,Interpolator.EASE_OUT);      
       KeyFrame keyFrame = new KeyFrame(Duration.millis(1000), valueSize);       
       Timeline timeline = new Timeline();
       timeline.getKeyFrames().add(keyFrame);
       timeline.play();
       timeline.setOnFinished(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {               
                startBettingRound();                 
            }  

            
            
        });
       
               
    }
    
    private void startBettingRound() {
                
        int betStartingPlayer = (dealer.getDealingPlayerOrder()+2)%GAME_PLAYERS; 
        dealer.incrementRoundCount();
        
        boolean playerPassed = false;
        
        final List<Transition> bettingTransitionsBeforePlayerChoice = new ArrayList<>();
        final List<Transition> bettingTransitionsAfterPlayerChoice = new ArrayList<>();
        System.out.println("HREHRE -- -- ");
        for(int i=betStartingPlayer;i<GAME_PLAYERS+betStartingPlayer;i++){
            Player player = table.getPlayers().get(i % GAME_PLAYERS);
            if(player.isFolded() || player.isAllIn() ){
                continue;
            }
            
            //PlayerAction action = null;
            
            if(player instanceof HumanPlayer){
                //action = player.getAction();
                playerPassed = true;
                continue;
            }else if(player instanceof ComputerPlayer){
                //action = ((ComputerPlayer)player).getAction(dealer.getPlayerPossibleActions(i%GAME_PLAYERS),dealer.getRound());
                
            }
            if(!playerPassed)
                bettingTransitionsBeforePlayerChoice.add(getPlayerBettingTransition(i%GAME_PLAYERS));
            else
                bettingTransitionsAfterPlayerChoice.add(getPlayerBettingTransition(i%GAME_PLAYERS));
            
        }
        
        if(bettingTransitionsBeforePlayerChoice.isEmpty()){
            showPlayerTurnAnimation(bettingTransitionsAfterPlayerChoice);
            
        }else{        
            SequentialTransition seqTransition = new SequentialTransition();
            seqTransition.getChildren().addAll(bettingTransitionsBeforePlayerChoice);
            seqTransition.setOnFinished(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    showPlayerTurnAnimation(bettingTransitionsAfterPlayerChoice);
                    //application.getSinglePlayerChoice();              
                }

            });
            seqTransition.play();    
        }
        
        
        
        
    }  
    private void showPlayerTurnAnimation(final List<Transition> afterTransitions) {
        
        if(table.getPlayers().get(0).isFolded() ||table.getPlayers().get(0).isAllIn()){
             showAfterPlayerTransitions(afterTransitions);
        }else{            
            
            roundMessageText.setText("Your Turn");
            roundMessageText.setOpacity(0);
            roundMessageBox.setOpacity(0);
           KeyValue valueOpacity = new KeyValue(roundMessageText.opacityProperty(),1,Interpolator.LINEAR);   
           KeyValue valueBoxOpacity = new KeyValue(roundMessageBox.opacityProperty(),1,Interpolator.EASE_IN);
           KeyFrame keyFrame = new KeyFrame(Duration.millis(500), valueOpacity,valueBoxOpacity);       
           Timeline timeline = new Timeline();
           timeline.getKeyFrames().add(keyFrame);
           timeline.setCycleCount(2);
           timeline.setAutoReverse(true);
           timeline.setDelay(Duration.millis(1500));
           timeline.setOnFinished(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) { 
                   showAndWaitPlayerControls(afterTransitions);
                }
            });
           timeline.play();
        }
    }
    
    private void showAndWaitPlayerControls(final List<Transition> afterTransitions) {
        
        setPlayerControlsToDefault();
        List<PlayerAction> possibleActions = dealer.getPlayerPossibleActions(0);
        
        for(PlayerAction action : possibleActions){
            if(action == PlayerAction.CALL)
                callButton.setDisable(false);
            else if(action  == PlayerAction.RAISE)
                raiseButton.setDisable(false);
            else if(action == PlayerAction.CHECK)
                checkButton.setDisable(false);
            else if(action == PlayerAction.FOLD)
                foldButton.setDisable(false);
            else if(action == PlayerAction.ALL_IN)
                allInButton.setDisable(false);            
        }
            playerActionsPane.setVisible(true);
            playerActionsPane.setOpacity(0);
            KeyValue valueOpacity = new KeyValue(playerActionsPane.opacityProperty(), 1, Interpolator.LINEAR);
            KeyFrame keyFrame = new KeyFrame(Duration.millis(500), valueOpacity);
            Timeline timeline = new Timeline();
            timeline.getKeyFrames().add(keyFrame);
            timeline.setDelay(Duration.millis(500));
            timeline.setOnFinished(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    showPlayerTimerAnimation(afterTransitions,false);
                }

            });
            timeline.play();
        
        
        
    }
    
    private void showPlayerTimerAnimation(final List<Transition> afterTransitions,boolean skip) {
        
        if(skip)
        {   
            showAfterPlayerTransitions(afterTransitions);
            return ;
        }
        playerTimeIndicatior.setVisible(true);
        playerTimeIndicatior.setProgress(0);
        playerSelectedAction = PlayerAction.FOLD;
        KeyValue valueProgress = new KeyValue(playerTimeIndicatior.progressProperty(), 1, Interpolator.LINEAR);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(PLAYER_TIME_OUT_SECONDS*1000), valueProgress);
        timelinePlayerActionAnimation = new Timeline();
        timelinePlayerActionAnimation.getKeyFrames().add(keyFrame);
        timelinePlayerActionAnimation.setDelay(Duration.millis(500));
        timelinePlayerActionAnimation.setOnFinished(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                hidePlayerControls();
                setPlayerActionText(playerSelectedAction);
                //handle single player default actions
                if(playerSelectedAction == PlayerAction.FOLD)
                    dealer.setFlod(0);
                
                playAudio("you_"+playerSelectedAction.toString().toLowerCase() + ".mp3");
                showAfterPlayerTransitions(afterTransitions);
            }

            
            
        });
        timelinePlayerActionAnimation.play();
    }
    private void showAfterPlayerTransitions(List<Transition> afterTransitions) {
        
        if(afterTransitions.isEmpty()){
                
                if(table.isSamePotValues() || dealer.allFoldedOrAllIn() || dealer.allFoldedOrAllInExceptOne()){
                    if(dealer.getRound() == Round.PRE_FLOP)
                        startFlopRound();
                    else if(dealer.getRound() == Round.FLOP)
                        startTurnRound();
                    else if(dealer.getRound() == Round.TURN)
                        startRiverRound();
                    else if(dealer.getRound() == Round.RIVER)
                        startShowDownRound();
                }else{
                    System.out.println("D-SBR");
                    startBettingRound(); 
                }
                
        }else {
    
            final SequentialTransition seqTransition = new SequentialTransition();
            seqTransition.getChildren().addAll(afterTransitions);

            seqTransition.play(); 
            seqTransition.setOnFinished(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {                
                    if (table.isSamePotValues() || dealer.allFoldedOrAllIn() || dealer.allFoldedOrAllInExceptOne()) {
                        if (dealer.getRound() == Round.PRE_FLOP) {
                            startFlopRound();
                        } else if (dealer.getRound() == Round.FLOP) {
                            startTurnRound();
                        } else if (dealer.getRound() == Round.TURN) {
                            startRiverRound();
                        } else if (dealer.getRound() == Round.RIVER) {
                            startShowDownRound();
                        }
                    } else {
                        System.out.println("E-SBR");
                        startBettingRound();
                    }
                        
                }           
            });
        }
    }
    
    private void setPlayerControlsToDefault() {
        raiseButton.setDisable(true);
        callButton.setDisable(true);
        checkButton.setDisable(true);
        foldButton.setDisable(true);
        allInButton.setDisable(true);
    }
    
    private Transition getPlayerBettingTransition(final int playerPos) {
        Rectangle messageBox = null;
        Text messageText = null;
        switch (playerPos) {
            case 0:
                messageBox = singlePlayerMessageBox;
                messageText = singlePlayerMessageText;
                break;
            case 1:
                messageBox = player1MessageBox;
                messageText = player1MessageText;
                break;
            case 2:
                messageBox = player2MessageBox;
                messageText = player2MessageText;
                break;
            case 3:
                messageBox = player3MessageBox;
                messageText = player3MessageText;
                break;
            case 4:
                messageBox = player4MessageBox;
                messageText = player4MessageText;
                break;
        }
        
        final Text finalMessageText = messageText;
        final Rectangle finalMessageBox = messageBox;
        

        KeyValue valueBox = new KeyValue(messageBox.fillProperty(), Paint.valueOf("3b72ff"), Interpolator.EASE_IN);        
        KeyValue valueTextThinking = new KeyValue(messageText.textProperty(),"Hmmm..");
        KeyFrame keyFrameBox = new KeyFrame(Duration.millis(300), valueBox,valueTextThinking);
        Timeline timelineBox = new Timeline();
        timelineBox.getKeyFrames().add(keyFrameBox);
        timelineBox.setCycleCount(15);
        timelineBox.setDelay(Duration.millis(2000));
        timelineBox.setAutoReverse(true);
        
        
        SequentialTransition seqTrans = new SequentialTransition();
        seqTrans.getChildren().addAll(timelineBox);
        seqTrans.setOnFinished(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) { 
                showAndDoPlayerAction(finalMessageText,playerPos);
                finalMessageBox.setFill(Paint.valueOf("e4e4e4"));
                finalMessageText.setText("");
            }           

            
        });
        return seqTrans;
    }
    private void showAndDoPlayerAction(final Text finalMessageText,final int playerPos) {
                
        
        Player player = table.getPlayers().get(playerPos);
        PlayerAction action = ((ComputerPlayer) player).getAction(dealer.getPlayerPossibleActions(playerPos),table.getCommunityCards(), dealer.getRound(), dealer.getRoundCount()); //for now
        
        if(player.isCalledForAllIn() && dealer.allFoldedOrAllInExceptOne())
            return;
        
                
        if (action == PlayerAction.CALL) {
            dealer.getCallFrom(playerPos);
        } else if (action == PlayerAction.FOLD) {
            dealer.setFlod(playerPos);
        } else if(action == PlayerAction.RAISE){
            dealer.getRaiseFrom(playerPos); 
        } else if(action == PlayerAction.ALL_IN){
            dealer.getAllInFrom(playerPos);
        }else if(action == PlayerAction.CHECK){            
            
        }
        if(action == PlayerAction.CALL && player.isAllIn())
            action = PlayerAction.ALL_IN;
        
        
        //Random rand = new Random();
        KeyValue valueText = new KeyValue(finalMessageText.textProperty(), action.toString());
        KeyFrame keyFrameText = new KeyFrame(Duration.millis(1000), valueText);
        Timeline timelineText = new Timeline();
        timelineText.setDelay(Duration.millis(400));
        timelineText.getKeyFrames().add(keyFrameText);
        timelineText.play();
        
        playAudio(action.toString().toLowerCase()+".mp3");        
        
        
        

        timelineText.setOnFinished(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

            }
        });
                
     }
    
    private void startFlopRound(){
        dealer.dealFlop();
        
        showFlopRoundMessage();
        
        
    }
    
    private void showFlopRoundMessage(){
         roundMessageText.setText("Flop Round");
         roundMessageBox.setOpacity(0);
         //playAudio("flop_start.mp3");
       KeyValue valueOpacity = new KeyValue(roundMessageText.opacityProperty(),1,Interpolator.EASE_OUT);       
       KeyValue valueBoxOpacity = new KeyValue(roundMessageBox.opacityProperty(),1,Interpolator.EASE_OUT);       
       KeyFrame keyFrame = new KeyFrame(Duration.millis(2000), valueOpacity,valueBoxOpacity);       
       Timeline timeline = new Timeline();
       timeline.getKeyFrames().add(keyFrame);
       timeline.setAutoReverse(true);
       timeline.setCycleCount(2);
       timeline.setDelay(Duration.millis(2000));
       timeline.setOnFinished(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                showDealingFlopAnimations();
            }
       });
       timeline.play();
    }
    
    private void showDealingFlopAnimations(){
        
        SequentialTransition seqTrans = getDealingTableCardsTransition(NUMBER_OF_FLOP_CARDS);
        
        seqTrans.play();
        seqTrans.setOnFinished(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                showTableCards();
            }

            
        });
    }
    
    private SequentialTransition getDealingTableCardsTransition(int numberOfCards){
        List<Transition> transitions = new ArrayList<>();
        for(int i=0;i<numberOfCards;i++)
            transitions.add(getDealingFlopAnimations());
        
        //tableCards.setPrefWidth(USE_COMPUTED_SIZE);
        tableCards.setMinWidth(280);
        tableCards.setSpacing(10);
        SequentialTransition seqTrans = new SequentialTransition();
        seqTrans.getChildren().addAll(transitions);
        return seqTrans;
    }
    
    private Transition getDealingFlopAnimations(){
        final CardControl showingCard = new CardControl("DIAMONDS",Value.CardValue.TWO);
        final Pane card = showingCard.getCard();
        
        card.setLayoutX(354);
        card.setLayoutY(530);
        
        tableAnchor.getChildren().add(card);
        showingCard.faceDown();        
        double destinationX = tableCards.getLayoutX(),destinationY = tableCards.getLayoutY();
        
        KeyValue valueX = new KeyValue(card.layoutXProperty(),destinationX,Interpolator.EASE_OUT);       
       KeyValue valueY = new KeyValue(card.layoutYProperty(),destinationY,Interpolator.EASE_OUT);    
       KeyValue valueScaleX = new KeyValue(card.scaleXProperty(),1.2,Interpolator.EASE_OUT);
       KeyValue valueScaleY = new KeyValue(card.scaleYProperty(),1.2,Interpolator.EASE_OUT);
       KeyFrame keyFrame = new KeyFrame(Duration.millis(500), valueX, valueY,valueScaleX,valueScaleY);       
       Timeline timeline = new Timeline();
       timeline.getKeyFrames().add(keyFrame);
       //timeline.setDelay(Duration.millis(2000));
       
       RotateTransition rotateTransition = new RotateTransition(Duration.millis(500), card);
       rotateTransition.setByAngle(720f);     
       //rotateTransition.setDelay(Duration.millis(2000));
       ParallelTransition parallel =  new ParallelTransition();        
       parallel.getChildren().addAll(timeline,rotateTransition);
       
       
       parallel.setOnFinished(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                tableCards.getChildren().add(card);
            }

            
        });
       return parallel;
    }
    
    private void showTableCards() {
         
        tableCards.getChildren().clear();
        
        
        
        List<Card> cards = table.getCommunityCards();
        
        List<Pane> tableCardPanes = new ArrayList<Pane>(cards.size());
            
        for(Card card: cards){
            CardControl cardControl =new CardControl(card.getSuit().getSuitType().toString(),card.getValue().getCardValue());
            
            cardControl.getCard().setScaleX(1.2);
            cardControl.getCard().setScaleY(1.2);
            tableCardPanes.add(cardControl.getCard());
            cardControl.getCard().setRotationAxis(Rotate.Y_AXIS);
            cardControl.getCard().setRotate(180);
            
            RotateTransition rt = new RotateTransition(Duration.millis(1000), cardControl.getCard());
            rt.setAxis(Rotate.Y_AXIS);
            rt.setByAngle(180);
            rt.play();
            
        }
        
       tableCards.getChildren().addAll(tableCardPanes);
       
       KeyValue valueSize = new KeyValue(tableCards.prefWidthProperty(),280,Interpolator.EASE_OUT);      
       KeyFrame keyFrame = new KeyFrame(Duration.millis(1000), valueSize);       
       Timeline timeline = new Timeline();
       timeline.getKeyFrames().add(keyFrame);
       timeline.play();
       timeline.setOnFinished(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {    
                dealer.setRound(Round.FLOP);
                dealer.setRoundCount(0);
                if(dealer.allFoldedOrAllIn() || dealer.allFoldedOrAllInExceptOne())
                {
                    dealer.setRound(Round.TURN);
                    dealer.setRoundCount(0);
                    startTurnRound();
                }
                else
                    startBettingRound();                 
            }              
            
        });
    }
    
    private void startTurnRound(){
        dealer.dealTurn();
        
        showTurnRoundMessage();
    }
    
    private void showTurnRoundMessage(){
        roundMessageText.setText("Turn Round");
         roundMessageBox.setOpacity(0);
         //playAudio("turn_start.mp3");
       KeyValue valueOpacity = new KeyValue(roundMessageText.opacityProperty(),1,Interpolator.EASE_OUT);       
       KeyValue valueBoxOpacity = new KeyValue(roundMessageBox.opacityProperty(),1,Interpolator.EASE_OUT);       
       KeyFrame keyFrame = new KeyFrame(Duration.millis(2000), valueOpacity,valueBoxOpacity);       
       Timeline timeline = new Timeline();
       timeline.getKeyFrames().add(keyFrame);
       timeline.setAutoReverse(true);
       timeline.setCycleCount(2);
       timeline.setDelay(Duration.millis(2000));
       timeline.setOnFinished(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                showDealingTurnAnimations();
            }
       });
       timeline.play();
    }
    
    private void showDealingTurnAnimations(){
        SequentialTransition seqTrans =getDealingTableCardsTransition(NUMBER_OF_TURN_CARDS);
        
        seqTrans.play();
        seqTrans.setOnFinished(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                showTurnCard();
            }
        });
    }
    
    private void showTurnCard() {
        tableCards.getChildren().remove(TURN_CARD_POS);      
        
        
        Card turnCard = table.getCommunityCards().get(TURN_CARD_POS);
        
        
        
        CardControl cardControl = new CardControl(turnCard.getSuit().getSuitType().toString(), turnCard.getValue().getCardValue());

        cardControl.getCard().setScaleX(1.2);
        cardControl.getCard().setScaleY(1.2);
        cardControl.getCard().setRotationAxis(Rotate.Y_AXIS);
        cardControl.getCard().setRotate(180);

        RotateTransition rt = new RotateTransition(Duration.millis(1000), cardControl.getCard());
        rt.setAxis(Rotate.Y_AXIS);
        rt.setByAngle(180);
        rt.play();
        
       tableCards.getChildren().add(cardControl.getCard());
       
       KeyValue valueSize = new KeyValue(tableCards.prefWidthProperty(),280,Interpolator.EASE_OUT);      
       KeyFrame keyFrame = new KeyFrame(Duration.millis(1000), valueSize);       
       Timeline timeline = new Timeline();
       timeline.getKeyFrames().add(keyFrame);
       timeline.play();
       timeline.setOnFinished(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {    
                dealer.setRound(Round.TURN);
                dealer.setRoundCount(0);
                if(dealer.allFoldedOrAllIn() || dealer.allFoldedOrAllInExceptOne()){
                    dealer.setRound(Round.RIVER);
                    dealer.setRoundCount(0);
                    startRiverRound();
                }else
                    startBettingRound();                 
            }              
            
        });
    }
    
    private void startRiverRound(){
        dealer.dealRiver();
        
        showRiverRoundMessage();
    }
    
    private void showRiverRoundMessage(){
        roundMessageText.setText("River Round");
         roundMessageBox.setOpacity(0);
         //playAudio("river_start.mp3");
       KeyValue valueOpacity = new KeyValue(roundMessageText.opacityProperty(),1,Interpolator.EASE_OUT);       
       KeyValue valueBoxOpacity = new KeyValue(roundMessageBox.opacityProperty(),1,Interpolator.EASE_OUT);       
       KeyFrame keyFrame = new KeyFrame(Duration.millis(2000), valueOpacity,valueBoxOpacity);       
       Timeline timeline = new Timeline();
       timeline.getKeyFrames().add(keyFrame);
       timeline.setAutoReverse(true);
       timeline.setCycleCount(2);
       timeline.setDelay(Duration.millis(2000));
       timeline.setOnFinished(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                showDealingRiverAnimations();
            }
       });
       timeline.play();
    }
    
     private void showDealingRiverAnimations(){
        SequentialTransition seqTrans =getDealingTableCardsTransition(NUMBER_OF_RIVER_CARDS);
        
        seqTrans.play();
        seqTrans.setOnFinished(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                showRiverCard();
            }
        });
    }
     
     private void showRiverCard() {
        tableCards.getChildren().remove(RIVER_CARD_POS);      
        
        
        Card turnCard = table.getCommunityCards().get(RIVER_CARD_POS);
        
        
        
        CardControl cardControl = new CardControl(turnCard.getSuit().getSuitType().toString(), turnCard.getValue().getCardValue());

        cardControl.getCard().setScaleX(1.2);
        cardControl.getCard().setScaleY(1.2);
        cardControl.getCard().setRotationAxis(Rotate.Y_AXIS);
        cardControl.getCard().setRotate(180);

        RotateTransition rt = new RotateTransition(Duration.millis(1000), cardControl.getCard());
        rt.setAxis(Rotate.Y_AXIS);
        rt.setByAngle(180);
        rt.play();
        
       tableCards.getChildren().add(cardControl.getCard());
       
       KeyValue valueSize = new KeyValue(tableCards.prefWidthProperty(),280,Interpolator.EASE_OUT);      
       KeyFrame keyFrame = new KeyFrame(Duration.millis(1000), valueSize);       
       Timeline timeline = new Timeline();
       timeline.getKeyFrames().add(keyFrame);
       timeline.play();
       timeline.setOnFinished(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {    
                dealer.setRound(Round.RIVER);
                dealer.setRoundCount(0);
                if(dealer.allFoldedOrAllIn() || dealer.allFoldedOrAllInExceptOne()){
                    startShowDownRound();
                }else
                    startBettingRound();                 
            }              
            
        });
    }
     
    private void startShowDownRound(){
       roundMessageText.setText("Show Down");
       roundMessageBox.setOpacity(0);
       //playAudio("showdown_start.mp3");
       KeyValue valueOpacity = new KeyValue(roundMessageText.opacityProperty(),1,Interpolator.EASE_OUT);       
       KeyValue valueBoxOpacity = new KeyValue(roundMessageBox.opacityProperty(),1,Interpolator.EASE_OUT);       
       KeyFrame keyFrame = new KeyFrame(Duration.millis(2000), valueOpacity,valueBoxOpacity);       
       Timeline timeline = new Timeline();
       timeline.getKeyFrames().add(keyFrame);
       timeline.setAutoReverse(true);
       timeline.setCycleCount(2);
       timeline.setDelay(Duration.millis(2000));
       timeline.setOnFinished(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                adjustShowDownCards();
            }
       });
       timeline.play();
    } 
    
    private void adjustShowDownCards(){
        
        List<Timeline> cardAjustTimeLines = new ArrayList<>();
        
        for(HBox playerCards: playerCardsList)
        {
            KeyValue valuePosAdjustment = null;
            if(playerCards == playerCards3 || playerCards == playerCards4){
                valuePosAdjustment = new KeyValue(playerCards.layoutXProperty(),playerCards.getLayoutX()-50);
            }
            KeyValue valueX = new KeyValue(playerCards.prefWidthProperty(),177,Interpolator.EASE_OUT);
            KeyFrame keyFrame = null; 
            if(valuePosAdjustment != null)
               keyFrame = new KeyFrame(Duration.millis(500), valueX,valuePosAdjustment); 
            else
                keyFrame =  new KeyFrame(Duration.millis(500), valueX); 
            Timeline timeline = new Timeline();
            timeline.getKeyFrames().add(keyFrame);          
            cardAjustTimeLines.add(timeline);
            
        }
        
        ParallelTransition transition = new ParallelTransition();
        transition.getChildren().addAll(cardAjustTimeLines);
        transition.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    showPlayerCardsTransitions();
                }

            });
        transition.play();
        
    }
    
    private void showPlayerCardsTransitions(){
        int showDownStartingPlayer = dealer.getDealingPlayerOrder()+2;
        
        boolean playerPassed = false;
        
        List<Transition> showDownTransitions = new ArrayList<>();
        
        for(int i=showDownStartingPlayer;i<GAME_PLAYERS+showDownStartingPlayer;i++){
            Player player = table.getPlayers().get(i % GAME_PLAYERS);            
            
            //PlayerAction action = null;
            
            if(player instanceof HumanPlayer){
                //action = player.getAction();
                playerPassed = true;
                continue;
            }else if(player instanceof ComputerPlayer){
                //action = ((ComputerPlayer)player).getAction(dealer.getPlayerPossibleActions(i%GAME_PLAYERS),dealer.getRound());
                
            }
            
            showDownTransitions.add(getPlayerShowDownTransition(i%GAME_PLAYERS));            
            
        }
        
        SequentialTransition seqShowCards = new SequentialTransition();
        seqShowCards.getChildren().addAll(showDownTransitions);
        seqShowCards.setOnFinished(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                //showPlayerTurnAnimation(bettingTransitionsAfterPlayerChoice);
                showFindingWinnersAnimation();             
            }
        });
        seqShowCards.play();         
        
        
    }
    
    private Transition getPlayerShowDownTransition(final int player){
               
        ParallelTransition cardsRotating = new ParallelTransition();
        
        
        Rectangle messageBox = null;
        Text messageText = null;
        switch (player) {
            case 0:
                messageBox = singlePlayerMessageBox;
                messageText = singlePlayerMessageText;
                break;
            case 1:
                messageBox = player1MessageBox;
                messageText = player1MessageText;
                break;
            case 2:
                messageBox = player2MessageBox;
                messageText = player2MessageText;
                break;
            case 3:
                messageBox = player3MessageBox;
                messageText = player3MessageText;
                break;
            case 4:
                messageBox = player4MessageBox;
                messageText = player4MessageText;
                break;
        }        

        KeyValue valueBox = new KeyValue(messageBox.fillProperty(), Paint.valueOf("3ba40e"), Interpolator.EASE_IN);        
        KeyValue valueTextThinking = new KeyValue(messageText.textProperty(),"");
        KeyFrame keyFrameBox = new KeyFrame(Duration.millis(300), valueBox,valueTextThinking);
        Timeline timelineBox = new Timeline();
        timelineBox.getKeyFrames().add(keyFrameBox);
        timelineBox.setCycleCount(5);
        timelineBox.setDelay(Duration.millis(2000));
        timelineBox.setAutoReverse(true);
        
        timelineBox.setOnFinished(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                final HBox playerCards = getPlayerCardsHBox(player); 
                playerCards.getChildren().clear();

                final List<Card> cards = table.getPlayers().get(player).getPlayerHand().getCards();        
                final List<Pane> playerCardPanes = new ArrayList<Pane>(cards.size()); 
                for (Card card : cards) {
                    CardControl cardControl = new CardControl(card.getSuit().getSuitType().toString(), card.getValue().getCardValue());

                    playerCardPanes.add(cardControl.getCard());
                    cardControl.getCard().setRotationAxis(Rotate.Y_AXIS);
                    cardControl.getCard().setRotate(180);
                    RotateTransition rt = new RotateTransition(Duration.millis(1000), cardControl.getCard());
                    rt.setAxis(Rotate.Y_AXIS);
                    rt.setByAngle(180);
                    rt.play();

                }
                playerCards.getChildren().addAll(playerCardPanes);
                             
            }  
        });
        
        cardsRotating.getChildren().add(timelineBox);
       
       return cardsRotating;
    }
    
    private void showFindingWinnersAnimation(){
       roundMessageText.setText("We are finding winners!!!");
       roundMessageBox.setOpacity(0);
       KeyValue valueOpacity = new KeyValue(roundMessageText.opacityProperty(),1,Interpolator.EASE_OUT);       
       KeyValue valueBoxOpacity = new KeyValue(roundMessageBox.opacityProperty(),1,Interpolator.EASE_OUT);       
       KeyFrame keyFrame = new KeyFrame(Duration.millis(2000), valueOpacity,valueBoxOpacity);       
       Timeline timeline = new Timeline();
       timeline.getKeyFrames().add(keyFrame);
       timeline.setAutoReverse(true);
       timeline.setCycleCount(2);
       timeline.setDelay(Duration.millis(2000));
       timeline.setOnFinished(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                //adjustShowDownCards();
                
                showHighHandWinner();
            }

           
       });
       timeline.play();
    }
    
    private void showHighHandWinner() {
       PlayerBestHighHand bestHand = dealer.getHighHandWinner();       
       application.setHighHandWinner(table.getPlayers().get(bestHand.getPlayerPosition()));
       
       String[] handText = {"","HIGH CARD","PAIR","TWO PAIRS","THREE OF KIND","STRAIGHT","FLUSH","FULL HOUSE",
                            "FOUR OF KIND","STRAIGHT FLUSH","ROYAL FLUSH"};
        
       roundMessageText.setText(table.getPlayers().get(bestHand.getPlayerPosition()).getName()+" Wins HighHand. " + handText[bestHand.getPlayerBestHand()].toUpperCase());
       roundMessageText.setStyle("-fx-font-size:32;");
       roundMessageBox.setOpacity(0);
       
       System.out.println("WINNING HAND: " + table.getPlayers().get(bestHand.getPlayerPosition()).getWinningCards());
       
       KeyValue valueOpacity = new KeyValue(roundMessageText.opacityProperty(),1,Interpolator.EASE_OUT);       
       KeyValue valueBoxOpacity = new KeyValue(roundMessageBox.opacityProperty(),1,Interpolator.EASE_OUT);       
       KeyFrame keyFrame = new KeyFrame(Duration.millis(2000), valueOpacity,valueBoxOpacity);       
       Timeline timeline = new Timeline();
       timeline.getKeyFrames().add(keyFrame);
       timeline.setAutoReverse(true);
       timeline.setCycleCount(2);
       timeline.setDelay(Duration.millis(2500));
       timeline.setOnFinished(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                showLowHandWinner();
                playAudio("applause.mp3");
            }
       });
       timeline.play();
    }
    
    private void showLowHandWinner() {
        PlayerBestLowHand lowHand = dealer.getLowHandWinner();
        if(lowHand == null)
            roundMessageText.setText("Nobody wins Low Hand");
        else{
            application.setLowHandWinner(table.getPlayers().get(lowHand.getPlayerPosition()));
            roundMessageText.setText(table.getPlayers().get(lowHand.getPlayerPosition()).getName()+" Wins Low Hand. ");
            System.out.println("WINNING LOW HAND: "+table.getPlayers().get(lowHand.getPlayerPosition()).getWinningLowCards());
        }
       roundMessageText.setStyle("-fx-font-size:32;");
       roundMessageBox.setOpacity(0);       
       
       KeyValue valueOpacity = new KeyValue(roundMessageText.opacityProperty(),1,Interpolator.EASE_OUT);       
       KeyValue valueBoxOpacity = new KeyValue(roundMessageBox.opacityProperty(),1,Interpolator.EASE_OUT);       
       KeyFrame keyFrame = new KeyFrame(Duration.millis(2000), valueOpacity,valueBoxOpacity);       
       Timeline timeline = new Timeline();
       timeline.getKeyFrames().add(keyFrame);
       timeline.setAutoReverse(true);
       timeline.setCycleCount(2);
       timeline.setDelay(Duration.millis(2500));
       timeline.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showWinnersDetails();
            }
       });
       timeline.play();
    }
    
    private void showWinnersDetails() {
        
        List<Player> winners = new ArrayList<>(2);
        winnersAnchorPane.setVisible(true);
        winnersAnchorPane.setScaleX(0.635);
        winnersAnchorPane.setScaleY(0.635);
        winnersAnchorPane.setLayoutY(winnersAnchorPane.getLayoutY()+10);
        winnersAnchorPane.setOpacity(0);
        Player highWinner = application.getHighHandWinner();
        winners.add(highWinner);
        highWinnerName.setText(highWinner.getName());
        highHandType.setText(highWinner.getWinningHandType());
        
        highWinningCards.getChildren().clear();
        List<Card> cards = highWinner.getWinningCards();
        Collections.sort(cards);
        List<Pane> playerCardPanes = new ArrayList<Pane>(cards.size());
            
        for(Card card: cards){
            CardControl cardControl =new CardControl(card.getSuit().getSuitType().toString(),card.getValue().getCardValue());
            playerCardPanes.add(cardControl.getCard());
        }
        
       highWinningCards.getChildren().addAll(playerCardPanes);
       highWinningCards.setScaleX(1.5);
       highWinningCards.setScaleY(1.5);
        
       lowWinnngCards.getChildren().clear();
        if(application.getLowHandWinner() == null){
            lowWinnerName.setText("NO WINNER :(");  
            lowWinnings.setText("$ 0");
        }else {
            Player lowWinner = application.getLowHandWinner();
            winners.add(lowWinner);
            lowWinnerName.setText(lowWinner.getName());            
            lowWinnngCards.setScaleX(1.5);
            lowWinnngCards.setScaleY(1.5);
            lowWinnings.setText("$ "+table.getTablePot().getTablePotChips()/winners.size());
            List<Card> lowCards = lowWinner.getWinningLowCards();
            Collections.sort(lowCards);
            List<Pane> lowHandPanes = new ArrayList<Pane>(lowCards.size());
            
            for(Card card: lowCards){
                CardControl cardControl =new CardControl(card.getSuit().getSuitType().toString(),card.getValue().getCardValue());
                lowHandPanes.add(cardControl.getCard());
            }            
            lowWinnngCards.getChildren().addAll(lowHandPanes);
            
        }
        highWinnings.setText("$ "+table.getTablePot().getTablePotChips()/winners.size());
        
       dealer.awardWinners(winners);
        
       KeyValue valueOpacity = new KeyValue(winnersAnchorPane.opacityProperty(),1,Interpolator.EASE_OUT);       
       KeyFrame keyFrame = new KeyFrame(Duration.millis(500), valueOpacity);       
       Timeline timeline = new Timeline();
       timeline.getKeyFrames().add(keyFrame);
       timeline.setDelay(Duration.millis(3000));
       timeline.play();
    }
    
    

    
}
