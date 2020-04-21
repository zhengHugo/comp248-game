// -------------------------------------------------------
// Assignment 4
// Class Board
// Written by: Yuguo Zheng 40125496
// For COMP 248 Section EC – Fall 2019
// --------------------------------------------------------

// Board for the game

public class Board {
    private int[][][] board;
    private static final int MIN_LEVEL = 3;
    private static final int MIN_SIZE = 3;
    private int level;
    private int size; // dimension: size x size

    // privatise the constructor to make sure only one board will be created
    private static Board instance;

    private Board(int l, int x) {
        this.level = l;
        this.size = x;
        this.board = createBoard(this.level, this.size);
    }

    // get instance of default parameter
    static Board getInstance() {
        return getInstance(3, 4);
    }

    // get instance of assigned parameter
    static Board getInstance(int l, int x) {
        if (instance == null) {
            instance = new Board(l, x);
        }
        return instance;
    }

    // create the board, called by the constructor
    private int[][][] createBoard(int level, int size) {
        board = new int[level][size][size];
        for (int i = 0; i < level; i++) {
            for (int j = 0; j < size; j++) {
                for (int k = 0; k < size; k++) {
                    if ((i + j + k) % 3 == 0) {
                        board[i][j][k] = -3;
                    } else if ((i + j + k) % 5 == 0) {
                        board[i][j][k] = -2;
                    } else if ((i + j + k) % 7 == 0) {
                        board[i][j][k] = 2;
                    } else {
                        board[i][j][k] = 0;
                    }
                }
            }
        }
        // 0 is a multiple of any number but it should remain 0
        board[0][0][0] = 0;
        return board;
    }

    int getLevel() {
        return this.level;
    }

    int getSize() {
        return this.size;
    }

    // get energy adjustment according to the current position
    public int getEnergyAdj(int l, int x, int y) {
        return this.board[l][x][y];
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.level; i++) {
            sb.append("\n");
            sb.append("Level ").append(i).append("\n");
            sb.append("--------\n");
            for (int j = 0; j < this.size; j++) {
                for (int k = 0; k < this.size; k++) {
                    sb.append("\t").append(this.board[i][j][k]);
                }
                sb.append("\n");
            }
        }
        return sb.toString();
    }

}
