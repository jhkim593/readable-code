package cleancode.minesweeper.tobe.io;

import cleancode.minesweeper.tobe.GameBoard;
import cleancode.minesweeper.tobe.GameException;
import cleancode.minesweeper.tobe.cell.CellSnapshot;
import cleancode.minesweeper.tobe.cell.CellSnapshotStatus;
import cleancode.minesweeper.tobe.position.CellPosition;
import cleancode.minesweeper.tobe.sign.*;

import java.util.List;
import java.util.stream.IntStream;

public class ConsoleOutputHandler implements OutputHandler{

    //매번 상태를 추가해야됨
//    private final CellSignFinder cellSignFinder = new CellSignFinder();
    @Override
    public void showGameStartComments() {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("지뢰찾기 게임 시작!");
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }

    @Override
    public void showBoard(GameBoard board) {
        generateColAlphabets(board);

        for (int row = 0; row < board.getRowSize(); row++) {
            System.out.printf("%2d  ", row + 1);
            for (int col = 0; col < board.getColSize(); col++) {
                CellPosition cellPosition = CellPosition.create(row,col);

                CellSnapshot snapShot = board.getSnapShot(cellPosition);
                String sign = CellSignProvider.findCellSignFrom(snapShot);
                System.out.print(sign + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    @Override
    public void generateColAlphabets(GameBoard board) {
        List<String> alphabets = IntStream.range(0, board.getColSize())
                .mapToObj(index -> (char) ('a' + index))
                .map(Object::toString)
                .toList();

        String joinAlphabets = String.join(" ", alphabets);
        System.out.println("    "+ joinAlphabets);
    }

    @Override
    public void showGameWinningComment() {
        System.out.println("지뢰를 모두 찾았습니다. GAME CLEAR!");
    }
    @Override
    public void showGameLosingComment() {
        System.out.println("지뢰를 밟았습니다. GAME OVER!");
    }
    @Override
    public void showCommentForSelecting() {
        System.out.println("선택한 셀에 대한 행위를 선택하세요. (1: 오픈, 2: 깃발 꽂기)");
    }
    @Override
    public void showCommentForUserAction() {
        System.out.println("선택할 좌표를 입력하세요. (예: a1)");
    }
    @Override
    public void showExceptionMessage(GameException e) {
        System.out.println(e.getMessage());
    }
    @Override
    public void showSimpleMessage(String message) {
        System.out.println(message);
    }
}
