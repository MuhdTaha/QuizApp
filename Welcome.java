import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;


public class Welcome extends JFrame {
    private JButton play;
    private JLabel welcomeMessage, byLabel;

    Border redBorder = new Quiz.RoundBorder(10, Color.red);
    Welcome() {
        setBounds(0, 0, 1450, 800);
        JPanel contentPane = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon bg = new ImageIcon(ClassLoader.getSystemResource("images/quizBackground.png"));
                g.drawImage(bg.getImage(), 0, 380, 1450, 380, this);
            }
        };
        contentPane.setLayout(null);
        setContentPane(contentPane);

        ImageIcon bn = new ImageIcon(ClassLoader.getSystemResource("images/quizBanner.png"));
        JLabel bnImage = new JLabel(bn);
        bnImage.setBounds(0, 0, 1450, 380);
        add(bnImage);

        play = new JButton("START");
        play.setBounds(500, 550, 300, 60);
        play.setFont(new Font("Rockwell", Font.PLAIN, 45));
        play.setForeground(Color.WHITE);
        play.setFocusPainted(false);
        play.setContentAreaFilled(false);
        play.setBorder(redBorder);
        play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                categoriesWindow cWindow = new categoriesWindow();
                cWindow.setVisible(true);
            }
        });
        add(play);

        welcomeMessage = new JLabel("Welcome to the Brain Busters Quiz!!!");
        welcomeMessage.setBounds(360, 410, 1000, 110);
        welcomeMessage.setFont(new Font("Rockwell", Font.BOLD, 35));
        welcomeMessage.setForeground(Color.white);
        add(welcomeMessage);

        byLabel = new JLabel("Made by: Muhammad Taha");
        byLabel.setBounds(500, 650, 1000, 110);
        byLabel.setFont(new Font("Rockwell", Font.BOLD, 18));
        byLabel.setForeground(Color.pink);
        add(byLabel);

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new Welcome();
    }
}

class categoriesWindow extends JFrame {
    private JButton sports, geography, general;
    private JLabel chooseCategory;
    categoriesWindow() {
        setBounds(0, 0, 1450, 800);
        Border greenBorder = new Quiz.RoundBorder(10, Color.green);
        JPanel contentPane = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon bg = new ImageIcon(ClassLoader.getSystemResource("images/quizBackground.png"));
                g.drawImage(bg.getImage(), 0, 380, 1450, 380, this);
            }
        };
        contentPane.setLayout(null);
        setContentPane(contentPane);

        ImageIcon bn = new ImageIcon(ClassLoader.getSystemResource("images/quizBanner.png"));
        JLabel bnImage = new JLabel(bn);
        bnImage.setBounds(0, 0, 1450, 380);
        add(bnImage);

        chooseCategory = new JLabel("Choose one of the categories to begin!");
        chooseCategory.setBounds(325, 430, 700, 50);
        chooseCategory.setFont(new Font("Rockwell", Font.BOLD, 35));
        chooseCategory.setForeground(Color.white);
        add(chooseCategory);

        sports = new JButton("Sports");
        sports.setBounds(155, 550, 200, 50);
        sports.setFont(new Font("Rockwell", Font.PLAIN, 30));
        sports.setForeground(Color.WHITE);
        sports.setFocusPainted(false);
        sports.setContentAreaFilled(false);
        sports.setBorder(greenBorder);
        sports.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Quiz("sports");
            }
        });
        add(sports);

        general = new JButton("General Knowledge");
        general.setBounds(415, 550, 400, 50);
        general.setFont(new Font("Rockwell", Font.PLAIN, 30));
        general.setForeground(Color.WHITE);
        general.setFocusPainted(false);
        general.setContentAreaFilled(false);
        general.setBorder(greenBorder);
        general.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Quiz("general");
            }
        });
        add(general);

        geography = new JButton("Geography");
        geography.setBounds(875, 550, 200, 50);
        geography.setFont(new Font("Rockwell", Font.PLAIN, 30));
        geography.setForeground(Color.WHITE);
        geography.setFocusPainted(false);
        geography.setContentAreaFilled(false);
        geography.setBorder(greenBorder);
        geography.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Quiz("geography");
            }
        });
        add(geography);
    }
}


