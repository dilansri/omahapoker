/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pokergamefx;

import com.xfinity.poker.Card;
import com.xfinity.poker.ComputerPlayer;
import com.xfinity.poker.Dealer;
import static com.xfinity.poker.DealerRules.PLAYER_HAND_SIZE;
import static com.xfinity.poker.GameRules.GAME_PLAYERS;
import com.xfinity.poker.HumanPlayer;
import com.xfinity.poker.Player;
import com.xfinity.poker.Player.PlayerAction;
import com.xfinity.poker.Value;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
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
    private Text singlePlayerPot,player1Pot,player2Pot,player3Pot,player4Pot;
    
    private List<HBox> playerCardsList;
    
    private Dealer dealer;
    
    Task secondTask;
    
    public TableControl(Dealer dealer){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLTable.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        this.dealer = dealer;
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        
        playerCardsList = new ArrayList<HBox>();        
        playerCardsList.add(singlePlayerCards);
        playerCardsList.add(playerCards1);
        playerCardsList.add(playerCards2);
        playerCardsList.add(playerCards3);
        playerCardsList.add(playerCards4);
    }
    
    public void startGame(){
        /*
        final Task task = new Task() {
            @Override
            protected Object call() {
                bindPlayers(dealer.getTable().getPlayers());
                dealer.collectBlinds();
                showPreFlopRoundAnimation();
                int dealingOrder = dealer.getDealingPlayerOrder();
                dealer.dealToPlayers();
                showDealingToPlayerAnimation(dealingOrder);
                return true;
            }
        };
        
        task.run();
                */
        bindPlayers(dealer.getTable().getPlayers());
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
    
    public void testAnimate(){
        final CardControl testCard = new CardControl("DIAMONDS",Value.CardValue.TWO);
        final Pane card = testCard.getCard();
        
        card.setLayoutX(354);
        card.setLayoutY(530);
        tableAnchor.getChildren().add(card);
        testCard.faceDown();
        
        //testCard.faceUp();
        
       KeyValue valueX = new KeyValue(card.layoutXProperty(),106,Interpolator.EASE_OUT);       
       KeyValue valueY = new KeyValue(card.layoutYProperty(),195,Interpolator.EASE_OUT);       
       KeyFrame keyFrame = new KeyFrame(Duration.millis(1000), valueX, valueY);       
       Timeline timeline = new Timeline();
       timeline.getKeyFrames().add(keyFrame);
       
       RotateTransition rotateTransition = new RotateTransition(Duration.millis(1000), card);
        rotateTransition.setByAngle(720f);
        
        ParallelTransition parallel =  new ParallelTransition();
        
        parallel.getChildren().addAll(timeline,rotateTransition);
       parallel.play();
       
       parallel.setOnFinished(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                playerCards1.getChildren().add(card);
                testCard.faceUp();
            }
        });
       
    }

    void showDealingToPlayerAnimation(int dealingOrder) {
        clearPlayerCards();
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
    
    private void showStartBettingAnimation(String message) {
        roundMessageText.setText(message);
       KeyValue valueOpacity = new KeyValue(roundMessageText.opacityProperty(),1,Interpolator.EASE_OUT);       
    //   KeyValue valueY = new KeyValue(card.layoutYProperty(),destinationY,Interpolator.EASE_OUT);       
       KeyFrame keyFrame = new KeyFrame(Duration.millis(2000), valueOpacity);       
       Timeline timeline = new Timeline();
       timeline.getKeyFrames().add(keyFrame);
       timeline.setAutoReverse(true);
       timeline.setCycleCount(2);
       timeline.play();
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
        
        
        
        List<Card> cards = dealer.getTable().getPlayers().get(player).getPlayerHand().getCards();
        
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
        int betStartingPlayer = dealer.getDealingPlayerOrder();
        
        List<Timeline> bettingTimelines = new ArrayList<>();
        for(int i=betStartingPlayer;i<GAME_PLAYERS+betStartingPlayer;i++){
            Player player = dealer.getTable().getPlayers().get(i % GAME_PLAYERS);
            if(player.isFolded() || player.isAllIn()){
                continue;
            }
            
            PlayerAction action = null;
            
            if(player instanceof HumanPlayer){
                action = player.getAction();
                continue;
            }else if(player instanceof ComputerPlayer){
                action = player.getAction();
            }
            
            bettingTimelines.add(getPlayerBettingTransition(action,i%GAME_PLAYERS));
            
        }
        
        SequentialTransition seqTransition = new SequentialTransition();
        seqTransition.getChildren().addAll(bettingTimelines);
        seqTransition.play();
    }
    
    private Timeline getPlayerBettingTransition(PlayerAction action, int player) {
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
        
        messageText.setText("hmm...");
        
        Random rand = new Random();

        KeyValue valueBox = new KeyValue(messageBox.fillProperty(), Paint.valueOf("3b72ff"), Interpolator.LINEAR);
        KeyValue valueText = new KeyValue(messageText.textProperty(),action.toString());
        KeyFrame keyFrame = new KeyFrame(Duration.millis(1000), valueBox,valueText);
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(keyFrame);
        timeline.setCycleCount(5);
        timeline.setAutoReverse(true);
        
        return timeline;
    }
    

    private Transition dealToPlayerAnimation(final int i) {
        final CardControl testCard = new CardControl("DIAMONDS",Value.CardValue.TWO);
        final Pane card = testCard.getCard();
        
        card.setLayoutX(354);
        card.setLayoutY(530);
        
        tableAnchor.getChildren().add(card);
        testCard.faceDown();        
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
        
        for(HBox playerCards: playerCardsList)
        {
            KeyValue valueX = new KeyValue(playerCards.prefWidthProperty(),50,Interpolator.EASE_OUT);
            KeyFrame keyFrame = new KeyFrame(Duration.millis(500), valueX);       
            Timeline timeline = new Timeline();
            timeline.getKeyFrames().add(keyFrame);
            timeline.play();
            
            timeline.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    showPlayerCards(0);
                }

            });
            
        }
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
            final int j = i;
            playerChipsControlList.get(i).textProperty().bind(allPlayers.get(i).getPlayerChipsProperty().asString());
            playerChipsControlList.get(i).textProperty().addListener( new ChangeListener<String>(){
                @Override
                public void changed(ObservableValue<? extends String> ov, String oldVal, String newVal) {
                    Rectangle messageBox = null;
                    Text messageText = null;
                    switch(j){
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
                    
                    Double changedValue = Double.parseDouble(newVal) - Double.parseDouble(oldVal);
                    messageText.setText("$"+changedValue);
                    
                    KeyValue valueBox = new KeyValue(messageBox.fillProperty(),Paint.valueOf("ffb476"),Interpolator.EASE_OUT);
                    
                    KeyFrame keyFrame = new KeyFrame(Duration.millis(500), valueBox);       
                    Timeline timeline = new Timeline();
                    timeline.getKeyFrames().add(keyFrame);
                    timeline.setCycleCount(5);
                    timeline.setAutoReverse(true);
                    timeline.play();                         
                }
            });
            
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
            playerPotControlList.get(i).textProperty().bind(dealer.getTable().getTablePot().getPlayerPots().get(i).playerContributionProperty().asString("%.0f"));           
            
        }
    }
    
      
    
}
