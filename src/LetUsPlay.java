// -------------------------------------------------------
// Assignment 4
// Class LetUsPlay
// Written by: Yuguo Zheng 40125496
// For COMP 248 Section EC – Fall 2019
// --------------------------------------------------------

import java.util.Random;

public class LetUsPlay {

    private Dice dice = Dice.getInstance();
    private MyReader myReader = new MyReader();
    private Board board;
    private Player[] players;
    private int first;
    private int second;

    private void setBoard() {
        // select board
        System.out.println("The default game board has 3 levels and each level has a 4x4 board.");
        System.out.println("You can use this default board size or change the size");
        System.out.println("\t0 to use the default board size");
        System.out.println("\t-1 to enter your own size");
        System.out.print("==> What do you want to do? ");
        int choice = myReader.nextInt((x) -> x.equals(-1) || x.equals(0));
        if (choice == -1) {
            // customized board
            System.out.print("How many level do you like? (minimum size 3, max 10) ");
            int level = myReader.nextInt((x) -> 3 <= (int) x && (int) x <= 10);
            System.out.println("What size do you want the nxn boards on each level to be?");
            System.out.println("Minimum size is 4x4, max is 10x10.");
            System.out.print("==> Enter the value of your n: ");
            int size = myReader.nextInt((x) -> 4 <= (int) x && (int) x <= 10);
            this.board = Board.getInstance(level, size);
        } else {
            // default board
            this.board = Board.getInstance();
        }
    }

    private void setPlayers() {
        players = new Player[2];
        System.out.print("What is player 0's name (one word only): ");
        players[0] = new Player(myReader.next());
        System.out.print("What is player 1's name (one word only): ");
        players[1] = new Player(myReader.next());
    }

    // who plays first
    private void setPlayerSequence() {
        Random rand = new Random();
        first = rand.nextInt(2);
        second = (first + 1) % 2;
    }


    // Players[index] play one turn
    private void playOneTurn(int index) {
        int theOther = (index + 1) % 2; // index of another player

        System.out.printf("It is %s\'s turn\n", players[index].getName());

        if (players[index].getEnergy() <= 0) {
            System.out.println("\tYou don't have any energy left now.");
            System.out.println("\tRoll dice three times to try to get some energy.");
            for (int i = 0; i < 3; i++) {
                dice.rollDice();
                System.out.printf("\tYou rolled %s\n", dice.toString());
                if (dice.isDouble()) {
                    System.out.println("\tCongratulations, you get 2 energies.");
                    players[index].setEnergy(players[index].getEnergy() + 2);
                }
            }
        }

        if (players[index].getEnergy() > 0) {
            int steps = dice.rollDice();
            System.out.printf("\t%s you rolled %s\n", players[index].getName(), dice.toString());
            if (dice.isDouble()) {
                System.out.printf("\tyou rolled double %d\n", dice.getDie1());
                players[index].setEnergy(players[index].getEnergy() + 2);
            }

            // get new position
            int newX = players[index].getX() + steps / board.getSize();
            int newY = players[index].getY() + steps % board.getSize();
            int newL = players[index].getLevel();
            if (newY >= board.getSize()) {
                newX += newY / board.getSize();
                newY = newY % board.getSize();
            }
            if (newX >= board.getSize()) {
                newL += newX / board.getSize();
                newX = newX % board.getSize();
            }
            if (newL >= board.getLevel()) {
                // off grid
                System.out.println("\t!!!Sorry you need to stay where you are - that throw takes" +
                    " you off the grid and you lose 2 units of energy.");
                players[index].setEnergy(players[index].getEnergy() - 2);
            } else if (players[theOther].getX() == newX && players[theOther].getY() == newY
                && players[theOther].getLevel() == newL) {
                // next grid occupied, challenge or not
                System.out.printf("\tPlayer %s is at your new location\n", players[theOther].getName());
                System.out.println("\tWhat do you want to do?");
                System.out.println("\t0 - Challenge and risk losing 50% of your energy units " +
                    "if you lose or move to new location and get 50% of other players energy " +
                    "units");
                System.out.println("\t1 - to move down one level or move to (0, 0) if at " +
                    "level 0 and lose 2 energy units");
                int choice = myReader.nextInt((x) -> (int) x == 0 || (int) x == 1);
                if (choice == 0) {
                    // challenge
                    System.out.println("\tYou choose to challenge!");
                    Random rand = new Random();
                    int x = rand.nextInt(11);
                    if (x >= 6) {
                        // challenge wins
                        System.out.println("\tBravo!!You won the challenge! ");
                        players[index].swapPlace(players[theOther]);
                        players[index].setEnergy(
                            players[index].getEnergy() + players[theOther].getEnergy() / 2);
                        players[theOther].setEnergy(players[theOther].getEnergy() / 2);
                    } else {
                        // challenge fails, lose half of the energy
                        System.out.println("\tSorry! You lose the challenge");
                        players[index].setEnergy(
                            players[index].getEnergy() - players[index].getEnergy() / 2);
                    }
                } else if (choice == 1) {
                    // not challenge
                    System.out.println("\tYou choose not to challenge.");
                    if(players[index].getLevel() == 0){
                        players[index].setX(0);
                        players[index].setY(0);
                    } else {
                        players[index].setLevel(players[index].getLevel() - 1);
                    }
                }
            } else {
                // normal case
                players[index].setX(newX);
                players[index].setY(newY);
                players[index].setLevel(newL);
                System.out.printf("Your energy is adjusted by %d for landing at (%d, %d) at level" +
                    " %d\n", board.getEnergyAdj(newL, newX, newY), newX, newY, newL);
                players[index].setEnergy(players[index].getEnergy() + board.getEnergyAdj(newL,
                    newX, newY));
            }

        }
    }

    // return the index of winner. Return -1 if the game continues
    private int endRound(){
        System.out.println("At the end of this round:");
        System.out.println("\t" + players[first]);
        System.out.println("\t" + players[second]);
        if (players[first].won(board)) {
            return first;
        }
        if (players[second].won(board)){
            return second;
        }
        return -1;
    }


    private void run() {
        // welcome message
        System.out.println("*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*");
        System.out.println("*                                           *");
        System.out.println("*    Welcome to Nancy's 3D Warrior Game!    *");
        System.out.println("*                                           *");
        System.out.println("*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*");

        setBoard();

        // show board
        System.out.println("Your 3D board has been set up and looks like this: ");
        System.out.println(this.board);

        setPlayers();

        setPlayerSequence();

        // Game start
        System.out.printf("The game has started. %s goes first\n", players[first].getName());
        System.out.println("=======================================");

        while(true){
            System.out.println();
            playOneTurn(first);
            playOneTurn(second);
            int result = endRound();
            if(result == -1) {
                System.out.print("Press any key and enter to continue to next round...");
                myReader.next();
            } else {
                System.out.printf("\nThe winner is %s. Well done!", players[result].getName());
                break;
            }
        }
    }

    public static void main(String[] args) {
        new LetUsPlay().run();
    }
}
