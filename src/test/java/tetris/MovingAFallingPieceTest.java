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
public class MovingAFallingPieceTest extends Assert {

    // TODO: a falling piece can be moved left

    private final Board board = new Board(7, 10);


    public class Move_a_piece_left {

        @Before
        public void dropPiece() {
            board.drop(Tetromino.T_SHAPE);
        }

        @Test
        public void a_falling_piece_is_moved_left() {
            board.moveLeft();
            board.tick();
            assertEquals("" +
                    "..........\n" +
                    "....T.....\n" +
                    "...TTT....\n" +
                    "..........\n" +
                    "..........\n" +
                    "..........\n" +
                    "..........\n", board.toString());

        }

        @Test
        public void move_I_Shape_Left() {
            board.down();
            board.drop(Tetromino.I_SHAPE);
            board.rotateRight();
            board.moveLeft();
            board.moveLeft();
            board.moveLeft();
            board.moveLeft();
            board.moveLeft();
            board.moveLeft();
            board.moveLeft();

            assertEquals("" +
                    "I.........\n" +
                    "I.........\n" +
                    "I.........\n" +
                    "I.........\n" +
                    "..........\n" +
                    ".....T....\n" +
                    "....TTT...\n", board.toString());
        }

        @Test
        public void move_left_over_the_board() {
            board.tick();
            board.moveLeft();
            board.moveLeft();
            board.moveLeft();
            board.moveLeft();
            board.moveLeft();
            board.moveLeft();
            assertEquals("" +
                    "..........\n" +
                    ".T........\n" +
                    "TTT.......\n" +
                    "..........\n" +
                    "..........\n" +
                    "..........\n" +
                    "..........\n", board.toString());
        }

        @Test
        public void a_falling_piece_is_moved_left_over_another_piece() {
            board.moveLeft();
            board.moveLeft();
            board.moveLeft();
            board.moveLeft();
            board.down();
            board.drop(Tetromino.O_SHAPE);
            board.tick();
            board.tick();
            board.tick();
            board.tick();
            board.tick();
            board.moveLeft();
            board.moveLeft();
            board.moveLeft();

            assertEquals("" +
                    "..........\n" +
                    "..........\n" +
                    "..........\n" +
                    "..........\n" +
                    "..........\n" +
                    ".T.OO.....\n" +
                    "TTTOO.....\n", board.toString());

        }


    }

    public class Move_a_piece_right {

        @Before
        public void dropPiece() {
            board.drop(Tetromino.T_SHAPE);
        }


        @Test
        public void a_falling_piece_is_moved_right() {
            board.tick();
            board.tick();
            board.moveRight();

            assertEquals("" +
                    "..........\n" +
                    "..........\n" +
                    "......T...\n" +
                    ".....TTT..\n" +
                    "..........\n" +
                    "..........\n" +
                    "..........\n", board.toString());

        }

        @Test
        public void move_right_over_the_board() {
            board.tick();
            board.moveRight();
            board.moveRight();
            board.moveRight();
            board.moveRight();
            board.moveRight();
            board.moveRight();
            assertEquals("" +
                    "..........\n" +
                    "........T.\n" +
                    ".......TTT\n" +
                    "..........\n" +
                    "..........\n" +
                    "..........\n" +
                    "..........\n", board.toString());
        }


        @Test
        public void a_falling_piece_is_moved_right_over_another_piece() {
            board.moveRight();
            board.moveRight();
            board.moveRight();
            board.moveRight();
            board.down();
            board.drop(Tetromino.O_SHAPE);
            board.tick();
            board.tick();
            board.tick();
            board.tick();
            board.tick();
            board.moveRight();
            board.moveRight();
            board.moveRight();
            board.moveRight();

            assertEquals("" +
                    "..........\n" +
                    "..........\n" +
                    "..........\n" +
                    "..........\n" +
                    "..........\n" +
                    ".....OO.T.\n" +
                    ".....OOTTT\n", board.toString());

        }


    }


    public class Move_a_piece_down {

        @Before
        public void dropPiece() {
            board.drop(Tetromino.O_SHAPE);
        }


        @Test
        public void a_falling_piece_is_moved_down() {
            board.down();

            assertEquals("" +
                    "..........\n" +
                    "..........\n" +
                    "..........\n" +
                    "..........\n" +
                    "..........\n" +
                    ".....OO...\n" +
                    ".....OO...\n", board.toString());

        }

        @Test
        public void a_falling_piece_cannot_move_down_over_the_table() {
            board.down();
            board.down();

            assertEquals("" +
                    "..........\n" +
                    "..........\n" +
                    "..........\n" +
                    "..........\n" +
                    "..........\n" +
                    ".....OO...\n" +
                    ".....OO...\n", board.toString());
        }

        @Test
        public void a_falling_piece_cannot_move_down_over_another_piece() {
            board.down();
            board.drop(Tetromino.T_SHAPE);
            board.down();


            assertEquals("" +
                    "..........\n" +
                    "..........\n" +
                    "..........\n" +
                    ".....T....\n" +
                    "....TTT...\n" +
                    ".....OO...\n" +
                    ".....OO...\n", board.toString());
        }


    }

    // TODO: it can not be moved right if another piece is in the way

    // P.S. Take into consideration, that part of the piece's area may be empty cells.
    // Only non-empty cells should take part in the collision checks.
}
