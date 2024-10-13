package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.cell.*;
import cleancode.minesweeper.tobe.gameLevel.GameLevel;
import cleancode.minesweeper.tobe.position.CellPosition;
import cleancode.minesweeper.tobe.position.CellPositions;
import cleancode.minesweeper.tobe.position.RelativePosition;

import java.util.List;

public class GameBoard {

    private final Cell[][] board;
    private final int landMineCount;
    private GameStatus gameStatus;

    public GameBoard(GameLevel gameLevel){
        int rowSize = gameLevel.getRowSize();
        int colSize = gameLevel.getColSize();
        board = new Cell[rowSize][colSize];
        landMineCount = gameLevel.getLandMineCount();
        initializedGameStatus();
    }
    public void initializeGame() {
        initializedGameStatus();
        CellPositions cellPositions = CellPositions.create(board);

        List<CellPosition> allPositions = cellPositions.getPositions();
        for (CellPosition position : allPositions) {
            updateCellAt(position, new EmptyCell());
        }

        List<CellPosition> landMinePositions = cellPositions.extractRandomPositions(landMineCount);
        for (CellPosition position : landMinePositions) {
            updateCellAt(position, new LandMineCell());
        }


        List<CellPosition> numberPositionCandidates = cellPositions.subtract(landMinePositions);
        for (CellPosition candidate : numberPositionCandidates) {
            int count = countNearbyLandMines(candidate);
            if (count !=0) updateCellAt(candidate, new NumberCell(count));
        }
    }
    private void updateCellAt(CellPosition position, Cell cell) {
        board[position.getRowIndex()][position.getColIndex()] = cell;
    }
    private void initializedGameStatus() {
        gameStatus = GameStatus.IN_PROGRESS;
    }

    private int countNearbyLandMines(CellPosition cellPosition) {
        int rowSize = getRowSize();
        int colSize = getColSize();

        long count = calculateSurroundedPositions(cellPosition, rowSize, colSize).stream()
                .filter(this::isLandMineCell)
                .count();
        return (int) count;
    }

    private static List<CellPosition> calculateSurroundedPositions(CellPosition cellPosition, int rowSize, int colSize) {
        return RelativePosition.SURROUNDED_POSITIONS.stream()
                .filter(cellPosition::canCalculatePositionBy)
                .map(cellPosition::calculatePositionBy)
                .filter(position -> position.isRowIndexLessThan(rowSize))
                .filter(position -> position.isColIndexLessThan(colSize)).toList();
    }

    public boolean isLandMineCell(CellPosition cellPosition) {
        return findCell(cellPosition).isLandMine();
    }

    public int getRowSize() {
        return board.length;
    }

    public int getColSize() {
        return board[0].length;
    }

    public boolean isAllCellChecked() {
        Cells cells = Cells.create(board);
        return cells.isAllChecked();
    }
    public Cell findCell(CellPosition cellPosition){
        return board[cellPosition.getRowIndex()][cellPosition.getColIndex()];
    }

    public void flagAt(CellPosition cellPosition) {
        Cell cell = findCell(cellPosition);
        cell.flag();
    }

    public void openOneCell(CellPosition cellPosition) {
        Cell cell = findCell(cellPosition);
        cell.open();
    }

    public void openSurroundedCell(CellPosition cellPosition) {
        if (isOpenedCell(cellPosition)) {
            return;
        }
        if (isLandMineCell(cellPosition)) {
            return;
        }
        openOneCell(cellPosition);

        if (doesCellHaveLandMineCount(cellPosition)) {
            return;
        }

        calculateSurroundedPositions(cellPosition, getRowSize(), getColSize())
                .forEach(this::openSurroundedCell);

        for (RelativePosition relativePosition : RelativePosition.SURROUNDED_POSITIONS) {
            if(canMovePosition(cellPosition, relativePosition)){
                CellPosition nextCellPosition = cellPosition.calculatePositionBy(relativePosition);
                openSurroundedCell(nextCellPosition);
            }
        }
    }

    private static boolean canMovePosition(CellPosition cellPosition, RelativePosition relativePosition) {
        return cellPosition.canCalculatePositionBy(relativePosition);
    }

    private boolean isOpenedCell(CellPosition cellPosition) {
        return findCell(cellPosition).isOpened();
    }

    private boolean doesCellHaveLandMineCount(CellPosition cellPosition) {
        return findCell(cellPosition).hasLandMineCount();
    }

    public boolean inInvalidCellPosition(CellPosition cellPosition) {
        int colSize = getColSize();
        int rowSize = getRowSize();

        return cellPosition.isRowIndexMoreThanOrEqual(rowSize)
                || cellPosition.isColIndexMoreThanOrEqual(colSize);
    }

    public CellSnapshot getSnapShot(CellPosition position) {
        Cell cell = findCell(position);
        return cell.getSnapShot();
    }

    public boolean isInProgress() {
        return gameStatus == GameStatus.IN_PROGRESS;
    }

    private void checkIfGamIsOver() {
        if (isAllCellChecked()) {
            changeGameStatusToWin();
        }
    }
    private void changeGameStatusToWin() {
        gameStatus = GameStatus.WIN;
    }

    public void openAt(CellPosition cellPosition) {
        if (isLandMineCell(cellPosition)) {
            openOneCell(cellPosition);
            changeGameStatusToLose();
            return;
        }

        openSurroundedCell(cellPosition);
        checkIfGamIsOver();
    }

    private void changeGameStatusToLose() {
        gameStatus = GameStatus.LOSE;
    }

    public boolean isWinStatus() {
        return gameStatus == GameStatus.WIN;
    }

    public boolean isLoseStatus() {
        return gameStatus == GameStatus.LOSE;
    }
}
