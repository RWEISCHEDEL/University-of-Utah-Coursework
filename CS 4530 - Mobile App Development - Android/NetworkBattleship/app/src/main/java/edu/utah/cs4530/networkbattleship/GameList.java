package edu.utah.cs4530.networkbattleship;

import android.app.Fragment;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;

import java.util.ArrayList;

/**
 * Created by Robert on 10/30/2016.
 */

public class GameList extends Fragment {

    private DataModel dataModel;
    private ListAdapter adapter;
    private ArrayList<String> gamesList;
    private LinearLayout listLayout;
    private ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dataModel = DataModel.getInstance();

        gamesList = dataModel.getGames();

        listLayout = new LinearLayout(getActivity());
        listLayout.setOrientation(LinearLayout.VERTICAL);

        listView = new ListView(getActivity());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    dataModel.requestNewGame();
                }
                else{
                    dataModel.requestLoadGame(position - 1);
                }
            }
        });

        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, gamesList);

        listView.setAdapter(adapter);

        final RadioButton waitingButton = new RadioButton(getActivity());
        waitingButton.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 60));
        waitingButton.setText("WAITING");

        final RadioButton playingButton = new RadioButton(getActivity());
        playingButton.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 60));
        playingButton.setText("PLAYING");

        final RadioButton doneButton = new RadioButton(getActivity());
        doneButton.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 60));
        doneButton.setText("DONE");

        waitingButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean selected) {
                if (selected) {
                    if (playingButton.isChecked()){
                        playingButton.setChecked(false);
                    }
                    else if (doneButton.isChecked()){
                        doneButton.setChecked(false);
                    }
                }
                dataModel.checkChanged(0);
            }
        });

        playingButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean selected) {
                if (selected) {
                    if (waitingButton.isChecked()){
                        waitingButton.setChecked(false);
                    }
                    else if (doneButton.isChecked()){
                        doneButton.setChecked(false);
                    }
                }
                dataModel.checkChanged(1);
            }
        });

        doneButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean selected) {
                if (selected) {
                    if (waitingButton.isChecked()){
                        waitingButton.setChecked(false);
                    }
                    else if (playingButton.isChecked()){
                        playingButton.setChecked(false);
                    }
                }
                dataModel.checkChanged(2);
            }
        });

        LinearLayout buttonLayout = new LinearLayout(getActivity());
        buttonLayout.setOrientation(LinearLayout.VERTICAL);
        buttonLayout.setPadding(0, 0, 0, 0);
        buttonLayout.addView(waitingButton, 0);
        buttonLayout.addView(playingButton, 1);
        buttonLayout.addView(doneButton, 2);

        waitingButton.setChecked(true);

        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int height = metrics.heightPixels;

        listLayout.addView(buttonLayout, 0, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 180));
        listLayout.addView(listView, 1, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height - 180));

        update();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return listLayout;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public void update() {

        ArrayList<String> games = dataModel.getGames();
        gamesList.clear();
        gamesList.add("MAKE NEW GAME");

        for (String game : games) {
            gamesList.add(game);
        }

        listView.invalidateViews();
    }
}
