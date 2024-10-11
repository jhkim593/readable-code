package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.game.GameInitializable;
import cleancode.minesweeper.tobe.game.GameRunnable;
import cleancode.minesweeper.tobe.gameLevel.GameLevel;
import cleancode.minesweeper.tobe.io.InputHandler;
import cleancode.minesweeper.tobe.io.OutputHandler;
import cleancode.minesweeper.tobe.position.CellPosition;

public class MineSweeper implements GameRunnable, GameInitializable {
    private final GameBoard gameBoard;
    private static int gameStatus = 0; // 0: 게임 중, 1: 승리, -1: 패배
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

        while (true) {
            try {
                outputHandler.showBoard(gameBoard);

                if (doesUserWinTheGame()) {
                    outputHandler.showGameWinningComment();
                    break;
                }
                if (doesUserLoseTheGame()) {
                    outputHandler.showGameLosingComment();
                    break;
                }

                CellPosition cellPosition = getCellInputFromUser();
                String actionInput = getUserActionInputFromUser();
                actOnCell(cellPosition, actionInput);
            } catch (GameException e){
                outputHandler.showExceptionMessage(e);
            } catch (Exception e){
                outputHandler.showSimpleMessage("프로그램에 문제가 생겼습니다.");
            }
        }
    }
    private void actOnCell(CellPosition cellPosition, String actionInput) {
        if (doesUserChooseToPlantFlag(actionInput)) {
            gameBoard.flagAt(cellPosition);
            checkIfGamIsOver();
            return;
        }

        if (doesUserChooseToOpenCell(actionInput)) {
            if (gameBoard.isLandMineCell(cellPosition)) {
                gameBoard.openAt(cellPosition);
                changeGameStatusToLose();
                return;
            }

            gameBoard.openSurroundedCell(cellPosition);
            checkIfGamIsOver();
            return;
        }

        throw new GameException("잘못된 번호를 선택했습니다.");
    }

    private void changeGameStatusToLose() {
        gameStatus = -1;
    }

    private boolean doesUserChooseToOpenCell(String actionInput) {
        return actionInput.equals("1");
    }

    private boolean doesUserChooseToPlantFlag(String actionInput) {
        return actionInput.equals("2");
    }

    private String getUserActionInputFromUser() {
        outputHandler.showCommentForSelecting();
        return inputHandler.getUserInput();
    }

    private CellPosition getCellInputFromUser() {
        outputHandler.showCommentForUserAction();
        CellPosition cellPosition = inputHandler.getCellPositionFromUser();
        if(gameBoard.inInvalidCellPosition(cellPosition)){
            throw new GameException("잘못된 좌표를 선택하셨습니다.");
        }
        return cellPosition;
    }

    private boolean doesUserLoseTheGame() {
        return gameStatus == -1;
    }

    private boolean doesUserWinTheGame() {
        return gameStatus == 1;
    }

    private void checkIfGamIsOver() {
        if (gameBoard.isAllCellChecked()) {
            changeGameStatusToWin();
        }
    }

    private void changeGameStatusToWin() {
        gameStatus = 1;
    }
}
