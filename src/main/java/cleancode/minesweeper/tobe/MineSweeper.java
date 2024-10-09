package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.io.ConsoleInputHandler;
import cleancode.minesweeper.tobe.io.ConsoleOutputHandler;

public class MineSweeper {
    public static final int BOARD_ROW_SIZE = 8;
    public static final int BOARD_COL_SIZE = 10;
    private final GameBoard gameBoard = new GameBoard(BOARD_ROW_SIZE,BOARD_COL_SIZE);
    private static int gameStatus = 0; // 0: 게임 중, 1: 승리, -1: 패배
    private final ConsoleInputHandler consoleInputHandler = new ConsoleInputHandler();
    private final ConsoleOutputHandler consoleOutputHandler = new ConsoleOutputHandler();
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
        int selectedColIndex = getSelectedColIndex(cellInput);
        int selectedRowIndex = getSelectedRowIndex(cellInput);
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

    private int getSelectedColIndex(String cellInput) {
        char cellInputCol = cellInput.charAt(0);
        int selectedColIndex = convertColFrom(cellInputCol);
        return selectedColIndex;
    }

    private int getSelectedRowIndex(String cellInput) {
        char cellInputRow = cellInput.charAt(1);
        int selectedRowIndex = convertRowFrom(cellInputRow);
        return selectedRowIndex;
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

    private int convertRowFrom(char cellInputRow) {
        int rowIndex = Character.getNumericValue(cellInputRow) - 1;
        if(rowIndex >= BOARD_ROW_SIZE){
            throw new GameException("잘못된 입력입니다.");
        }

        return rowIndex;
    }

    private int convertColFrom(char cellInputCol) {
        switch (cellInputCol) {
            case 'a':
                return 0;
            case 'b':
                return 1;
            case 'c':
                return 2;
            case 'd':
                return 3;
            case 'e':
                return 4;
            case 'f':
                return 5;
            case 'g':
                return 6;
            case 'h':
                return 7;
            case 'i':
                return 8;
            case 'j':
                return 9;
            default:
                throw new GameException("잘못된 입력입니다.");
        }
    }
}
