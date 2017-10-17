package edu.utah.cs4530.battleship;

import android.content.Context;
import android.provider.ContactsContract;
import android.renderscript.ScriptGroup;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Robert on 10/10/2016.
 */

public class DataModel {

    public interface GameUpdate{
        public void updateGame(int[][] playerBoard, int[][] opponentBoard);
        public void requestLoadGame(String id);
        public void requestNewGame();
        public void updateList();
    }

    GameUpdate updater = null;

    public GameUpdate getUpdate(){
        return updater;
    }

    public void setUpdater(GameUpdate updater){
        this.updater = updater;
    }

    private static DataModel dataModel = null;
    private Game currentGame;
    private ArrayList<Game> games = new ArrayList<Game>();
    public ArrayList<String> gamesList = new ArrayList<String>();
    private String activeGame;
    private Context context = null;

    public int[][] getPlayerBoard(){
        return currentGame.playerBoard;
    }

    public int[][] getOpponentBoard(){
        return currentGame.opponentBoard;
    }

    public Game getCurrentGame(){
        return currentGame;
    }

    public void setCurrentGame(Game currentGame){
        this.currentGame = currentGame;
    }

    private DataModel(Context context){
        this.context = context;
    }

    public static DataModel getInstance(){
        if(dataModel != null){
            return dataModel;
        }
        else{
            Log.i("DATAMODEL", "Problem happened");
            return null;
        }
    }

    public static DataModel getInstance(Context context){
        if(dataModel == null){
            dataModel = new DataModel(context);
        }

        return dataModel;
    }

    private DataModel(){

    }

    public Context getContext(){
        return context;
    }

    public ArrayList<String> getGamesList(){
        return gamesList;
    }

    public void setGameslist(ArrayList<String> gamesList){
        this.gamesList = gamesList;
    }

    public void clearGamesList(){
        gamesList.clear();
    }

    public void requestNewGame(){
        createNewGame();
        updater.requestNewGame();
    }

    public void requestLoadGame(int position){
        Game requestedGame = games.get(position);
        setCurrentGame(requestedGame);
        updater.requestLoadGame("Whatever");
    }

