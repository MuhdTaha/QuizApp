import com.sun.security.auth.module.JndiLoginModule;
import jdk.jfr.Category;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.border.Border;

public class Quiz extends JFrame implements ActionListener {
    private Connection connection;
    private PreparedStatement preparedStatement;
    private JLabel qnum, question, totalScore, errorMessage;
    private JRadioButton ans1, ans2, ans3, ans4;
    private JButton next, submit, playAgain;
    private ButtonGroup group;
    private int count = 0;
    private String[][] questionsArray, userAnswers;
    private String[] answersArray;
    private int correctAns = 0;
    public String Category;


    Quiz(String category) {
        correctAns = 0;
        Category = category;
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

        Border redBorder = new RoundBorder(10, Color.red);
        Border blueBorder = new RoundBorder(10, Color.blue);
        Border greenBorder = new RoundBorder(10, Color.green);

        qnum = new JLabel();
        qnum.setBounds(190, 400, 50, 30);
        qnum.setFont(new Font("Rockwell", Font.BOLD, 26));
        qnum.setForeground(Color.white);
        add(qnum);

        question = new JLabel();
        question.setBounds(220, 400, 1500, 28);
        question.setFont(new Font("Rockwell", Font.BOLD, 26));
        question.setForeground(Color.white);
        add(question);

        ans1 = new JRadioButton();
        ans1.setBounds(250, 450, 700, 30);
        ans1.setBackground(Color.black);
        ans1.setForeground(Color.white);
        ans1.setFocusPainted(false);
        ans1.setContentAreaFilled(false);
        ans1.setFont(new Font ("Rockwell", Font.PLAIN, 22));
        add(ans1);

        ans2 = new JRadioButton();
        ans2.setBounds(250, 500, 700, 30);
        ans2.setBackground(Color.black);
        ans2.setForeground(Color.white);
        ans2.setFocusPainted(false);
        ans2.setContentAreaFilled(false);
        ans2.setFont(new Font ("Rockwell", Font.PLAIN, 22));
        add(ans2);

        ans3 = new JRadioButton();
        ans3.setBounds(250, 550, 700, 30);
        ans3.setBackground(Color.black);
        ans3.setForeground(Color.white);
        ans3.setFocusPainted(false);
        ans3.setContentAreaFilled(false);
        ans3.setFont(new Font ("Rockwell", Font.PLAIN, 22));
        add(ans3);

        ans4 = new JRadioButton();
        ans4.setBounds(250, 600, 700, 30);
        ans4.setBackground(Color.black);
        ans4.setForeground(Color.white);
        ans4.setFocusPainted(false);
        ans4.setContentAreaFilled(false);
        ans4.setFont(new Font ("Rockwell", Font.PLAIN, 22));
        add(ans4);

        group = new ButtonGroup();
        group.add(ans1);
        group.add(ans2);
        group.add(ans3);
        group.add(ans4);

        next = new JButton("Next");
        next.setBounds(460, 670, 110, 40);
        next.setFont(new Font("Rockwell", Font.PLAIN, 20));
        next.setForeground(Color.white);
        next.setFocusPainted(false);
        next.setContentAreaFilled(false);
        next.setBorder(blueBorder);
        next.addActionListener(this);
        add(next);

        submit = new JButton("Submit");
        submit.setBounds(620, 670, 120, 40);
        submit.setFont(new Font("Rockwell", Font.PLAIN, 23));
        submit.setForeground(Color.WHITE);
        submit.setFocusPainted(false);
        submit.setContentAreaFilled(false);
        submit.setBorder(redBorder);
        submit.addActionListener(this);
        submit.setEnabled(false);
        add(submit);

        totalScore = new JLabel();
        totalScore.setBounds(470, 435, 1000, 50);
        totalScore.setFont(new Font("Rockwell", Font.BOLD, 35));
        add(totalScore);

        errorMessage = new JLabel();
        errorMessage.setBounds(470, 625, 1000, 50);
        errorMessage.setFont(new Font("Calibri", Font.BOLD, 25));
        errorMessage.setForeground(Color.red);
        add(errorMessage);

        playAgain = new JButton("Play Again");
        playAgain.setBounds(500, 550, 300, 60);
        playAgain.setFont(new Font("Rockwell", Font.PLAIN, 30));
        playAgain.setForeground(Color.WHITE);
        playAgain.setFocusPainted(false);
        playAgain.setContentAreaFilled(false);
        playAgain.setBorder(greenBorder);
        playAgain.addActionListener(this);

        initializeDB(Category);
        fetchQuestionsAnswers();
        start(count);

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    static class RoundBorder implements Border {
        private int radius;
        private Color color;

        public RoundBorder(int radius, Color color) {
            this.radius = radius;
            this.color = color;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(color);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(radius, radius, radius, radius);
        }

        @Override
        public boolean isBorderOpaque() {
            return true;
        }
    }

    public void actionPerformed(ActionEvent ae) {
        // check if next button is clicked
        if (ae.getSource() == next) {
            // repaint to update display
            repaint();

            // check if no option is selected
            if (group.getSelection() == null) {
                errorMessage.setText("You must select an option!");

                // set a timer to clear the error message after 1 second
                Timer timer = new Timer(1000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        errorMessage.setText("");
                    }
                });
                timer.setRepeats(false);
                timer.start();
                return;
            } else {
                // save the user's answer
                userAnswers[count][0] = group.getSelection().getActionCommand();
            }

            // check if it's the last question
            if (count == 8) {
                next.setEnabled(false);
                submit.setEnabled(true);
            }

            count++;
            start(count);

            // check if submit button is clicked
        } else if (ae.getSource() == submit) {

            // check if no option is selected
            if (group.getSelection() == null) {
                errorMessage.setText("You must select an option!");

                // set a timer to clear the error message after 1 second
                Timer timer = new Timer(1000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        errorMessage.setText("");
                    }
                });
                timer.setRepeats(false);
                timer.start();
                return;
            } else {
                // save the user's answers
                userAnswers[count][0] = group.getSelection().getActionCommand();
            }

            // check if user's answers are correct and display total score
            for (int i = 0; i < 10; i++) {
                if (userAnswers[i][0] != null && userAnswers[i][0].equals(answersArray[i])) {
                    correctAns += 1;
                }
            }
            displayTotalScore(correctAns);
        }
        // check if play again button is clicked
        else if (ae.getSource() == playAgain) {
            // close current window and open a new welcome window
            dispose();
            new Welcome();
        }
    }

    private void initializeDB(String category) {
        try {
            // load mySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // connect to database
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/questionsans", "root", "abcd1234");

            // statement to fetch questions based on category chosen
            String sql;
            switch (category) {
                case "sports" -> sql = "SELECT * FROM questions WHERE questionType = 'Sports' ORDER BY RAND()";
                case "geography" -> sql = "SELECT * FROM questions WHERE questionType = 'Geography' ORDER BY RAND()";
                case "general" -> sql = "SELECT * FROM questions WHERE questionType = 'General Knowledge' ORDER BY RAND()";
                default -> sql = "SELECT * FROM questions WHERE questionType = 'General Knowledge' ORDER BY RAND()";
            }

            preparedStatement = connection.prepareStatement(sql);
        }
        // catch any exceptions and print stack trace
        catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void fetchQuestionsAnswers() {
        try {
            // execute the query
            ResultSet resultSet = preparedStatement.executeQuery();

            // initialize the questions and answer arrays
            int rowCount = 20;
            questionsArray = new String[rowCount][5];
            answersArray = new String[rowCount];
            userAnswers = new String[rowCount][1];

            // populate the arrays
            int i = 0;
            while (resultSet.next()) {
                questionsArray[i][0] = resultSet.getString("QuestionText");
                questionsArray[i][1] = resultSet.getString("optionA");
                questionsArray[i][2] = resultSet.getString("optionB");
                questionsArray[i][3] = resultSet.getString("optionC");
                questionsArray[i][4] = resultSet.getString("optionD");

                // convert correct option to corresponding string
                char correctOptionChar = resultSet.getString("correctOption").charAt(0);
                switch (correctOptionChar) {
                    case 'A' -> answersArray[i] = questionsArray[i][1];
                    case 'B' -> answersArray[i] = questionsArray[i][2];
                    case 'C' -> answersArray[i] = questionsArray[i][3];
                    case 'D' -> answersArray[i] = questionsArray[i][4];
                }
               i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void start(int count) {
        // set the question number and questions label
        qnum.setText("" + (count + 1) + ".");
        question.setText("  " + questionsArray[count][0]);

        // set the text and action command for all answer options
        ans1.setText("  " + questionsArray[count][1]);
        ans1.setActionCommand(questionsArray[count][1]);
        ans2.setText("  " + questionsArray[count][2]);
        ans2.setActionCommand(questionsArray[count][2]);
        ans3.setText("  " + questionsArray[count][3]);
        ans3.setActionCommand(questionsArray[count][3]);
        ans4.setText("  " + questionsArray[count][4]);
        ans4.setActionCommand(questionsArray[count][4]);

        // clear the selection in the radio button group
        group.clearSelection();
    }

    private void displayTotalScore(int score) {
        // calculate and display the total score
        qnum.setVisible(false);
        question.setVisible(false);
        ans1.setVisible(false);
        ans2.setVisible(false);
        ans3.setVisible(false);
        ans4.setVisible(false);
        next.setVisible(false);
        submit.setVisible(false);

        // set the color for score based on score
        if (score >= 8) {
            totalScore.setForeground(Color.GREEN);
        } else if (score >= 6) {
            totalScore.setForeground(Color.YELLOW);
        } else if (score >= 4) {
            totalScore.setForeground(Color.ORANGE);
        } else if (score >= 0) {
            totalScore.setForeground(Color.RED);
        }

        // display total score and play again button
        totalScore.setText("TOTAL SCORE: " + score + "/10");
        add(playAgain);
    }

    public static void main(String[] args) {
        new Quiz("general");
    }
}
