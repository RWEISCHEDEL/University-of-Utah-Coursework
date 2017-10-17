package edu.utah.cs4530.painting;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.PointF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements PaintView.OnPolyLineEndedListener, Button.OnClickListener{
    PaintView _paintView = null;
    Gallery _gallery = null;
    int _currentDrawing = 0;
    boolean _runAnimation = true;
    int _currentAnimationStroke = 0;
    Button _backButton = null;
    Button _nextButton = null;
    Button _colorButton = null;
    Button _movieButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _paintView = new PaintView(this);

        if(savedInstanceState != null){
            ArrayList<Drawing> drawings = (ArrayList<Drawing>) savedInstanceState.getSerializable("drawings");
            Log.i("ONCREATE", "Drawing List Size: " + drawings.size());
            Gallery.getInstance().set_drawings(drawings);
            Drawing drawing1 = Gallery.getInstance().getDrawing(0);
            insertDrawing(Gallery.getInstance().get_currentDrawing());
        }
        else{
            Log.i("ONCREATE", "saved instance");
            ArrayList<Drawing> gallery = readFile();

            if(gallery != null){
                Log.i("ONCREATE", "Gallery Count: " + gallery.size());
                Gallery.getInstance().set_drawings(gallery);
                Gallery.getInstance().set_currentDrawing(0);
                insertDrawing(0);
            }
            else{
                Log.i("ONCREATE", "Very first time");
                Gallery.getInstance().addNewDrawing();
                Gallery.getInstance().set_currentDrawing(_currentDrawing);
            }

        }

        LinearLayout rootLayout = new LinearLayout(this);
        rootLayout.setOrientation(LinearLayout.VERTICAL);

        rootLayout.addView(_paintView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 4));

        LinearLayout buttonLayout = new LinearLayout(this);

        _backButton = new Button(this);
        _nextButton = new Button(this);
        _colorButton = new Button(this);
        _movieButton = new Button(this);

        _backButton.setOnClickListener(this);
        _nextButton.setOnClickListener(this);
        _colorButton.setOnClickListener(this);
        _movieButton.setOnClickListener(this);

        _backButton.setTag("back");
        _backButton.setText("<-");
        _nextButton.setTag("next");
        _nextButton.setText("->");
        _colorButton.setTag("color");
        _colorButton.setText("C");
        _movieButton.setTag("movie");
        _movieButton.setText("=>");

        buttonLayout.addView(_backButton);
        buttonLayout.addView(_nextButton);
        buttonLayout.addView(_colorButton);
        buttonLayout.addView(_movieButton);


        rootLayout.addView(buttonLayout, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));

        setContentView(rootLayout);

        _paintView.set_polyLineEndedListener(this);
    }

    @Override
    public void onPolyLineEnded(PointF[] polyLine) {
        Log.i("Activity", "Got that polyline finished!" + polyLine);

        Stroke stroke = new Stroke();
        for(int pointIndex = 0; pointIndex < polyLine.length; pointIndex++){
            PointF polyLinePoint = polyLine[pointIndex];
            float pixelsPerInch = getResources().getDisplayMetrics().density * 160.0f;
            Point strokePoint = new Point(polyLinePoint.x / pixelsPerInch, polyLinePoint.y / pixelsPerInch);
            stroke.addPoint(strokePoint);
        }

        stroke.set_color(Gallery.getInstance().getCurrentSelectedColor());

        Log.i("onPlolyLineFinished", "Add ths stroke.");

        Gallery.getInstance().addStrokeToDrawing(_currentDrawing, stroke);

        Log.i("onPolyLineEnded", "Stoke Count: " + Gallery.getInstance().getDrawing(_currentDrawing).getStrokeCount());

        _paintView.invalidate();
    }

    public void insertDrawing(int drawingIndex){
        _paintView.emptyView();
        Drawing drawing = Gallery.getInstance().getDrawing(drawingIndex);

        float pixelsPerInch = getResources().getDisplayMetrics().density * 160.0f;

        for(int strokeIndex = 0; strokeIndex < drawing.getStrokeCount(); strokeIndex++){
            Stroke stroke = drawing.getStroke(strokeIndex);
            PointF[] polyLine = new PointF[stroke.getPointCount()];

            for(int pointIndex = 0; pointIndex < stroke.getPointCount(); pointIndex++) {
                Point strokePoint = stroke.getPoint(pointIndex);
                PointF polyLinePoint = new PointF();
                polyLinePoint.x = strokePoint.x * pixelsPerInch;
                polyLinePoint.y = strokePoint.y * pixelsPerInch;
                polyLine[pointIndex] = polyLinePoint;
            }

            _paintView.set_currentDrawingView(_currentDrawing);

            _paintView.addPolyLine(polyLine);
        }
    }

    public void animateDrawing(int drawingIndex, String strokeString){
        int animationStroke = Integer.parseInt(strokeString);
        _paintView.emptyView();
        Drawing drawing = Gallery.getInstance().getDrawing(drawingIndex);

        float pixelsPerInch = getResources().getDisplayMetrics().density * 160.0f;

        _paintView.set_currentDrawingView(_currentDrawing);

        for(int strokeIndex = 0; strokeIndex < animationStroke; strokeIndex++){
            Stroke stroke = drawing.getStroke(strokeIndex);
            PointF[] polyLine = new PointF[stroke.getPointCount()];

            for(int pointIndex = 0; pointIndex < stroke.getPointCount(); pointIndex++) {
                Point strokePoint = stroke.getPoint(pointIndex);
                PointF polyLinePoint = new PointF();
                polyLinePoint.x = strokePoint.x * pixelsPerInch;
                polyLinePoint.y = strokePoint.y * pixelsPerInch;
                polyLine[pointIndex] = polyLinePoint;
            }

            _paintView.addPolyLine(polyLine);

            _currentAnimationStroke++;




        }
    }

    @Override
    public void onClick(View view) {
        if(view.getTag().equals("back")){
            Log.i("Back", "Touched Back");
            if(_currentDrawing > 0){
                Log.i("Back", "Went into if statement");
                _currentDrawing--;
                Gallery.getInstance().set_currentDrawing(_currentDrawing);
                Log.i("Back", "Looking at: " + _currentDrawing);
                insertDrawing(_currentDrawing);
            }
        }
        else if(view.getTag().equals("next")){
            Log.i("Next", "Touched Next");
            if(_currentDrawing == Gallery.getInstance().getDrawingCount() - 1){
                Log.i("Next", "New Drawing");
                Gallery.getInstance().addNewDrawing();
                _currentDrawing++;
                Gallery.getInstance().set_currentDrawing(_currentDrawing);
                insertDrawing(_currentDrawing);
                Log.i("Next", "Looking at: " + _currentDrawing);
            }
            else{
                _currentDrawing++;
                Log.i("Next", "Looking at: " + _currentDrawing);
                insertDrawing(_currentDrawing);
            }
        }
        else if(view.getTag().equals("color")){
            Intent intent = new Intent(this, ColorControlActivity.class);
            startActivity(intent);
        }
        else{
            ValueAnimator animator = ValueAnimator.ofInt(0, Gallery.getInstance().getDrawing(_currentDrawing).getStrokeCount());
            animator.setDuration(5000);
            animator.addListener(new ValueAnimator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    Log.i("CANCEL", "Animiation revert");
                    _movieButton.setText("=>");
                    insertDrawing(_currentDrawing);
                    _backButton.setEnabled(true);
                    _nextButton.setEnabled(true);
                    _colorButton.setEnabled(true);
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    Log.i("UPDATE", valueAnimator.getAnimatedValue().toString());
                    if(_runAnimation != true){
                        animateDrawing(_currentDrawing, valueAnimator.getAnimatedValue().toString());
                    }

                }
            });

            if(_runAnimation){
                _runAnimation = false;
                _movieButton.setText("[]");
                _backButton.setEnabled(false);
                _nextButton.setEnabled(false);
                _colorButton.setEnabled(false);
                animator.start();


            }
            else{
                Log.i("Stop", "Animiation stop");
                _runAnimation = true;
                animator.end();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        ArrayList<Drawing> drawings = (ArrayList<Drawing>) Gallery.getInstance().get_drawings();

        outState.putSerializable("drawings", drawings);

        writeFile(drawings);

        super.onSaveInstanceState(outState);

    }

    public void writeFile(ArrayList<Drawing> gallery){
        try{
            OutputStreamWriter writer = new OutputStreamWriter(openFileOutput("gallery.txt", MODE_PRIVATE));
            for(Drawing drawing : gallery){
                Log.i("WRITE FILE", "Drawing Count: "+ drawing.getStrokeCount());
                for(int strokeIndex = 0; strokeIndex < drawing.getStrokeCount(); strokeIndex++){
                    Stroke stroke = drawing.getStroke(strokeIndex);
                    int color = drawing.getStroke(strokeIndex).get_color();
                    writer.append(color + " ");
                    for(int pointIndex = 0; pointIndex < stroke.getPointCount(); pointIndex++){
                        Point point = stroke.getPoint(pointIndex);
                        writer.append(point.x + " " + point.y + " ");
                    }

                    writer.append("\n");
                }
                writer.append("ND\n");
                writer.flush();
            }

            writer.close();
        }
        catch(Exception e){
            Log.i("WRITE FILE EXECPTION", "BAD");
        }
    }

    public ArrayList<Drawing> readFile(){
        Log.i("READ FILE", "MADE IT IN");
        ArrayList<Drawing> drawings = new ArrayList<Drawing>();
        try{
            InputStream inputStream = openFileInput("gallery.txt");
            Scanner scanner = new Scanner(inputStream);
            Drawing current = new Drawing();

            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                Log.i("READ LINE", line);
                if (line.equals("ND")) {
                    drawings.add(current);
                    current = new Drawing();
                    continue;
                }

                Scanner scan = new Scanner(line);

                Stroke stroke = new Stroke();
                int color = scan.nextInt();
                stroke.set_color(color);

                while(scan.hasNext()){
                    float x = scan.nextFloat();
                    float y = scan.nextFloat();
                    Point p = new Point(x, y);
                    stroke.addPoint(p);
                }

                current.addStroke(stroke);
            }
        }
        catch(Exception e){
            Log.i("READ FILE", e.toString());
            e.printStackTrace();
            return null;
        }

        return drawings;
    }


}
