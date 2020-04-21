// -------------------------------------------------------
// Assignment 4
// Class Dice
// Written by: Yuguo Zheng 40125496
// For COMP 248 Section EC – Fall 2019
// --------------------------------------------------------

import java.util.Random;


//Dice class for rolling the dice

public class Dice {
    private int die1;
    private int die2;

    // java Random object to generate random number
    private static Random random = new Random();

    // private constructor to prevent multiple objects
    private Dice(){
        this.die1 = random.nextInt(6) + 1;
        this.die2 = random.nextInt(6) + 1;
    }

    private static final Dice dice = new Dice();

    static Dice getInstance(){
        return dice;
    }

    int getDie1(){
        return this.die1;
    }

    public int getDie2(){
        return this.die2;
    }

    int rollDice(){
        this.die1 = random.nextInt(6) + 1;
        this.die2 = random.nextInt(6) + 1;
        return this.die1 + this.die2;
    }

    boolean isDouble(){
        return this.die1 == this.die2;
    }

    public String toString(){
        return String.format("Die1: %d  Die2: %d", this.die1, this.die2);
    }

}
