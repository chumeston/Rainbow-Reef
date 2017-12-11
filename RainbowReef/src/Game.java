import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.Graphics;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JFrame;


public class Game extends Canvas implements Runnable {

    static GraphicsDevice graphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public static int gameWidth = (int) screenSize.getWidth();
    public static int gameHeight = (int) screenSize.getHeight();

    public static final int width = gameWidth;
    public static final int height = gameHeight;

    public static boolean running = false;

    public int ammo1 = 10;
    public int ammo2 = 10;

    boolean keyW = false;
    boolean keyA = false;
    boolean keyD = false;
    boolean keyS = false;

    boolean keyLeft = false;
    boolean keyRight = false;
    boolean keyDown = false;
    boolean keyUp = false;

    private Thread thread;

    private BufferedImage gameBackground, walls, button, spritesheet, playBackground, playerBlock, breakWall, ammo;

    private BufferImageLoader loader;

    public BufferedImage bulletTank1;

    public BufferedImage bulletTank2;

    //Controller controls;

    Button playButton;
    Button quitButton;
    //GlobalTexture tex;
    Menu menu;
    public static Player player;
    //public static Player p2;

    /*
    public int score = 0;
    public int score2 = 0;
    public int hp1 = 100;
    public int hp2 = 100;

*/

    private int gamestate;

    static Sound sound = new Sound();

    private void init() {

        //addMouseListener(new MouseInput(this));
        addKeyListener(new KeyInput(this));

        //sound.playSound("res/menusound.wav");
        loader = new BufferImageLoader();
        try {

            //gameBackground = loader.loadImage("res/menu.png");
            //button = loader.loadImage("res/spritesheetbutton.png");
            //spritesheet = loader.loadImage("res/tankspritesheet.png");
            //bulletTank1 = loader.loadImage("res/bluebullet.png");
            //bulletTank2 = loader.loadImage("res/redbullet.png");
            //walls = loader.loadImage("res/wall.png");
            //breakWall = loader.loadImage("res/breakablewall.png");
            //ammo = loader.loadImage("res/ammo.png");
            playBackground = loader.loadImage("res/Background1.bmp");
            playerBlock = loader.loadImage("res/Katch.gif");

        } catch (IOException e) {
            e.printStackTrace();
        }

        //tex = new GlobalTexture(this);
        //controls = new Controller(this, tex);
        //menu = new Menu();
        //playButton = new Button(300, 350, tex).setTyped(1);
        //quitButton = new Button(300, 450, tex).setTyped(3);
        //p2 = new Player(250, 440, tex);
    }

    private synchronized void start() {
        if (running) {
            return;
        }
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    private synchronized void stop() {
        if (!running) {
            return;
        }
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.exit(1);

    }

    public void tick() {
        //p.tick();
        //p2.tick();
        //controls.tick();
    }

    public void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(4);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        g.drawImage(playBackground, 0, 0, null);
        g.drawImage(playerBlock, 200, 440, null);

            //p.render(g);
            //p2.render(g);
            //controls.render(g);

            //Font font2 = new Font("Calibri", Font.BOLD, 16);
            // g.setFont(font2);
            // g.setColor(Color.blue);
            //g.drawString("Tank 1", (int) p.getX() + 6, (int) p.getY());
            //g.setColor(Color.red);
            //g.drawString("Tank 2", (int) p2.getX() + 6, (int) p2.getY());

            /*
            if (hp1 > 0) {
                g.setColor(Color.blue);
                g.setFont(new Font("Calibri", Font.BOLD, 16));
                g.drawString("Ammo: " + ammo1, 140, 50);
                g.drawString("Score: " + score, 40, 50);
                g.setColor(Color.gray);
                g.fillRect(2, 5, 200, 32);
                g.setColor(Color.blue);
                g.fillRect(2, 5, hp1 * 2, 32);
                g.setColor(Color.black);
                g.drawRect(2, 5, 200, 32);
            } else {
                g.setColor(Color.red);
                score2++;
                hp1 = 100;
                hp2 = 100;
                p = new Player(980, 440, tex);
                p2 = new Player(250, 440, tex);
                ammo1 = 10;
                ammo2 = 10;
            }
            if (hp2 > 0) {
                g.setColor(Color.red);
                g.setFont(new Font("Calibri", Font.BOLD, 16));
                g.drawString("Ammo: " + ammo2, 140, 110);
                g.drawString("Score: " + score2, 40, 110);
                g.setColor(Color.gray);
                g.fillRect(2, 60, 200, 32);
                g.setColor(Color.red);
                g.fillRect(2, 60, hp2 * 2, 32);
                g.setColor(Color.black);
                g.drawRect(2, 60, 200, 32);

            } else {
                g.setColor(Color.blue);
                p = new Player(980, 440, tex);
                p2 = new Player(250, 440, tex);
                score++;
                hp2 = 100;
                hp1 = 100;
                ammo1 = 10;
                ammo2 = 10;
            }
        }
*/
            g.dispose();
            bs.show();

        }

