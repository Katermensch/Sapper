package model;

public enum Difficult {
    LOW(9, 9, 10),
    MEDIUM(16, 16, 40),
    HARD(16, 30, 99),
    CUSTOM();

    private int line;
    private int column;
    private int mine;

    Difficult(){}

    Difficult(int rows, int column, int mine) {
        this.line = rows;
        this.column = column;
        this.mine = mine;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    public int getMine() {
        return mine;
    }
}
