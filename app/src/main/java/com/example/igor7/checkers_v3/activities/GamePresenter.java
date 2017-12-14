package com.example.igor7.checkers_v3.activities;

import android.util.Log;
import android.widget.ImageView;

import com.example.igor7.checkers_v3.common.DiskType;
import com.example.igor7.checkers_v3.common.Point;
import com.example.igor7.checkers_v3.models.PlayerVsPlayerGame;
import com.example.igor7.checkers_v3.models.contracts.IGame;

class GamePresenter implements IGameContract.Presenter {
    private IGameContract.View gameBoardView;
    private IGame game;

    GamePresenter(GameBoardFragment gameBoardView) {
        this.gameBoardView = gameBoardView;
        game = new PlayerVsPlayerGame();
    }
    @Override
    public void initialize() {
        DiskType[][] gameBoard = game.getDiskMatrix();
        gameBoardView.initializeGameBoard(gameBoard);
    }

    @Override
    public void click(int row, int column) {
        ImageView[][] disks = gameBoardView.getDisks();
        try {
            ImageView disk = disks[row][column];
            if(disk == null)
                tryMove(row, column);
            else
                trySelect(row, column);
        }
        catch(IndexOutOfBoundsException ex) {
            Log.d("GamePresenter.click", ex.getMessage());
        }
    }
    //endregion

    //region Helper methods
    private void tryMove(int row, int column) {
        game.move(new Point(row, column));
    }

    private void trySelect(int row, int column) {
        game.select(new Point(row, column));
    }
    //endregion
}
