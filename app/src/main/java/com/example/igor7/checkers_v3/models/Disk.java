package com.example.igor7.checkers_v3.models;

import com.example.igor7.checkers_v3.common.DiskType;
import com.example.igor7.checkers_v3.common.Point;
import com.example.igor7.checkers_v3.models.contracts.IDisk;

public class Disk implements IDisk {
    private DiskType diskType;
    private boolean isDeleted;
    private Point position;

    static IDisk createWhiteDisk(Point position) {
        return new Disk(position, DiskType.WhiteDisk);
    }
    static IDisk createBlackDisk(Point position) {
        return new Disk(position, DiskType.BlackDisk);
    }

    Disk(Point position, DiskType diskType) {
        this.position = new Point(position);
        this.diskType = diskType;
    }

    @Override
    public void promote() {
        if(diskType == DiskType.BlackDisk) {
            diskType = DiskType.BlackKing;
            return;
        }
        if(diskType == DiskType.WhiteDisk) {
            diskType = DiskType.WhiteKing;
        }
    }

    @Override
    public boolean isWhite() {
        return diskType == DiskType.WhiteDisk || diskType == DiskType.WhiteKing;
    }

    @Override
    public boolean isKing() {
        return diskType == DiskType.WhiteKing || diskType == DiskType.BlackKing;
    }

    @Override
    public boolean isOpposite(boolean isWhite) {
        return isWhite() != isWhite;
    }

    @Override
    public DiskType getDiskType() {
        return diskType;
    }

    @Override
    public Point getPosition() {
        return position;
    }

    @Override
    public void setPosition(Point point) {
        this.position = new Point(point);
    }

    @Override
    public void delete() {
        isDeleted = true;
    }

    @Override
    public boolean isDeleted() {
        return isDeleted;
    }
}
