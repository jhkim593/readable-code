package cleancode.minesweeper.tobe.io;

import cleancode.minesweeper.tobe.GameBoard;
import cleancode.minesweeper.tobe.GameException;

public interface OutputHandler {
    void showGameStartComments();

    void showBoard(GameBoard board);

    void generateColAlphabets(GameBoard board);

    void showGameWinningComment();

    void showGameLosingComment();

    void showCommentForSelecting();

    void showCommentForUserAction();

    void showExceptionMessage(GameException e);

    void showSimpleMessage(String message);
}
