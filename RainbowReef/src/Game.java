import java.awt.*;
import java.awt.image.*;
import java.net.*;
import javax.swing.*;

abstract public class Game extends JApplet implements Runnable {
    private BufferedImage bufferedImage;
    private Thread thread;

    abstract public void draw(int width, int height, Graphics2D graphics2D);
    abstract public void imageLoop();

    @Override
    public void start() {
        thread = new Thread(this);
        thread.setPriority(Thread.MAX_PRIORITY);
        thread.start();
    }

    @Override
    public void run() {
        Thread thread = Thread.currentThread();
        setFocusable(true);
        while (this.thread == thread) {
            repaint();
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    @Override
    public void init() {
        imageLoop();
    }

    @Override
    public void paint(Graphics graphics) {
        Dimension dimension = getSize();
        Graphics2D graphics2D = paint2D(dimension.width, dimension.height);
        draw(dimension.width, dimension.height, graphics2D);
        //Draws screen
        graphics2D.dispose();
        graphics.drawImage(bufferedImage, 0, 0, this);
    }

    public Graphics2D paint2D(int width, int height) {
        Graphics2D graphics2D;
        if (bufferedImage == null) {
            bufferedImage = (BufferedImage) createImage(width, height);
        }
        graphics2D = bufferedImage.createGraphics();
        graphics2D.setBackground(getBackground());
        graphics2D.clearRect(0, 0, width, height);
        return graphics2D;
    }

    public Image drawSprite(String spriteName) {
        URL resource = this.getClass().getResource(spriteName);
        Image image = getToolkit().getImage(resource);
        try {
            MediaTracker mediaTracker = new MediaTracker(this);
            mediaTracker.addImage(image, 0);
            mediaTracker.waitForID(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }

    public BufferedImage bufferedImage(String spriteName) {
        Image image = drawSprite(spriteName);
        if (image instanceof BufferedImage) {
            return (BufferedImage) image;
        } else {
            BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null),
                    //Shows area around sprites to help collision (change to TYPE_INT_ARGB for regular)
                    BufferedImage.TYPE_INT_ARGB);
            Graphics2D graphics2D = bufferedImage.createGraphics();
            graphics2D.drawImage(image, 0, 0, image.getWidth(null), image.getHeight(null), null);
            graphics2D.dispose();
            return bufferedImage;
        }
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public void setBufferedImage(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }

    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }
}
