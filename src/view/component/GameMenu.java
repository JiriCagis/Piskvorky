package view.component;

import data.Constant;
import java.awt.Dimension;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

public class GameMenu extends JMenuBar {

    private final JMenu gameMenu;
    private final JMenuItem newGameItem;
    private final JMenuItem moveBackItem;
    private final JMenuItem exitItem;

    private final JMenu themeMenu;
    private final JMenuItem brightItem;
    private final JMenuItem darkItem;
    private final JMenuItem festivalItem;

    private final JMenu helpMenu;
    private final JMenuItem controlItem;
    private final JMenuItem rulesItem;
    private final JMenuItem aboutProgramItem;

    public GameMenu(int width, int height) {
        this.setPreferredSize(new Dimension(width, height));
        
        gameMenu = new JMenu(Constant.MENU_GAME);
        newGameItem = new JMenuItem(Constant.MENU_ITEM_NEW_GAME);
        moveBackItem = new JMenuItem(Constant.MENU_ITEM_MOVE_BACK);
        exitItem = new JMenuItem(Constant.MENU_ITEM_EXIT);
        gameMenu.add(newGameItem);
        gameMenu.add(moveBackItem);
        gameMenu.add(new JSeparator());
        gameMenu.add(exitItem);
        this.add(gameMenu);

        themeMenu = new JMenu(Constant.MENU_THEME);
        brightItem = new JMenuItem(Constant.MENU_ITEM_BRIGHT_THEME);
        darkItem = new JMenuItem(Constant.MENU_ITEM_DARK_THEME);
        festivalItem = new JMenuItem(Constant.MENU_ITEM_FESTIVAL_THEME);
        themeMenu.add(brightItem);
        themeMenu.add(darkItem);
        themeMenu.add(festivalItem);
        this.add(themeMenu);

        helpMenu = new JMenu(Constant.MENU_HELP);
        controlItem = new JMenuItem(Constant.MENU_ITEM_CONTROL_HELP);
        rulesItem = new JMenuItem(Constant.MENU_ITEM_RULES);
        aboutProgramItem = new JMenuItem(Constant.MENU_ITEM_ABOUT_PROGRAM);
        helpMenu.add(controlItem);
        helpMenu.add(rulesItem);
        helpMenu.add(aboutProgramItem);
        this.add(helpMenu);
        
        registerListeners();

    }

    private void registerListeners() {

    }

}
