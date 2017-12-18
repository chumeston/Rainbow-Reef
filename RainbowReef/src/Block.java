import java.awt.*;
import java.util.*;

public class Block extends TickingObject {

    private MapLevel mapLevel = new MapLevel();
    private int hp = 1;
    private int blockType = 0;

    public Block(int x, int y, int tick, int speed, Image[] image, Events events, int blockType) {
        super(x, y, tick, speed, image, events, 0, 0);
        this.blockType = blockType;
        if (blockType == 3) {
            hp = hp * 2;
        }
    }

    @Override
    public void NewCollision(GameObject gameObject) {
        if (gameObject instanceof Star) {
            if (blockType == 1) {
                return;
            } else if (blockType == 2) {
                if (MainGame.player != null) {
                    MainGame.player.get(0).setDestroy(MainGame.player.get(0).getDestroy() + 1);
                }
            }
            hp -= ((Star) gameObject).getCollide();
            if (hp <= 0) {
                this.setReset(true);
            }
        }
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
    public void canMove() {}

    @Override
    public void isDead() {
        this.setFinished(true);
    }

    @Override
    public void collide(TickingObject single) {}

    public MapLevel getMapLevel() {
        return mapLevel;
    }

    public void setMapLevel(MapLevel mapLevel) {
        this.mapLevel = mapLevel;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getBlockType() {
        return blockType;
    }

    public void setBlockType(int blockType) {
        this.blockType = blockType;
    }
}