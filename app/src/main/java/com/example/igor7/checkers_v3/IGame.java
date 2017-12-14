package com.example.igor7.checkers_v3;

public interface IGame {
    void select(Point position);
    void move(Point to);
    void undo();
}
