package edu.utah.cs4530.battleship;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.*;
import android.widget.GridView;

/**
 * Created by Robert on 10/23/2016.
 */

public class GameFragment extends Fragment implements View.OnClickListener{

    private DataModel data = null;
    private Button[][] playerButtonBoard = null;
    private Button[][] opponentButtonBoard = null;
    private boolean firstStart = true;
    LinearLayout rootLayout;
    GridLayout playerGrid;
    GridLayout opponentGrid;

    public static GameFragment newInstance(){
        return new GameFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);

        data = DataModel.getInstance();

        playerButtonBoard = new Button[6][5];
        opponentButtonBoard = new Button[6][5];

        Log.i("GAMEFRAGMENT", "Made it in");

        rootLayout = new LinearLayout(getActivity());

        rootLayout.setOrientation(LinearLayout.VERTICAL);

        playerGrid = new GridLayout(getActivity());
        opponentGrid = new GridLayout(getActivity());

        playerGrid.setRowCount(6);
        playerGrid.setColumnCount(5);

        opponentGrid.setRowCount(6);
        opponentGrid.setColumnCount(5);

        playerGrid.setPadding(0, 0, 0, 0);
        opponentGrid.setPadding(0, 0, 0, 0);

        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 5; j++){
                Button player = new Button(getActivity());
                Button opponent = new Button(getActivity());
                player.setTag("" + i + "" + j);
                playerButtonBoard[i][j] = player;
                opponent.setPadding(0, 0, 0, 0);
                opponent.setGravity(0);
                opponent.setOnClickListener(this);
                opponent.setTag("" + i + "" + j);
                opponentButtonBoard[i][j] = opponent;
                playerGrid.addView(player, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                opponentGrid.addView(opponent, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            }
        }

        rootLayout.addView(opponentGrid, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));


        TextView leftBmp = new TextView(getActivity());
        leftBmp.setGravity(Gravity.CENTER);
        leftBmp.setTextColor(Color.WHITE);
        leftBmp.setText("Player Board Down - Opponent Board Up");
        leftBmp.setBackgroundColor(Color.DKGRAY);

        int textViewHeight = 60;

        rootLayout.addView(leftBmp, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        rootLayout.addView(playerGrid, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        setUpGameBoard();

        data.writeFile();

        return rootLayout;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onClick(View view) {

        String buttonId = (String)view.getTag();

        char r = buttonId.charAt(0);
        char c = buttonId.charAt(1);
        //char d = buttonId.charAt(2);

        int i = (int)r - 48;
        int j = (int)c - 48;

        Log.i("onClick", "Char r Id: " + r);
        Log.i("onClick", "Char c Id: " + c);
        //Log.i("onClick", "Char d Id: " + d);
        Log.i("onClick", "String Id: " + buttonId);

        Log.i("onClick", "Int i Id: " + i);
        Log.i("onClick", "Int j Id: " + j);

        Game currentGame = data.getCurrentGame();

        int[][] playerBoard = currentGame.getPlayerBoard();
        int[][] opponentBoard = currentGame.getOpponentBoard();

        if(currentGame.getCurrentPlayer()){
            if(opponentBoard[i][j] == 0){
                opponentBoard[i][j] = 3;
            }
            else if(opponentBoard[i][j] == 1){
                opponentBoard[i][j] = 2;
            }
            else if(opponentBoard[i][j] == 2){
                opponentBoard[i][j] = 2;
            }
            else{
                opponentBoard[i][j] = 3;
            }
        }
        else{
            if(playerBoard[i][j] == 0){
                playerBoard[i][j] = 3;
            }
            else if(playerBoard[i][j] == 1){
                playerBoard[i][j] = 2;
            }
            else if(playerBoard[i][j] == 2){
                playerBoard[i][j] = 2;
            }
            else{
                playerBoard[i][j] = 3;
            }
        }

        currentGame.setOpponentBoard(opponentBoard);
        currentGame.setPlayerBoard(playerBoard);

        data.writeFile();

        playerSwitch();

        if(currentGame.getGameWon()){
            AlertDialog.Builder gameWon = new AlertDialog.Builder(getActivity());
            String gameWonMsg = "Winner!";
            gameWon.setPositiveButton(gameWonMsg, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            gameWon.show();
            gameWon.setView(LinearLayout.LayoutParams.MATCH_PARENT);
        }
        else{
                Log.i("MADE ", "IT");
                AlertDialog.Builder changePlayers = new AlertDialog.Builder(getActivity());
                String changePlayersMsg = "Your Turn Has Ended. Click To Continue.";
                changePlayers.setPositiveButton(changePlayersMsg, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        setUpGameBoard();
                    }
                });
                changePlayers.show();
                changePlayers.setView(LinearLayout.LayoutParams.MATCH_PARENT);

        }

        //setUpGameBoard();

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void setUpGameBoard(){

        Game currentGame = data.getCurrentGame();

        int[][] playerBoard = currentGame.getPlayerBoard();
        int[][] opponentBoard = currentGame.getOpponentBoard();

        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 5; j++){
                if(currentGame.getCurrentPlayer()){
                    if(playerBoard[i][j] == 0){
                        playerButtonBoard[i][j].setBackgroundColor(Color.BLUE);
                    }
                    else if(playerBoard[i][j] == 1){
                        playerButtonBoard[i][j].setBackgroundColor(Color.BLACK);
                    }
                    else if(playerBoard[i][j] == 2){
                        playerButtonBoard[i][j].setBackgroundColor(Color.RED);
                    }
                    else{
                        playerButtonBoard[i][j].setBackgroundColor(Color.WHITE);
                    }

                    if(opponentBoard[i][j] == 0){
                        opponentButtonBoard[i][j].setBackgroundColor(Color.BLUE);
                    }
                    else if(opponentBoard[i][j] == 1){
                        opponentButtonBoard[i][j].setBackgroundColor(Color.BLUE);
                    }
                    else if(opponentBoard[i][j] == 2){
                        opponentButtonBoard[i][j].setBackgroundColor(Color.RED);
                    }
                    else{
                        opponentButtonBoard[i][j].setBackgroundColor(Color.WHITE);
                    }
                }
                else{
                    if(playerBoard[i][j] == 0){
                        opponentButtonBoard[i][j].setBackgroundColor(Color.BLUE);
                    }
                    else if(playerBoard[i][j] == 1){
                        opponentButtonBoard[i][j].setBackgroundColor(Color.BLUE);
                    }
                    else if(playerBoard[i][j] == 2){
                        opponentButtonBoard[i][j].setBackgroundColor(Color.RED);
                    }
                    else{
                        opponentButtonBoard[i][j].setBackgroundColor(Color.WHITE);
                    }

                    if(opponentBoard[i][j] == 0){
                        playerButtonBoard[i][j].setBackgroundColor(Color.BLUE);
                    }
                    else if(opponentBoard[i][j] == 1){
                        playerButtonBoard[i][j].setBackgroundColor(Color.BLACK);
                    }
                    else if(opponentBoard[i][j] == 2){
                        playerButtonBoard[i][j].setBackgroundColor(Color.RED);
                    }
                    else{
                        playerButtonBoard[i][j].setBackgroundColor(Color.WHITE);
                    }
                }
            }
        }

        if(currentGame.getCurrentPlayer()){
            currentGame.setCurrentPlayer(false);
        }
        else{
            currentGame.setCurrentPlayer(true);
        }

    }

    public void playerSwitch(){
        for(int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                playerButtonBoard[i][j].setBackgroundColor(Color.BLACK);
                opponentButtonBoard[i][j].setBackgroundColor(Color.BLACK);
            }
        }
    }

    public boolean winnerCheck(){
        Game currentGame = data.getCurrentGame();

        int[][] playerBoard = currentGame.getPlayerBoard();
        int[][] opponentBoard = currentGame.getOpponentBoard();

        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 5; j++){
                if(currentGame.getCurrentPlayer()){
                    if(opponentBoard[i][j] == 1){
                        return false;
                    }
                }
                else{
                    if(playerBoard[i][j] == 1){
                        return false;
                    }
                }
            }
        }

        return true;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }
}
