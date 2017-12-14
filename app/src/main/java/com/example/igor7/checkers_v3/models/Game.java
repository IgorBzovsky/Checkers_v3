package com.example.igor7.checkers_v3.models;

import com.example.igor7.checkers_v3.common.DiskType;
import com.example.igor7.checkers_v3.models.contracts.IDisk;
import com.example.igor7.checkers_v3.models.contracts.IGame;
import com.example.igor7.checkers_v3.models.contracts.IGameBoard;

public abstract class Game implements IGame {

    private IGameBoard gameBoard;
    public Game() {
        gameBoard = new GameBoard();
    }

    @Override
    public DiskType[][] getDiskMatrix() {
        IDisk[][] discBoard = gameBoard.getDiskMatrix();
        DiskType[][] diskTypeBoard = new DiskType[discBoard.length][];
        for (int i = 0; i < discBoard.length; i++) {
            diskTypeBoard[i] = new DiskType[discBoard[i].length];
            for (int j = 0; j < discBoard[i].length; j++) {
                if(discBoard[i][j] == null)
                    diskTypeBoard[i][j] = null;
                else
                    diskTypeBoard[i][j] = discBoard[i][j].getDiskType();
            }
        }
        return diskTypeBoard;
    }
}
