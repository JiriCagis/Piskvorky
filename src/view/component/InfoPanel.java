package view.component;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class InfoPanel extends JPanel{
    private final JLabel messageLabel;

    public InfoPanel() {
        setBackground(Color.GRAY);
        messageLabel = new JLabel();
        add(messageLabel);
    }
    
    public void showMessage(String message){
        messageLabel.setText(message);
    }
        
}
