//Core Terminal Game Logic Implementation
/*
    The following code impliments a 16 level terminal version of wordle
    On start the game loads up the data in a reusable matrix 
    Once started the user is provided with a definite amount of attempts
    As the user guesses the code relays the output to guide on guessing 
    If the player uses up all their attemps its game over
    If they guess right they proceed to the next level
*/
import java.util.Scanner;

public class GameBase1{

    //methods method = new methods();
    data wordData  = new data();

    //Vuyani Mabuza - @26M1595
    public String randomWord(int level){
        String randomWord = wordData.wordSelector(level);
        if (randomWord == null) {
            System.out.println("Could not load a word.");
            return null;
        }return randomWord;
    }

    //Vuyani Mabuza - @26M1595
    public boolean wordValidator(String guess,int reqLen){
        //If the word is not doesn't exist
        if (guess == null) {
            System.out.println("Sorry but nothing was typed");
            return false;
        }//Checks if a word was written
        for (char c : guess.toCharArray()) {
            if (!Character.isLetter(c)) {
                System.out.println("A non letter was included in the word");
                return false;
            }
        }//Checks for non letter characters
        if (guess.length() != reqLen) {
            System.out.println("Your guess must have exactly "+ reqLen+" letters.");
            return false;
        }//Checks if the word is of required length
        if (!wordData.wordValidator(guess)) {
            System.out.println("'" + guess.toUpperCase() + "' is not a valid word.");
            return false;
        }//Checks for word existance
        return true;
    }//Checks the word

    //Vuyani Mabuza - @26M1595
    public char[] displayResult(String word,String guess){
        int len = guess.length();
        char[] display   = new char[len];
        char[] wordChar  = word.toUpperCase().toCharArray();
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

        //Display guessed letters
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

        return display;
    }

    //Rhulani Tshabalala - @Pruchuuu
    private void gameLoop() {// 4 tiers so sample in 16 rounds
        int playerLevel = 0; 
        boolean cont = true;
        int Attempts = 6;
        while (playerLevel < 16 && cont) {
            String choosenWord = randomWord(playerLevel);
            System.out.println(choosenWord); //For testing
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
                    if (choosenWord.equals(guess)) {
                        playerLevel++; //Player Advances to next Level
                        break;         //Guessed correctly
                    }
                    Attempts--;        //Player missed the word
                }
            }
            
            if(playerLevel == 2 || playerLevel == 6 || playerLevel == 10 || playerLevel == 14){
                Attempts = 5;
            }
            if(playerLevel == 4 || playerLevel == 8 || playerLevel == 12){
                Attempts = 6;
            }//Alternates the player attempts for difficulty


            if (Attempts == 0) break; // You lost
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
                    System.out.println("Come-on Lets GO again!!!!");
                    playerLevel = 1;
                    break;
                } else if (request.equals("q")) {
                    System.out.println("Thank you for playing see you again next time");
                    cont = false;
                    wordData.terminate();
                    break;
                } else {
                    System.out.println("Please choose either 'c' or 'q'");
                }
            }
        } else {
            System.out.println("GAME OVER!!!");}
    }

    public void RUNGAME_1(){
        gameLoop();
    }
}