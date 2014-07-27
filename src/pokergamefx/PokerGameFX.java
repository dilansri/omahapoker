/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pokergamefx;

import com.xfinity.poker.ComputerPlayer;
import com.xfinity.poker.Dealer;
import static com.xfinity.poker.GameRules.GAME_PLAYERS;
import com.xfinity.poker.HumanPlayer;
import com.xfinity.poker.Player;
import com.xfinity.poker.Table;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Administrator
 */
public class PokerGameFX extends Application {
    
    private Dealer dealer;
    Player singlePlayer;
    List<Player> computerPlayers;
    Table table;
    TableControl tableControl;
    
    private Player highHandWinner;
    private Player lowHandWinner;
    
    Stage primaryOwner;
    
    @Override
    public void start(Stage stage) throws Exception {
        //Parent root = FXMLLoader.load(getClass().getResource("FXMLTable.fxml"));
        //clickShow();
        primaryOwner = stage;
        createPlayers();
        createTable();
        createDealer();
        tableControl = new TableControl(dealer,this);
        tableControl.setMaxWidth(800);
        tableControl.setMaxHeight(600);
        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(tableControl);
        StackPane.setAlignment(tableControl, Pos.CENTER);
        stackPane.setStyle("-fx-background-color:  rgb(244,190,79);");
        
        Scene scene = new Scene(stackPane);
        stage.setScene(scene);        
        //stage.initStyle(StageStyle.UTILITY);
        //stage.setFullScreen(true);
        stage.setResizable(false);
        stage.setTitle("Omaha Hi/Lo 8 or Better - xFinity");
        //stage.
        stage.show();
        //tableControl.startGame(); 
        
    }
    
    public void setHighHandWinner(Player player){
        highHandWinner = player;
    }
    
    public Player getHighHandWinner(){
        return highHandWinner;
    }
    
    public void setLowHandWinner(Player player){
        lowHandWinner = player;
    }
    
    public Player getLowHandWinner(){
        return lowHandWinner;
    }
    
    //WindowShow Dialog
    private void clickShow() {
        Stage stage = new Stage();
        Parent root = null;
        try{
            root = FXMLLoader.load(TableCardController.class.getResource("TableCard.fxml"));
        }catch(Exception e){
            
        }
        stage.setScene(new Scene(root));
        stage.setTitle("My modal window");
        stage.initModality(Modality.WINDOW_MODAL);
        //stage.initOwner(parentStage.getScene().getWindow());
        stage.showAndWait();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    private void dealHoleCards() {
        int dealingOrder = dealer.getDealingPlayerOrder();
        dealer.dealToPlayers();
        tableControl.showDealingToPlayerAnimation(dealingOrder);
        
    }

    private void createPlayers() {
        singlePlayer = new HumanPlayer("Single Player",0);
		singlePlayer.setDealer(true);
		
		computerPlayers = new ArrayList<Player>();
		for(int i=1;i<GAME_PLAYERS;i++)
			computerPlayers.add(new ComputerPlayer("CP"+i,i));
    }

    private void createTable() {
        List<Player> allPlayers = new ArrayList<Player>(GAME_PLAYERS);
	allPlayers.add(singlePlayer);
	allPlayers.addAll(computerPlayers);
	table = new Table(allPlayers);
    }
    
    private void createDealer() {
	dealer = new Dealer();
        dealer.shuffleDeck();
	dealer.setTable(table);		
    }

    private void bindPlayers() {
        List<Player> allPlayers = dealer.getTable().getPlayers();
        tableControl.bindPlayers(allPlayers);
    }
    
    private void preFlopRound(){
        dealer.collectBlinds();
        tableControl.showPreFlopRoundAnimation();
        dealHoleCards();
    }

    private void flopRound() {
        tableControl.showDealFlopCardsAnimation();
    }
    
}
