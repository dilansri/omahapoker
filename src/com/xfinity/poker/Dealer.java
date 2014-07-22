package com.xfinity.poker;

import com.xfinity.poker.Player.PlayerAction;
import java.util.ArrayList;
import java.util.List;

public class Dealer implements DealerRules {

    private CardDeck deck;
    private Table table;
	//HighHandCardAnalyser highAnalyser;

    private Round currentRound;

    public enum Round {

        PRE_FLOP, FLOP, TURN, RIVER
    };

    private int dealingPlayerOrder;

    public Dealer() {
        deck = new CardDeck();
        dealingPlayerOrder = 1;
        currentRound = Round.PRE_FLOP;
		//highAnalyser = new HighHandCardAnalyser();

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

    public void dealPreFlop() {

    }

    public void dealFlop() {

    }

    public void dealTurn() {

    }

    public void dealRiver() {

    }

    public void dealToPlayers() {
        List<Player> players = table.getPlayers();
        dealToPlayers(players);
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

    private void deal(Player player) {

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
        if (table.getPlayers().get(i).getPlayerChips() >= HIGHEST_POT_VALUE) {
            possibleActions.add(PlayerAction.ALL_IN);
        }

        //CALL
        if (table.getPlayers().get(i).getPlayerChips() > HIGHEST_POT_VALUE
                && table.getTablePot().getPlayerPots().get(i).getPlayerContribution() < HIGHEST_POT_VALUE) {
            possibleActions.add(PlayerAction.CALL);
        }

        //CHECK
        if (table.getTablePot().getPlayerPots().get(i).getPlayerContribution() == HIGHEST_POT_VALUE
                && table.getPlayers().get(i).getPlayerChips() >= HIGHEST_POT_VALUE) {
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

}
