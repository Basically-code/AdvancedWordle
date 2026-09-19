
import java.io.FileWriter;
import java.util.Random;
import java.io.IOException;

public class data {
    private String[][] miniDict;

    methods method = new methods();

    public void preLoader(){
        this.miniDict = method.alphabetical("database/miniWords.txt");
    }

    private void usedWord(String word){
        try (FileWriter writer = new FileWriter("database/used.txt",true)){
            writer.write(word);
            writer.write(System.lineSeparator());
        }catch(IOException e) {System.err.println("Unwritable file");}
    }

    private boolean checkUse(String word){
        String[] uw = method.readFile("database/used.txt").split(",");
        for(String u: uw){
            if (word.equals(u)) return true;
        }usedWord(word);
        return false;
    }//Checks if the word has already been guessed

    public String wordSelector(int level){
        int div = level / 4; //This determines how many words till the next tier
        String[] files = {"tier1.txt","tier2.txt","tier3.txt","tier4.txt"};

        String[] lf = method.readFile("database/" + files[div]).split(",");
        Random random = new Random();
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