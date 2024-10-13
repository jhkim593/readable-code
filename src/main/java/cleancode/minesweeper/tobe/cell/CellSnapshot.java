package cleancode.minesweeper.tobe.cell;

import java.util.Objects;

public class CellSnapshot {

    private final CellSnapshotStatus status;
    private final int nearbyLandMineCount;
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CellSnapshot that = (CellSnapshot) o;
        return getNearbyLandMineCount() == that.getNearbyLandMineCount() && getStatus() == that.getStatus();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStatus(), getNearbyLandMineCount());
    }
    public CellSnapshotStatus getStatus() {
        return status;
    }
    public int getNearbyLandMineCount() {
        return nearbyLandMineCount;
    }
    public CellSnapshot(CellSnapshotStatus status, int nearbyLandMineCount) {
        this.status = status;
        this.nearbyLandMineCount = nearbyLandMineCount;
    }
    public static CellSnapshot create(CellSnapshotStatus status, int nearbyLandMineCount){
        return new CellSnapshot(status,nearbyLandMineCount);
    }

    public static CellSnapshot createEmpty(){
        return new CellSnapshot(CellSnapshotStatus.EMPTY,0);
    }

    public static CellSnapshot createNumber(int nearbyLandMineCount){
        return new CellSnapshot(CellSnapshotStatus.NUMBER,nearbyLandMineCount);
    }

    public static CellSnapshot createFlag(){
        return new CellSnapshot(CellSnapshotStatus.FLAG,0);
    }

    public static CellSnapshot createLandMine(){
        return new CellSnapshot(CellSnapshotStatus.LAND_MINE,0);
    }

    public static CellSnapshot createUnchecked(){
        return new CellSnapshot(CellSnapshotStatus.UNCHECKED,0);
    }
}
