import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

class MainPanel extends JPanel implements Runnable {
    private int num;
    private int number_ball;
    public static int start_y = 100;
    private float ball_x = 385;
    private float ball_y = 50;
    private float radius = 15;
    private static int panel_x = 300;
    private static int panel_y = 100;
    private int diameter = 20;
    private int last_x = 0;
    private final static Random generator = new Random();
    ArrayList<Balls> list_ball = new ArrayList<Balls>();
    private int m_interval = 100;
    private Timer m_timer;
    private int currentBallIndex = 0;
    public MainPanel() {
    }
    public MainPanel(int number) {
        num = number;
    }
    public MainPanel(int number, int ball) {
        num = number;
        number_ball = ball;
        for (int i = 1; i <= number_ball; i++)
        {
            list_ball.add(new Balls());
        }
        m_timer = new Timer(m_interval, (ActionListener) new TimerAction());
    }
    public int getPanel_y() {
        return panel_y;
    }
    public void start()
    {
        m_timer.setInitialDelay(250);
        m_timer.start();
    }
    @Override
    protected void paintComponent(Graphics g) {
        int start_y = 100;
        panel_x = 300;
        panel_y = 100;
        diameter = 20;
        last_x = 0;
        super.paintComponent(g);
        if (num % 2 == 0) {
            for (int i = 1; i <= num; i++) {
                if ((i % 2) != 0) {
                    for (int k = 1; k <= num; k++) {
                        g.setColor(Color.BLUE);
                        g.fillOval(panel_x, panel_y, diameter, diameter);
                        panel_x = panel_x + 40;
                    }
                } else if ((i % 2) == 0) {
                    for (int k = 1; k <= num + 1; k++) {
                        g.setColor(Color.BLUE);
                        g.fillOval(panel_x - 20, panel_y, diameter, diameter);
                        panel_x = panel_x + 40;
                    }
                }
                panel_y = panel_y + 40;
                panel_x = 300;
            }
        } else if (num % 2 != 0) {
            for (int i = 1; i <= num; i++) {
                if ((i % 2) != 0) {
                    for (int k = 1; k <= num; k++) {
                        g.setColor(Color.BLUE);
                        g.fillOval(panel_x, panel_y, diameter, diameter);
                        panel_x = panel_x + 40;
                    }
                } else if ((i % 2) == 0) {
                    for (int k = 1; k <= num + 1; k++) {
                        g.setColor(Color.BLUE);
                        g.fillOval(panel_x - 20, panel_y, diameter, diameter);
                        panel_x = panel_x + 40;
                    }
                }
                panel_y = panel_y + 40;
                panel_x = 300;
            }
        }
        for (int n = 40; n < panel_y - 40; n = n + 40) {
            if (num % 2 == 0) {
                g.drawLine(panel_x - 50 + n, panel_y - 10, panel_x - 50 + n,
                        panel_y + 80);
                g.drawLine(panel_x, panel_y + 80, panel_x - 50 + n, panel_y + 80);
                last_x = panel_x - 50 + n;
            } else if (num % 2 != 0) {
                g.drawLine(panel_x - 30 + n, panel_y - 10, panel_x - 30 + n,
                        panel_y + 80);
                g.drawLine(panel_x, panel_y + 80, panel_x - 30 + n, panel_y + 80);
                last_x = panel_x - 30 + n;
            }
        }
        for (int i = 0; i< list_ball.size(); i++)
        {
            list_ball.get(i).draw(g);
        }
    }

    @Override
    public void run() {
        while (true) {
            for (int i = 0; i< list_ball.size(); i++)
            {
                list_ball.get(i).move();
                //return;
                //m_timer.stop();
                repaint();
            }
        }
    }

    class TimerAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (currentBallIndex < list_ball.size()) {
                Balls currentBall = list_ball.get(currentBallIndex);

                // If the ball hasn't hit the bottom, advance it
                if (currentBall.getBallY() < getPanel_y() + 65) {
                    currentBall.advanceToNextPosition();
                    repaint();
                }
                // If the current ball hit the bottom, advance to the next ball
                else {
                    currentBallIndex++;
                }
            }
        }
    }
}