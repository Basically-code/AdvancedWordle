//Core Game Logic
//Importing all necessary libraries
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class gameLogic{

    methods method = new methods();//Imports needed methods
    data wordData  = new data();

    private String randomWord(int level){
        String randomWord = wordData.wordSelector(level);
        if (randomWord == null) {
            System.out.println("Could not load a word.");
            return null;
        }return randomWord;
    }

    private boolean wordValidator(String guess,int reqLen){
        if (guess.length() != reqLen) {
            System.out.println("Your guess must have exactly "+ reqLen+" letters.");
            return false;
        }
        if (!wordData.wordValidator(guess)) {
            System.out.println("'" + guess.toUpperCase() + "' is not a valid word.");
            return false;
        }
        return true;
    }//Checks word length and existance

    private void displayResult(String word,String guess){
        int len = guess.length();
        char[] display   = new char[len];
        boolean[]  used  = new boolean[len];
        char[] wordChar  = word.toUpperCase().toCharArray();
        char[] guessChar = guess.toUpperCase().toCharArray();

        for (int j = 0; j < len; j++) {
            if (wordChar[j] == guessChar[j]) {
                display[j] = 'G';
                used[j] = true;
            } else {
                for (int m = 0; m < len; m++) {
                    if (!used[m] && (guessChar[m] == wordChar[j])) {
                        display[j] = 'Y';
                        used[j] = true;
                        break;
                    }
                }
            }
        }//Checks for words present in the guess
        for (int k = 0; k < len; k++){
            if(!used[k]) display[k] = 'X';
        }//Patches remaining words as 'X'

        // Display guessed letters
        System.out.println();

        for (int i = 0; i < len; i++) {
            System.out.print(guess.toUpperCase().charAt(i) + " ");
        }
        System.out.println();
        // Display result
        for (int i = 0; i < len; i++) {
            System.out.print(display[i] + " ");
        }
        System.out.println();
        System.out.println("G = Correct position | " + "Y = Wrong position | " + "X = Not in word");
    }





    private void gameLoop() {// 4 tiers so sample in 16 rounds
        int playerLevel = 0;//Should be dynamic
        boolean cont = true;

        while (playerLevel < 16 && cont) {
            String choosenWord = randomWord(playerLevel);
            System.out.println(choosenWord); //For testing
            boolean correct = false;
            int Attempts = 6;
            Scanner in = new Scanner(System.in);

            while (Attempts > 0) {
                System.out.printf("|Level: %d| |Attempts: %d| (Enter a %d letter word): ",
                        playerLevel + 1, Attempts, choosenWord.length());

                String guess = in.nextLine();
                if (guess.equals("quit")) {
                    cont = false;
                    break;
                }

                if (wordValidator(guess, choosenWord.length())) {
                    displayResult(choosenWord, guess);
                    Attempts--;
                    if (choosenWord.equals(guess)) {
                        correct = true;
                        break;//Guessed correctly
                    }
                }
            }
            if (correct) playerLevel++;
        }

        if (playerLevel == 16) {
            System.out.println("WINNER, Congrats you have finished the challenge!!!");
            Scanner end = new Scanner(System.in);
            System.out.println("""
                    If you would wish to start the challenge again 
                    please press 'c' or quiting press 'q'
                    """);
            while (true) {
                String request = end.nextLine();
                if (request.equals("c")) {
                    playerLevel = 1;
                    break;
                } else if (request.equals("q")) {
                    cont = false;
                    break;
                } else {
                    System.out.println("Please choose either 'c' or 'q'");
                }
            }

        } else {
            System.out.println("GAME OVER!!!");}
    }

    public void runGame(){
        wordData.preLoader();
        System.out.println("""
                Hello There welcome to the Advanced Wordle Game
                As you start the game you must guess the secret word
                under limited trials
                type 'quit' at anypoint to stop the game
                GOOD LUCK AND HAVE FUN
                """);
        gameLoop();
    }


}