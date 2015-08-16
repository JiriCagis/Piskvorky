package logic;

import java.awt.Point;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import logic.inteligence.Intelligence;
import utils.xmlParser.XmlParser;
import utils.xmlParser.XmlParserImpl;

/**
 * Logic class game Piskvorky. Piskvorky is strategic game for two 
 * players. In this case human and computer. Players sparingly draw symbols
 * on grid. Symbols are barrow and cross. Player which as first create
 * series of five his symbols win game. Series can be vertical,
 * horizontal or obliquely.
 * 
 * Official rules Czech game Piskorky:
 * http://www.piskvorky.cz/federace/oficialni-pravidla-piskvorek/
 * @author adminuser
 */

public class PiskvorkyImpl implements Piskvorky {

    private final int COUNT_ROWS;
    private final int COUNT_COLUMNS;
    private int[][] array;
    private Stack<Point> previousMoves;

    private final int HUMAN_PLAYER = 2;
    private final int COMPUTER_PLAYER = 1;
    private final int WHITE_SPACE = 0;

    private final Intelligence intelligence;
    private final File outFile = new File("piskvorky_saved_game.xml");
    private final XmlParser<Object> xmlParser;

    public PiskvorkyImpl(int rows, int columns) {
        this.COUNT_ROWS = rows;
        this.COUNT_COLUMNS = columns;
        this.array = new int[rows][columns];
        previousMoves = new Stack<>();
        intelligence = new Intelligence(COUNT_ROWS, COUNT_COLUMNS, COMPUTER_PLAYER, HUMAN_PLAYER, WHITE_SPACE);
        xmlParser = new XmlParserImpl<Object>();
        loadSavedGame();
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
        if (array[row][column] == WHITE_SPACE) {
            array[row][column] = HUMAN_PLAYER;
            previousMoves.push(new Point(row, column));
            return true;
        }
        return false;
    }

    @Override
    public void playComputer() {
        Point point = intelligence.getBestMove(array);
        array[point.x][point.y] = COMPUTER_PLAYER;
        previousMoves.push(point);
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
            if (test_row >= COUNT_ROWS || test_column < 0 || array[test_row][test_column] != player) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void loadSavedGame() {
        List<Object> list = xmlParser.parse(outFile);
        if (list.size() == 2) {
            array = (int[][]) list.get(0);
            previousMoves = (Stack<Point>) list.get(1);
        } else {
            newGame();
        }
    }

    @Override
    public void saveGame() {
        List<Object> list = new ArrayList<Object>();
        list.add(array);
        list.add(previousMoves);
        xmlParser.save(list, outFile);
    }

}
