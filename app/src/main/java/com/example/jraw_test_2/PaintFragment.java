package com.example.jraw_test_2;

import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class PaintFragment extends Fragment {

    PaintView paintView;
    Button undoButton, clearButton, submitButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_paint, container, false);
        initUI(view);
        return view;
    }

    private void initUI(View view) {
        paintView = (PaintView) view.findViewById(R.id.PaintView);

        undoButton = (Button) view.findViewById(R.id.undoButton);
        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paintView.undo();
            }
        });

        clearButton = (Button) view.findViewById(R.id.clearButton);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paintView.clear();
                paintView.invalidate();
            }
        });

        submitButton = (Button) view.findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: implement paintView.submitDrawing()
                paintView.submitDrawing();
                paintView.clear();
                paintView.invalidate();
                // TODO: display submission confirmation
            }
        });

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


}