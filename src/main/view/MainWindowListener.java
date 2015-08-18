package main.view;

import main.data.Theme;

public interface MainWindowListener {
    void newGame();
    void moveBack();   
    void setTheme(Theme theme);
    void closeWindow();   
}
