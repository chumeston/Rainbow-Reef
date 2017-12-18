import java.awt.event.*;
import java.util.*;

public class Events extends Observable {

    private Object brickObject, blockObject;
    private int blockType;

    public void collision(GameObject brickObject, GameObject blockObject) {
        blockType = 1;
        this.brickObject = brickObject;
        this.blockObject = blockObject;
        setChanged();
        this.notifyObservers(this);
    }

    public void keyPressed(KeyEvent keyEvent) {
        blockType = 2;
        this.blockObject = keyEvent;
        setChanged();
        notifyObservers(this);
    }

    public Object getBrickObject() {
        return brickObject;
    }

    public void setBrickObject(Object brickObject) {
        this.brickObject = brickObject;
    }

    public Object getBlockObject() {
        return blockObject;
    }

    public void setBlockObject(Object blockObject) {
        this.blockObject = blockObject;
    }

    public int getBlockType() {
        return blockType;
    }

    public void setBlockType(int blockType) {
        this.blockType = blockType;
    }
}