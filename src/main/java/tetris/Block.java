package tetris;

/**
 * Created with IntelliJ IDEA.
 * User: snaware
 * Date: 6/25/13
 * Time: 2:41 PM
 * To change this template use File | Settings | File Templates.
 */
//This class specifies the structure of the block

public class Block {


    public char getType() {
        return type;
    }

    protected char type;
    private int xpos;
    private int ypos;

    public Block(char type) {
        this.type = type;
    }

}
