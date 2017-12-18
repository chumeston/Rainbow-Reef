import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

public class World extends JPanel {

    private int width = 640;
    private int height = 480;
    private Events events;
    private Image playerHealthBar;
    private MapLevel mapLevel;
    private BufferedImage[] entireScreen;
    private Image[] entireScreenImage;
    private Image[][] image;
    private ArrayList<ArrayList<GameObject>> blockList = new ArrayList<>();


    public World(Image[] entireScreenImage, Image[] image, Events events, MapLevel mapLevel, Image playerHealthBar) {
        this.image = new Image[11][1];
        for (int i = 0; i <= 10; i++) {
            this.image[i][0] = image[i];
        }
        this.entireScreenImage = new Image[4];
        this.entireScreen = new BufferedImage[4];
        this.events = events;
        this.mapLevel = mapLevel;
        this.playerHealthBar = playerHealthBar;
        this.drawBlocks();
        for (int i = 0; i <= 3; i++) {
            this.entireScreenImage[i] = entireScreenImage[i];
            this.entireScreen[i] = bufferedImage(entireScreenImage[i]);
        }
    }

    public void updateEntireScreen() {
        BufferedImage bufferedImage = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = bufferedImage.createGraphics();
        if (mapLevel.getBlockType() == 1) {
            graphics2D.drawImage(entireScreenImage[0], 0, 0, width, height, null);
        } else if (mapLevel.getBlockType() == 2) {
            graphics2D.drawImage(entireScreenImage[1], 0, 0, width, height, null);
        } else if (mapLevel.getBlockType() == 3) {
            graphics2D.drawImage(entireScreenImage[2], 0, 0, width, height, null);
        } else {
            graphics2D.drawImage(entireScreenImage[3], 0, 0, width, height, null);
        }
        Iterator<GameObject> iterator = blockList.get(mapLevel.getBlockType() - 1).listIterator();
        while (iterator.hasNext()) {
            GameObject gameObject = iterator.next();
            if (!gameObject.isFinished()) {
                gameObject.draw(graphics2D, null);
            }
        }
        graphics2D.dispose();
        //If this isn't here, it will leave a trail of new draw images behind.
        if (mapLevel.getBlockType() == 1) {
            this.entireScreen[0] = bufferedImage;
        } else if (mapLevel.getBlockType() == 2) {
            this.entireScreen[1] = bufferedImage;
        } else if (mapLevel.getBlockType() == 3) {
            this.entireScreen[2] = bufferedImage;
        } else {
            this.entireScreen[3] = bufferedImage;
        }
    }

    public void drawEntireScreen(Graphics2D graphics2D) {
        if (mapLevel.getBlockType() == 1) {
            graphics2D.drawImage(entireScreen[0], 0, 0, this);
        } else if (mapLevel.getBlockType() == 2) {
            graphics2D.drawImage(entireScreen[1], 0, 0, this);
        } else if (mapLevel.getBlockType() == 3) {
            graphics2D.drawImage(entireScreen[2], 0, 0, this);
        } else if (mapLevel.getBlockType() == 4) {
            graphics2D.drawImage(entireScreen[3], 0, 0, this);
        }
        if (MainGame.player != null && MainGame.player.size() > 0) {
            int lives = MainGame.player.get(0).getDestroy() - MainGame.player.get(0).getDamage();
            for (int i = 0; i < lives; i++) {
                //Position of health bar
                graphics2D.drawImage(playerHealthBar, i * (playerHealthBar.getWidth(null) + 5) + 25, 60, this);
            }
        }
        Font font = new Font("Sans-Serif", Font.BOLD, 25);
        graphics2D.setFont(font);
        //Position of Score
        graphics2D.drawString("Score: " + MainGame.score, 20, 50);
    }

    public void draw(ArrayList<ArrayList> arrayList, Graphics2D graphics2D) {
        if (mapLevel.getBlockType() == 1) {
            graphics2D = this.entireScreen[0].createGraphics();
        } else {
            graphics2D = this.entireScreen[1].createGraphics();
        }
        GameObject gameObject;
        Iterator<ArrayList> iterator = arrayList.subList(0, 2).listIterator();
        while (iterator.hasNext()) {
            Iterator<GameObject> iterator2 = iterator.next().listIterator();
            while (iterator2.hasNext()) {
                gameObject = iterator2.next();
                gameObject.draw(graphics2D, null);
            }
        }
        graphics2D.dispose();
    }