    @Override
    public void run() {

        init();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        int updates = 0;
        int frames = 0;
        long timer = System.currentTimeMillis();
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                tick();
                updates++;
                delta--;
            }
            render();
            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println(updates + " tick : fps " + frames);
                updates = 0;
                frames = 0;
            }

        }
        stop();
    }

    public static void main(String args[]) {
        JFrame frame = new JFrame("RainbowReef by Mark and Corey");
        Game game = new Game();
        frame.add(game);
        frame.setFocusable(true);
        frame.setUndecorated(true);
        frame.setSize(640, 480);

        try {
            //graphicsDevice.setFullScreenWindow(frame);
        } catch (Exception e) {
            e.printStackTrace();
        }

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.pack();
        game.start();
    }

    public BufferedImage getButtons() {
        return button;
    }

    public BufferedImage getSpriteSheet() {
        return spritesheet;
    }

    public int getGW() {
        return gameWidth;
    }

    public int getGH() {
        return gameHeight;
    }


    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        /*
        if (key == KeyEvent.VK_W) {
            if (p.isCanMoveUp()) {
                p.setVelY(-5);
                p.setDirection(Direction.UP);
                keyW = true;
            }
        }
        if (key == KeyEvent.VK_A) {
            if (p.isCanMoveLeft()) {
                p.setVelX(-5);
                p.setDirection(Direction.LEFT);
                keyA = true;
            }
        }
        if (key == KeyEvent.VK_S) {
            if (p.isCanMoveDown()) {
                p.setVelY(5);
                p.setDirection(Direction.DOWN);
                keyS = true;
            }
        }
        if (key == KeyEvent.VK_D) {
            if (p.isCanMoveRight()) {
                p.setVelX(5);
                p.setDirection(Direction.RIGHT);
                keyD = true;
            }
        }
        if (keyW == true && keyD == true) {
            if (p.isCanMoveRight()) {
                p.setVelX(5);
                p.setDirection(Direction.UP_RIGHT);

            }
        }
        if (keyW == true && keyA == true) {
            if (p.isCanMoveLeft()) {
                p.setVelX(-5);
                p.setDirection(Direction.UP_LEFT);
            }
        }
        if (keyS == true && keyD == true) {
            if (p.isCanMoveRight()) {
                p.setVelX(5);
                p.setDirection(Direction.DOWN_RIGHT);
            }
        }
        if (keyS == true && keyA == true) {
            if (p.isCanMoveLeft()) {
                p.setVelX(-5);
                p.setDirection(Direction.DOWN_LEFT);

            }
        }

        if (key == KeyEvent.VK_UP) {
            if (p2.isCanMoveUp()) {
                p2.setVelY(-5);
                p2.setDirection(Direction.UP);
                keyUp = true;
            }
        }
        if (key == KeyEvent.VK_LEFT) {
            if (p2.isCanMoveLeft()) {
                p2.setVelX(-5);
                p2.setDirection(Direction.LEFT);
                keyLeft = true;
            }
        }
        if (key == KeyEvent.VK_DOWN) {
            if (p2.isCanMoveDown()) {
                p2.setVelY(5);
                p2.setDirection(Direction.DOWN);
                keyDown = true;
            }
        }
        if (key == KeyEvent.VK_RIGHT) {
            if (p2.isCanMoveRight()) {
                p2.setVelX(5);
                p2.setDirection(Direction.RIGHT);
                keyRight = true;
            }
        }
        if (keyUp == true && keyRight == true) {
            if (p2.isCanMoveRight()) {
                p2.setVelX(5);
                p2.setDirection(Direction.UP_RIGHT);

            }
        }
        if (keyUp == true && keyLeft == true) {
            if (p2.isCanMoveLeft()) {
                p2.setVelX(-5);
                p2.setDirection(Direction.UP_LEFT);
            }
        }
        if (keyDown == true && keyRight == true) {
            if (p2.isCanMoveRight()) {
                p2.setVelX(5);
                p2.setDirection(Direction.DOWN_RIGHT);
            }
        }
        if (keyDown == true && keyLeft == true) {
            if (p2.isCanMoveLeft()) {
                p2.setVelX(-5);
                p2.setDirection(Direction.DOWN_LEFT);

            }
        }

        if (key == KeyEvent.VK_ESCAPE)
        {
            System.exit(1);
        }
        */
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

