package tetris;

import com.sun.istack.internal.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Board {

    private final int rows;
    private final int columns;
    private char cells[][];
    private int fallingPieceXPosition;
    private int fallingPieceYPosition;
    private char[][] tetrominoCells;//initializing to 1 to account for a Block
    private Tetromino droppingShape;
    private boolean falling = false;

    public Board(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;

        cells = new char[rows][columns];

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                cells[row][col] = '.';
            }

        }
    }

    //The following method returns true if the board has a falling block
    public boolean hasFalling() {
        return falling;
    }

    //This method is a drop action to be performed on a Piece or a Tetromino
    public void drop(Tetromino T) {

        if (this.hasFalling()) {
            throw new IllegalStateException("already falling");
        }

        if (T != null) {
            droppingShape = T;
            tetrominoCells = T.cells;
            fallingPieceYPosition = getFallingYPosition();
            fallingPieceXPosition = 0;
            copyTetrominoToBoard();
            falling = true;
        }
    }

    private int getFallingYPosition() {
        double subtraction = cells[0].length - tetrominoCells[0].length;
        int startAtCell = (int) Math.ceil(subtraction / 2);
        return startAtCell;
    }


    private void copyTetrominoToBoard(char c) {
        for (Coordinate single : droppingShape.occupiedCells) {
            cells[fallingPieceXPosition + single.row][fallingPieceYPosition + single.column] = c;
        }
    }

    private void copyTetrominoToBoard() {
        for (Coordinate single : droppingShape.occupiedCells) {
            cells[fallingPieceXPosition + single.row][fallingPieceYPosition + single.column] = droppingShape.cells[single.row][single.column];
        }
    }



    //This method is a drop action to be performed on a block
    public void drop(Block block) {

        droppingShape = new Tetromino(Character.toString(block.getType()), 1);
        drop(droppingShape);

    }

    private void clearCurrentOccupiedCells() {
        copyTetrominoToBoard('.');
    }

    public void tick() {

        if (!hasFalling()) {
            return;
        }

        clearCurrentOccupiedCells();
        if (fallingPieceXPosition + tetrominoCells.length > rows) {
            //We dont want to move the block beyond last row.
            //We are at the last row. do the last copy here.
            // Set falling to false as the block is is settled in its final position
            falling = false;
            copyTetrominoToBoard();

            //We can put mechanism to clear row here
        } else if (lowerRowOccupied()) {
            copyTetrominoToBoard();
            falling = false;

        } else {
            copyCellsToLowerRow();
        }

    }

    private void copyCellsToLowerRow() {
        //Set falling x position to the next row
        fallingPieceXPosition += 1;
        copyTetrominoToBoard();
    }

    private boolean lowerRowOccupied() {

        for (Coordinate single : droppingShape.occupiedCells) {
            int rowToBeCopied = fallingPieceXPosition + 1 + single.row;
            int columnToBeCopied = fallingPieceYPosition + single.column;

            //Check if the next row goes out of the board boundry ot
            if ((rowToBeCopied >= cells.length) || (cells[rowToBeCopied][columnToBeCopied] != '.')) {
                return true;
            }
        }
        // if we reach here, we know the next row is not occupied
        return false;

    }

    private boolean shouldMoveRight() {

        for (Coordinate single : droppingShape.occupiedCells) {

            int rowToBeCopied = fallingPieceXPosition + single.row;
            int columnToBeCopied = fallingPieceYPosition + single.column + 1;//check the next column

            if (columnToBeCopied >= cells[0].length) {
                //if the column is outside the board returm false
                return false;
            } else {
                if (cells[rowToBeCopied][columnToBeCopied] != '.') {
                    return false;
                }
            }
        }
        // if we reach here, we know that the next column is free. return true here
        return true;
    }

    public void moveRight() {

        clearCurrentOccupiedCells();
        if (shouldMoveRight()) {
            //If the next column is free, increment starting cell and copy tetromino to board
            fallingPieceYPosition = fallingPieceYPosition + 1;
        }
        copyTetrominoToBoard();

    }

    private boolean shouldMoveLeft() {

        for (Coordinate single : droppingShape.occupiedCells) {

            int rowToBeCopied = fallingPieceXPosition + single.row;
            int columnToBeCopied = fallingPieceYPosition + single.column - 1;//check the left column

            if (columnToBeCopied < 0) {
                //if the column is outside the board returm false
                return false;
            } else {
                if (cells[rowToBeCopied][columnToBeCopied] != '.') {
                    return false;
                }
            }
        }
        // if we reach here, we know that the left column is free. return true here
        return true;
    }

    public void moveLeft() {
        clearCurrentOccupiedCells();
        if (shouldMoveLeft()) {
            //If the left column is free, decrement starting cell and copy tetromino to board
            fallingPieceYPosition = fallingPieceYPosition - 1;
        }
        copyTetrominoToBoard();

    }

    public void down() {
        //We will use tick() method to implement the down mechanism
        while (falling) {
            tick();
        }
    }

    public void rotateLeft() {
        if (hasFalling()) {
            //clear present tetromino location before rotating
            clearCurrentOccupiedCells();
            List<Coordinate> coordinates;
            List<Integer> leftWallDifference = new ArrayList<Integer>();
            List<Integer> rightWallDifference = new ArrayList<Integer>();

            //We need to check if the rotated tetromino can be copied to the board
            // Copy to board only if the non '.' cells are free.
            Tetromino temp = droppingShape.rotateLeft();
            boolean shouldCopyRotated = true;
            coordinates = temp.occupiedCells;
            int difference = generateDifference(leftWallDifference, rightWallDifference, coordinates);

            for (Coordinate single : coordinates) {

                int rowToBeCopied = fallingPieceXPosition + single.row;
                int columnToBeCopied = fallingPieceYPosition + single.column;

                if (cells[rowToBeCopied][columnToBeCopied + difference] != '.') {
                    shouldCopyRotated = false;
                }
            }

            if (shouldCopyRotated) {
                droppingShape = temp;
            }

            tetrominoCells = droppingShape.cells;
            List<Coordinate> coordinatesToBeCopied = droppingShape.occupiedCells;
            for (Coordinate single : coordinatesToBeCopied) {
                cells[fallingPieceXPosition + single.row][fallingPieceYPosition + single.column + difference] = droppingShape.cells[single.row][single.column];
            }

        }
    }


    public void rotateRight() {
        if (hasFalling()) {
            //clear present tetromino location before rotating
            clearCurrentOccupiedCells();
            List<Coordinate> coordinates;
            List<Integer> leftWallDifference = new ArrayList<Integer>();
            List<Integer> rightWallDifference = new ArrayList<Integer>();

            //We need to check if the rotated tetromino can be copied to the boad
            // Copy to board only if the non '.' cells are free.
            Tetromino temp = droppingShape.rotateRight();
            boolean shouldCopyRotated = true;
            coordinates = temp.occupiedCells;

            int difference = generateDifference(leftWallDifference, rightWallDifference, coordinates);

            for (Coordinate single : coordinates) {

                int rowToBeCopied = fallingPieceXPosition + single.row;
                int columnToBeCopied = fallingPieceYPosition + single.column;

                if (cells[rowToBeCopied][columnToBeCopied + difference] != '.') {
                    shouldCopyRotated = false;
                }
            }

            if (shouldCopyRotated) {
                droppingShape = temp;
            }

            tetrominoCells = droppingShape.cells;

            for (Coordinate single : droppingShape.occupiedCells) {
                cells[fallingPieceXPosition + single.row][fallingPieceYPosition + single.column + difference] = droppingShape.cells[single.row][single.column];
            }

        }
    }

    int generateDifference(List<Integer> leftWallDifference, List<Integer> rightWallDifference, List<Coordinate> coordinates){

        //Now we know which are occupied cells inside the tetromino
        //we will check if the corresponding cells on the board are occupied, if not, we will copy the new rotated tetromino
        for (Coordinate single : coordinates) {

            int columnToBeCopied = fallingPieceYPosition + single.column;
            //check if we have a wallkick situation and handle it
            leftWallDifference.add(handleWallkick(columnToBeCopied));
            rightWallDifference.add(handleWallkick(columnToBeCopied));
        }

        return Collections.max(leftWallDifference) + Collections.min(rightWallDifference);

    }


    private int handleWallkick(int columnToBeCopied) {

        int retvalue = 0;

        //check if wallkick should be done on the left wall of the board
        if (columnToBeCopied < 0) {
            retvalue = 0 - columnToBeCopied;
            return retvalue;
        }

        //check if wallkick should be done on the right wall of the board
        if (columnToBeCopied >= cells[0].length) {
            retvalue = cells[0].length - 1 - columnToBeCopied;
            return retvalue;
        }

        return retvalue;
    }


    //This returns the present state of the board.
    public String toString() {
        String s = "";
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {

                s += cells[row][col];
            }
            s += "\n";
        }
        return s;
    }


}
