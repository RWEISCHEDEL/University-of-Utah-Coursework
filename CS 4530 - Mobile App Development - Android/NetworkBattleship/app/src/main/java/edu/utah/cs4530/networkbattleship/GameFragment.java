package edu.utah.cs4530.networkbattleship;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Robert on 10/30/2016.
 */

public class GameFragment extends Fragment {
    GameLayout playerGrid;
    GameLayout opponentGrid;
    LinearLayout gridLayout;
    DataModel dataModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        dataModel = DataModel.getInstance();

        gridLayout = new LinearLayout(getActivity());
        gridLayout.setOrientation(LinearLayout.HORIZONTAL);
        gridLayout.setWeightSum(2.0f);

        playerGrid = new GameLayout(getActivity(), true);
        playerGrid.setBackgroundColor(Color.DKGRAY);
        playerGrid.setupTiles(dataModel.getPlayerBoard());

        opponentGrid = new GameLayout(getActivity(), false);
        opponentGrid.setBackgroundColor(Color.DKGRAY);
        opponentGrid.setupTiles(dataModel.getOpponentBoard());

        opponentGrid.setClicked(new GameLayout.Clicked() {
            @Override
            public void tileClicked(int row, int column) {
                dataModel.missileFired(row, column);
            }
        });

        LinearLayout leftSide = new LinearLayout(getActivity());
        leftSide.setOrientation(LinearLayout.VERTICAL);
        leftSide.setBackgroundColor(Color.DKGRAY);

        LinearLayout rightSide = new LinearLayout(getActivity());
        rightSide.setOrientation(LinearLayout.VERTICAL);
        rightSide.setBackgroundColor(Color.DKGRAY);

        TextView playerTextView = new TextView(getActivity());
        playerTextView.setGravity(Gravity.CENTER);
        playerTextView.setTextColor(Color.WHITE);
        playerTextView.setBackgroundColor(Color.DKGRAY);
        playerTextView.setText(" Player Board:");

        TextView opponentTextView = new TextView(getActivity());
        opponentTextView.setGravity(Gravity.CENTER);
        opponentTextView.setTextColor(Color.WHITE);
        opponentTextView.setBackgroundColor(Color.DKGRAY);
        opponentTextView.setText(" Opponent Board:");

        int screenHeight = getResources().getDisplayMetrics().heightPixels;
        int textViewSize = 60;

        leftSide.addView(playerTextView, 0, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, textViewSize));
        leftSide.addView(playerGrid, 1, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, screenHeight - textViewSize));

        rightSide.addView(opponentTextView, 0, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, textViewSize));
        rightSide.addView(opponentGrid, 1, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, screenHeight - textViewSize));

        gridLayout.addView(leftSide, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f));
        gridLayout.addView(rightSide, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f));

        return gridLayout;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public void updateGame(int[][] playerBoard, int[][] opponentBoard) {

        playerGrid.setupTiles(playerBoard);
        opponentGrid.setupTiles(opponentBoard);

        playerGrid.invalidate();
        opponentGrid.invalidate();
    }
}
