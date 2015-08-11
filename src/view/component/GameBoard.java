package view.component;

import com.sun.org.glassfish.external.statistics.CountStatistic;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class GameBoard extends JPanel {

    //General
    private final int COUNT_ROWS;
    private final int COUNT_COLUMNS;
    private int array[][];

    //Game board symbol
    private final int PLAYER_1 = 1;//symbol barrow
    private final int PLAYER_2 = 2;//symbol cross
    private final int FREE_SPACE = 0;

    //Colors
    private Color color_player1 = Color.gray;
    private Color color_player2 = Color.red;
    private Color color_grid = Color.blue;
    private Color background = Color.white;

    public GameBoard(int countRows, int countColums) {
        this.COUNT_ROWS = countRows;
        this.COUNT_COLUMNS = countColums;
        array = new int[countRows][countColums];
        array[0][0] =1;
        array[0][1] = 2;
    }

    @Override
    public void paint(Graphics g) {
        int width = this.getWidth();
        int height = this.getHeight();
        int row_size = getHeight() / COUNT_ROWS;
        int column_size = getWidth() / COUNT_COLUMNS;

        //draw backgroud
        g.setColor(background);
        g.fillRect(0, 0, width, height);

        //draw grid   
        g.setColor(color_grid);
        for (int i = 0; i < COUNT_COLUMNS; i++) {
            int offset = i * column_size;
            g.drawLine(offset, 0, offset, height);
        }
        for (int i = 0; i < COUNT_ROWS; i++) {
            int offset = i * row_size;
            g.drawLine(0, offset, width, offset);
        }

        //draw game situation
        for (int row = 0; row < COUNT_ROWS; row++) {
            for (int column = 0; column < COUNT_COLUMNS; column++) {
                switch (array[row][column]) {
                    case PLAYER_1:
                        drawBarrow(row * row_size, column * column_size,row_size,column_size,g);
                        break;
                    case PLAYER_2:
                        drawCross(row * row_size, column * column_size,row_size,column_size,g);
                        break;
                }
            }
        }
    }

    private void drawBarrow(int x, int y,int width,int height, Graphics g) {
        g.setColor(color_player1);
        g.drawLine(x,y,x+width, y+height);
        g.drawLine(x, y+height, x+width, y);
    }

    private void drawCross(int x, int y, int width, int height,Graphics g) {
        g.setColor(color_player2);
        g.drawOval(x,y,width,height);

    }

    public void setColors(Color player1, Color player2, Color grid, Color backgroud) {
        this.color_player1 = player1;
        this.color_player2 = player2;
        this.color_grid = grid;
        this.background = backgroud;
    }

}
