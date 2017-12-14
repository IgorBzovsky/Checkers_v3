package com.example.igor7.checkers_v3.activities;

import android.widget.ImageView;

import com.example.igor7.checkers_v3.common.DiskType;
import com.example.igor7.checkers_v3.common.Point;

public interface IGameContract {
    interface View {
        void initializeGameBoard(DiskType[][] gameBoard);
        void setPresenter(Presenter presenter);
        ImageView[][] getDisks();

        //Highlighting
        void highlightSelection(int row, int column);
        void highlightMove(int row, int column, DiskType diskType);
        void resetSelectionHighlighting();
        void resetSelectionHighlighting(int row, int column);
        void resetMoveHighlighting();
        void resetMoveHighlighting(int row, int column);
        void resetHighlighting();

        void move(Point from, Point to);
    }

    interface Presenter {
        void initialize();
        void click(int row, int column);
    }
}
