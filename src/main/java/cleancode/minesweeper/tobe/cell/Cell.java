package cleancode.minesweeper.tobe.cell;

public interface Cell {
    static final String FLAG_SIGN = "⚑";
    static final String UNCHECKED_SIGN = "□";
    String getSign();
    void flag();
    boolean isChecked();
    boolean isLandMine();
    void open();
    boolean isOpened();
    boolean hasLandMineCount();
}
