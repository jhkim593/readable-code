package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.gameLevel.GameLevel;
import cleancode.minesweeper.tobe.io.ConsoleInputHandler;
import cleancode.minesweeper.tobe.io.ConsoleOutputHandler;

public class MineSweeper {
    private final GameBoard gameBoard;
    private static int gameStatus = 0; // 0: 게임 중, 1: 승리, -1: 패배

    private final BoardIndexConverter boardIndexConverter = new BoardIndexConverter();
    private final ConsoleInputHandler consoleInputHandler = new ConsoleInputHandler();
    private final ConsoleOutputHandler consoleOutputHandler = new ConsoleOutputHandler();
    public MineSweeper(GameLevel gameLevel){
        gameBoard = new GameBoard(gameLevel);
    }

    public void run() {
        consoleOutputHandler.showGameStartComments();
        gameBoard.initializeGame();

        while (true) {
            try {
                consoleOutputHandler.showBoard(gameBoard);

                if (doesUserWinTheGame()) {
                    consoleOutputHandler.printGameWinningComment();
                    break;
                }
                if (doesUserLoseTheGame()) {
                    consoleOutputHandler.printGameLosingComment();
                    break;
                }

                String cellInput = getCellInputFromUser();
                String actionInput = getUserActionInputFromUser();
                actOnCell(cellInput, actionInput);
            } catch (GameException e){
                consoleOutputHandler.printExceptionMessage(e);
            } catch (Exception e){
                consoleOutputHandler.printSimpleMessage("프로그램에 문제가 생겼습니다.");
            }
        }
    }
    private void actOnCell(String cellInput, String actionInput) {
        int selectedColIndex = boardIndexConverter.getSelectedColIndex(cellInput, gameBoard.getColSize());
        int selectedRowIndex = boardIndexConverter.getSelectedRowIndex(cellInput, gameBoard.getRowSize());
        if (doesUserChooseToPlantFlag(actionInput)) {
            gameBoard.flag(selectedRowIndex,selectedColIndex);
            checkIfGamIsOver();
            return;
        }

        if (doesUserChooseToOpenCell(actionInput)) {
            if (gameBoard.isLandMineCell(selectedRowIndex, selectedColIndex)) {
                gameBoard.open(selectedRowIndex,selectedColIndex);
                changeGameStatusToLose();
                return;
            }

            gameBoard.openSurroundedCell(selectedRowIndex, selectedColIndex);
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
        consoleOutputHandler.printCommentForSelecting();
        return consoleInputHandler.getUserInput();
    }

    private String getCellInputFromUser() {
        consoleOutputHandler.printCommentForUserAction();
        return consoleInputHandler.getUserInput();
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
