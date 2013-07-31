// Copyright (c) 2008-2012  Esko Luontola <www.orfjackal.net>
// You may use and modify this source code freely for personal non-commercial use.
// This source code may NOT be used as course material without prior written agreement.

package tetris;

import net.orfjackal.nestedjunit.NestedJUnit;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Esko Luontola
 */
@RunWith(NestedJUnit.class)
public class FallingPiecesTest extends Assert {


    private final Board board = new Board(6, 8);


    public class When_a_piece_is_dropped {

        @Before
        public void dropPiece() {
            board.drop(Tetromino.T_SHAPE);
        }

        @Test
        public void it_starts_from_top_middle() {
            assertEquals("" +
                    "....T...\n" +
                    "...TTT..\n" +
                    "........\n" +
                    "........\n" +
                    "........\n" +
                    "........\n", board.toString());
        }

        @Test
        public void at_most_one_tetromino_may_be_falling_at_a_time() {
            try {
                board.drop(Tetromino.T_SHAPE);
                fail();
            } catch (IllegalStateException e) {
                assertTrue(e.getMessage().contains("already falling"));
            }
            assertEquals("" +
                    "....T...\n" +
                    "...TTT..\n" +
                    "........\n" +
                    "........\n" +
                    "........\n" +
                    "........\n", board.toString());
        }
    }


    public class When_a_piece_reaches_the_bottom {

        @Before
        public void fallToLastRow() {
            board.drop(Tetromino.T_SHAPE);
            board.tick();
            board.tick();
            board.tick();
            board.tick();
        }

        @Test
        public void it_is_still_falling_on_the_last_row() {
            assertEquals("" +
                    "........\n" +
                    "........\n" +
                    "........\n" +
                    "........\n" +
                    "....T...\n" +
                    "...TTT..\n", board.toString());
            assertTrue(board.hasFalling());
        }

        @Test
        public void it_stops_when_it_hits_the_bottom() {
            board.tick();
            assertEquals("" +
                    "........\n" +
                    "........\n" +
                    "........\n" +
                    "........\n" +
                    "....T...\n" +
                    "...TTT..\n", board.toString());
            assertFalse(board.hasFalling());
        }
    }


    public class When_a_piece_lands_on_another_piece {

        @Before
        public void landOnAnother() {
            board.drop(Tetromino.T_SHAPE);
            board.tick();
            board.tick();
            board.tick();
            board.tick();
            board.tick();
            assertEquals("" +
                    "........\n" +
                    "........\n" +
                    "........\n" +
                    "........\n" +
                    "....T...\n" +
                    "...TTT..\n", board.toString());
            assertFalse(board.hasFalling());

            board.drop(Tetromino.T_SHAPE);
            board.tick();
            board.tick();
        }

        @Test
        public void it_is_still_falling_right_above_the_other_piece() {
            assertEquals("" +
                    "........\n" +
                    "........\n" +
                    "....T...\n" +
                    "...TTT..\n" +
                    "....T...\n" +
                    "...TTT..\n", board.toString());
            assertTrue(board.hasFalling());
        }

        @Test
        public void it_stops_when_it_hits_the_other_piece() {
            board.tick();
            assertEquals("" +
                    "........\n" +
                    "........\n" +
                    "....T...\n" +
                    "...TTT..\n" +
                    "....T...\n" +
                    "...TTT..\n", board.toString());
            assertFalse(board.hasFalling());
        }
    }

}
