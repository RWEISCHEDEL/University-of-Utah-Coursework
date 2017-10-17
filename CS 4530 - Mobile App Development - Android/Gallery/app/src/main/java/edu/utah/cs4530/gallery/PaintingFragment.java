package edu.utah.cs4530.gallery;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Robert on 10/5/2016.
 */

public class PaintingFragment extends Fragment {
    public static PaintingFragment newInstance(){
        return new PaintingFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View yellowView = new View(getActivity());
        yellowView.setBackgroundColor(Color.YELLOW);
        return yellowView;
    }
}
