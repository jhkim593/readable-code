package cleancode.minesweeper.tobe.cell;

public class LandMineCell implements Cell {
    private final CellState cellState = CellState.initialize();
    @Override
    public CellSnapshot getSnapShot(){
        if(cellState.isOpened()){
            return CellSnapshot.createLandMine();
        }
        if(cellState.isFlagged()){
            return CellSnapshot.createFlag();
        }
        return CellSnapshot.createLandMine();
    }
    @Override
    public void flag() {
        cellState.flag();
    }

    @Override
    public boolean isChecked() {
        return cellState.isChecked();
    }

    @Override
    public void open() {
        cellState.open();
    }

    @Override
    public boolean isOpened() {
        return cellState.isOpened();
    }
    @Override
    public boolean isLandMine() {
        return true;
    }
    @Override
    public boolean hasLandMineCount() {
        return false;
    }
}
