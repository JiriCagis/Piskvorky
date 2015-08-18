package test;



import java.util.Random;
import junit.framework.*;
import main.logic.Piskvorky;
import main.logic.PiskvorkyImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestPiskvorkyImpl extends TestCase {

    private final int rows = 10;
    private final int columns = 15;
    private Piskvorky piskvorky;

    @Before
    public void setUp() {
        piskvorky = new PiskvorkyImpl(rows, columns);
    }
    
    @After
    public void tearDown(){
        //operation after finished tests
    }

    @Test
    public void testNewGame() {
        piskvorky.newGame();
        int[][] result = piskvorky.getActualState();
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                assertEquals(Piskvorky.WHITE_SPACE, result[row][column]);
            }
        }
    }

    @Test
    public void testMoveBack() {
        int[][] previousArray = new int[rows][columns];
        int[][] arrayFromGame = piskvorky.getActualState();

        //save array for test
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                previousArray[row][column] = arrayFromGame[row][column];
            }
        }

        //play human annd computer with move back
        piskvorky.play(3, 5);
        piskvorky.playComputer();
        piskvorky.moveBack();

        //Equals arrays
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                assertTrue(previousArray[row][column] == arrayFromGame[row][column]);
            }
        }
    }

    @Test
    public void testPlay() {
        piskvorky.newGame();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            int row = random.nextInt(rows - 1);
            int column = random.nextInt(columns - 1);
            piskvorky.play(row, column);
            int[][] array = piskvorky.getActualState();
            assertEquals(Piskvorky.HUMAN_PLAYER, array[row][column]);
        }
    }

    @Test
    public void testWinPlayer() {
        //horizontal
        piskvorky.newGame();
        piskvorky.play(1, 1);
        piskvorky.play(1, 2);
        piskvorky.play(1, 3);
        piskvorky.play(1, 4);
        piskvorky.play(1, 5);
        assertTrue(piskvorky.winPlayer());

        //vertical
        piskvorky.newGame();
        piskvorky.play(1, 2);
        piskvorky.play(2, 2);
        piskvorky.play(3, 2);
        piskvorky.play(4, 2);
        piskvorky.play(5, 2);
        assertTrue(piskvorky.winPlayer());

        //right obliquely
        piskvorky.newGame();
        piskvorky.play(1, 1);
        piskvorky.play(2, 2);
        piskvorky.play(3, 3);
        piskvorky.play(4, 4);
        piskvorky.play(5, 5);
        assertTrue(piskvorky.winPlayer());

        //left obliquely
        piskvorky.newGame();
        piskvorky.play(1, 10);
        piskvorky.play(2, 9);
        piskvorky.play(3, 8);
        piskvorky.play(4, 7);
        piskvorky.play(5, 6);
        assertTrue(piskvorky.winPlayer());
    }

    @Test
    public void testWinComputer() {
        piskvorky.newGame();
        for (int i = 0; i < 5; i++) {
            piskvorky.playComputer();
        }
        assertTrue(piskvorky.winComputer());
    }

    @Test
    public void testGetActualState() {
        piskvorky.newGame();
        piskvorky.play(0, 0);
        piskvorky.play(1, 1);
        piskvorky.play(2, 2);
        int array[][] = piskvorky.getActualState();
        assertTrue(array[0][0] == Piskvorky.HUMAN_PLAYER
                && array[1][1] == Piskvorky.HUMAN_PLAYER
                && array[2][2] == Piskvorky.HUMAN_PLAYER);
    }

    @Test
    public void testSaveAndLoadGame() {
        piskvorky.newGame();
        piskvorky.play(1, 1);
        piskvorky.playComputer();

        int[][] previousArray = new int[rows][columns];
        int[][] arrayFromGame = piskvorky.getActualState();

        //save array for test
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                previousArray[row][column] = arrayFromGame[row][column];
            }
        }

        piskvorky.saveGame();
        piskvorky.loadSavedGame();

        //test game array
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                assertTrue(previousArray[row][column] == arrayFromGame[row][column]);
            }
        }

        //test saved moves
        int countMovesBack = 0;
        while (piskvorky.hasMoveBack()) {
            countMovesBack++;
            piskvorky.moveBack();
        }
        assertEquals(1, countMovesBack);
    }
}
