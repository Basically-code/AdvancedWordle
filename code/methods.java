import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Random;


public class methods{

    /*
        Method 1
        The Following method 'range' inputs four parameters and does the following
        - It creates a dummy file by the path "tempName" and prints the name and the size on completion
        - It then reads through the provided file "fileName" line by line
        - On reading a single line it checks if the length is between start and end
        - If Pass the above condition it writes to the tempName file
        - In both Files each line has one word.
        - Implementantion is on data.java
    */
    public void range(String fileName, String tempName, int start, int end){

        try(Scanner read = new Scanner(new File(fileName))){

            try (FileWriter writer = new FileWriter(tempName)) {

                while(read.hasNextLine()){
                    //Clean the line to one word
                    String tempWord = read.nextLine().trim();
                    if (tempWord.length() >= start && tempWord.length() <= end){
                        writer.write(tempWord);
                        writer.write(System.lineSeparator());
                    }
                }
            }

            System.out.printf("File '%s' was updated to %d\n",
                    tempName, new File(tempName).length());

        }catch (FileNotFoundException e){
            System.out.printf("The file %s was not found\n", fileName);
        }catch(IOException e){
            System.out.println("An error has occured!!");
            e.printStackTrace();
        }
    }

    /**
     * Method 2
     * On the problem of finding if the word exists or not the alphabetical solves this without passing through the whoel file
     * The output is a matrix rows: a - z 
     * Inputs a file name, takes everyword and puts it in its respective position
     * This method doesn't need an arranged file as it parses through the file once
     * Time complexity O(n) where n = length of the file
    */

    public String[][] alphabetical(String fileName){
        String oneLiner = readFile(fileName);
        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        String[] words  = oneLiner.split(",");
        int[] valueInt  = valueFreq(words,alphabet.length);
        int max         = maxFreq(valueInt);

        int[] cnt = new int[alphabet.length];
        String[][] a2z = new String[alphabet.length][max];

        for(String oneWord: words){
            int ascii = ((int) oneWord.charAt(0) - 97);
            a2z[ascii][cnt[ascii]] = oneWord;
            cnt[ascii]++;
        }
        return cleanMatrix(a2z,valueInt);
    }


    /*
        Below are submethods just for convinience
        1. createfile
        2. readFile
        3. valueFreq
        4. maxFreq
        5. cleanMatrix
        6. writer
    */


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

    private int[] valueFreq(String[] value, int len){
        int[] freq = new int[len];
        for(String unit: value){
            freq[((int) unit.charAt(0) - 97)]++;
        }
        return freq;
    }

    private int maxFreq(int[] freq){
        int max = -1;
        for (int i: freq){
            if(i > max) max = i;
        }
        return max;
    }

    public String[][] cleanMatrix(String[][] input, int[] valueFreq){
        String[][] clean = new String[input.length][];
        int cnt = 0;

        for(String[] row: input){
            String[] tempRow = new String[valueFreq[cnt]];

            for(int i = 0; i < valueFreq[cnt]; i++)
                tempRow[i] = row[i];

            clean[cnt] = tempRow;
            cnt++;
        }

        return clean;
    }
}