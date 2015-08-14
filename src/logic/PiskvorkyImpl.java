package logic;

import java.awt.Point;
import java.util.Stack;

public class PiskvorkyImpl implements Piskvorky {

    private final int COUNT_ROWS;
    private final int COUNT_COLUMNS;
    private final int[][] array;
    private final Stack<Point> previousMoves;

    private final int HUMAN_PLAYER = 2;
    private final int COMPUTER_PLAYER = 1;
    private final int WHITE_SPACE = 0;

    public PiskvorkyImpl(int rows, int columns) {
        this.COUNT_ROWS = rows;
        this.COUNT_COLUMNS = columns;
        this.array = new int[rows][columns];
        previousMoves = new Stack<>();
        newGame();
    }

    @Override
    public void newGame() {
        for (int row = 0; row < COUNT_ROWS; row++) {
            for (int column = 0; column < COUNT_COLUMNS; column++) {
                array[row][column] = WHITE_SPACE;
            }
        }
        previousMoves.clear();
    }

    @Override
    public boolean play(int row, int column) {
        if (array[row][column] != HUMAN_PLAYER || array[row][column] != COMPUTER_PLAYER) {
            array[row][column] = HUMAN_PLAYER;
            previousMoves.push(new Point(row, column));
            return true;
        }
        return false;
    }

    @Override
    public void playComputer() {
        array[0][0] = COMPUTER_PLAYER;
        previousMoves.push(new Point(0, 0));
    }

    @Override
    public void moveBack() {
        if (previousMoves.size() >= 2) {
            Point computerMove = previousMoves.pop();
            array[computerMove.x][computerMove.y] = WHITE_SPACE;

            Point humanMove = previousMoves.pop();
            array[humanMove.x][humanMove.y] = WHITE_SPACE;
        }
    }

    @Override
    public int[][] getActualState() {
        return array;
    }

    @Override
    public boolean winPlayer() {
        return testWin(HUMAN_PLAYER);
    }

    @Override
    public boolean winComputer() {
        return testWin(COMPUTER_PLAYER);
    }

    private boolean testWin(int player) {
        for (int row = 0; row < COUNT_ROWS; row++) {
            for (int column = 0; column < COUNT_COLUMNS; column++) {
                if (array[row][column] == player) {
                    if (testRightDirection(row, column, player)
                            || testDownDirection(row, column, player)
                            || testRightObliquelyDirection(row, column, player)
                            || testLeftObliquelyDirection(row, column, player)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean testRightDirection(int row, int column, int player) {
        for (int i = 0; i < 5; i++) {
            int test_column = column + i;
            if (test_column >= COUNT_COLUMNS || array[row][test_column] != player) {
                return false;
            }
        }
        return true;
    }

    private boolean testDownDirection(int row, int column, int player) {
        for (int i = 0; i < 5; i++) {
            int test_row = row + i;
            if (test_row >= COUNT_ROWS || array[test_row][column] != player) {
                return false;
            }
        }
        return true;
    }

    private boolean testRightObliquelyDirection(int row, int column, int player) {
        for (int i = 0; i < 5; i++) {
            int test_row = row + i;
            int test_column = column + i;
            if (test_row >= COUNT_ROWS || test_column >= COUNT_COLUMNS || array[test_row][test_column] != player) {
                return false;
            }
        }
        return true;
    }

    private boolean testLeftObliquelyDirection(int row, int column, int player) {
        for (int i = 0; i < 5; i++) {
            int test_row = row + i;
            int test_column = column - i;
            if (test_row < 0 || test_column < 0 || array[test_row][test_column] != player) {
                return false;
            }
        }
        return true;
    }

}
