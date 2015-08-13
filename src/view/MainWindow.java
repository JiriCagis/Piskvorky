package view;

import data.Constant;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;
import view.component.gameBoard.GameBoadListener;
import view.component.gameBoard.GameBoard;
import view.component.GameMenu;
import view.component.InfoPanel;

public class MainWindow extends JFrame implements MainWindowListener, GameBoadListener {

    private final GameMenu gameMenu;
    private final GameBoard gameBoard;
    private final InfoPanel infoPanel;
    private int array[][];

    public MainWindow() {
        setTitle(Constant.APP_NAME + " " + Constant.APP_VERSION);
        setLayout(new BorderLayout());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension dimension = new Dimension(400, 440);
        setSize(dimension);
        setLocationRelativeTo(null);
        setResizable(true);

        array = new int[25][20];
        gameMenu = new GameMenu(getWidth(), 20, this);
        gameBoard = new GameBoard(25, 20);
        gameBoard.setListener(this);
        infoPanel = new InfoPanel();
        infoPanel.showMessage(Constant.START_MESSAGE);

        this.add(gameMenu, BorderLayout.NORTH);
        this.add(gameBoard, BorderLayout.CENTER);
        this.add(infoPanel, BorderLayout.SOUTH);

    }

    @Override
    public void clickOnBoard(int row, int column) {
        array[row][column] = GameBoard.PLAYER_2;
        gameBoard.setArray(array);
        infoPanel.showMessage(Constant.COORDINATES_MESSAGE + row + ":" + column);
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
