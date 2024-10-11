package cleancode.minesweeper.tobe.cell;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class Cells {

    private final List<Cell> cells;

    public Cells(List<Cell> cells) {
        this.cells = cells;
    }
    public static Cells create(List<Cell> cells){
        return new Cells(cells);
    }

    public static Cells create(Cell[][] cells) {
        return new Cells(Arrays.stream(cells)
                .flatMap(Arrays::stream)
                .toList());
    }

    public boolean isAllChecked() {
        return cells.stream()
                .allMatch(Cell::isChecked);
    }
}
