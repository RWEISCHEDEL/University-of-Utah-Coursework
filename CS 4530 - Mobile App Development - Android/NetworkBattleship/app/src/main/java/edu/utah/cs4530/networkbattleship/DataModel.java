package edu.utah.cs4530.networkbattleship;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by Robert on 10/30/2016.
 */

public class DataModel {
    public interface GameUpdater {
        public void updateGame(int[][] playerBoard, int[][] oppBoard);
        public void requestLoadGame(String id);
        public void requestNewGame();
        public void sendMessage(String message);
        public void updateList();
    }

    GameUpdater updater = null;

    public GameUpdater getUpdater() {
        return updater;
    }

    public void setUpdater(GameUpdater updater) {
        this.updater = updater;
    }

    private static DataModel dataModel = null;
    private Context context;
    private int[][] playerBoard = new int[10][10];
    private int[][] opponentBoard = new int[10][10];
    private boolean gameLoaded;
    private ArrayList<Game> waitingGames;
    private ArrayList<Game> playingGames;
    private ArrayList<Game> doneGames;
    public String radioChecked;
    private String currentGameId;
    private String currentPlayerId;
    private Game currentGame;
    private HashMap<String, String> gamesPlaying;
    private boolean myTurn = false;

    private DataModel (Context context) {

        radioChecked = "WAITING";
        this.context = context;
        gameLoaded = false;
        waitingGames = new ArrayList<Game>();
        playingGames = new ArrayList<Game>();
        doneGames = new ArrayList<Game>();
        gamesPlaying = new HashMap<String, String>();

        File file = new File(context.getFilesDir(), "BattleShipGamesList");

        if (file.exists()) {
            try {
                FileReader fileReader = new FileReader(file);
                BufferedReader fileBuffer = new BufferedReader(fileReader);

                String fileLine = "";
                while ((fileLine = fileBuffer.readLine()) != null && fileLine.length() != 0) {
                    Scanner scanner = new Scanner(fileLine);
                    String game = scanner.next();
                    gamesPlaying.put(game, scanner.next());
                }

                fileBuffer.close();
                fileReader.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        NetworkManager manager = new NetworkManager(context, 1, null);
        new Network().execute(manager);
    }

    public static DataModel getInstance(Context context) {
        if (dataModel == null){
            dataModel = new DataModel(context);
        }
        return dataModel;
    }

    public static DataModel getInstance() {
        if (dataModel != null){
            return dataModel;
        }
        else{
            throw new IllegalArgumentException("Tried to create DataModel without a Context.");
        }
    }

    public int[][] getPlayerBoard () {
        return playerBoard;
    }
    public int[][] getOpponentBoard() {
        return opponentBoard;
    }

    private void writeFile() {

        File file = new File(context.getFilesDir(), "BattleShipGamesList");

        if (file.exists()){
            file.delete();
        }

        try {
            FileWriter fileWriter = new FileWriter(file, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            for (String k : gamesPlaying.keySet()) {
                bufferedWriter.append(k + " " + gamesPlaying.get(k) + " \n");
            }

            bufferedWriter.close();
            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getGames() {

        ArrayList<String> gamesList = new ArrayList<String>();
        ArrayList<Game> removeList = new ArrayList<Game>();

        if (radioChecked.equals("WAITING")) {
            for (Game game : waitingGames) {
                if (game != null && game.name != null){
                    gamesList.add(game.name);
                }
                else {
                    removeList.add(game);
                }
            }
            for (Game game : removeList) {
                waitingGames.remove(game);
            }
        }
        else if (radioChecked.equals("PLAYING")) {
            for (Game game : playingGames) {
                if (game != null && game.name != null){
                    gamesList.add(game.name);
                }
                else{
                    removeList.add(game);
                }
            }
            for (Game game : removeList){
                playingGames.remove(game);
            }
        }
        else {
            for (Game game : doneGames) {
                if (game != null && game.name != null){
                    gamesList.add(game.name);
                }
                else{
                    removeList.add(game);
                }
            }
            for (Game game : removeList){
                doneGames.remove(game);
            }
        }
        return gamesList;
    }

    public void requestNewGame() {
        updater.requestNewGame();
    }

    public void newGame(String name) {
        String gameInfo = "{\"gameName\":\"" + name + "\",\"playerName\":\"Robert\"}";
        NetworkManager manager = new NetworkManager(context, 5, gameInfo);
        new Network().execute(manager);
    }

    public void requestLoadGame(int game) {

        if (radioChecked.equals("WAITING")) {
            if (gamesPlaying.containsKey(waitingGames.get(game).id)) {
                updater.sendMessage("Waiting for player to join this game.");
            }
            else {
                updater.requestLoadGame(waitingGames.get(game).id);
            }
        }
        else if (radioChecked.equals("PLAYING")) {
            if (gamesPlaying.containsKey(playingGames.get(game).id)) {
                Log.i("requestLG", "Option 1");
                Log.i("requestLG", playingGames.get(game).id);
                updater.requestLoadGame(playingGames.get(game).id);
            }
            else {
                Log.i("requestLG", "Option 2");
                NetworkManager manager = new NetworkManager(context, 6, "");
                manager.setGameID(playingGames.get(game).id);
                Log.i("requestLG", manager.getGameID());
                new Network().execute(manager);
            }
        }
        else {
            NetworkManager manager = new NetworkManager(context, 6, "");
            manager.setGameID(doneGames.get(game).id);
            new Network().execute(manager);
        }

        updater.updateList();
    }

    public void loadGame(String id) {
        NetworkManager manager;

        if (!gamesPlaying.containsKey(id)) {
            Log.i("DMLoadGME", "if");
            manager = new NetworkManager(context, 0, "{\"playerName\":\"Robert\"}");
            manager.setGameID(id);
            currentGameId = id;
        }
        else {
            Log.i("DMLoadGME", "else");
            currentGame = new Game();
            // Gets GAMEID
            Log.i("DMLoadGME - gameID", id);
            currentGame.gameId = currentGameId = id;
            Log.i("DMLoadGME - playerID", gamesPlaying.get(id));
            currentGame.playerId = currentPlayerId = gamesPlaying.get(id);
            Log.i("DMLoadGME currGame.pID", currentGame.playerId);
            Log.i("DMLoadGME - currpID", currentGame.playerId);

            //manager = new NetworkManager(context, 4, "{\"playerId\":\"" + currentPlayerId + "\"}");
            manager = new NetworkManager(context, 4, currentPlayerId);
            manager.setGameID(currentGameId);
        }

        new Network().execute(manager);
    }

    public void missileFired(int row, int column) {
        String toPack = "{\"playerId\":\"" + currentPlayerId + "\",\"xPos\":\"" + column +
                "\",\"yPos\":\"" + row + "\"}";

        NetworkManager manager = new NetworkManager(context, 2, toPack);
        manager.setGameID(currentGameId);
        manager.playerID = currentPlayerId;

        new Network().execute(manager);
    }

    public void checkChanged(int checkedId) {

        if (checkedId == 1) {
            radioChecked = "PLAYING";
        }
        else if (checkedId == 0) {
            radioChecked = "WAITING";
        }
        else{
            radioChecked = "DONE";
        }

        NetworkManager updateList = new NetworkManager(context, 1, null);
        new Network().execute(updateList);

        updater.updateList();
    }

    public void runUpdate() {
        if (currentGameId != null) {
            NetworkManager manager = new NetworkManager(context, 3, "{\"playerId\":\"" + currentPlayerId + "\"}");
            manager.setGameID(currentGameId);
            new Network().execute(manager);
        }
    }

    public void returnFromServer(NetworkManager manager) {

        if (manager.getReturnInfo().equals("BAD")){
            return;
        }

        switch (manager.getRequestCode()) {
            case 0: joinGame(manager);
                break;
            case 1: getGames(manager);
                break;
            case 2: guess(manager);
                break;
            case 3: whoseTurn(manager);
                break;
            case 4: boards(manager);
                break;
            case 5: createGame(manager);
                break;
            case 6: gameDetail(manager);
                break;
        }
    }

    private void getGames(NetworkManager manager) {

        Gson gson = new Gson();

        Type returnType = new TypeToken<ArrayList<Game>>() {}.getType();
        String returnInfo = manager.getReturnInfo();

        ArrayList<Game> gameList = gson.fromJson(returnInfo, returnType);

        for (Game game : gameList) {
            if (game.status.equals("WAITING")){
                if(!waitingGames.contains(game)){
                    waitingGames.add(game);
                }

            }
            else if (game.status.equals("PLAYING")){
                if(!playingGames.contains(game)){
                    playingGames.add(game);
                }
            }
            else{
                if(!doneGames.contains(game)){
                    doneGames.add(game);
                }
            }
        }

        updater.updateList();
    }

    private void gameDetail (NetworkManager manager) {

        Gson gson = new Gson();
        String returnInfo = manager.getReturnInfo();

        Log.e("msg", returnInfo);

        Game game = gson.fromJson(returnInfo, Game.class);

        updater.sendMessage("Game Name: " + game.name + "\n" +
                "Player One Name: " + game.player1 + "\n" +
                "Player Two Name: " + game.player2 + "\n" +
                "Winner/Status: " + game.winner + "\n" +
                "Missiles Launched: " + game.missilesLaunched);

    }

    private void joinGame (NetworkManager manager) {

        Gson gson = new Gson();
        String returnInfo = manager.getReturnInfo();

        Game game = gson.fromJson(returnInfo, Game.class);
        game.id = manager.getGameID();

        currentGame = game;
        currentPlayerId = game.playerId;
        gamesPlaying.put(game.id, game.playerId);

        writeFile();

        NetworkManager getGames = new NetworkManager(context, 1, null);
        new Network().execute(getGames);

        NetworkManager getBoard = new NetworkManager(context, 4, "{\"playerId\":\"" + game.playerId + "\"}");
        getBoard.setGameID(game.id);
        getBoard.playerID = currentPlayerId;
        new Network().execute(getBoard);
    }

    private void createGame (NetworkManager manager) {

        Gson gson = new Gson();
        String returnInfo = manager.getReturnInfo();

        Log.i("DM - CGame", returnInfo);

        try {
            Game theGame = gson.fromJson(returnInfo, Game.class);
            Log.i("DM - CGame", theGame.gameId);
            Log.i("DM - CGame", theGame.playerId);
            gamesPlaying.put(theGame.gameId, theGame.playerId);

            writeFile();

        } catch (Exception e) {
            e.printStackTrace();
        }

        NetworkManager getGames = new NetworkManager(context, 1, null);
        new Network().execute(getGames);
    }

    private void guess (NetworkManager manager) {

        if (manager.getReturnInfo().contains("Not")){
            updater.sendMessage("It is not your turn.");
        }
        else {
            if (manager.getReturnInfo().contains("true")){
                updater.sendMessage("You hit a opponent Battleship!");
            }
            else{
                updater.sendMessage("You missed.");
            }
        }

        manager = new NetworkManager(context, 4, currentPlayerId);
        manager.setGameID(currentGameId);
        new Network().execute(manager);
    }

    private void whoseTurn(NetworkManager manager) {

        String returnInfo = manager.getReturnInfo();

        if (returnInfo.contains("true")) {
            if (myTurn == false) {
                updater.sendMessage("It's your turn!");
                manager = new NetworkManager(context, 4, "{\"playerId\":\"" + currentPlayerId + "\"}");
                manager.setGameID(currentGameId);
                new Network().execute(manager);
            }
            myTurn = true;
        }
        else {
            if (myTurn == true) {
                myTurn = false;
                manager = new NetworkManager(context, 4, "{\"playerId\":\"" + currentPlayerId + "\"}");
                manager.setGameID(currentGameId);
                new Network().execute(manager);
            }
        }
    }

    private void boards(NetworkManager manager) {

        Gson gson = new Gson();
        String returnInfo = manager.getReturnInfo();

        Log.i("boards 1: ", returnInfo);
        Log.i("boards 2: ", currentGameId);
        //Log.i("boards 3: ", currentPlayerId);
        Log.i("boards 4: ", currentGame.gameId);
        //Log.i("boards 5: ", currentGame.playerId);
        Log.i("boards 2: ", currentGame.player1 + "");
        Log.i("boards 2: ", currentGame.player2 + "");

        int position = returnInfo.indexOf("\"opponentBoard") - 2;
        String player = "[" + returnInfo.substring(16, position) + "]";
        String opponent = "[" + returnInfo.substring(position + 19, returnInfo.length() - 2) + "]";

        Type returnType = new TypeToken<ArrayList<Tile>>() {}.getType();

        ArrayList<Tile> playerBoard = gson.fromJson(player, returnType);
        ArrayList<Tile> opponentBoard = gson.fromJson(opponent, returnType);

        int playerShipCount = 0;
        int playerHitCount = 0;

        int opponentShipCount = 0;
        int opponentHitCount = 0;

        for (Tile tile : playerBoard) {
            if (tile.status.equals("NONE")){
                this.playerBoard[tile.yPos][tile.xPos] = 0;
            }
            else if (tile.status.equals("SHIP")){
                this.playerBoard[tile.yPos][tile.xPos] = 2;
                playerShipCount++;
            }
            else if (tile.status.equals("MISS")){
                this.playerBoard[tile.yPos][tile.xPos] = 1;
            }
            else{
                this.playerBoard[tile.yPos][tile.xPos] = 6;
                playerHitCount++;
            }
        }

        for (Tile tile : opponentBoard) {
            if (tile.status.equals("NONE")){
                this.opponentBoard[tile.yPos][tile.xPos] = 0;
            }
            else if (tile.status.equals("SHIP")){
                this.opponentBoard[tile.yPos][tile.xPos] = 2;
                opponentShipCount++;
            }
            else if (tile.status.equals("MISS")){
                this.opponentBoard[tile.yPos][tile.xPos] = 1;
            }
            else{
                this.opponentBoard[tile.yPos][tile.xPos] = 6;
                opponentHitCount++;
            }
        }

        Log.i("boards ply ships: ", playerShipCount + "");
        Log.i("boards ply hit: ", playerHitCount + "");

        Log.i("boards opp ships: ", opponentShipCount + "");
        Log.i("boards opp hit: ", opponentHitCount + "");

        if(playerHitCount == 17){
            updater.sendMessage("You Lose!");
            NetworkManager updateList = new NetworkManager(context, 1, null);
            new Network().execute(updateList);
        }

        if(opponentHitCount == 17){
            updater.sendMessage("You Win!");
            NetworkManager updateList = new NetworkManager(context, 1, null);
            new Network().execute(updateList);
        }


        updater.updateGame(this.playerBoard, this.opponentBoard);
        updater.updateList();
    }
}
