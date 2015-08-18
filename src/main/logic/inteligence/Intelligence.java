package main.logic.inteligence;

import java.awt.Point;

/**
 * Intelligence for Czech game Piskvorky.
 * Calculate best move on base actually situation on grid.
 * Using algorithm Minimax. Algorithm calculate best move on
 * two parts, first get best move for defense and second get best
 * move for attack. When attack best have greater evaluate return
 * best move for attack else return best move for defense.
 * Best moves(attack and defense) are obtain passing all 
 * blank field and save field with max value after calculate
 * number according situation around blank space.
 * @author adminuser
 */
public interface Intelligence {
   Point getBestMove(int array[][]);   
}
