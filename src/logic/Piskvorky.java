package logic;

public interface Piskvorky {    
    void newGame();
    void moveBack();
    boolean play(int row,int column);
    void playComputer();
    boolean winPlayer();
    boolean winComputer();
    int[][] getActualState();
}
