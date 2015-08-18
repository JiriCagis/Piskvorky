package main.logic;

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
public interface Piskvorky {    
    
    public final int HUMAN_PLAYER = 2;
    public final int COMPUTER_PLAYER = 1;
    public final int WHITE_SPACE = 0;
    
    void newGame();
    void moveBack();
    boolean hasMoveBack();
    boolean play(int row,int column);
    void playComputer();
    boolean winPlayer();
    boolean winComputer();
    int[][] getActualState();
    
    void loadSavedGame();
    void saveGame();
}
