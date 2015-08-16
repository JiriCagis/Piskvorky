package view;

import logic.Piskvorky;
import data.Theme;
import data.Constant;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import logic.PiskvorkyImpl;
import utils.xmlParser.XmlParser;
import utils.xmlParser.XmlParserImpl;
import view.component.gameBoard.GameBoadListener;
import view.component.gameBoard.GameBoard;
import view.component.GameMenu;
import view.component.InfoPanel;

public class MainWindow extends JFrame implements MainWindowListener, GameBoadListener {

    //gui components
    private final GameMenu gameMenu;
    private final GameBoard gameBoard;
    private final InfoPanel infoPanel;
    private Theme actualTheme = Theme.DARK;

    //Logic
    private final int COUNT_ROWS = 25;
    private final int COUNT_COLUMNS = 20;
    private final Piskvorky piskvorky;
    private final XmlParser<Object> xmlParser;
    private final File outFile = new File("piskvorky_window_config.xml");

    public MainWindow() {
        xmlParser = new XmlParserImpl<Object>();
        piskvorky = new PiskvorkyImpl(COUNT_ROWS, COUNT_COLUMNS);
        
        gameMenu = new GameMenu(getWidth(), 20, this);
        gameBoard = new GameBoard(COUNT_ROWS, COUNT_COLUMNS);
        gameBoard.setListener(this);
        gameBoard.setArray(piskvorky.getActualState());
        infoPanel = new InfoPanel();
        infoPanel.showMessage(Constant.START_MESSAGE);

        setTitle(Constant.APP_NAME + " " + Constant.APP_VERSION);
        loadWindowConfigurate();
        setLayout(new BorderLayout());

        this.add(gameMenu, BorderLayout.NORTH);
        this.add(gameBoard, BorderLayout.CENTER);
        this.add(infoPanel, BorderLayout.SOUTH);

        //register listeners
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                closeWindow();
            }
        });
    }

    @Override
    public void clickOnBoard(int row, int column) {
        if (piskvorky.play(row, column)) {
            gameBoard.setArray(piskvorky.getActualState());

            if (piskvorky.winPlayer()) {
                JOptionPane.showMessageDialog(new JFrame(), Constant.WIN_GAME_MESSAGE, Constant.END_GAME_HEADER, JOptionPane.WARNING_MESSAGE);
                piskvorky.newGame();
                gameBoard.setArray(piskvorky.getActualState());
                return;
            }

            piskvorky.playComputer();
            if (piskvorky.winComputer()) {
                JOptionPane.showMessageDialog(new JFrame(), Constant.LOSE_GAME_MESSAGE, Constant.END_GAME_HEADER, JOptionPane.WARNING_MESSAGE);
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
        piskvorky.saveGame();
        saveWindowConfigurate();
        System.exit(0);
    }

    private void loadWindowConfigurate() {
        List<Object> list = xmlParser.parse(outFile);
        if (list.size() == 3) {
            setSize((Dimension) list.get(0));
            setLocation((Point) list.get(1));
            setTheme((Theme) list.get(2));
        } else {
            setSize(new Dimension(500, 500));
            setLocationRelativeTo(null);
            setTheme(Theme.DARK);
        }
    }

    private void saveWindowConfigurate() {
        List<Object> list = new ArrayList<Object>();
        list.add(getSize());
        list.add(getLocationOnScreen());
        list.add(actualTheme);
        xmlParser.save(list, outFile);
    }

    @Override
    public void setTheme(Theme theme) {
        actualTheme = theme;
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
