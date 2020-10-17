package model;

import view.View;

import java.util.*;

public class Model {

    private int width;
    private int height;
    private int countFlags;
    private int countClosedCell;
    private int countMinesOnField;
    private boolean isGameStopped;
    private String seconds;
    private Difficult difficult;
    private SapperCell[][] gameField;
    private ModelListener listener;


    public void setListener(View listener) {
        this.listener = (ModelListener) listener;
    }

    public void setTime(long seconds) {
        if (!isGameStopped) {
            this.seconds = String.valueOf(seconds);
            listener.setGameTime(String.valueOf(seconds));
        }
    }

    public void runCustomGame(int line, int column, int mine) {
        this.difficult = Difficult.CUSTOM;
        this.width = line;
        this.height = column;
        this.countMinesOnField = mine;
        listener.setScreenSize(line, column);
        createGame();
    }

    public void restart() {
        isGameStopped = true;
        listener.setGameTime("");
        countClosedCell = width * height;
        createGame();
        listener.restartScreen(width, height);
    }

    public void run(Difficult difficult) {
        this.difficult = difficult;
        width = difficult.getLine();
        height = difficult.getColumn();
        countMinesOnField = difficult.getMine();
        listener.setScreenSize(width, height);
        createGame();
    }

    private void createGame() {
        isGameStopped = false;
        countFlags = countMinesOnField;
        countClosedCell = width * height;
        createCellField();
    }

    private void createCellField() {
        gameField = new SapperCell[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                gameField[i][j] = new SapperCell(i, j, false);
            }
        }
        addMines();
        countMineNeighbors();
        countMineNeighbors();
    }

    private void addMines() {
        int minesCount = 0;
        boolean notAllMines = true;
        int randomWidth;
        int randomHeighth;
        Random random = new Random();
        while (notAllMines) {
            randomWidth = random.nextInt(width);
            randomHeighth = random.nextInt(height);
            if (!gameField[randomWidth][randomHeighth].mine) {
                gameField[randomWidth][randomHeighth].mine = true;
                minesCount++;
            }
            if (minesCount == countMinesOnField) {
                notAllMines = false;
            }
        }

    }

    private void countMineNeighbors() {

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (!gameField[i][j].mine) {
                    getCount(i, j);
                }
            }
        }
    }

    private void getCount(int i, int j) {
        List<SapperCell> list;
        list = getCellNeighbors(gameField[i][j]);
        for (SapperCell object : list) {
            if (object.mine) {
                gameField[i][j].countMineNeighbors++;
            }
        }
    }

    private List<SapperCell> getCellNeighbors(SapperCell cell) {
        ArrayList<SapperCell> listOfNeighbors = new ArrayList<>();
        for (int i = cell.width - 1; i <= cell.width + 1; i++) {
            for (int j = cell.height - 1; j <= cell.height + 1; j++) {
                if ((i != cell.width || j != cell.height) && ((i >= 0) && (j >= 0) && (i < width) && (j < height))) {
                    listOfNeighbors.add(gameField[i][j]);
                }
            }
        }
        return listOfNeighbors;
    }

    public void markCell(int line, int column) {
        SapperCell cell = gameField[line][column];
        if (isGameStopped) {
            return;
        }
        if (!cell.open && countFlags > 0 && !cell.flag) {
            cell.flag = true;
            listener.setCellFlag(line, column);
            countFlags--;

        } else if (!cell.open && cell.flag) {
            cell.flag = false;
            listener.setCellDefault(line, column);
            countFlags++;

        }
    }

    public void openCell(int width, int height) {
        SapperCell cell = gameField[width][height];
        if (cell.open || cell.flag || isGameStopped) {
            return;
        }
        if (cell.mine) {
            listener.setCellMine(width, height);
            listener.openAllMine(gameField);
            gameOver();
        } else {
            open(cell);
        }
    }


    private void open(SapperCell cell) {
        cell.open = true;
        countClosedCell--;
        if (countClosedCell == countMinesOnField) {
            win();
        }
        System.out.println(countClosedCell);
        if (cell.countMineNeighbors == 0) {
            openAllNeighbors(cell);
        } else {
            listener.setCellMineNearNeighbors(cell.width, cell.height, cell.countMineNeighbors);
        }
    }

    private void openAllNeighbors(SapperCell cell) {
        listener.setCellOpened(cell.width, cell.height);
        List<SapperCell> cells = getCellNeighbors(cell);
        for (SapperCell obj : cells) {
            if (!obj.mine) {
                listener.setCellOpened(cell.width, cell.height);
                openCell(obj.width, obj.height);
            }
        }
    }

    private void win() {
        listener.Win();
        isGameStopped = true;
    }

    private void gameOver() {
        listener.GameOver();
        isGameStopped = true;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getCountMinesOnField() {
        return countMinesOnField;
    }


    public String getSeconds() {
        return seconds;
    }

    public Difficult getDifficult() {
        return difficult;
    }

}
