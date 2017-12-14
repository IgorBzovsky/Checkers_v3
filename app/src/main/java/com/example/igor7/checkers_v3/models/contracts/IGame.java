package com.example.igor7.checkers_v3.models.contracts;

import com.example.igor7.checkers_v3.common.DiskType;
import com.example.igor7.checkers_v3.common.Point;

public interface IGame {
    void select(Point position);
    void move(Point to);
    void undo();

    DiskType[][] getDiskMatrix();
}
