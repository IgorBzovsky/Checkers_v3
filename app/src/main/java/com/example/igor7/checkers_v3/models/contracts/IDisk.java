package com.example.igor7.checkers_v3.models.contracts;

import com.example.igor7.checkers_v3.common.DiskType;
import com.example.igor7.checkers_v3.common.Point;

public interface IDisk {
    void promote();
    boolean isWhite();
    boolean isKing();
    boolean isOpposite(boolean isWhite);
    DiskType getDiskType();
    Point getPosition();
    void setPosition(Point point);
    void delete();
    boolean isDeleted();
}
