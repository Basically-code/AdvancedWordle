
import java.io.FileWriter;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.io.IOException;
import java.io.File;
import java.util.Scanner;

public class data {
    //private String[][] miniDict;
    private String[] set;
    private Set<String> wordSet;

    public data(){
        //this.miniDict = method.alphabetical("database/miniWords.txt");
        this.set = readFile("database/miniWords.txt").split(",");
        initiate();
        createSet();
    }

    public String readFile(String fileName){
        String output = "";
        try(Scanner read = new Scanner(new File(fileName))){
            while(read.hasNextLine()){
                output += read.nextLine().trim() + ",";
            }
        }catch(IOException e){
            System.out.println("An error has occured!!");
            e.printStackTrace();
        }
        return output.toLowerCase();
    }

    private void initiate(){
         try {
            File used = new File("database/used.txt");
            used.createNewFile();
        } catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }
    public void terminate(){
        File used = new File("database/used.txt");
        if(!used.delete()){
            System.out.println("Something went wrong");
        }
    }

    private void usedWord(String word){
        try (FileWriter writer = new FileWriter("database/used.txt",true)){
            writer.write(word);
            writer.write(System.lineSeparator());
        }catch(IOException e) {System.err.println("Unwritable file");}
    }

    private boolean checkUse(String word){
        String[] uw = readFile("database/used.txt").split(",");
        for(String u: uw){
            if (word.equals(u)) return true;
        }usedWord(word);
        return false;
    }//Checks if the word has already been guessed

    public final int tierDifferentiator = 4;
    /*
        This determines how many words till the next tier
        maxPlayerLevels = tierDifferentiator * Number of files;
    */
    public String wordSelector(int level){
        int div = level / tierDifferentiator;
        String[] files = {"tier1.txt","tier2.txt","tier3.txt","tier4.txt"};

        String[] lf = readFile("database/" + files[div]).split(",");
        Random random = new Random();
        while(true){
            int rand = random.nextInt(lf.length);
            if (!checkUse(lf[rand])) {
                return lf[rand];
            }
        }
    }

    private void createSet(){
        Set<String> hash = new HashSet<>();
        for(String s: this.set) hash.add(s);
        this.wordSet = hash;
    }//Creates a set for easy finding of values

    public boolean wordValidator(String word){
        return this.wordSet.contains(word);
    }//Validates if the word exists
}