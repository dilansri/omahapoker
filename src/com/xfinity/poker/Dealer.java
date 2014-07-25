package com.xfinity.poker;

import static com.xfinity.poker.GameRules.GAME_PLAYERS;
import com.xfinity.poker.Player.PlayerAction;
import static com.xfinity.poker.TableRules.NUMBER_OF_FLOP_CARDS;
import java.util.ArrayList;
import java.util.List;

public class Dealer implements DealerRules {

    private CardDeck deck;
    private Table table;
    HighHandCardAnalyser highAnalyser;
    

    private Round currentRound;
    
    private int roundCount;

    

    public enum Round {

        PRE_FLOP, FLOP, TURN, RIVER
    };

    private int dealingPlayerOrder;

    public Dealer() {
        deck = new CardDeck();
       
        dealingPlayerOrder = 1;
        currentRound = Round.PRE_FLOP;
        roundCount = 0;
	highAnalyser = new HighHandCardAnalyser();
    }
    
    public int incrementRoundCount(){
        roundCount++;
        return roundCount;
    }
    
    public void setRoundCount(int count){
        roundCount = count;
    }
    
    public int getRoundCount(){
        return roundCount;
    }

    public Round getRound() {
        return currentRound;
    }

    public void setRound(Round round) {
        this.currentRound = round;
    }

    public void shuffleDeck() {
        deck.shuffle();
    }

    public int getDealingPlayerOrder() {
        return dealingPlayerOrder;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public void collectBlinds() {
        getBigBlind();
        getSmallBlind();
    }

    private void getSmallBlind() {
        table.takeSmallBlindFromPlayer(dealingPlayerOrder);
    }

    private void getBigBlind() {
        table.takeBigBlindFromPlayer(dealingPlayerOrder + 1);
    }

    public void collectPlayerPots() {

    }

    private Player findHighHandWinner() {
        return null;
    }

    private Player findLowHandWinner() {
        return null;
    }

    public void dealFlop() {
        for(int i=0;i<NUMBER_OF_FLOP_CARDS;i++)
            table.getCommunityCards().add(deck.getTopCard());
    }

    public void dealTurn() {
        table.getCommunityCards().add(deck.getTopCard());
    }

    public void dealRiver() {
        table.getCommunityCards().add(deck.getTopCard());
    }

    public void dealToPlayers() {
        List<Player> players = table.getPlayers();
        dealToPlayers(players);
        
        //TESTING PURPOSES
        
        for(int i=0;i<table.getPlayers().size();i++){
            System.out.println("Player "+i);
            for(int j=0;j<4;j++){
                System.out.print(table.getPlayers().get(i).getPlayerHand().getCards().get(j)+" , ");
            }
            System.out.println("------");
        } 
    }

    public void dealToPlayers(List<Player> players) {

        for (int i = 0, j = dealingPlayerOrder; i < players.size() * PLAYER_HAND_SIZE; i++, j++) {
            players.get(j % players.size()).giveCard(deck.getTopCard());
        }
        //dealingPlayerOrder++;
    }

    public CardDeck getDeck() {
        return deck;
    }

    public void setDeck(CardDeck deck) {
        this.deck = deck;
    }
    
    public PlayerBestHighHand getHighHandWinner(){
        return highAnalyser.getHighHandWinner(table.getPlayers(), table.getCommunityCards());
    }

    public List<Player> getWinners() {

        return null;
    }

    public Table getTable() {
        return table;
    }

    public List<PlayerAction> getPlayerPossibleActions(int i) {
        List<PlayerAction> possibleActions = new ArrayList<>();

        final double HIGHEST_POT_VALUE = table.getHighestPotValue();

        final double RAISE_VALUE = table.getRaiseAmount();

        //FOLD        
        possibleActions.add(PlayerAction.FOLD);

        //ALLIN
        if (table.getPlayers().get(i).getPlayerChips() > 0) {
            possibleActions.add(PlayerAction.ALL_IN);
        }

        //CALL
        if (table.getPlayers().get(i).getPlayerChips() > HIGHEST_POT_VALUE - (table.getTablePot().getPlayerPots().get(i).getPlayerContribution())
                && table.getTablePot().getPlayerPots().get(i).getPlayerContribution() < HIGHEST_POT_VALUE) {
            possibleActions.add(PlayerAction.CALL);
        }

        //CHECK
        if (table.getTablePot().getPlayerPots().get(i).getPlayerContribution() == HIGHEST_POT_VALUE
                && table.getPlayers().get(i).getPlayerChips() > 0) {
            possibleActions.add(PlayerAction.CHECK);
        }

        //RAISE
        if (table.getPlayers().get(i).getPlayerChips() >= RAISE_VALUE) {
            possibleActions.add(PlayerAction.RAISE);
        }

        return possibleActions;
    }

    synchronized public void getCallFrom(int playerPosition) {
        Player player = table.getPlayers().get(playerPosition);
        PlayerPot playerPot = table.getTablePot().getPlayerPots().get(playerPosition);
        double playerPotAmount = playerPot.getPlayerContribution();
        double amount = player.takeChips(table.getHighestPotValue() - playerPotAmount);
        table.getTablePot().getPlayerPots().get(playerPosition).addToPot(amount);
    }

    synchronized public void setFlod(int position) {
        Player player = table.getPlayers().get(position);
        player.setFolded(true);

    }

    synchronized public void getRaiseFrom(int playerPosition) {
        Player player = table.getPlayers().get(playerPosition);
        PlayerPot playerPot = table.getTablePot().getPlayerPots().get(playerPosition);
        double playerPotAmount = playerPot.getPlayerContribution();
        double amount = player.takeChips(table.getRaiseAmount()- playerPotAmount);
        table.getTablePot().getPlayerPots().get(playerPosition).addToPot(amount);
        table.setHighestPotValue(table.getRaiseAmount());
    }
    
    public boolean allOtherFolded(int playerPos) {
        
        for(int i=playerPos+1;i<playerPos+GAME_PLAYERS;i++){
            if(!table.getPlayers().get(playerPos%GAME_PLAYERS).isFolded())
                return false;
        }
        
        return true;
    }

}
