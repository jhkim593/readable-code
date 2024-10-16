package cleancode.minesweeper.tobe;

public class BoardIndexConverter {
    private static final char BASE_CHAR_FOR_COL = 'a';
    public  int getSelectedRowIndex(String cellInput) {
        String cellInputRow = cellInput.substring(1);
        int selectedRowIndex = convertRowFrom(cellInputRow);
        return selectedRowIndex;
    }

    public int getSelectedColIndex(String cellInput) {
        char cellInputCol = cellInput.charAt(0);
        int selectedColIndex = convertColFrom(cellInputCol);
        return selectedColIndex;
    }

    public int convertRowFrom(String cellInputRow) {
        int rowIndex = Integer.parseInt(cellInputRow) - 1;
        if(rowIndex < 0 ){
            throw new GameException("잘못된 입력입니다.");
        }

        return rowIndex;
    }

    public int convertColFrom(char cellInputCol) {
        int colIndex = cellInputCol - BASE_CHAR_FOR_COL;
        if(colIndex < 0 ) throw new GameException("잘못된 입력입니다.");

        return colIndex;
    }
}