    private BufferedImage bufferedImage(Image image) {
        if (image instanceof BufferedImage) {
            return (BufferedImage) image;
        }
        BufferedImage bufferedImage = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = bufferedImage.createGraphics();
        graphics2D.drawImage(image, 0, 0, width, height, null);
        for (GameObject gameObject : blockList.get(mapLevel.getBlockType() - 1)) {
            gameObject.draw(graphics2D, null);
        }
        graphics2D.dispose();
        return bufferedImage;
    }

    private void drawBlocks() {
        blockList.add(new ArrayList<>());
        blockList.add(new ArrayList<>());
        blockList.add(new ArrayList<>());
        InputStream inputStream = this.getClass().getResourceAsStream("res/World_maps");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            String lineReader;
            while ((lineReader = bufferedReader.readLine()) != null) {
                //Won't put in blocks if there's comments. Had to trim.
                if (lineReader.startsWith("//") || lineReader.trim().equals("")) {
                    continue;
                }
                String[] list = lineReader.split(",");
                if (list.length == 5) {
                    if (list[4].equals("1")) {
                        for (int i = Integer.valueOf(list[0]); i < this.width; i += image[Integer.valueOf(list[2])][0].getWidth(null)) {
                            blockList.get(0).add(new Block(i, Integer.valueOf(list[1]), 0, 0, image[Integer.valueOf(list[2])], this.events, 1));
                            blockList.get(1).add(new Block(i, Integer.valueOf(list[1]), 0, 0, image[Integer.valueOf(list[2])], this.events, 1));
                        }
                    } else {
                        for (int i = Integer.valueOf(list[1]); i < this.height; i += image[Integer.valueOf(list[2])][0].getHeight(null)) {
                            blockList.get(0).add(new Block(Integer.valueOf(list[0]), i, 0, 0, image[Integer.valueOf(list[2])], this.events, 1));
                            blockList.get(1).add(new Block(Integer.valueOf(list[0]), i, 0, 0, image[Integer.valueOf(list[2])], this.events, 1));
                        }
                    }
                } else if (list.length == 6) {
                    int[] newInput = new int[6];
                    for (int i = 0; i <= 5; i++) {
                        newInput[i] = Integer.valueOf(list[i]);
                    }
                    for (int i = 0; i < newInput[5]; i++) {
                        blockList.get(newInput[4] - 1).add(new Block(newInput[0] + i * image[newInput[2]][0].getWidth(null),
                                newInput[1], 0, 0, image[newInput[2]], this.events, newInput[3]));
                    }
                } else {
                    int[] input = new int[6];
                    for (int i = 0; i <= 5; i++) {
                        input[i] = Integer.valueOf(list[i]);
                    }
                    for (int i = 0; i < input[5]; i++) {
                        blockList.get(input[4] - 1).add(new Block(input[0] + (int) (1.5 * i * image[input[2]][0].getWidth(null)),
                                input[1], 0, 0, image[input[2]], this.events, input[3]));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<GameObject> showBlocks() {
        return blockList.get(mapLevel.getBlockType() - 1);
    }


    public boolean valid(GameObject gameObject, int x, int y) {
        for (GameObject object : blockList.get(mapLevel.getBlockType() - 1)) {
            if (object.touchBlock(x, y, gameObject)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Events getEvents() {
        return events;
    }

    public void setEvents(Events events) {
        this.events = events;
    }

    public Image getPlayerHealthBar() {
        return playerHealthBar;
    }

    public void setPlayerHealthBar(Image playerHealthBar) {
        this.playerHealthBar = playerHealthBar;
    }

    public MapLevel getMapLevel() {
        return mapLevel;
    }

    public void setMapLevel(MapLevel mapLevel) {
        this.mapLevel = mapLevel;
    }

    public BufferedImage[] getEntireScreen() {
        return entireScreen;
    }

    public void setEntireScreen(BufferedImage[] entireScreen) {
        this.entireScreen = entireScreen;
    }

    public Image[] getEntireScreenImage() {
        return entireScreenImage;
    }

    public void setEntireScreenImage(Image[] entireScreenImage) {
        this.entireScreenImage = entireScreenImage;
    }

    public Image[][] getImage() {
        return image;
    }

    public void setImage(Image[][] image) {
        this.image = image;
    }

    public ArrayList<ArrayList<GameObject>> getBlockList() {
        return blockList;
    }

    public void setBlockList(ArrayList<ArrayList<GameObject>> blockList) {
        this.blockList = blockList;
    }
}
