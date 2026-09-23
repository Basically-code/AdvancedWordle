import java.util.Scanner;
import javax.swing.SwingUtilities;

public class Start {
    public static void main(String[] args) {

        Scanner Gamechoice = new Scanner(System.in);
        System.out.print("""
                Hello and Welcome to the Advanced Wordle game
                This game has two sections:
                1. The terminal Based game
                2. UI based Game
                Please choose (1 / 2) to play either game:    
                """); 
        boolean correctChoice = false;
        while(!correctChoice){
            int c = Gamechoice.nextInt();
            if(c == 0) {
                correctChoice = true;
                System.out.println("THANK YOU FOR PLAYING WITH US");
            }
            if (c == 1) {
                GameBase1 game1 = new GameBase1();
                game1.RUNGAME_1();
                correctChoice = true;
            }else if (c == 2){
                SwingUtilities.invokeLater(() -> {
                GameBase2 game2 = new GameBase2();
                game2.StartGame();
            });correctChoice = true;
            }else{
                System.out.println("Press 0 to quit, or choose either 1 or 2");
            }
        }
    }
}