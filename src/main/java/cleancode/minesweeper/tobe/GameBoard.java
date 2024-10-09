package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.gameLevel.GameLevel;

import java.util.Arrays;
import java.util.Random;

public class GameBoard {

    private final Cell[][] board;
    private final int landMineCount;

    public GameBoard(GameLevel gameLevel){
        int rowSize = gameLevel.getRowSize();
        int colSize = gameLevel.getColSize();
        board = new Cell[rowSize][colSize];
        landMineCount = gameLevel.getLandMineCount();
    }

    public void initializeGame() {
        int rowSize = getRowSize();
        int colSize = getColSize();
        for (int row = 0; row < rowSize; row++) {
            for (int col = 0; col < colSize; col++) {
                board[row][col] = Cell.create();
            }
        }

        for (int i = 0; i <landMineCount ; i++) {
            int landMineRow = new Random().nextInt(rowSize);
            int landMineCol = new Random().nextInt(colSize);
            findCell(landMineRow,landMineCol).turnOnLandMine();
        }

        for (int row = 0; row < rowSize; row++) {
            for (int col = 0; col < colSize; col++) {
                if(isLandMineCell(row,col)){
                    continue;
                }
                int count = countNearbyLandMines(row, col);
                findCell(row,col).updateNearbyLandMinceCount(count);
            }
        }
    }

    private int countNearbyLandMines(int row, int col) {
        int rowSize = getRowSize();
        int colSize = getColSize();
        int count = 0;
        if (row - 1 >= 0 && col - 1 >= 0 && isLandMineCell(row - 1, col - 1)) {
            count++;
        }
        if (row - 1 >= 0 && isLandMineCell(row - 1, col)) {
            count++;
        }
        if (row - 1 >= 0 && col + 1 < colSize && isLandMineCell(row - 1, col + 1)) {
            count++;
        }
        if (col - 1 >= 0 && isLandMineCell(row, col - 1)) {
            count++;
        }
        if (col + 1 < colSize && isLandMineCell(row, col + 1)) {
            count++;
        }
        if (row + 1 < rowSize && col - 1 >= 0 && isLandMineCell(row + 1, col - 1)) {
            count++;
        }
        if (row + 1 < rowSize && isLandMineCell(row + 1, col)) {
            count++;
        }
        if (row + 1 < rowSize && col + 1 < colSize && isLandMineCell(row + 1, col + 1)) {
            count++;
        }
        return count;
    }

    public boolean isLandMineCell(int selectedRowIndex, int selectedColIndex) {
        return findCell(selectedRowIndex,selectedColIndex).isLandMine();
    }

    public int getRowSize() {
        return board.length;
    }

    public int getColSize() {
        return board[0].length;
    }

    public boolean isAllCellChecked() {
        return Arrays.stream(board)
                .flatMap(Arrays::stream)
                .allMatch(cell -> cell.isChecked());
    }
    public Cell findCell(int rowIndex, int colIndex){
        return board[rowIndex][colIndex];
    }

    public String getSign(int row, int col) {
        return board[row][col].getSign();
    }

    public void flag(int selectedRowIndex, int selectedColIndex) {
        Cell cell = findCell(selectedRowIndex, selectedColIndex);
        cell.flag();
    }

    public void open(int selectedRowIndex, int selectedColIndex) {
        Cell cell = findCell(selectedRowIndex, selectedColIndex);
        cell.open();
    }

    public void openSurroundedCell(int row, int col) {
        if (row < 0 || row >= getRowSize() || col < 0 || col >= getColSize()) {
            return;
        }
        if (isOpened(row, col)) {
            return;
        }
        if (isLandMineCell(row, col)) {
            return;
        }
        open(row,col);

        if (doesCellHaveLandMineCount(row, col)) {
            return;
        }
        openSurroundedCell(row - 1, col - 1);
        openSurroundedCell(row - 1, col);
        openSurroundedCell(row - 1, col + 1);
        openSurroundedCell(row, col - 1);
        openSurroundedCell(row, col + 1);
        openSurroundedCell(row + 1, col - 1);
        openSurroundedCell(row + 1, col);
        openSurroundedCell(row + 1, col + 1);
    }

    private boolean isOpened(int row, int col) {
        return findCell(row, col).isOpened();
    }

    private boolean doesCellHaveLandMineCount(int row, int col) {
        return findCell(row, col).hasLandMineCount();
    }
}
