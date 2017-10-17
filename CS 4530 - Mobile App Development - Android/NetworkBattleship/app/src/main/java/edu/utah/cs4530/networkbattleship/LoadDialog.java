package edu.utah.cs4530.networkbattleship;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;
import android.widget.LinearLayout;

/**
 * Created by Robert on 10/30/2016.
 */

public class LoadDialog extends DialogFragment {
    public interface DialogListener {
        public void load();
    }

    DialogListener dialogListener = null;

    public void setDialogListener(DialogListener dialogListener) {
        this.dialogListener = dialogListener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder loadBuilder = new AlertDialog.Builder(getActivity());

        LinearLayout rootLayout = new LinearLayout(getActivity());

        loadBuilder.setMessage("Do you want to load this game?");
        loadBuilder.setView(rootLayout);

        loadBuilder.setPositiveButton("Load", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (dialogListener != null){
                    dialogListener.load();
                }
            }
        });

        loadBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        return loadBuilder.create();
    }
}
