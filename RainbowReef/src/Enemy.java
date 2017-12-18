import java.awt.*;
import java.util.*;

public class Enemy extends TickingObject {

    private int blockType = 0;
    private int destroy;

    public Enemy(int x, int y, int tick, int speed, Image[] image, Events events, int destroy, int collide) {
        super(x, y, tick, speed, image, events, destroy, collide);
    }

    @Override
    //Use this method for changing level 2 Enemy Position
    public void canMove() {}

    @Override
    public void NewCollision(GameObject caller) {
        if (caller instanceof Star) {
            destroy -= ((Star) caller).getCollide();
            if (destroy <= 0) {
                this.setReset(true);
            }
        }
    }

    @Override
    public void update(int width, int height) {
        super.update(width, height);
        this.tick();
    }

    @Override
    public void update(Observable observable, Object object) {
        Events events = (Events) object;
        if (events.getBlockType() == 1) {
            if (events.getBlockObject() == this) {
                if (events.getBrickObject() instanceof TickingObject) {
                    ((TickingObject) events.getBrickObject()).collide(this);
                }
                NewCollision((GameObject) events.getBrickObject());
            }
        }
    }

    @Override
    public void isDead() {
        this.setFinished(true);
    }

    public int getBlockType() {
        return blockType;
    }

    public void setBlockType(int blockType) {
        this.blockType = blockType;
    }

    @Override
    public int getDestroy() {
        return destroy;
    }

    @Override
    public void setDestroy(int destroy) {
        this.destroy = destroy;
    }

}
