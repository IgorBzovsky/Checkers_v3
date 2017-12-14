package com.example.igor7.checkers_v3;

public class Point {
    private int row;
    private int column;

    public Point(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public Point(Point point) {
        this.row = point.getRow();
        this.column = point.getColumn();
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
}
