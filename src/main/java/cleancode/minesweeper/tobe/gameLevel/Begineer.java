package cleancode.minesweeper.tobe.gameLevel;

public class Begineer implements GameLevel{
    @Override
    public int getRowSize() {
        return 8;
    }

    @Override
    public int getColSize() {
        return 10;
    }

    @Override
    public int getLandMineCount() {
        return 10;
    }
}
