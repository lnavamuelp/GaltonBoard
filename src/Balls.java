import java.util.Random;
import java.awt.*;
public class Balls {
private int Ball_x;
private int Ball_y;
public int radius;
public  int start_y;
private final static Random generator = new Random();
MainPanel pa = new MainPanel();
public Balls()
        {
start_y = 100;
Ball_x = 385;
Ball_y = 50;
radius = 15;
        }
public void draw(Graphics g)
{
    g.setColor(Color.RED);
    g.fillOval(Ball_x, Ball_y, radius, radius);
}
public void move()
{
    if (Ball_y < pa.getPanel_y() + 65)
    {
        int direction = generator.nextInt(2);
        Ball_y = Ball_y + 5;
        if (Ball_y == start_y - 10 && start_y < pa.getPanel_y())
        {
            if (direction == 0)
            {
                Ball_x = Ball_x - 20;
            }
            else Ball_x = Ball_x + 20;
            start_y = start_y + 40;
        }
    }
    // Ball_x = Ball_x + 5;

}
    public boolean isAtFinalPosition() {
        return Ball_y >= pa.getPanel_y() + 65;
    }

    public int getBallY() {
        return Ball_y;
    }

    public void advanceToNextPosition() {
        // your code that calculates the new position
        int direction = generator.nextInt(2);
        Ball_y = Ball_y + 5;
        if (Ball_y == start_y - 10 && start_y < pa.getPanel_y())
        {
            if (direction == 0)
            {
                Ball_x = Ball_x - 20;
            }
            else Ball_x = Ball_x + 20;

            start_y = start_y + 40;
        }
    }

    public Rectangle getDrawBounds() {
        return new Rectangle(Ball_x - radius, Ball_y - radius, 2 * radius, 2 * radius);
    }

}