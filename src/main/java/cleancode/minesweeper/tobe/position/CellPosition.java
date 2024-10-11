package cleancode.minesweeper.tobe.position;

import java.util.Objects;

public class CellPosition {
    private final int rowIndex;
    private final int colIndex;


    public CellPosition(int rowIndex, int colIndex) {
        if(rowIndex < 0 || colIndex < 0){
            throw new IllegalArgumentException("올바르지 않은 좌표입니다.");
        }
        this.rowIndex = rowIndex;
        this.colIndex = colIndex;
    }
    public static CellPosition create(int rowIndex, int colIndex){
        return new CellPosition(rowIndex, colIndex);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CellPosition that = (CellPosition) o;
        return rowIndex == that.rowIndex && colIndex == that.colIndex;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rowIndex, colIndex);
    }

    public boolean isRowIndexMoreThanOrEqual(int rowIndex) {
        return this.rowIndex >= rowIndex;
    }

    public boolean isColIndexMoreThanOrEqual(int colIndex) {
        return this.colIndex >= colIndex;
    }

    public int getColIndex() {
        return this.colIndex;
    }

    public int getRowIndex() {
        return this.rowIndex;
    }

    public CellPosition calculatePositionBy(RelativePosition relativePosition) {
        if(canCalculatePositionBy(relativePosition)) {
            return CellPosition.create(
                    this.getRowIndex() + relativePosition.getDeltaRow(),
                    this.colIndex + relativePosition.getDeltaCol());
        }
        throw new IllegalArgumentException("잘못된 좌표입니다.");
    }

    public boolean canCalculatePositionBy(RelativePosition relativePosition) {
        return getRowIndex() + relativePosition.getDeltaRow() >= 0
                && getColIndex() + relativePosition.getDeltaCol() >= 0;
    }

    public boolean isRowIndexLessThan(int rowIndex) {
        return this.rowIndex < rowIndex;
    }

    public boolean isColIndexLessThan(int colIndex) {
        return this.colIndex < colIndex;
    }
}
