package com.example.igor7.checkers_v3.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.igor7.checkers_v3.R;
import com.example.igor7.checkers_v3.common.DiskType;
import com.example.igor7.checkers_v3.common.Point;

import java.util.LinkedList;

public class GameBoardFragment extends Fragment implements IGameContract.View {

    //Constants
    //INITIAL_DISK_ID - necessary for dynamic creation of ImageViews in constraint layout
    private static final int INITIAL_DISK_ID = 100;
    private static final int ANIMATION_DURATION = 500;

    private int  diskId = INITIAL_DISK_ID;
    //Prevents initialization game board after restoring of fragment
    private boolean isInitialized = false;

    private LinkedList<ImageView> selectedHighlights = new LinkedList<>();
    private LinkedList<ImageView> moveHighlights = new LinkedList<>();

    //View components
    private GridLayout boardGridLayout;
    private ImageView boardImage;
    private ConstraintLayout disksLayout;
    private ImageView playerWhiteImage;
    private ImageView playerBlackImage;
    private ImageView playerWhiteSandwatch;
    private ImageView playerBlackSandwatch;
    private TextView playerWhiteText;
    private TextView playerBlackText;
    private ImageView[][] cells;
    private ImageView[][] disks;
    private IGameContract.Presenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_game_board, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        boardGridLayout = (GridLayout) view.findViewById(R.id.boardGridLayout);
        boardImage = (ImageView) view.findViewById(R.id.boardImageView);
        disksLayout = (ConstraintLayout) view.findViewById(R.id.disksLayout);
        playerWhiteImage = (ImageView) view.findViewById(R.id.playerWhiteImage);
        playerBlackImage = (ImageView) view.findViewById(R.id.playerBlackImage);
        playerWhiteText = (TextView) view.findViewById(R.id.playerWhiteText);
        playerBlackText = (TextView) view.findViewById(R.id.playerBlackText);
        playerWhiteSandwatch = (ImageView) view.findViewById(R.id.playerWhiteSandwatch);
        playerBlackSandwatch = (ImageView) view.findViewById(R.id.playerBlackSandwatch);
    }

    @Override
    public void onResume() {
        if(!isInitialized) {
            presenter.initialize();
            isInitialized = true;
        }
        super.onResume();
    }



    @Override
    public void initializeGameBoard(DiskType[][] gameBoard) {
        disks = new ImageView[gameBoard.length][];
        cells = new ImageView[gameBoard.length][];

        //Initialize empty game board
        for (int row = 0; row < gameBoard.length; row++) {
            disks[row] = new ImageView[gameBoard[row].length];
            cells[row] = new ImageView[gameBoard[row].length];
            for (int column = 0; column < gameBoard[row].length; column++) {
                //If cell is black create active ImageView
                if((row + column) % 2 == 1) {
                    ImageView cell = createCell(true);
                    cells[row][column] = cell;
                    cell.setOnClickListener(new CellClickListener(row, column));
                    boardGridLayout.addView(cell);
                    //Populate game board
                    if(gameBoard[row][column] != null) {
                        disks[row][column] = createDisk(gameBoard[row][column], row, column);
                    }
                }
                else {
                    ImageView cell = createCell(false);
                    cells[row][column] = cell;
                    boardGridLayout.addView(cell);
                }
            }
        }
    }

    @Override
    public void setPresenter(IGameContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public ImageView[][] getDisks() {
        return disks;
    }

    @Override
    public void highlightSelection(int row, int column) {
        ImageView selectedCell = cells[row][column];
        selectedCell.setBackgroundResource(R.drawable.cell_border_highlight);
        selectedHighlights.add(selectedCell);
    }

    @Override
    public void highlightMove(int row, int column, DiskType diskType) {
        ImageView cell = cells[row][column];
        setCellBackgroundImage(cell, diskType);
        moveHighlights.add(cell);
    }

    @Override
    public void resetSelectionHighlighting() {
        for (ImageView cell : selectedHighlights) {
            cell.setBackgroundColor(Color.TRANSPARENT);
        }
        selectedHighlights.clear();
    }

    @Override
    public void resetSelectionHighlighting(int row, int column) {
        ImageView selectedCell = cells[row][column];
        selectedCell.setBackgroundColor(Color.TRANSPARENT);
        selectedHighlights.remove(selectedCell);
    }

    @Override
    public void resetMoveHighlighting() {
        for (ImageView cell : moveHighlights) {
            cell.setBackgroundColor(Color.TRANSPARENT);
        }
        moveHighlights.clear();
    }

    @Override
    public void resetMoveHighlighting(int row, int column) {
        ImageView cell = cells[row][column];
        cell.setBackgroundColor(Color.TRANSPARENT);
        moveHighlights.remove(cell);
    }

    @Override
    public void resetHighlighting() {
        resetSelectionHighlighting();
        resetMoveHighlighting();
    }

    @Override
    public void move(Point from, Point to) {

    }

    //region Helper methods
    private ImageView createCell(boolean isEnabled) {
        ImageView cell = new ImageView(getContext());
        cell.setSoundEffectsEnabled(false);
        int side = getResources().getDimensionPixelSize(R.dimen.cellSide);
        cell.setLayoutParams(new ViewGroup.LayoutParams(side, side));
        if(!isEnabled) {
            cell.setEnabled(false);
            return cell;
        }
        return cell;
    }


    private class CellClickListener implements View.OnClickListener {
        private int row;
        private int column;

        CellClickListener(int row, int column) {
            this.row = row;
            this.column = column;
        }

        @Override
        public void onClick(View v) {
            presenter.click(row, column);
        }
    }

    private ImageView createDisk(DiskType diskType, int row, int column) {

        int cellSide = getResources().getDimensionPixelSize(R.dimen.cellSide);
        int constraintX = column * cellSide;
        int constraintY = row * cellSide;

        ImageView disk = new ImageView(getActivity());
        disk.setSoundEffectsEnabled(false);
        setDiskImage(disk, diskType);

        //Set constraints for newly created disk
        disk.setId(diskId++);
        disk.setLayoutParams(new ViewGroup.LayoutParams(cellSide, cellSide));
        disksLayout.addView(disk);
        setConstraints(disk, constraintX, constraintY);
        return disk;
    }

    private void setConstraints(ImageView disk, int constraintX, int constraintY) {
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(disksLayout);
        constraintSet.connect(disk.getId(), ConstraintSet.TOP, disksLayout.getId(), ConstraintSet.TOP, constraintY);
        constraintSet.connect(disk.getId(), ConstraintSet.START, disksLayout.getId(), ConstraintSet.START, constraintX);
        constraintSet.applyTo(disksLayout);
    }

    //Set disk image depending on disk type
    private void setDiskImage(ImageView disk, DiskType diskType) {
        switch (diskType) {
            case BlackDisk:
                disk.setImageResource(R.drawable.black_disk);
                break;
            case WhiteDisk:
                disk.setImageResource(R.drawable.white_disk);
                break;
            case BlackKing:
                disk.setImageResource(R.drawable.black_king);
                break;
            case WhiteKing:
                disk.setImageResource(R.drawable.white_king);
                break;
        }
    }

    //Set cell background depending on disk type
    private void setCellBackgroundImage(ImageView cell, DiskType diskType) {
        switch (diskType) {
            case BlackDisk:
                cell.setBackgroundResource(R.drawable.black_disk_transparent);
                break;
            case WhiteDisk:
                cell.setBackgroundResource(R.drawable.white_disk_transparent);
                break;
            case BlackKing:
                cell.setBackgroundResource(R.drawable.black_king_transparent);
                break;
            case WhiteKing:
                cell.setBackgroundResource(R.drawable.white_king_transparent);
                break;
        }
    }
    //endregion
}
