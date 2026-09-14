import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class code {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        System.out.println("Choose a level:");
        System.out.println("1. Level 1 - 5 letter words");
        System.out.println("2. Level 2 - 6 letter words");
        System.out.println("3. Level 3 - 7 letter words");

        System.out.print("Enter level: ");

        int level = input.nextInt();
        input.nextLine();

        String fileName = "";
        int wordLength = 0;

        // Choose the correct file depending on the level
        if (level == 1) {
            fileName = "data/Level1Words.txt";
            wordLength = 5;

        } else if (level == 2) {
            fileName = "data/Level2Words.txt";
            wordLength = 6;

        } else if (level == 3) {
            fileName = "data/Level3Words.txt";
            wordLength = 7;

        } else {
            System.out.println("Invalid level selected.");
            input.close();
            return;
        }


        // Select a random secret word
        String secretWord = getRandomWord(fileName);

        if (secretWord == null) {
            System.out.println("Could not load a word.");
            input.close();
            return;
        }


        int attempts = 6;
        boolean won = false;

        System.out.println();
        System.out.println("Guess the " + wordLength + "-letter word.");

        System.out.println(
            "You have " + attempts + " attempts."
        );

        System.out.println();


        // Main game loop
        for (int attempt = 1; attempt <= attempts; attempt++) {

            System.out.print(
                "Attempt " + attempt + ": "
            );

            String guess = input.nextLine().trim().toLowerCase();


            // Check if the guess contains only letters
            if (!guess.matches("[a-zA-Z]+")) {

                System.out.println(
                    "Please enter letters only."
                );

                attempt--;
                continue;
            }


            // Check if the guess has the correct length
            if (guess.length() != wordLength) {

                System.out.println("Your guess must have exactly "+wordLength+ " letters.");
                attempt--;
                continue;
            }


            // Check if the word exists in mini_words.txt
            if (!wordExists("data/mini_words.txt", guess)) {
                System.out.println("'" + guess.toUpperCase()+ "' is not a valid word.");
                attempt--;
                continue;
            }


            // Display Wordle feedback
            displayResult(secretWord, guess);


            // Check if the player won
            if (guess.equalsIgnoreCase(secretWord)) {

                System.out.println();

                System.out.println("Congratulations! You guessed the word.");

                won = true;
                break;
            }

            System.out.println();
        }


        // Player failed all 6 attempts
        if (!won) {

            System.out.println("Game Over!");

            System.out.println("The correct word was: "+ secretWord.toUpperCase());
        }


        input.close();
    }


    /*
        Selects a random word from the selected
        level file.
    */
    public static String getRandomWord(String fileName) {

        ArrayList<String> words = new ArrayList<>();

        try {

            Scanner fileReader = new Scanner(new File(fileName));

            while (fileReader.hasNextLine()) {

                String word = fileReader.nextLine().trim().toLowerCase();

                if (!word.isEmpty()) {
                    words.add(word);
                }
            }

            fileReader.close();

        } catch (FileNotFoundException e) {

            System.out.println("The file "+ fileName+ " was not found.");
            return null;
        }

        if (words.isEmpty()) {
            return null;
        }


        Random random = new Random();

        int position = random.nextInt(words.size());

        return words.get(position);
    }


    /*
        Checks whether the player's guess exists
        inside mini_words.txt.
    */
   //FIX !!!
    public static boolean wordExists(String fileName,String guess) {
        try {

            Scanner reader = new Scanner(new File(fileName));

            while (reader.hasNextLine()) {

                String word = reader.nextLine().trim();

                if (word.equalsIgnoreCase(guess)) {

                    reader.close();
                    return true;
                }
            }

            reader.close();

        } catch (FileNotFoundException e) {

            System.out.println("The file "+ fileName+ " was not found.");
        }

        return false;
    }


    /*
        Compares the player's guess with the
        secret word.

        G = correct letter in correct position
        Y = correct letter in wrong position
        X = letter is not in the word
    */
    public static void displayResult(
        String secretWord,
        String guess
    ) {

        int length = secretWord.length();

        char[] result = new char[length];

        boolean[] usedSecret = new boolean[length];

        boolean[] usedGuess = new boolean[length];


        // First check for correct positions
        for (int i = 0; i < length; i++) {

            if (guess.charAt(i)== secretWord.charAt(i)) {

                result[i] = 'G';

                usedSecret[i] = true;
                usedGuess[i] = true;
            }
        }


        // Check for correct letters
        // in the wrong positions
        for (int i = 0; i < length; i++) {

            if (usedGuess[i]) {
                continue;
            }

            for (int j = 0; j < length; j++) {

                if (!usedSecret[j] && (guess.charAt(i) == secretWord.charAt(j))) {

                    result[i] = 'Y';

                    usedSecret[j] = true;
                    usedGuess[i] = true;

                    break;
                }
            }
        }


        // All remaining letters are incorrect
        for (int i = 0; i < length; i++) {

            if (result[i] != 'G' && result[i] != 'Y') {

                result[i] = 'X';
            }
        }


        // Display guessed letters
        System.out.println();

        for (int i = 0; i < length; i++) {
            System.out.print(Character.toUpperCase(guess.charAt(i))+ " ");
        }

        System.out.println();


        // Display result
        for (int i = 0; i < length; i++) {

            System.out.print(result[i] + " "
            );
        }

        System.out.println();


        System.out.println("G = Correct position | Y = Wrong position | X = Not in word");
    }
}