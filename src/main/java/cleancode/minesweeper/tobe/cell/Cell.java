package cleancode.minesweeper.tobe.cell;

public interface Cell {
    CellSnapshot getSnapShot();
    void flag();
    boolean isChecked();
    boolean isLandMine();
    void open();
    boolean isOpened();
    boolean hasLandMineCount();
}
