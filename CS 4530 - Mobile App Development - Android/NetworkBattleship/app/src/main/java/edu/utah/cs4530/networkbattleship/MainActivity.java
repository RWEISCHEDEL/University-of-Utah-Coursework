package edu.utah.cs4530.networkbattleship;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends FragmentActivity {

    private boolean isTablet;
    private LinearLayout rootLayout;
    private GameList gameList;
    private GameFragment gameFrag;
    private boolean inGame;
    private boolean requestLoad = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final FragmentManager manager = getFragmentManager();

        final DataModel dataModel = DataModel.getInstance(this);

        inGame = false;

        isTablet = (getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK) >=
                Configuration.SCREENLAYOUT_SIZE_LARGE;

        dataModel.setUpdater(new DataModel.GameUpdater() {

            @Override
            public void updateList() {
                gameList.update();
            }

            @Override
            public void requestLoadGame(final String id) {
                LoadDialog load = new LoadDialog();
                load.setDialogListener(new LoadDialog.DialogListener() {
                    @Override
                    public void load() {
                        requestLoad = true;
                        dataModel.loadGame(id);
                    }
                });
                load.show(getFragmentManager(), "Hi");
            }

            @Override
            public void updateGame(int[][] playerBoard, int[][] oppBoard) {
                if (!isTablet && requestLoad) {
                    gameFrag = (GameFragment) getFragmentManager().findFragmentByTag("gamePlay");
                    if (gameFrag != null){
                        gameFrag.updateGame(playerBoard, oppBoard);
                    }
                    if (gameFrag == null){
                        gameFrag = new GameFragment();
                    }
                    FragmentTransaction trans = manager.beginTransaction();
                    trans.replace(rootLayout.getChildAt(0).getId(), gameFrag, "gamePlay");
                    inGame = true;
                    trans.commit();
                    requestLoad = false;
                } else {
                    gameFrag.updateGame(playerBoard, oppBoard);
                }
            }

            @Override
            public void requestNewGame() {

                NewDialog dialog = new NewDialog();
                dialog.setNewGame(new NewDialog.NewGameListener() {
                    @Override
                    public void newGame(String name) {
                        if (name.equals("")){
                            return;
                        }
                        else {
                            dataModel.newGame(name);
                        }
                    }
                });

                dialog.show(getSupportFragmentManager(), "NewGame");
            }

            @Override
            public void sendMessage(String message) {
                AlertDialog.Builder messageBox = new AlertDialog.Builder(MainActivity.this);
                messageBox.setTitle("Battleship Game Status");
                messageBox.setMessage(message);
                messageBox.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                AlertDialog dialogBox = messageBox.create();
                dialogBox.show();
            }
        });

        rootLayout = new LinearLayout(this);

        setContentView(rootLayout);

        if (isTablet) {
            FrameLayout listLayout = new FrameLayout(this);
            listLayout.setId(2);
            FrameLayout gameLayout = new FrameLayout(this);
            gameLayout.setId(3);

            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);

            int width = metrics.widthPixels;

            rootLayout.addView(listLayout, 0, new LinearLayout.LayoutParams(width / 4, LinearLayout.LayoutParams.MATCH_PARENT));
            rootLayout.addView(gameLayout, 1, new LinearLayout.LayoutParams((width / 4) * 3, LinearLayout.LayoutParams.MATCH_PARENT));
        }
        else {
            FrameLayout mainLayout = new FrameLayout(this);
            mainLayout.setId(1);
            rootLayout.addView(mainLayout);
        }

        gameList = (GameList)getFragmentManager().findFragmentByTag("gameList");

        if (gameList == null){
            gameList = new GameList();
        }

        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(rootLayout.getChildAt(0).getId(), gameList, "gameList");

        if (isTablet) {

            gameFrag = (GameFragment)getFragmentManager().findFragmentByTag("gamePlay");

            if (gameFrag == null){
                gameFrag = new GameFragment();
            }

            transaction.replace(rootLayout.getChildAt(1).getId(), gameFrag, "gamePlay");
        }
        transaction.commit();

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                dataModel.runUpdate();
            }
        }, 500, 500);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (inGame)
            {
                getFragmentManager().popBackStack();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(rootLayout.getChildAt(0).getId(), gameList, "gameList");
                transaction.commit();
                inGame = false;
            }
        }
        return false;
    }
}
