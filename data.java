
import java.io.FileWriter;
import java.util.Scanner;
import java.io.File;
import java.util.Random;
import java.io.IOException;

public class data {

    private String levelFile;
    private String[][] miniDict;

    methods method = new methods();

    public void preLoader(){
        this.miniDict = method.alphabetical("data/miniWords.txt");
    }

    private void usedWord(String word){
        try (FileWriter writer = new FileWriter("data/used.txt",true)){
            writer.write(word);
            writer.write(System.lineSeparator());
        }catch(IOException e) {e.printStackTrace();}
    }

    private boolean checkUse(String word){
        String[] uw = method.readFile("data/used.txt").split(",");
        for(String u: uw){
            if (word.equals(u)) return true;
        }usedWord(word);
        return false;
    }//Checks if the word has already been guessed

    public String wordSelector(int level){
        int div = level / 4; //This determines how many words till the next tier
        String[] files = {"data/tier1.txt","data/tier2.txt","data/tier3.txt","data/tier4.txt"};
        this.levelFile = files[div];

        String[] lf = method.readFile(this.levelFile).split(",");
        Random random = new Random();
        String word;
        while(true){
            int rand = random.nextInt(lf.length);
            if (!checkUse(lf[rand])) {
                return lf[rand];
            }
        }
    }



    public boolean wordValidator(String word){
        int ascii = ((int) word.toLowerCase().charAt(0) - 97);
        for(String w: this.miniDict[ascii]){
            if(word.equals(w)) return true;
        }return false;
    }//Checks if the word actually exists
}