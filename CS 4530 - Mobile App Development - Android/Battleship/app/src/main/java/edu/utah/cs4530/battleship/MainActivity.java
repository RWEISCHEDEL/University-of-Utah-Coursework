package edu.utah.cs4530.battleship;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    private LinearLayout rootLayout;
    private LinearLayout gameBoardLayout;
    private boolean isTablet;
    private GameList gameList;
    private GameFragment gameFrag;
    private boolean inGame;
    private boolean requested = false;
    private FrameLayout masterLayout;
    private FrameLayout gameLayout;
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        final FragmentManager fragManager = getSupportFragmentManager();

        final DataModel data = DataModel.getInstance(this);

        data.readFile();

        inGame = false;

        isTablet = (getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;

        data.setUpdater(new DataModel.GameUpdate() {
            @Override
            public void updateGame(int[][] playerBoard, int[][] opponentBoard) {
                gameList.update();
            }

            @Override
            public void requestLoadGame(String id) {
                FrameLayout gameLayout = new FrameLayout(data.getContext());
                gameLayout.setId(11);
                gameBoardLayout.addView(gameLayout, new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1));

                FragmentTransaction gameTransition = getSupportFragmentManager().beginTransaction();

                gameFrag = (GameFragment) getSupportFragmentManager().findFragmentByTag("gameFrag");

                gameFrag = GameFragment.newInstance();

                gameTransition.add(gameLayout.getId(), gameFrag, "gameFrag");

                gameTransition.commit();

                setContentView(gameBoardLayout);
            }

            @Override
            public void requestNewGame() {

                Log.i("IN MAIN", "HITS HERE TOO");
                //callGameBoardActivity();

                FrameLayout gameLayout = new FrameLayout(data.getContext());
                gameLayout.setId(11);
                gameBoardLayout.addView(gameLayout, new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1));

                FragmentTransaction gameTransition = getSupportFragmentManager().beginTransaction();

                gameFrag = (GameFragment) getSupportFragmentManager().findFragmentByTag("gameFrag");

                gameFrag = GameFragment.newInstance();

                gameTransition.add(gameLayout.getId(), gameFrag, "gameFrag");

                gameTransition.commit();

                setContentView(gameBoardLayout);

            }

            @Override
            public void updateList() {
                gameList.update();
            }
        });

        rootLayout = new LinearLayout(this);

        gameBoardLayout = new LinearLayout(this);

        setContentView(rootLayout);

        masterLayout = new FrameLayout(this);
        masterLayout.setId(10);
        rootLayout.addView(masterLayout, new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1));

        transaction = getSupportFragmentManager().beginTransaction();
        gameList = (GameList) getSupportFragmentManager().findFragmentByTag("gameList");

        gameList = GameList.newInstance();

       transaction.add(masterLayout.getId(), gameList, "gameList");

        transaction.commit();
    }
}
