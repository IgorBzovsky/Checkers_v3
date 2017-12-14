package com.example.igor7.checkers_v3.models;

import com.example.igor7.checkers_v3.common.Point;
import com.example.igor7.checkers_v3.models.contracts.IDisk;
import com.example.igor7.checkers_v3.models.contracts.IGameBoard;

public class GameBoard implements IGameBoard {
    private static final int ROWS = 8;
    private static final int COLUMNS = 8;
    private static final int FRONT = 3;

    private IDisk[][] disks;

    public GameBoard() {
        disks = new IDisk[ROWS][COLUMNS];
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if(isBlackStartPosition(new Point(i, j)))
                    disks[i][j] = Disk.createBlackDisk(new Point(i, j));
                else if(isWhiteStartPosition(new Point(i, j)))
                    disks[i][j] = Disk.createWhiteDisk(new Point(i, j));
            }
        }
        /*disks[4][3] = Disk.createWhiteDisk(new Point(4, 3));
        disks[5][2] = Disk.createBlackDisk(new Point(5, 2));
        disks[3][4] = Disk.createBlackDisk(new Point(3, 4));
        disks[3][6] = Disk.createBlackDisk(new Point(3, 6));
        disks[1][4] = Disk.createBlackDisk(new Point(1, 4));
        disks[2][1] = Disk.createBlackDisk(new Point(2, 1));*/
    }

    public GameBoard(IGameBoard gameBoard) {
        disks = new IDisk[ROWS][COLUMNS];
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                IDisk source = gameBoard.getDisk(new Point(i, j));
                if(source == null)
                    disks[i][j] = null;
                else
                    disks[i][j] = new Disk(new Point(i, j), source.getDiskType());
            }
        }
    }

    //region API
    /**
     * IDisk represents occupied cell, null represents empty cell
     * @return IDisk matrix
     */
    @Override
    public IDisk[][] getDiskMatrix() {
        return disks;
    }

    @Override
    public int getRows() {
        return ROWS;
    }

    @Override
    public int getColumns() {
        return COLUMNS;
    }

    @Override
    public IDisk getDisk(Point point) {
        if(!isInBounds(point))
            return null;
        return disks[point.getRow()][point.getColumn()];
    }

    @Override
    public void removeDisk(Point point) {
        if(!isInBounds(point))
            return;
        disks[point.getRow()][point.getColumn()] = null;
    }

    @Override
    public void shift(Point from, Point to) {
        if(!isInBounds(from) || !isInBounds(to))
            return;
        disks[to.getRow()][to.getColumn()] = disks[from.getRow()][from.getColumn()];
        disks[from.getRow()][from.getColumn()] = null;
    }

    @Override
    public boolean isInBounds(Point point) {
        if(point == null)
            return false;
        return point.getRow() >= 0 && point.getRow() < ROWS && point.getColumn() >= 0 && point.getColumn() < COLUMNS;
    }

    /**
     * Check position for availability to move
     * @param point position to check
     * @return position is in bounds and is black empty cell
     */
    @Override
    public boolean isAvailableToMove(Point point) {
        return isBlackCell(point) && getDisk(point) == null;
    }

    @Override
    public void deleteEatenDisks() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                IDisk disk = getDisk(new Point(i, j));
                if(disk != null && disk.isDeleted())
                    disks[i][j] = null;
            }
        }
    }
    //endregion

    //region Helper methods
    private boolean isBlackCell(Point point) {
        return isInBounds(point) && (point.getRow() + point.getColumn()) % 2 == 1;
    }

    private boolean isBlackStartPosition(Point point) {
        return isBlackCell(point) && point.getRow() < FRONT;
    }

    private boolean isWhiteStartPosition(Point point) {
        return isBlackCell(point) && point.getRow() >= ROWS - FRONT;
    }
    //endregion
}
