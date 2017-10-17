package edu.utah.cs4530.battleship;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
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
 * Created by Robert on 10/10/2016.
 */

public class GameList extends Fragment{
    private ListAdapter listAdapter;
    private DataModel data;
    //private ArrayList<String> gamesList;
    private LinearLayout layout;
    private ListView listView;

    private ArrayList<String> testList;

    public static GameList newInstance(){
        return new GameList();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boolean tablet = (getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;

        data = DataModel.getInstance();

        //gamesList = data.getGames();

        testList = new ArrayList<String>();

        layout = new LinearLayout(getActivity());

        layout.setOrientation(LinearLayout.VERTICAL);

        listView = new ListView(getActivity());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    data.requestNewGame();
                    Log.i("GAMELIST", "Postion Value: " + position);
                }
                else{
                    data.requestLoadGame(position - 1);
                    Log.i("GAMELIST", "Postion Value: " + position);
                }
            }
        });

        listAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, data.getGamesList());

        listView.setAdapter(listAdapter);

        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int height = metrics.heightPixels;

        //layout.addView(theGroup, 0, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 180));
        layout.addView(listView, 0, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height - 180));

        //update();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return layout;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public void update(){
        ArrayList<String> temp = data.getGamesList();
        data.gamesList.clear();
        data.gamesList.add("NEW GAME");
        for(String s : temp){
            data.gamesList.add(s);
    }

        listView.invalidateViews();
    }
}
