package test;


import java.awt.Point;
import junit.framework.TestCase;
import main.logic.inteligence.Intelligence;
import main.logic.inteligence.IntelligenceImpl;
import org.junit.Before;
import org.junit.Test;

public class TestIntelligenceImpl extends TestCase{
 
    public final int rows = 10;
    public final int columns = 10;
    public final int HUMAN_PLAYER = 2;
    public final int COMPUTER_PLAYER = 1;
    public final int WHITE_SPACE = 0;
    
    private Intelligence intelligence;
    private int[][] array;
      
    @Before
    public void setUp(){
        array = new int[rows][columns];
        intelligence = new IntelligenceImpl(rows,columns, COMPUTER_PLAYER, HUMAN_PLAYER,WHITE_SPACE);      
    }
    
    @Test
    public void testSimpleWin(){
        array[0][0] = COMPUTER_PLAYER;
        array[0][1] = COMPUTER_PLAYER;
        array[0][2] = COMPUTER_PLAYER;
        array[0][3] = COMPUTER_PLAYER;
       
        Point assumePoint = new Point(0,4);
        assertEquals(assumePoint, intelligence.getBestMove(array));
    }
    
}
