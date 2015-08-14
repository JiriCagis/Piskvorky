package view;

import logic.Piskvorky;
import data.Theme;
import data.Constant;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import logic.PiskvorkyImpl;
import view.component.gameBoard.GameBoadListener;
import view.component.gameBoard.GameBoard;
import view.component.GameMenu;
import view.component.InfoPanel;

public class MainWindow extends JFrame implements MainWindowListener, GameBoadListener {

    //gui components
    private final GameMenu gameMenu;
    private final GameBoard gameBoard;
    private final InfoPanel infoPanel;

    //Logic
    private final int COUNT_ROWS = 25;
    private final int COUNT_COLUMNS = 20;
    private final Piskvorky piskvorky;
    

    public MainWindow() {
        setTitle(Constant.APP_NAME + " " + Constant.APP_VERSION);
        setLayout(new BorderLayout());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension dimension = new Dimension(400, 440);
        setSize(dimension);
        setLocationRelativeTo(null);
        setResizable(true);

        piskvorky = new PiskvorkyImpl(COUNT_ROWS, COUNT_COLUMNS);

        gameMenu = new GameMenu(getWidth(), 20, this);
        gameBoard = new GameBoard(COUNT_ROWS, COUNT_COLUMNS);
        gameBoard.setListener(this);
        infoPanel = new InfoPanel();
        infoPanel.showMessage(Constant.START_MESSAGE);

        this.add(gameMenu, BorderLayout.NORTH);
        this.add(gameBoard, BorderLayout.CENTER);
        this.add(infoPanel, BorderLayout.SOUTH);

        setTheme(Theme.DARK);
    }

    @Override
    public void clickOnBoard(int row, int column) {
        if (piskvorky.play(row, column)) {
            gameBoard.setArray(piskvorky.getActualState());

            if (piskvorky.winPlayer()) {
                JOptionPane.showMessageDialog(new JFrame(),Constant.WIN_GAME_MESSAGE,Constant.END_GAME_HEADER, JOptionPane.WARNING_MESSAGE);
                piskvorky.newGame();
                gameBoard.setArray(piskvorky.getActualState());
                return;
            }
            
            piskvorky.playComputer();
            if(piskvorky.winComputer()){
                JOptionPane.showMessageDialog(new JFrame(),Constant.LOSE_GAME_MESSAGE,Constant.END_GAME_HEADER, JOptionPane.WARNING_MESSAGE);
                piskvorky.newGame();
                gameBoard.setArray(piskvorky.getActualState());
                return;
            }
        }

        infoPanel.showMessage(Constant.COORDINATES_MESSAGE + row + ":" + column);
    }

    @Override
    public void newGame() {
        piskvorky.newGame();
        gameBoard.repaint();
    }

    @Override
    public void moveBack() {
        piskvorky.moveBack();
        gameBoard.repaint();
    }

    @Override
    public void closeWindow() {
        System.exit(0);
    }
    
    @Override
    public void setTheme(Theme theme) {
        switch (theme) {
            case DARK:
                gameBoard.setColors(Constant.DARK_THEME_BARROW_COLOR,
                        Constant.DARK_THEME_CROSS_COLOR,
                        Constant.DARK_THEME_GRID_COLOR,
                        Constant.DARK_THEME_BACKGROUND_COLOR);
                break;
            case BRIGHT:
                gameBoard.setColors(Constant.BRIGHT_THEME_BARROW_COLOR,
                        Constant.BRIGHT_THEME_CROSS_COLOR,
                        Constant.BRIGHT_THEME_GRID_COLOR,
                        Constant.BRIGHT_THEME_BACKGROUND_COLOR);
                break;
            case FESTIVAL:
                gameBoard.setColors(Constant.FESTIVAL_THEME_BARROW_COLOR,
                        Constant.FESTIVAL_THEME_CROSS_COLOR,
                        Constant.FESTIVAL_THEME_GRID_COLOR,
                        Constant.FESTIVAL_THEME_BACKGROUND_COLOR);
                break;
        }
    }
}
