package tetris;

/**
 * Created with IntelliJ IDEA.
 * User: snaware
 * Date: 6/27/13
 * Time: 10:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class Piece {
    public char[][] cells;
    private char[] cols;
    private char[][] temp;

    public Piece(String shape) {

        if (shape != null) {

            String rows[] = shape.split("\n");

            cells = new char[rows.length][rows[0].length()];

            for (int i = 0; i < rows.length; i++) {
                cols = rows[i].toCharArray();
                for (int j = 0; j < rows[i].length(); j++) {
                    cells[i][j] = cols[j];
                }

            }
        }

    }

    public Piece rotateRight() {
        if (cells != null) {
            final int i = cells.length;
            final int j = cells[0].length;
            temp = new char[j][i];
            for (int r = 0; r < i; r++) {
                for (int c = 0; c < j; c++) {
                    temp[c][i - 1 - r] = cells[r][c];
                }
            }

        }
        cells = temp;
        //We create a new piece from the present shape
        return new Piece(toString());
    }


    public Piece rotateLeft() {
        if (cells != null) {
            final int i = cells.length;
            final int j = cells[0].length;
            temp = new char[j][i];
            for (int r = 0; r < i; r++) {
                for (int c = 0; c < j; c++) {
                    temp[i - 1 - c][r] = cells[r][c];
                }
            }

        }
        cells = temp;
        //We create a new piece from the present shape
        return new Piece(toString());
    }


    //This returns the present state of the piece.
    @Override
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
