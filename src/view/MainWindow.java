package view;

import data.Constant;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import view.component.GameBoadListener;
import view.component.GameBoard;
import view.component.GameMenu;

public class MainWindow extends JFrame implements GameBoadListener{
    private GameMenu gameMenu;
    private GameBoard gameBoard;
    private int array[][];
    
    public MainWindow() {
       setTitle(Constant.APP_NAME + " " + Constant.APP_VERSION);
       setLayout(new BorderLayout());

       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       Dimension dimension = new Dimension(400,420);
       setSize(dimension);
       setLocationRelativeTo(null);
       setResizable(true);
       
       array = new int[25][20];
       gameMenu = new GameMenu(getWidth(),20);
       gameBoard = new GameBoard(25, 20);
       gameBoard.setListener(this);
       
       this.add(gameMenu,BorderLayout.NORTH);
       this.add(gameBoard,BorderLayout.CENTER);
       
    }

    @Override
    public void clickOnBoard(int row, int column) {
        array[row][column] = GameBoard.PLAYER_2;
        gameBoard.setArray(array);

    }
       
}
