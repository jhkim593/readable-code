package cleancode.minesweeper.tobe.position;

import cleancode.minesweeper.tobe.cell.Cell;
import cleancode.minesweeper.tobe.cell.EmptyCell;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CellPositions {
    private final List<CellPosition> positions;

    public CellPositions(List<CellPosition> positions) {
        this.positions = positions;
    }

    public static CellPositions create(List<CellPosition> positions){
        return new CellPositions(positions);
    }

    public static CellPositions create(Cell[][] board) {
        List<CellPosition> cellPositions = new ArrayList<>();
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                CellPosition cellPosition = CellPosition.create(row, col);
                cellPositions.add(cellPosition);
            }
        }
        return create(cellPositions);
    }

    public List<CellPosition> getPositions() {
        return new ArrayList<>(positions);
    }

    public List<CellPosition> extractRandomPositions(int count) {
        List<CellPosition> cellPositions = new ArrayList<>(positions);
        Collections.shuffle(cellPositions);
        return cellPositions.subList(0,count);
    }

    public List<CellPosition> subtract(List<CellPosition> positionListToSubtract) {
        List<CellPosition> cellPositions = new ArrayList<>(positions);
        CellPositions positionsToSubtract = CellPositions.create(positionListToSubtract);

        return cellPositions.stream()
                .filter(positionsToSubtract::doesNotContain)
                .toList();
    }
    private boolean doesNotContain(CellPosition cellPosition){
        return !positions.contains(positions);
    }
}
