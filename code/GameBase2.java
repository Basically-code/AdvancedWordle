import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.InputMap;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.Timer;

/**
 * 
 * GameBase2
 * This code impliments A Java GUI Library called swing
 * Creating a life-like wordle game 
*/

public class GameBase2 {
    //Private variables
    private int row = 6;
    private int currentRow = 0;
    private int currentCol = 0;
    private int col = 4;
    private JLabel[][] grid;
    private String selectWord;
    private String guessBuild = "";
    private int playerLevel = 14; //Testting
    private boolean correct = false;


    data wordData = new data();
    //Preliminary methods

    private void randomWord(int level){
        String randomWord = wordData.wordSelector(level);
        this.selectWord = randomWord;
    }//Random Word Selector sets "selectWord"

    private void gridSetUp() {
        JFrame frame = new JFrame("Advanced Wordle");

        //For the top label
        JPanel topPanel = new JPanel();
        topPanel.setPreferredSize(new Dimension(400, 40));
        JLabel titleLabel = new JLabel("LEVEL " + (this.playerLevel + 1), JLabel.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        titleLabel.setForeground(new Color(230, 225, 211)); 
        topPanel.setBackground(new Color(18, 18, 19));
        topPanel.add(titleLabel);

        // For the GRID
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 500);
        frame.setLocationRelativeTo(null); //Sets the window at the center of the screen
        frame.getContentPane().setBackground(new Color(18, 18, 19));

        final int gap = 5;
        JPanel gridPanel = new JPanel(new GridLayout(this.row, this.col, gap, gap));
        gridPanel.setBackground(new Color(18, 18, 19));

        Font labelFont = new Font("Arial", Font.BOLD, 24);
        Border boxBorder = BorderFactory.createLineBorder(new Color(58, 58, 60), 2);
        Dimension boxSize = new Dimension(50, 50);

        for (int r = 0; r < this.row; r++) {
            for (int c = 0; c < this.col; c++) {
                JLabel box = new JLabel("", SwingConstants.CENTER);
                box.setPreferredSize(boxSize);
                box.setMinimumSize(boxSize); // FIX: Ensures boxes don't shrink smaller than 50x50
                box.setFont(labelFont);
                box.setBorder(boxBorder);
                box.setForeground(Color.WHITE);
                box.setOpaque(true);
                box.setBackground(new Color(18, 18, 19));
                this.grid[r][c] = box;
                gridPanel.add(box);
            }
        }

        setupKeyBindings(gridPanel, frame);
        JPanel gridWrapper = new JPanel(new GridBagLayout());
        gridWrapper.setBackground(new Color(18, 18, 19));
        gridWrapper.add(gridPanel);

        // Add elements to the frame
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(gridWrapper, BorderLayout.CENTER); 
        
        frame.setVisible(true);
        return;
    }//Create the grid in one method

    private char[] processDisplay(String guess){
        int len = guess.length();
        char[] display   = new char[len];
        char[] wordChar  = this.selectWord.toUpperCase().toCharArray();
        char[] guessChar = guess.toUpperCase().toCharArray();

        boolean[] wordUsed = new boolean[len];
        for (int i = 0; i < len; i++){
            display[i] = 'X';
            if (wordChar[i] == guessChar[i]) {
                display[i] = 'G';
                wordUsed[i] = true;
            }
        }

        for (int i = 0; i < len; i++) {
            if (display[i] == 'G') {
                continue;
            }
            for (int j = 0; j < len; j++) {
                if (!wordUsed[j] && guessChar[i] == wordChar[j]) {
                    display[i] = 'Y';
                    wordUsed[j] = true;
                    break;
                }
            }
        }
        return display;
    }

    private void displayGuessResults(String guess, int rowIndex) {
        
        char[] display = processDisplay(guess);

        for (int c = 0; c < this.col; c++) {
            JLabel box = this.grid[rowIndex][c];
            if (display[c] == 'G') {
                this.grid[rowIndex][c].setBackground(new Color(83, 141, 78));
            } else if (display[c] == 'Y') {
                box.setBackground(new Color(181, 159, 59));
            } else {
                box.setBackground(new Color(58, 58, 60));
            }
        }
            
        }//Display the results from a user guess


        //Handling Inputs
        private void handleLetterInput(char l, JFrame frame){
            if (this.correct || (this.currentRow >= this.row)) return;
            if(this.currentCol < col) {
                this.guessBuild += l;
                this.grid[this.currentRow][this.currentCol].setText("" + l);
                currentCol++; //Move to the next column
            }
        }//Inputs the letters typed 

