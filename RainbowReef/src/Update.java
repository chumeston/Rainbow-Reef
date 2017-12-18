import java.awt.*;

public class Update implements Entity {

    @Override
    public void draw(GameObject gameObject, Graphics2D graphics2D, World world) {
        gameObject.draw(graphics2D, world);
    }
}
