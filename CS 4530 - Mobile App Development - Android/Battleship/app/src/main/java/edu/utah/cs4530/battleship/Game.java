package edu.utah.cs4530.battleship;

/**
 * Created by Robert on 10/25/2016.
 */

public class Game {

    public String name;
    public boolean gameWon;
    public String winner;
    public boolean currentPlayer;
    public int[][] playerBoard;
    public int[][] opponentBoard;

    public Game(int[][] playerB, int[][] opponentB, String name){
        playerBoard = playerB;
        opponentBoard = opponentB;
        this.name = name;
        gameWon = false;
        currentPlayer = true;
        winner = "None";
    }

    public int[][] getOpponentBoard() {
        return opponentBoard;
    }

    public void setOpponentBoard(int[][] opponentBoard){
        this.opponentBoard = opponentBoard;
    }

    public int[][] getPlayerBoard() {
        return playerBoard;
    }

    public void setPlayerBoard(int[][] playerBoard){
        this.playerBoard = playerBoard;
    }

    public String getName() {
        return name;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner){
        this.winner = winner;
    }

    public boolean getGameWon(){
        return gameWon;
    }

    public void setGameWon(boolean gameWon){
        this.gameWon = gameWon;
    }

    public boolean getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(boolean currentPlayer) {
        this.currentPlayer = currentPlayer;
    }
}
