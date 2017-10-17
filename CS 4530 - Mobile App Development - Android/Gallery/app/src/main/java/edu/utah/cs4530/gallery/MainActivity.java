package edu.utah.cs4530.gallery;

import android.graphics.Color;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    public static int MASTER_FRAME_ID = 10;
    public static int DETAIL_FRAME_ID = 11;
    public static String MASTER_FRAGMENT_TAG = "GalleryFragment";
    public static String GALLERY_FRAGMENT_TAG = "GalleryFragment";
    public static String PAINTING_FRAGMENT_TAG = "PaintingFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        LinearLayout rootLayout = new LinearLayout(this);
        setContentView(rootLayout);

        FrameLayout masterLayout = new FrameLayout(this);
        masterLayout.setId(MASTER_FRAME_ID);
        rootLayout.addView(masterLayout, new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1));

        FrameLayout detailLayout = new FrameLayout(this);
        detailLayout.setId(DETAIL_FRAME_ID);
        rootLayout.addView(detailLayout, new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 2));

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        GalleryFragment  galleryFragment = (GalleryFragment)getSupportFragmentManager().findFragmentByTag(GALLERY_FRAGMENT_TAG);
        PaintingFragment  paintingFragment = (PaintingFragment)getSupportFragmentManager().findFragmentByTag(PAINTING_FRAGMENT_TAG);
        if(galleryFragment == null){
            galleryFragment = GalleryFragment.newInstance();
            transaction.add(masterLayout.getId(), galleryFragment, GALLERY_FRAGMENT_TAG);
            paintingFragment = PaintingFragment.newInstance();
            transaction.add(detailLayout.getId(), paintingFragment, PAINTING_FRAGMENT_TAG);
        }
        else{
            transaction.add(masterLayout.getId(), galleryFragment);
            transaction.add(detailLayout.getId(), paintingFragment);
        }

        transaction.commit();

        View greenView = new View(this);
        greenView.setBackgroundColor(Color.GREEN);
        detailLayout.addView(greenView);
    }
}
