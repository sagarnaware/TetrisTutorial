// Copyright (c) 2008-2012  Esko Luontola <www.orfjackal.net>
// You may use and modify this source code freely for personal non-commercial use.
// This source code may NOT be used as course material without prior written agreement.

package tetris;

import net.orfjackal.nestedjunit.NestedJUnit;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(NestedJUnit.class)
public class RotatingAFallingPieceTest extends Assert {

    private final Board board = new Board(7, 10);


    @Before
    public void dropPiece() {
        board.drop(Tetromino.T_SHAPE);
    }

    @Test
    public void a_falling_piece_is_rotated_left() {
        board.tick();
        board.rotateLeft();

        assertEquals("" +
                "..........\n" +
                ".....T....\n" +
                "....TT....\n" +
                ".....T....\n" +
                "..........\n" +
                "..........\n" +
                "..........\n", board.toString());

    }

    @Test
    public void a_falling_piece_is_rotated_right() {
        board.tick();
        board.rotateRight();

        assertEquals("" +
                "..........\n" +
                ".....T....\n" +
                ".....TT...\n" +
                ".....T....\n" +
                "..........\n" +
                "..........\n" +
                "..........\n", board.toString());

    }

    @Test
    public void a_rotated_falling_piece_is_moved_right() {
        board.tick();
        board.rotateRight();
        board.moveRight();
        board.moveRight();
        board.moveRight();

        assertEquals("" +
                "..........\n" +
                "........T.\n" +
                "........TT\n" +
                "........T.\n" +
                "..........\n" +
                "..........\n" +
                "..........\n", board.toString());

    }

    @Test
    public void cannot_rotate_falling_piece_when_no_room() {
        board.rotateRight();
        board.moveRight();
        board.moveRight();
        board.moveRight();
        board.down();
        board.drop(Tetromino.T_SHAPE);
        board.down();
        board.drop(Tetromino.I_SHAPE);
        board.rotateLeft();
        board.moveRight();
        board.moveRight();
        board.tick();
        board.tick();
        board.tick();
        board.rotateLeft();
        board.rotateRight();


        assertEquals("" +
                "..........\n" +
                "..........\n" +
                "..........\n" +
                ".......I..\n" +
                ".......IT.\n" +
                ".....T.ITT\n" +
                "....TTTIT.\n", board.toString());

    }

    @Test
    public void piece_is_wallkicked_when_against_left_wall() {
        board.rotateRight();
        board.moveLeft();
        board.moveLeft();
        board.moveLeft();
        board.moveLeft();
        board.moveLeft();
        board.rotateLeft();

        assertEquals("" +
                ".T........\n" +
                "TTT.......\n" +
                "..........\n" +
                "..........\n" +
                "..........\n" +
                "..........\n" +
                "..........\n", board.toString());

    }

    @Test
    public void piece_is_wallkicked_when_against_right_wall() {
        board.rotateLeft();
        board.moveRight();
        board.moveRight();
        board.moveRight();
        board.moveRight();
        board.rotateRight();

        assertEquals("" +
                "........T.\n" +
                ".......TTT\n" +
                "..........\n" +
                "..........\n" +
                "..........\n" +
                "..........\n" +
                "..........\n", board.toString());

    }

    @Test
    public void I_shape_can_be_wallkicked_too() {
        board.down();
        board.drop(Tetromino.I_SHAPE);
        board.rotateRight();
        board.moveLeft();
        board.moveLeft();
        board.moveLeft();
        board.moveLeft();
        board.moveLeft();
        board.rotateRight();

        assertEquals("" +
                "..........\n" +
                "..........\n" +
                "IIII......\n" +
                "..........\n" +
                "..........\n" +
                ".....T....\n" +
                "....TTT...\n", board.toString());

    }


    // See: http://bsixcentdouze.free.fr/tc/tgm-en/tgm.html
    // http://bsixcentdouze.free.fr/tc/tgm-en/img/wallkick1.png
    // http://bsixcentdouze.free.fr/tc/tgm-en/img/wallkick2.png
    // http://bsixcentdouze.free.fr/tc/tgm-en/img/wallkick3.png
}
