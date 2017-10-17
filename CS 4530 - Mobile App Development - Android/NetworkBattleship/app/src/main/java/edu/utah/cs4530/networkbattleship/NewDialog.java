package edu.utah.cs4530.networkbattleship;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;
import android.widget.LinearLayout;

/**
 * Created by Robert on 10/30/2016.
 */

public class NewDialog extends DialogFragment {

    public interface NewGameListener {
        public void newGame(String name);
    }

    NewGameListener newGameListener = null;

    public void setNewGame(NewGameListener newGame) {
        this.newGameListener = newGame;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder newBuilder = new AlertDialog.Builder(getActivity());

        LinearLayout rootLayout = new LinearLayout(getActivity());
        final EditText text = new EditText(getActivity());
        rootLayout.addView(text);

        newBuilder.setTitle("Game Name");
        newBuilder.setMessage("Choose a name for this game:");
        newBuilder.setView(rootLayout);

        newBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                newGameListener.newGame(text.getText().toString());
            }
        });

        newBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        return newBuilder.create();
    }
}
