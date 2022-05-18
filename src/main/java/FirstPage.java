import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import static com.sun.java.accessibility.util.AWTEventMonitor.addMouseListener;

public class FirstPage{
    private  JFrame frame = new JFrame();
    private  JPanel panel;
    private  JButton button_NewGame;
    private  JButton button_Exit;

    public FirstPage() {


        panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(null);

        frame.setTitle("First Page");
        frame.setLocation(new Point(604, 40));
        frame.add(panel);
        frame.setSize(new Dimension(712, 950));
        frame.setUndecorated(true);


        ImageIcon icon = new ImageIcon(".\\src\\main\\java\\Images\\FirstPage.png");
        JLabel labelFirstPage = new JLabel();
        labelFirstPage.setIcon(icon);
        labelFirstPage.setBounds(0, -20, 712, 1000);
        panel.add(labelFirstPage);


        button_NewGame = new JButton("New game");
        button_NewGame.setBounds(195, 400, 150, 50);
        button_NewGame.setFont(new Font("SERIF", Font.ITALIC, 20));
        button_NewGame.setForeground(Color.BLACK);
        button_NewGame.setBackground(new Color(14, 116, 41));
        panel.add(button_NewGame);

        button_Exit = new JButton("Exit");
        button_Exit.setBounds(450, 500, 150, 50);
        button_Exit.setFont(new Font("SERIF", Font.ITALIC, 20));
        button_Exit.setForeground(Color.BLACK);
        button_Exit.setBackground(Color.RED);
        panel.add(button_Exit);

        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);


        button_NewGame.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new Main(new JFrame());
            }
        });


        button_Exit.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });


    }


    public static void main(String[] args) {
        FirstPage first_page = new FirstPage();
    }
}
