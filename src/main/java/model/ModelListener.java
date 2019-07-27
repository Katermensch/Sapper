package main.java.model;


public interface ModelListener {
    void setGameTime(String seconds);
    void setCellOpened(int line, int column);
    void setCellDefault(int line, int column);
    void setCellFlag(int line, int column);
    void setCellMine(int line, int column);
    void openAllMine(SapperCell[][] gameField);
    void Win();
    void GameOver();
    void setCellMineNearNeighbors(int line, int column, int countMineNeighbors);
    void setScreenSize(int line, int column);
    void restartScreen(int line, int column);
}
