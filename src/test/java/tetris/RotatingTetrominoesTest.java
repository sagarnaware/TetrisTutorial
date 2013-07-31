// Copyright (c) 2008-2013  Esko Luontola <www.orfjackal.net>
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
public class RotatingTetrominoesTest extends Assert {


    private Tetromino shape;


    public class All_shape_instances {

        @Before
        public void createAnyShape() {
            shape = Tetromino.T_SHAPE;
        }

        @Test
        public void are_immutable() {
            String original = shape.toString();
            shape.rotateRight();
            assertEquals(original, shape.toString());
            shape.rotateLeft();
            assertEquals(original, shape.toString());
        }
    }


    public class The_T_shape {

        @Before
        public void createTShape() {
            shape = Tetromino.T_SHAPE;
        }

        @Test
        public void is_shaped_like_T() {
            assertEquals("" +
                    ".T.\n" +
                    "TTT\n" +
                    "...\n", shape.toString());
        }

        @Test
        public void can_be_rotated_right_3_times() {
            shape = shape.rotateRight();
            assertEquals("" +
                    ".T.\n" +
                    ".TT\n" +
                    ".T.\n", shape.toString());
            shape = shape.rotateRight();
            assertEquals("" +
                    "...\n" +
                    "TTT\n" +
                    ".T.\n", shape.toString());
            shape = shape.rotateRight();
            assertEquals("" +
                    ".T.\n" +
                    "TT.\n" +
                    ".T.\n", shape.toString());
        }

        @Test
        public void can_be_rotated_left_3_times() {
            shape = shape.rotateLeft();
            assertEquals("" +
                    ".T.\n" +
                    "TT.\n" +
                    ".T.\n", shape.toString());
            shape = shape.rotateLeft();
            assertEquals("" +
                    "...\n" +
                    "TTT\n" +
                    ".T.\n", shape.toString());
            shape = shape.rotateLeft();
            assertEquals("" +
                    ".T.\n" +
                    ".TT\n" +
                    ".T.\n", shape.toString());
        }

        @Test
        public void rotating_it_4_times_will_go_back_to_the_original_shape() {
            String originalShape = shape.toString();
            shape = shape.rotateRight().rotateRight().rotateRight().rotateRight();
            assertEquals(originalShape, shape.toString());
            shape = shape.rotateLeft().rotateLeft().rotateLeft().rotateLeft();
            assertEquals(originalShape, shape.toString());
        }

    }


    public class The_I_shape {

        @Before
        public void createIShape() {
            shape = Tetromino.I_SHAPE;
        }

        @Test
        public void is_shaped_like_I() {
            assertEquals("" +
                    ".....\n" +
                    ".....\n" +
                    "IIII.\n" +
                    ".....\n" +
                    ".....\n", shape.toString());
        }

        @Test
        public void can_be_rotated_right_once() {
            shape = shape.rotateRight();
            assertEquals("" +
                    "..I..\n" +
                    "..I..\n" +
                    "..I..\n" +
                    "..I..\n" +
                    ".....\n", shape.toString());
        }

        @Test
        public void can_be_rotated_left_once() {
            shape = shape.rotateLeft();
            assertEquals("" +
                    "..I..\n" +
                    "..I..\n" +
                    "..I..\n" +
                    "..I..\n" +
                    ".....\n", shape.toString());

        }

        @Test
        public void rotating_it_twice_will_get_back_to_the_original_shape() {
            String originalShape = shape.toString();
            shape = shape.rotateRight();
            shape = shape.rotateRight();
            assertEquals(originalShape, shape.toString());
            shape = shape.rotateLeft().rotateLeft();
            assertEquals(originalShape, shape.toString());
        }
    }


    public class The_O_shape {

        @Before
        public void createOShape() {
            shape = Tetromino.O_SHAPE;
        }

        @Test
        public void is_shaped_like_O() {
            assertEquals("" +
                    ".OO\n" +
                    ".OO\n" +
                    "...\n", shape.toString());
        }

        @Test
        public void can_not_be_rotated_right() {
            shape = shape.rotateRight();
            assertEquals("" +
                    ".OO\n" +
                    ".OO\n" +
                    "...\n", shape.toString());
        }

        @Test
        public void can_not_be_rotated_left() {
            shape = shape.rotateLeft();
            assertEquals("" +
                    ".OO\n" +
                    ".OO\n" +
                    "...\n", shape.toString());
        }
    }


}