/*
        if (key == KeyEvent.VK_W) {
            p.setVelY(0);
            keyW = false;
        } else if (key == KeyEvent.VK_A) {
            p.setVelX(0);
            keyA = false;
        } else if (key == KeyEvent.VK_S) {
            p.setVelY(0);
            keyS = false;
        } else if (key == KeyEvent.VK_D) {
            p.setVelX(0);
            keyD = false;
        }



        else if (key == KeyEvent.VK_LEFT) {
            p2.setVelX(0);
            keyLeft = false;
        } else if (key == KeyEvent.VK_DOWN) {
            p2.setVelY(0);
            keyDown = false;
        } else if (key == KeyEvent.VK_RIGHT) {
            p2.setVelX(0);
            keyRight = false;
        } else if (key == KeyEvent.VK_UP) {
            p2.setVelY(0);
            keyUp = false;
        }

        if (ammo1 >= 1) {
            if (key == KeyEvent.VK_SPACE) {

                if (p.getDirection() == Direction.DOWN) {
                    controls.addBullet(new Bullet(p.getX() + 20, p.getY() + 70, tex, this, 4));
                    ammo1--;
                } else if (p.getDirection() == Direction.UP) {
                    controls.addBullet(new Bullet(p.getX() + 20, p.getY() - 30, tex, this, 1));
                    ammo1--;
                } else if (p.getDirection() == Direction.RIGHT) {
                    controls.addBullet(new Bullet(p.getX() + 70, p.getY() + 20, tex, this, 2));
                    ammo1--;
                } else if (p.getDirection() == Direction.LEFT) {
                    controls.addBullet(new Bullet(p.getX() - 30, p.getY() + 20, tex, this, 3));
                    ammo1--;

                } else if (p.getDirection() == Direction.UP_RIGHT) {
                    controls.addBullet(new Bullet(p.getX() + 70, p.getY() - 30, tex, this, 5));
                    ammo1--;
                } else if (p.getDirection() == Direction.UP_LEFT) {
                    controls.addBullet(new Bullet(p.getX() - 25, p.getY() - 25, tex, this, 6));
                    ammo1--;
                } else if (p.getDirection() == Direction.DOWN_LEFT) {
                    controls.addBullet(new Bullet(p.getX() - 10, p.getY() + 65, tex, this, 8));
                    ammo1--;
                } else if (p.getDirection() == Direction.DOWN_RIGHT) {
                    controls.addBullet(new Bullet(p.getX() + 60, p.getY() + 65, tex, this, 7));
                    ammo1--;
                }

            }
        }
        if (ammo2 >= 1) {
            if (key == KeyEvent.VK_L) {

                if (p2.getDirection() == Direction.DOWN) {
                    controls.addBullet(new Bullet(p2.getX() + 20, p2.getY() + 60, tex, this, 4));
                    ammo2--;
                } else if (p2.getDirection() == Direction.UP) {
                    controls.addBullet(new Bullet(p2.getX() + 20, p2.getY() - 30, tex, this, 1));
                    ammo2--;
                } else if (p2.getDirection() == Direction.RIGHT) {
                    controls.addBullet(new Bullet(p2.getX() + 70, p2.getY() + 20, tex, this, 2));
                    ammo2--;
                } else if (p2.getDirection() == Direction.LEFT) {
                    controls.addBullet(new Bullet(p2.getX() - 30, p2.getY() + 20, tex, this, 3));
                    ammo2--;

                } else if (p2.getDirection() == Direction.UP_RIGHT) {
                    controls.addBullet(new Bullet(p2.getX() + 70, p2.getY() - 30, tex, this, 5));
                    ammo2--;
                } else if (p2.getDirection() == Direction.UP_LEFT) {
                    controls.addBullet(new Bullet(p2.getX() - 20, p2.getY() - 20, tex, this, 6));
                    ammo2--;
                } else if (p2.getDirection() == Direction.DOWN_LEFT) {
                    controls.addBullet(new Bullet(p2.getX() - 10, p2.getY() + 55, tex, this, 8));
                    ammo2--;
                } else if (p2.getDirection() == Direction.DOWN_RIGHT) {
                    controls.addBullet(new Bullet(p2.getX() + 60, p2.getY() + 65, tex, this, 7));
                    ammo2--;
                }
            }

       }*/
    }

/*
    public void mouseClicked(MouseEvent e) {
        int mouse = e.getButton();

        Rectangle r = new Rectangle(e.getX(), e.getY(), 1, 1);
        if (mouse == MouseEvent.BUTTON1) {
            if (r.intersects(playButton.getButtonBounds())) {
                playButton.clickButton(this);
            }
            if (r.intersects(quitButton.getButtonBounds())) {
                quitButton.clickButton(this);
            }
        }
    }
    */

/*
    public void setGameState(int gamestate) {
        this.gamestate = gamestate;
    }

    public BufferedImage getWalls() {
        return walls;
    }

    public BufferedImage getBreakableWalls() {
        return breakWall;
    }

    public BufferedImage getAmmo() {
        return ammo;
    }
    */


}