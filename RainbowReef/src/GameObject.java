import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;

abstract class GameObject {

    private int x;
    private int y;
    private int speed;
    private boolean finished;
    private boolean reset;
    private Image[] image;
    private int tick;
    private Events events;

    abstract void canMove();

    abstract void isDead();

    abstract void collide(TickingObject tickingObject);

    public void gameLoop() {}

    public GameObject(int x, int y, int tick, int speed, Image[] image, Events events) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.image = image;
        this.events = events;
        this.tick = tick;
    }

    public void update(int width, int height) {
        if (reset) {
            isDead();
        } else {
            gameLoop();
            canMove();
        }
    }

    public int getHeight() {
        return image[tick].getHeight(null);
    }

    public int getWidth() {
        return image[tick].getWidth(null);
    }

    public void keyListener(int event) {
        this.x += event;
    }

    public void tick() {
        tick++;
        if (tick >= image.length) {
            tick = 0;
        }
    }

    public boolean touchBlock(int x, int y, GameObject gameObject) {
        int r1 = gameObject.getHeight() / 2;
        int r2 = this.getHeight() / 2;
        for (int i = 0; i < this.getWidth() / 2; i += this.getHeight() / 2) {
            double newSphere = Math.sqrt(Math.pow((this.x + i - x), 2.0) + Math.pow((this.y - y), 2.0));
            if (newSphere < r1 + r2) {
                return true;
            }
            newSphere = Math.sqrt(Math.pow((this.x - i - x), 2.0) + Math.pow((this.y - y), 2.0));
            if (newSphere < r1 + r2) {
                return true;
            }
        }
        return false;
    }

    public boolean collision(GameObject gameObject) {
        return !isFinished() && touchBlock(gameObject.getX(), gameObject.getY(), gameObject);
    }

    public void draw(Graphics2D graphics2D, ImageObserver observer) {
        int width = image[tick].getWidth(observer);
        int height = image[tick].getHeight(observer);
        AffineTransform transform = new AffineTransform();
        transform.translate(x - width / 2, y - height / 2);
        if (!reset) {
            graphics2D.drawImage(image[tick], transform, observer);
        }
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public int getTick() {
        return tick;
    }

    public void setTick(int tick) {
        this.tick = tick;
    }

    public Image[] getImage() {
        return image;
    }

    public void setImage(Image[] image) {
        this.image = image;
    }

    public boolean isReset() {
        return reset;
    }

    public void setReset(boolean reset) {
        this.reset = reset;
    }

    public Events getEvents() {
        return events;
    }

    public void setEvents(Events events) {
        this.events = events;
    }
}