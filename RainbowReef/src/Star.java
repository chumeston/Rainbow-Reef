import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.*;
import java.net.*;
import java.util.*;

public class Star extends TickingObject {

    private ArrayList<ArrayList> arrayList;
    private Direction direction = new Direction();
    private URL[] soundUrl;

    public Star(int x, int y, int tick, int speed, Image[] image, Events events, int destroy, int collide, ArrayList<ArrayList> arrayList, URL[] soundUrl) {
        super(x, y, tick, speed, image, events, destroy, collide);
        this.arrayList = arrayList;
        this.soundUrl = soundUrl;
    }

    @Override
    public void NewCollision(GameObject gameObject) {}

    @Override
    public void canMove() {
        int newX = (int) (Math.cos(direction.getDirection()) * getSpeed());
        int newY = (int) (Math.sin(direction.getDirection()) * getSpeed());
        setX(getX() + newX);
        setY(getY() - newY);
        if (getY() > 460) {
            setReset(true);
            AudioInputStream audioInputStream;
            Clip clip;
            try {
                audioInputStream = AudioSystem.getAudioInputStream(this.soundUrl[3]);
                clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();
            } catch(Exception e) {
                e.printStackTrace();
            }
            return;
        }
        for (GameObject gameObject : (ArrayList<GameObject>) arrayList.get(0)) {
            if (gameObject == this) {
                continue;
            }
            if (this.collision(gameObject)) {
                getEvents().collision(this, gameObject);
            }
        }

        for (GameObject gameObject : (ArrayList<GameObject>) arrayList.get(1)) {
            if (this.collision(gameObject)) {
                getEvents().collision(this, gameObject);
            }
        }

        for (GameObject gameObject : (ArrayList<GameObject>) arrayList.get(2)) {
            if (this.collision(gameObject)) {
                getEvents().collision(this, gameObject);
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
            if (events.getBlockObject( ) == this) {
                NewCollision((GameObject)events.getBlockObject());
            }
        }
    }

    public void collide(TickingObject tickingObject) {
        if (tickingObject instanceof Block) {
            if (getX() > tickingObject.getX() - tickingObject.getWidth() / 2
                    && this.getX() < tickingObject.getX() + tickingObject.getWidth() / 2 ) {

                //If we don't multiply by 2, the Star will never come back
                direction.setDirection(2 * Math.PI - direction.getDirection());
            } else {
                direction.setDirection(Math.PI - direction.getDirection());
            }

            //Needs to be here to calculate score!
            if (((Block) tickingObject).getBlockType() != 1) {
                MainGame.score += 10;
            }

            AudioInputStream audioInputStream;
            Clip clip;
            try {
                audioInputStream = AudioSystem.getAudioInputStream( this.soundUrl[0]);
                clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();
            } catch (Exception e) {
                e.printStackTrace( );
            }
        } else if (tickingObject instanceof Enemy) {
            MainGame.score += 100;
            AudioInputStream audioInputStream;
            Clip clip;
            try {
                audioInputStream = AudioSystem.getAudioInputStream(this.soundUrl[1]);
                clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();
            } catch(Exception e) {
                e.printStackTrace();
            }
        } else if (tickingObject instanceof Player) {
            //Direction for when moving left
            if (((Player) tickingObject).isMovingRight() && !((Player) tickingObject).isMovingLeft()) {
                direction.setDirection(2 * Math.PI - direction.getDirection() - (3.0 / 12.0) * Math.PI);

            } else if (((Player) tickingObject).isMovingLeft() && !((Player) tickingObject).isMovingRight()) {
                //Direction for when moving right
                direction.setDirection( 2 * Math.PI - direction.getDirection() + (3.0 / 12.0 ) * Math.PI);
            } else {
                //Try to set the direction our block is facing if we're not moving
                direction.setDirection(2 * Math.PI - direction.getDirection());
            }
            AudioInputStream audioInputStream;
            Clip clip;
            try {
                audioInputStream = AudioSystem.getAudioInputStream(this.soundUrl[2]);
                clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void isDead() {
        this.setFinished(true);
    }

    public ArrayList<ArrayList> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<ArrayList> arrayList) {
        this.arrayList = arrayList;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}