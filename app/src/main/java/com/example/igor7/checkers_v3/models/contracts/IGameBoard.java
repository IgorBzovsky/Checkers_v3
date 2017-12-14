package com.example.igor7.checkers_v3.models.contracts;

import com.example.igor7.checkers_v3.common.Point;

public interface IGameBoard {
    IDisk[][] getDiskMatrix();
    int getRows();
    int getColumns();
    IDisk getDisk(Point point);
    void removeDisk(Point point);
    void shift(Point from, Point to);
    boolean isInBounds(Point point);
    boolean isAvailableToMove(Point point);
    void deleteEatenDisks();
}
