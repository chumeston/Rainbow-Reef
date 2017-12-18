import java.awt.*;
import java.util.*;

abstract class TickingObject extends GameObject implements Observer {

    private int damage;
    private int destroy;
    private int collide;

    abstract public void NewCollision(GameObject gameObject);

    public TickingObject(int x, int y, int tick, int speed, Image[] image, Events events, int destroy, int collide) {
        super(x, y, tick, speed, image, events);
        this.destroy = destroy;
        this.collide = collide;
        events.addObserver(this);
    }

    public void damage(int damage) {
        this.damage += damage;
    }

    public void collide(TickingObject tickingObject) {
        tickingObject.damage(getCollide());
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getDestroy() {
        return destroy;
    }

    public void setDestroy(int destroy) {
        this.destroy = destroy;
    }

    public int getCollide() {
        return collide;
    }

    public void setCollide(int collide) {
        this.collide = collide;
    }
}
