import java.awt.event.KeyEvent;

public class GameController extends MainGame {

    private int timer;

    public GameController() {
        timer = 0;
    }

    public void timer() {
        switch (timer) {
            case 0:
                //Can change KeyEvents here for new keybindings
                player.add(new MainGame.PlayerBlock(320, 440, 1,
                        15, playerBlock, events, 3, 0,
                        KeyEvent.VK_A, KeyEvent.VK_D, world, 0));
                objects.add(new Enemy(320, 80, 1, 0, bigEnemy, events, 1,0));
                objects.add(new Star(320,350, 1, 5, star, events, 1, 1, arrayList));
                break;
        }
        if (timer == Integer.MAX_VALUE) {
            timer = 1;
        } else {
            timer++;
        }
    }

}