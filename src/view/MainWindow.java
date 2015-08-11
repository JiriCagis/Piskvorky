package view;

import data.Constant;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import view.component.GameBoard;
import view.component.GameMenu;

public class MainWindow extends JFrame{

    public MainWindow() {
       setTitle(Constant.APP_NAME + " " + Constant.APP_VERSION);
       setLayout(new BorderLayout());

       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       Dimension dimension = new Dimension(400,468);
       setSize(dimension);
       setLocationRelativeTo(null);
       setResizable(true);
       
       this.add(new GameMenu(getWidth(),20),BorderLayout.NORTH);
       this.add(new GameBoard(30, 20),BorderLayout.CENTER);
       
    }
       
}
