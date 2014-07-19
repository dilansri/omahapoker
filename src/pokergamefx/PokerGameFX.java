/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pokergamefx;

import com.xfinity.poker.Dealer;
import static com.xfinity.poker.GameRules.GAME_PLAYERS;
import com.xfinity.poker.Player;
import com.xfinity.poker.Table;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
    
    @Override
    public void start(final Stage stage) throws Exception {
        //Parent root = FXMLLoader.load(getClass().getResource("FXMLTable.fxml"));
        
        createPlayers();
        createTable();
        createDealer();
        tableControl = new TableControl(dealer);
        Scene scene = new Scene(tableControl);
        stage.setScene(scene);
        stage.show();
        tableControl.startGame();
        
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
        singlePlayer = new Player("Single Player",0);
		singlePlayer.setDealer(true);
		
		computerPlayers = new ArrayList<Player>();
		for(int i=1;i<GAME_PLAYERS;i++)
			computerPlayers.add(new Player("CP"+i,i));
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
