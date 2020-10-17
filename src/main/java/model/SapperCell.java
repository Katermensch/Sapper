package model;

public class SapperCell{
    int width;
    int height;
    boolean mine;
    boolean open;
    boolean flag;
    int countMineNeighbors = 0;

    SapperCell(int width, int height, boolean mine) {
        this.width = width;
        this.height = height;
        this.mine = mine;
    }

    public boolean isMine() {
        return mine;
    }
}