    public void createNewGame(){
        int[][] playerBoard = new int[6][5];
        int[][] opponentBoard = new int[6][5];

        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 5; j++){
                playerBoard[i][j] = 0;
                opponentBoard[i][j] = 0;
            }
        }

        playerBoard = fillNewGameBoard(playerBoard);
        opponentBoard = fillNewGameBoard(opponentBoard);



        Game newGame = new Game(playerBoard, opponentBoard, "" + gamesList.size());

        setCurrentGame(newGame);

        games.add(newGame);

        gamesList.add(newGame.getName());


    }

    public int[][] fillNewGameBoard(int[][] gameBoard){
        int[] shipSizes = {5, 4, 3, 3, 2};

        //String gameName = gamesList.get(gamesList.size()-1);

        for(int j = 0; j < 5; j++){

            int currentShip = shipSizes[j];

            //Log.i("createNewGame", "Current Ship: " + currentShip);

            boolean shipNotPlaced = true;

            while(shipNotPlaced) {

                Random orientaionGenerator = new Random();
                int orientation = orientaionGenerator.nextInt(2);

                //Log.i("createNewGame", "Row or Column Value: " + orientation);

                if (orientation == 0) {
                    Random rowGenerator = new Random();
                    int rowOrientation = rowGenerator.nextInt(6);
                    boolean placeShips = true;
                    //Log.i("createNewGame", "Row Value: " + rowOrientation);

                    for(int k = 0; k < currentShip; k++){
                        if(gameBoard[rowOrientation][k] != 0){
                            placeShips = false;
                            break;
                        }
                    }

                    if(placeShips) {
                        for (int l = 0; l < currentShip; l++) {
                            gameBoard[rowOrientation][l] = 1;
                            shipNotPlaced = false;
                        }
                    }



                } else {
                    Random colGenerator = new Random();
                    int colOrientation = colGenerator.nextInt(5);
                    boolean placeShips = true;
                    //Log.i("createNewGame", "Column Value: " + colOrientation);

                    for(int m = 0; m < currentShip; m++){
                        if(gameBoard[m][colOrientation] != 0){
                            placeShips = false;
                            break;
                        }
                    }

                    if(placeShips) {
                        for (int n = 0; n < currentShip; n++) {
                            gameBoard[n][colOrientation] = 1;
                            shipNotPlaced = false;
                        }
                    }


                }
            }

        }

        String board = "";

        for(int i = 0; i < 6; i++){
            board = "";
            for(int j = 0; j < 5; j++){
                board = board + gameBoard[i][j];
            }
            Log.i("onCreateGame", board);
        }

        return gameBoard;
    }

    public void writeFile(){

        Log.i("writeFile", "Made it in");
        try{
            OutputStreamWriter writer = new OutputStreamWriter(getContext().openFileOutput("battle2.txt", MODE_PRIVATE));
            //Log.i("writeFile", "Made it in");
            for(Game game : games){
                //Log.i("WRITE FILE", "Drawing Count: "+ drawing.getStrokeCount());
                writer.append(game.getName() + " ");

                if(game.getGameWon()){
                    writer.append("" + 1 + " ");
                }
                else{
                    writer.append("" + 0 + " ");
                }

                if(game.getCurrentPlayer()){
                    writer.append("" + 1 + " ");
                }
                else{
                    writer.append("" + 0 + " ");
                }

                int[][] playerBoard = game.getPlayerBoard();
                int[][] opponentBoard = game.getOpponentBoard();

                for(int i = 0; i < 6; i++){
                    for(int j = 0; j < 5; j++) {
                        writer.append("" + playerBoard[i][j] + " ");
                    }
                }

                for(int k = 0; k < 6; k++){
                    for(int l = 0; l < 5; l++) {
                        writer.append("" + opponentBoard[k][l] + " ");
                    }
                }

                writer.append("\n");
                writer.flush();
            }

            writer.close();
        }
        catch(Exception e){
            Log.i("WRITE FILE EXECPTION", "BAD");
        }
    }

    public void readFile(){
        Log.i("READ FILE", "MADE IT IN 1");

        try{
            InputStream inputStream = null;
            try{
                inputStream = getContext().openFileInput("battle2.txt");
            }
            catch (FileNotFoundException e){
                Log.i("READ FILE", "Problem");
                gamesList.add("NEW GAME");
                updater.updateList();
                return;
            }

           // Log.i("READ FILE", "MADE IT IN 2");
            Scanner scanner = new Scanner(inputStream);

            while(scanner.hasNextLine()){
               // Log.i("READ FILE", "MADE IT IN First Scanner");
                String line = scanner.nextLine();

                //Log.i("READ FILE: ", line);

                Scanner scan = new Scanner(line);

                String gameName = scan.next();
                gamesList.add(gameName);

                //Log.i("READ FILE get getName: ", gameName);

                int gameWonVal = Integer.parseInt(scan.next());
                int currentPlayerVal = Integer.parseInt(scan.next());

               // Log.i("READ FILE gameWonVal: ", "" + gameWonVal);
                //Log.i("READ FILE curPlyVal: ", "" + currentPlayerVal);

                boolean gameWon;
                boolean currentPlayer;

                int[][] playerBoard = new int[6][5];
                int[][] opponentBoard = new int[6][5];

                if(gameWonVal == 1){
                    gameWon = true;
                }
                else{
                    gameWon = false;
                }

                if(currentPlayerVal == 1){
                    currentPlayer = true;
                }
                else{
                    currentPlayer = false;
                }

                int r = 0;
                int c = 0;

                boolean playerRead = true;

                while(scan.hasNext()){

                    if(playerRead){
                        playerBoard[r][c] = scan.nextInt();
                        //Log.i("READ FILE boardplVal: ", "" + playerBoard[r][c]);
                    }
                    else{
                        opponentBoard[r][c] = scan.nextInt();
                       // Log.i("READ FILE boardopVal: ", "" + opponentBoard[r][c]);
                    }


                   //// Log.i("READ FILE r: ", "" + r);
                    //Log.i("READ FILE c: ", "" + c);
                    if(r == 5 && c == 4){
                        playerRead = false;
                    }

                    if(c == 4){
                        if(!playerRead){
                            r = 0;
                            c = 0;
                        }
                        else{
                            r++;
                            c = 0;
                        }
                    }

                    c++;


                }

                Game addGame = new Game(playerBoard, opponentBoard, gameName);
                addGame.setCurrentPlayer(currentPlayer);
                addGame.setGameWon(gameWon);

                //Log.i("READ FILE", "MADE IT IN 3");
                games.add(addGame);
                //Log.i("READ FILE", "MADE IT IN 4");

                updater.updateList();
            }
        }
        catch(Exception e){
            Log.i("READ FILE", "Other issue");
        }

        //updater.updateList();

    }

}
