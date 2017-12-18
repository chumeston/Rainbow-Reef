import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;
import javax.swing.*;

public class MainGame extends Game {

    public static NewEvents events;
    public static ArrayList<ArrayList> arrayList;
    public static ArrayList<GameObject> objects;
    public static ArrayList<Player> player;
    public static Image playerLives;
    public static Image[] playerBlock;
    public static Image[] star;
    public static Image[] bigEnemy;
    public static Image[] smallEnemy;
    public static GameController gameController;
    public static World world;
    public static int score = 0;
    public static MapLevel mapLevel;
    public static boolean fin;
    public static boolean destroy = false;
    public static boolean temp = false;

    public static void main(String[] args) {
        final MainGame mainGame = new MainGame();
        mainGame.init();
        final JFrame jFrame = new JFrame("Rainbow Reef");
        jFrame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                jFrame.dispose();
                System.exit(0);
            }
        });
        jFrame.getContentPane().add("Center", mainGame);
        jFrame.setSize(new Dimension(640, 480));
        jFrame.setVisible(true);
        jFrame.setResizable(false);
        mainGame.start();
    }

    @Override
    public void draw(int width, int height, Graphics2D graphics2D) {
        GameObject gameObject;
        Iterator<ArrayList> iterator = arrayList.listIterator();
        try {
            while (iterator.hasNext()) {
                ArrayList<GameObject> arrayList = iterator.next();
                Iterator<GameObject> iterator2 = arrayList.listIterator();
                while (iterator2.hasNext()) {
                    if (fin) {
                        break;
                    }
                    gameObject = iterator2.next();
                    gameObject.update(width, height);
                    if (gameObject.isFinished()) {
                        iterator2.remove();
                    }
                }
                if (fin) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        checkNewMap();
        world.updateEntireScreen();
        world.draw(arrayList, graphics2D);
        world.drawEntireScreen(graphics2D);
        if (destroy) {
            iterator = arrayList.listIterator();
            while (iterator.hasNext()) {
                Iterator<GameObject> iterator2 = iterator.next().listIterator();
                while (iterator2.hasNext()) {
                    iterator2.next();
                    iterator2.remove();
                }
            }
            gameController = new GameController();
            destroy = false;
            fin = false;
            events.deleteObservers();
            this.requestFocus();
        }
        gameController.timer();
    }

    @Override
    public void init() {
        super.init();
        events = new NewEvents();
        mapLevel = new MapLevel();
        Image[] image = new Image[11];
        image[0] = drawSprite("res/Block_double.png");
        //Draws blocks 1 through 7 in res folder
        for (int i = 1; i < 8; i++ ) {
            image[i] = drawSprite("res/Block" + i + ".png");
        }
        image[8] = drawSprite("res/Block_life.png");
        image[9] = drawSprite("res/Block_solid.png");
        image[10]= drawSprite("res/Wall.png");
        //Player lives in top right corner (subject to change)
        playerLives = drawSprite("res/Katch_small.png");
        Image[] backgroundImage = new Image[4];
        //Draws background for levels
        for (int i = 1; i < 3; i++ ) {
            backgroundImage [i - 1] = drawSprite("res/background" + i + ".png");
        }
        backgroundImage[2] = drawSprite("res/Congratulation.png");
        backgroundImage[3] = drawSprite("res/Title.png");
        world = new World(backgroundImage, image, events, mapLevel, playerLives);
        add(world, BorderLayout.CENTER);
        arrayList = new ArrayList<>();
        objects = new ArrayList<>();
        arrayList.add(objects);
        player = new ArrayList<>();
        arrayList.add(player);
        ArrayList<GameObject> blocks = world.showBlocks();
        arrayList.add(blocks);
        Controller controller = new Controller(events);
        addKeyListener(controller);
        gameController = new GameController();
        score = 0;
        fin = false;
    }

    @Override
    public void imageLoop() {
        try {
            star = new Image[45];
            BufferedImage bufferedImage = bufferedImage("res/Pop.png");
            int width = bufferedImage.getWidth() / 45;
            //Star movement
            for (int i = 0; i < 45; i++) {
                star[i] = bufferedImage.getSubimage(i * 35, 0, width, bufferedImage.getHeight());
            }
            playerBlock = new Image[24];
            BufferedImage bufferedImage2 = bufferedImage("res/Katch.png");
            int width2 = bufferedImage2.getWidth() / 24;
            //Player Block we use to catch star
            for (int i = 0; i < 24; i++) {
                playerBlock[i] = bufferedImage2.getSubimage(i * 80, 0, width2, bufferedImage2.getHeight());
            }
            bigEnemy = new Image[24];
            BufferedImage bufferedImage3 = bufferedImage("res/Bigleg.png");
            //Big enemy octopus
            int width3 = bufferedImage3.getWidth() / 24;
            for (int i = 0; i < 24; i++) {
                bigEnemy[i] = bufferedImage3.getSubimage(i * 80, 0, width3, bufferedImage3.getHeight());
            }
            smallEnemy = new Image[24];
            BufferedImage bufferedImage4 = bufferedImage("/res/Bigleg_small.png");
            //Small enemy octopus
            int width4 = bufferedImage4.getWidth() / 24;
            for (int i = 0; i < 24; i++) {
                smallEnemy[i] = bufferedImage4.getSubimage(i * 40, 0, width4, bufferedImage4.getHeight());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkNewMap() {
        if (!temp) {
            return;
        }
        boolean flag = true;
        for (GameObject gameObject : objects) {
            if (gameObject instanceof Enemy) {
                flag = false;
                break;
            }
        }
        if (flag) {
            if (mapLevel.getBlockType() == 1) {
                mapLevel.setBlockType(2);
                objects.clear();
                objects.add(new Enemy(320, 70, 1, 0, bigEnemy, events, 1, 0));
                objects.add(new Enemy(320, 200, 1, 5, smallEnemy, events, 1, 0));
                player.get(0).setX(320);
                player.get(0).setY(440);
                objects.add(new Star(320,340, 1, 5, star, events, 1, 1, arrayList));
                arrayList.remove(2);
                arrayList.add(world.showBlocks());
            } else if (mapLevel.getBlockType() == 2) {
                mapLevel.setBlockType(3);
                objects.clear();
                arrayList.remove(2);
            } else if (mapLevel.getBlockType() == 4) {
                objects.clear();
                arrayList.remove(2);
            }
        }
    }

    public class PlayerBlock extends Player {

        private int x, y;

        public PlayerBlock(int x, int y, int tick, int speed, Image[] image, Events events, int destroy, int collide,
                           int left, int right, World world, int blockType) {
            super(x, y, tick, speed, image, events, destroy, collide, left, right, world, blockType);
            this.x = x;
            this.y = y;
        }

        @Override
        public void update(int width, int height) {
            super.update(width, height);
            this.tick();
        }

        @Override
        public void isDead() {
            setFinished(true);
            mapLevel.setBlockType(4);
        }


        @Override
        public void NewCollision(GameObject gameObject) {
            if (gameObject instanceof Star) {
                gameObject.collide(this);
            }
        }

        @Override
        public void collide(TickingObject tickingObject) {}

        @Override
        public int getWidth() {
            return super.getWidth() - 20;
        }

        @Override
        public int getHeight() {
            return super.getHeight() - 20;
        }

        @Override
        public void canMove() {
            if (isMovingLeft()) {
                int x = getSpeed();
                int newX = this.getX() - x;
                //Y stays the same entire game
                int newY = this.getY();
                if (!this.getWorld().valid(this, newX, newY) || (this.getPlayer() != null &&
                        this.getPlayer().touchBlock(newX, newY, this))) {
                    return;
                }
                //Negative towards left
                keyListener(-x);
            }
            if (isMovingRight()) {
                int x = getSpeed();
                int newX = this.getX() + x;
                //Y stays the same entire game
                int newY = this.getY();
                if (!this.getWorld().valid(this, newX, newY) || (this.getPlayer() != null &&
                        this.getPlayer().touchBlock( newX, newY, this))) {
                    return;
                }
                //Positive towards right
                keyListener(x);
            }
        }

        public void gameLoop() {
            boolean flag = false;
            for (int i = 0; i < objects.size(); i++) {
                if (objects.get(i) instanceof Star) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                this.setDamage(getDamage() + 1);
                if (getDamage() >= getDestroy()) {
                    setReset(true);
                } else {
                    reset();
                }
            }
        }

        public void reset() {
            //Doesn't work if setReset is true
            this.setReset(false);
            this.setX(x);
            this.setY(y);
            objects.add(new Star(320,350, 1, 5, star, events, 1, 1, arrayList));
        }
    }

    public static NewEvents getEvents() {
        return events;
    }

    public static void setEvents(NewEvents events) {
        MainGame.events = events;
    }

    public static ArrayList<ArrayList> getArrayList() {
        return arrayList;
    }

    public static void setArrayList(ArrayList<ArrayList> arrayList) {
        MainGame.arrayList = arrayList;
    }

    public static ArrayList<GameObject> getObjects() {
        return objects;
    }

    public static void setObjects(ArrayList<GameObject> objects) {
        MainGame.objects = objects;
    }

    public static ArrayList<Player> getPlayer() {
        return player;
    }

    public static void setPlayer(ArrayList<Player> player) {
        MainGame.player = player;
    }

    public static Image getPlayerLives() {
        return playerLives;
    }

    public static void setPlayerLives(Image playerLives) {
        MainGame.playerLives = playerLives;
    }

    public static Image[] getPlayerBlock() {
        return playerBlock;
    }

    public static void setPlayerBlock(Image[] playerBlock) {
        MainGame.playerBlock = playerBlock;
    }

    public static Image[] getStar() {
        return star;
    }

    public static void setStar(Image[] star) {
        MainGame.star = star;
    }

    public static Image[] getBigEnemy() {
        return bigEnemy;
    }

    public static void setBigEnemy(Image[] bigEnemy) {
        MainGame.bigEnemy = bigEnemy;
    }

    public static Image[] getSmallEnemy() {
        return smallEnemy;
    }

    public static void setSmallEnemy(Image[] smallEnemy) {
        MainGame.smallEnemy = smallEnemy;
    }

    public static GameController getGameController() {
        return gameController;
    }

    public static void setGameController(GameController gameController) {
        MainGame.gameController = gameController;
    }

    public static World getWorld() {
        return world;
    }

    public static void setWorld(World world) {
        MainGame.world = world;
    }

    public static int getScore() {
        return score;
    }

    public static void setScore(int score) {
        MainGame.score = score;
    }

    public static MapLevel getMapLevel() {
        return mapLevel;
    }

    public static void setMapLevel(MapLevel mapLevel) {
        MainGame.mapLevel = mapLevel;
    }

    public static boolean isFin() {
        return fin;
    }

    public static void setFin(boolean fin) {
        MainGame.fin = fin;
    }

    public static boolean isDestroy() {
        return destroy;
    }

    public static void setDestroy(boolean destroy) {
        MainGame.destroy = destroy;
    }

    public static boolean isTemp() {
        return temp;
    }

    public static void setTemp(boolean temp) {
        MainGame.temp = temp;
    }
}
