package view.component.gameBoard;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import javax.swing.JPanel;

public class GameBoard extends JPanel {

    //General
    private final int COUNT_ROWS;
    private final int COUNT_COLUMNS;
    private int array[][];

    //Game board symbol
    public final static int PLAYER_1 = 1;//symbol barrow
    public final static int PLAYER_2 = 2;//symbol cross
    public final static int FREE_SPACE = 0;

    //Colors
    private Color color_player1 = Color.BLUE;
    private Color color_player2 = Color.RED;
    private Color color_grid = Color.DARK_GRAY;
    private Color background = Color.LIGHT_GRAY;
    
    //Listener
    GameBoadListener listener = null;

    public GameBoard(int countRows, int countColums) {
        this.COUNT_ROWS = countRows;
        this.COUNT_COLUMNS = countColums;
        array = new int[countRows][countColums];
        this.addMouseListener(new MyMouseLister());
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        int width = this.getWidth();
        int height = this.getHeight();
        float row_size = (float) getHeight() / COUNT_ROWS;
        float column_size = (float) getWidth() / COUNT_COLUMNS;

        //draw backgroud
        g2.setColor(background);
        g2.fillRect(0, 0, width, height);

        //draw grid  
        g2.setStroke(new BasicStroke(1));
        g.setColor(color_grid);
        for (int i = 0; i < COUNT_COLUMNS; i++) {
            float offset = i * column_size;
            //g.drawLine(offset, 0, offset, height);
            g2.draw(new Line2D.Float(offset, 0, offset, height));
        }
        for (int i = 0; i < COUNT_ROWS; i++) {
            float offset = i * row_size;
            g2.draw(new Line2D.Float(0, offset, width, offset));
        }

        //draw game situation
        g2.setStroke(new BasicStroke(2));
        for (int row = 0; row < COUNT_ROWS; row++) {
            for (int column = 0; column < COUNT_COLUMNS; column++) {
                switch (array[row][column]) {
                    case PLAYER_1:
                        drawBarrow(row * row_size, column * column_size, column_size, row_size, g2);
                        break;
                    case PLAYER_2:
                        drawCross(row * row_size, column * column_size, column_size, row_size, g2);
                        break;
                }
            }
        }
    }

    private void drawCross(float x, float y, float width, float height, Graphics2D g2) {
        g2.setColor(color_player1);
        g2.draw(new Line2D.Float(y, x, y + width, x + height));
        g2.draw(new Line2D.Float(y, x + height, y + width, x));
    }

    private void drawBarrow(float x, float y, float width, float height, Graphics2D g2) {
        g2.setColor(color_player2);
        g2.draw(new Ellipse2D.Float(y, x, width, height));

    }

    private class MyMouseLister extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent mouseEvent) {
            int x = mouseEvent.getX();
            int y = mouseEvent.getY();

            float row_size = (float) getHeight() / COUNT_ROWS;
            float column_size = (float) getWidth() / COUNT_COLUMNS;

            while (x % (int)column_size == 0) {
                x--;
            }
            while (y % (int)row_size == 0) {
                y--;
            }

            int row = (int) (y/row_size);
            int column = (int) (x/column_size);

            if(listener!=null)
                listener.clickOnBoard(row, column);
        }
    }

    public void setArray(int[][] array) {
        //check size array
        try{
           // int  value = array[COUNT_ROWS][COUNT_COLUMNS]; //test approach to last item
        } catch(IndexOutOfBoundsException e){
            e.printStackTrace();
            return;
        }

        this.array = array;
        repaint();
    }
 
    public void setListener(GameBoadListener listener) {
        this.listener = listener;
    }
    
    public void setColors(Color player1, Color player2, Color grid, Color backgroud) {
        this.color_player1 = player1;
        this.color_player2 = player2;
        this.color_grid = grid;
        this.background = backgroud;
    }
       
}
