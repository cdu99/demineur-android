package fr.uge.demineur;

import java.io.Serializable;

public class Cell implements Serializable {
    public static final int BOMB = -1;
    public static final int EMPTY = 0;
    private boolean isOpen;
    private boolean isFlag;
    private int value;
    private final int x;
    private final int y;

    public Cell(int value, int x, int y) {
        this.value = value;
        this.isOpen = false;
        this.x = x;
        this.y = y;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public boolean isFlag() {
        return isFlag;
    }

    public void setFlag(boolean flag) {
        isFlag = flag;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}