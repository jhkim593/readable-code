package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.game.GameInitializable;
import cleancode.minesweeper.tobe.game.GameRunnable;
import cleancode.minesweeper.tobe.gameLevel.GameLevel;
import cleancode.minesweeper.tobe.io.InputHandler;
import cleancode.minesweeper.tobe.io.OutputHandler;
import cleancode.minesweeper.tobe.position.CellPosition;
import cleancode.minesweeper.tobe.user.UserAction;

public class MineSweeper implements GameRunnable, GameInitializable {
    private final GameBoard gameBoard;
    private final InputHandler inputHandler;
    private final OutputHandler outputHandler;
    public MineSweeper(GameLevel gameLevel, InputHandler inputHandler, OutputHandler outputHandler){
        gameBoard = new GameBoard(gameLevel);
        this.inputHandler = inputHandler;
        this.outputHandler = outputHandler;
    }

    @Override
    public void initialize() {
        gameBoard.initializeGame();
    }

    @Override
    public void run() {
        outputHandler.showGameStartComments();

        while (gameBoard.isInProgress()) {
            try {
                outputHandler.showBoard(gameBoard);

                CellPosition cellPosition = getCellInputFromUser();
                UserAction userAction = getUserActionInputFromUser();
                actOnCell(cellPosition, userAction);
            } catch (GameException e){
                outputHandler.showExceptionMessage(e);
            } catch (Exception e){
                outputHandler.showSimpleMessage("프로그램에 문제가 생겼습니다.");
            }
        }
        outputHandler.showBoard(gameBoard);

        if (gameBoard.isWinStatus()) {
            outputHandler.showGameWinningComment();
        }
        if (gameBoard.isLoseStatus()) {
            outputHandler.showGameLosingComment();
        }
    }
    private void actOnCell(CellPosition cellPosition, UserAction userAction) {
        if (doesUserChooseToPlantFlag(userAction)) {
            gameBoard.flagAt(cellPosition);
            return;
        }

        if (doesUserChooseToOpenCell(userAction)) {
            gameBoard.openAt(cellPosition);
            return;
        }

        throw new GameException("잘못된 번호를 선택했습니다.");
    }

    private boolean doesUserChooseToOpenCell(UserAction userAction) {
        return userAction == UserAction.OPEN;
    }

    private boolean doesUserChooseToPlantFlag(UserAction userAction) {
        return userAction == UserAction.FLAG;
    }

    private UserAction getUserActionInputFromUser() {
        outputHandler.showCommentForSelecting();
        return inputHandler.getUserActionFromUser();
    }

    private CellPosition getCellInputFromUser() {
        outputHandler.showCommentForUserAction();
        CellPosition cellPosition = inputHandler.getCellPositionFromUser();
        if(gameBoard.inInvalidCellPosition(cellPosition)){
            throw new GameException("잘못된 좌표를 선택하셨습니다.");
        }
        return cellPosition;
    }
}