        private void handleBackspace(JFrame frame){
            if (this.correct || (this.currentRow >= this.row)) return;
            if(this.currentCol > 0){
                this.currentCol--;
                this.guessBuild = guessBuild.substring(0,guessBuild.length() - 1);
                this.grid[currentRow][currentCol].setText("");
            }
        }//Clears previous char

        private void handleEnter(JFrame frame){
            if (this.currentRow >= this.row && !this.correct) {
                System.out.println("Game Over! The word was: " + this.selectWord);
                //Handle restart
                Restart(frame);
            }

            if (this.currentCol < this.col){
                System.out.println("Please enter correctly");
                return;
            }
            if (!wordData.wordValidator(this.guessBuild.toLowerCase())) {
                System.out.printf("%s is an invalid word",this.guessBuild);
                return;
            }else{
                displayGuessResults(this.guessBuild, this.currentRow);

                if (this.guessBuild.toLowerCase().equals(this.selectWord.toLowerCase())) {
                    this.correct = true;
                    Timer winTimer = new Timer(500, e -> {
                        frame.dispose(); 
                        this.playerLevel++;
                        if(playerLevel < wordData.tierDifferentiator * 4) StartGame();
                        else{WinnerMessage(frame);}
                    });
                    winTimer.setRepeats(false);
                    winTimer.start();
                }
            
            }
            // 5. Advance to next row
            this.currentRow++;
            this.currentCol = 0;
            this.guessBuild = "";

            if (this.currentRow >= this.row && !this.correct) {
                System.out.println("Game Over! The word was: " + this.selectWord);
                //Handle restart
                Restart(frame);
            }
        }//Handles when the user preses enter


        private void setupKeyBindings(JPanel mainPanel,JFrame frame) {
        InputMap  im = mainPanel.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = mainPanel.getActionMap();

        // 1. Bind A-Z Letters
        for (char c = 'A'; c <= 'Z'; c++) {
            String key = String.valueOf(c);
            im.put(KeyStroke.getKeyStroke(c), key);
            im.put(KeyStroke.getKeyStroke(Character.toLowerCase(c)), key);
            am.put(key, new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    handleLetterInput(Character.toUpperCase(e.getActionCommand().charAt(0)), frame);
                }
            });
        }

        // 2. Bind Backspace
        im.put(KeyStroke.getKeyStroke("BACK_SPACE"), "BACKSPACE");
        am.put("BACKSPACE", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleBackspace(frame);
            }
        });

        // 3. Bind Enter
        im.put(KeyStroke.getKeyStroke("ENTER"), "ENTER");
        am.put("ENTER", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleEnter(frame);
            }
        });

        im.put(KeyStroke.getKeyStroke("ESCAPE"), "ESCAPE");
        am.put("ESCAPE", new AbstractAction() {
            @Override 
            public void actionPerformed(ActionEvent e){
                wordData.terminate();
                System.exit(0);
            }
        });
    }//Handles Input

    private void Restart(JFrame frame){ 
        int choice = JOptionPane.showConfirmDialog(
            frame,
            "Would you like to restart?",
            "Game Over",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );

        if (choice == JOptionPane.YES_OPTION) {
            frame.dispose();
            this.playerLevel = 0; // Reset progress (or keep current level to retry)
            StartGame();
        } else {
            wordData.terminate();
            System.exit(0);
        }
    }

    private void WinnerMessage(JFrame frame){
        int choice = JOptionPane.showConfirmDialog(
            frame,
            "You won! Would you like to restart?",
            "Game Complete",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.INFORMATION_MESSAGE
            );

            if (choice == JOptionPane.YES_OPTION) {
                this.playerLevel = 0;
                StartGame();
            } else {
                wordData.terminate();
                System.exit(0);
            }
    }

    public void StartGame(){

        if(playerLevel <= wordData.tierDifferentiator * 4){
            randomWord(this.playerLevel);
            System.out.println(this.selectWord); //For testing
            this.correct = false;
            this.col = this.selectWord.length();
            this.currentRow = 0;
            this.currentCol = 0;
            this.guessBuild = "";
            this.grid = new JLabel[this.row][this.col];

            gridSetUp();
        }else{
            System.out.println("GAME OVER!!");
        }
    }
}

