import java.util.*;
import javax.swing.JOptionPane;
import java.lang.Math;

/* The aim of this program is to replicate the Rock,Paper,Scissors game.
   @author Tanuj Yadav 
*/
public class Game
{
        private final static int ROCK = 1;
        private final static int SCISSORS = 3;
        private final static int PAPER = 2;

        public void runGame()

        {
                Scanner in = new Scanner(System.in);
                int input;
                int computerInput;
                int checkwin;
                do {
                System.out.println("Welcome\nPlease enter an Option");
                System.out.println("1. Rock");
                System.out.println("2. Paper");
                System.out.println("3. Scissors");
                System.out.println("4. Exit");

                input = in.nextInt();

        if(input == 4){
           System.out.println("Thanks for playing");
           return;
        }
             switch(input) {
                        case 1: System.out.println("You played Rock");
                        break;

                        case 2: System.out.println("You played Paper");
                        break;

                        case 3: System.out.println("You played Scissors");
                        break;

                }


                computerInput = simulateComputerMove();

                switch(computerInput){
                        case 1: System.out.println("The computer played Rock");
                        break;

                        case 2: System.out.println("The computer played Paper");
                        break;

                        case 3: System.out.println("The computer played Scissors");
                        break;

                        case 4: break;
                        }

                        checkwin = checkWinner(input, computerInput);

                        switch(checkwin){
                                case 0: System.out.println("The game is a tie");
                                break;

                                case 1: System.out.println("You win!");
                                break;

                                case 2: System.out.println("The Computer wins!");

                        }

                }
                while(input!=4);

                }

        private int checkWinner(int move1 , int move2)
        {
                if (move1 == ROCK && move2 == PAPER){
                         return 2;
        }
                else if (move1 == PAPER && move2 == ROCK){
                         return 1;
        }
                else if (move1 == ROCK && move2 == SCISSORS){
                         return 1;
        }
                else if (move1 == SCISSORS && move2 == ROCK){
                         return 2;
        }
                else if (move1 == PAPER && move2 == SCISSORS){
                         return 2;
       }
                else if (move1 == SCISSORS && move2 == PAPER){
                         return 1;
         }
                    return 0;
        }

        private int simulateComputerMove()
        {
                Random rand = new Random();
                int computerMove = rand.nextInt(3) + 1;
                return computerMove;
        }
         
         public static void main (String[] args)
        {
                Game game = new Game();
                game.runGame();
        }
}
                                                                                                                                                                   111,1         Bot

