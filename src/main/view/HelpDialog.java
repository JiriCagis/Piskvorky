package main.view;

import main.data.Constant;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class HelpDialog extends JDialog implements ActionListener {

    //components
    private final JLabel headerLabel;
    private final JTextArea contentTextArea;
    private final JButton okButton;
    
    //Constnant
    private final Dimension dimension = new Dimension(200, 400);

    public HelpDialog(String header, String content) {        
        setSize(dimension);
        setMaximumSize(dimension);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setBackground(Color.white);
        setTitle(header);

        headerLabel = new JLabel(header);
        headerLabel.setFont(new Font("SansSerif", Font.ITALIC, 20));
        headerLabel.setForeground(Color.BLUE);
        this.add(headerLabel,BorderLayout.NORTH);

        contentTextArea = new JTextArea(content);
        contentTextArea.setLineWrap(true);
        contentTextArea.setWrapStyleWord(true);
        contentTextArea.setEditable(false);
        this.add(contentTextArea,BorderLayout.CENTER);

        okButton = new JButton(Constant.OK_BUTTON_LABEL);
        okButton.addActionListener(this);
        this.add(okButton,BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        dispose();
    }

}
