public class Direction {

    //Having issues with Star going side to side. Tried fixing it with Math.PI
    private double direction = (Math.random() / 3.0 + 0.35) * Math.PI;

    public double getDirection() {
        return direction;
    }

    public void setDirection(double direction) {
        this.direction = direction;
    }
}