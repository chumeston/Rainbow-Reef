import java.awt.event.*;

public class Controller extends KeyAdapter {

    private Events events;

    public Controller(Events events) {
        this.events = events;
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        events.keyPressed(keyEvent);
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        events.keyPressed(keyEvent);
    }

    public Events getEvents() {
        return events;
    }

    public void setEvents(Events events) {
        this.events = events;
    }
}
