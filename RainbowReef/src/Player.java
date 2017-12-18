import java.awt.*;
import java.awt.event.*;
import java.util.*;

abstract public class Player extends TickingObject implements Observer {

    private int left, right;
    private boolean movingLeft, movingRight;
    private World world;
    private int blockType;
    private Player player;

    public Player(int x, int y, int tick, int speed, Image[] image, Events events,
                  int destroy, int collide, int left, int right, World world, int blockType) {

        super(x, y, tick, speed, image, events, destroy, collide);
        this.left = left;
        this.right = right;
        this.world = world;
        this.blockType = blockType;
    }

    @Override
    public void update(Observable observable, Object object) {
        Events events = (Events) object;
        if (events.getBlockType() == 1) {
            if (events.getBlockObject() == this) {
                NewCollision((GameObject) events.getBrickObject());
            }
        } else if (events.getBlockType() == 2) {
            action((KeyEvent) events.getBlockObject());
        }
    }

    public void keyPressed(KeyEvent keyEvent) {
        if (keyEvent.getID() == KeyEvent.KEY_PRESSED) {
            if (keyEvent.getKeyCode() == getLeft()) {
                movingLeft = true;
            } else if (keyEvent.getKeyCode() == getRight()) {
                movingRight = true;
            }
        }
        if (keyEvent.getID() == KeyEvent.KEY_RELEASED) {
            if (keyEvent.getKeyCode() == getLeft()) {
                movingLeft = false;
            } else if (keyEvent.getKeyCode() == getRight()) {
                movingRight = false;
            }
        }
    }

    public void action(KeyEvent keyEvent) {
        int eventKeyCode = keyEvent.getKeyCode();
        if (eventKeyCode == left || eventKeyCode == right) {
            keyPressed(keyEvent);
        }
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public boolean isMovingLeft() {
        return movingLeft;
    }

    public void setMovingLeft(boolean movingLeft) {
        this.movingLeft = movingLeft;
    }

    public boolean isMovingRight() {
        return movingRight;
    }

    public void setMovingRight(boolean movingRight) {
        this.movingRight = movingRight;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public int getBlockType() {
        return blockType;
    }

    public void setBlockType(int blockType) {
        this.blockType = blockType;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
