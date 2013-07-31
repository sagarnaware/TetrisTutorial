package tetris;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: snaware
 * Date: 6/28/13
 * Time: 5:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class Tetromino {

    public char cells[][];
    public List<Coordinate> occupiedCells = new ArrayList<Coordinate>();

    private String tetrominoShape = "";
    private int orientations = 0;
    private int count;



    public static Tetromino T_SHAPE = new Tetromino("" +
            ".T.\n" +
            "TTT\n" +
            "...\n", 4);

    public static Tetromino I_SHAPE = new Tetromino("" +
            ".....\n" +
            ".....\n" +
            "IIII.\n" +
            ".....\n" +
            ".....\n", 2);


    public static Tetromino O_SHAPE = new Tetromino("" +
            ".OO\n" +
            ".OO\n" +
            "...\n", 1);


    //Constructor for creating new tetrominoes
    public Tetromino(String shape, int orientations) {

        Piece P = new Piece(shape);
        this.cells = P.cells;
        this.tetrominoShape = shape;
        this.orientations = orientations;


        for (int i = 0; i < this.cells.length; i++) {
            for (int j = 0; j < this.cells[0].length; j++) {
                if (this.cells[i][j] != '.') {
                    occupiedCells.add(new Coordinate(i, j));
                }
            }
        }


    }

    public Tetromino rotateRight() {

        if (orientations == 1) {
            return this;
        }

        Piece P = new Piece(tetrominoShape);

        Piece P2;
        if (tetrominoShape.contains(".I.")) {
            P2 = P.rotateLeft();
            count = 1;
        } else {
            P2 = P.rotateRight();

        }

        count++;

        Tetromino T2 = new Tetromino(P2.toString(), orientations);
        //return a new rotated Tetromino
        return T2;

    }

    public Tetromino rotateLeft() {

        if (orientations == 1) {
            return this;
        }

        Piece P = new Piece(tetrominoShape);
        Piece P2;

        if (tetrominoShape.contains("II.")) {
            P2 = P.rotateRight();
            count = 1;
        } else {
            P2 = P.rotateLeft();
        }

        count++;

        Tetromino T2 = new Tetromino(P2.toString(), orientations);
        //return a new rotated Tetromino
        return T2;

    }


    //Returns the present state of the Tetromino
    public String toString() {

        String s = "";
        for (int row = 0; row < cells.length; row++) {
            for (int col = 0; col < cells[0].length; col++) {

                s += cells[row][col];
            }
            s += "\n";
        }
        return s;
    }

}
