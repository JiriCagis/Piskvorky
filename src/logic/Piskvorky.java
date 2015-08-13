package logic;

public interface Piskvorky {
    boolean play(int row,int column);
    void playComputer();
    boolean winPlayer();
    boolean winComputer();
    int[][] getActualState();
}
