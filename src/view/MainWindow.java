package view;

import data.Constant;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import view.component.gameBoard.GameBoadListener;
import view.component.gameBoard.GameBoard;
import view.component.GameMenu;
import view.component.InfoPanel;

public class MainWindow extends JFrame implements GameBoadListener{
    
    private final GameMenu gameMenu;
    private final GameBoard gameBoard;
    private final InfoPanel infoPanel;
    private int array[][];
    
    public MainWindow() {
       setTitle(Constant.APP_NAME + " " + Constant.APP_VERSION);
       setLayout(new BorderLayout());

       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       Dimension dimension = new Dimension(400,440);
       setSize(dimension);
       setLocationRelativeTo(null);
       setResizable(true);
       
       array = new int[25][20];
       gameMenu = new GameMenu(getWidth(),20);
       gameBoard = new GameBoard(25, 20);
       gameBoard.setListener(this);
       infoPanel = new InfoPanel();
       infoPanel.showMessage(Constant.START_MESSAGE);
       
       this.add(gameMenu,BorderLayout.NORTH);
       this.add(gameBoard,BorderLayout.CENTER);
       this.add(infoPanel,BorderLayout.SOUTH);
       
    }

    @Override
    public void clickOnBoard(int row, int column) {
        array[row][column] = GameBoard.PLAYER_2;
        gameBoard.setArray(array);
        infoPanel.showMessage(Constant.COORDINATES_MESSAGE + row + ":"+column);
    }
       
}
