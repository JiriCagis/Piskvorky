package logic.inteligence;

import java.awt.Point;

public class Move extends Point{
    
    private final int evaluate;

    public Move(int evaluate, int x, int y) {
        super(x, y);
        this.evaluate = evaluate;
    }

    public Move(int evaluate, Point p) {
        super(p);
        this.evaluate = evaluate;
    }

    public int getEvaluate() {
        return evaluate;
    }  
}
