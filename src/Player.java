// -------------------------------------------------------
// Assignment 4
// Class Player
// Written by: Yuguo Zheng 40125496
// For COMP 248 Section EC – Fall 2019
// --------------------------------------------------------

public class Player {
    private String name;
    private int level;
    private int x;
    private int y;
    private int energy;

    Player() {
        this("");
    }

    // constructor by giving name
    Player(String name) {
        this.name = name;
        this.level = 0;
        this.x = 0;
        this.y = 0;
        this.energy = 10;
    }

    // constructor by giving position
    public Player(int level, int x, int y) {
        this.name = "";
        this.level = level;
        this.x = x;
        this.y = y;
        this.energy = 10;
    }

    // copy constructor
    private Player(Player player) {
        this.name = player.getName();
        this.level = player.getLevel();
        this.x = player.getX();
        this.y = player.getY();
        this.energy = player.getEnergy();
    }

    String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    int getLevel() {
        return level;
    }

    void setLevel(int level) {
        this.level = level;
    }

    int getX() {
        return x;
    }

    void setX(int x) {
        this.x = x;
    }

    int getY() {
        return y;
    }

    void setY(int y) {
        this.y = y;
    }

    int getEnergy() {
        return energy;
    }

    void setEnergy(int energy) {
        this.energy = energy;
    }

    void moveTo(Player player) {
        this.level = player.getLevel();
        this.x = player.getX();
        this.y = player.getY();
    }

    void swapPlace(Player player) {
        Player temp = new Player(player);
        player.moveTo(this);
        this.moveTo(temp);
    }

    boolean won(Board board) {
        return this.level == board.getLevel() - 1 &&
            this.x == board.getSize() - 1 && this.y == board.getSize() - 1;
    }

    @Override
    public String toString() {
        return String.format("%s is on level %d at location (%d, %d) and has %d units of energy."
            , this.name, this.level, this.x, this.y, this.energy);
    }
}
