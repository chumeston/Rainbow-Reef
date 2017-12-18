import java.awt.event.KeyEvent;
import java.util.Observer;

public class NewEvents extends Events {

    public NewEvents() {}

    @Override
    public void collision(GameObject brickObject, GameObject blockObject) {
        super.collision(brickObject, blockObject);
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        super.keyPressed(keyEvent);
    }

    @Override
    public Object getBrickObject() {
        return super.getBrickObject();
    }

    @Override
    public void setBrickObject(Object brickObject) {
        super.setBrickObject(brickObject);
    }

    @Override
    public Object getBlockObject() {
        return super.getBlockObject();
    }

    @Override
    public void setBlockObject(Object blockObject) {
        super.setBlockObject(blockObject);
    }

    @Override
    public int getBlockType() {
        return super.getBlockType();
    }

    @Override
    public void setBlockType(int blockType) {
        super.setBlockType(blockType);
    }

    @Override
    public synchronized void addObserver(Observer o) {
        super.addObserver(o);
    }

    @Override
    public synchronized void deleteObserver(Observer o) {
        super.deleteObserver(o);
    }

    @Override
    public void notifyObservers() {
        super.notifyObservers();
    }

    @Override
    public void notifyObservers(Object arg) {
        super.notifyObservers(arg);
    }

    @Override
    public synchronized void deleteObservers() {
        super.deleteObservers();
    }

    @Override
    protected synchronized void setChanged() {
        super.setChanged();
    }

    @Override
    protected synchronized void clearChanged() {
        super.clearChanged();
    }

    @Override
    public synchronized boolean hasChanged() {
        return super.hasChanged();
    }

    @Override
    public synchronized int countObservers() {
        return super.countObservers();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

}
