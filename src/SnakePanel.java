import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;


public class SnakePanel extends JPanel implements KeyListener, ActionListener {
    ImageIcon up = new ImageIcon("up.png");
    ImageIcon down = new ImageIcon("down.png");
    ImageIcon left = new ImageIcon("left.png");
    ImageIcon right = new ImageIcon("right.png");
    ImageIcon food = new ImageIcon("food.png");
    ImageIcon body = new ImageIcon("body.png");
    ImageIcon title = new ImageIcon("title.jpeg");

    int[] snakeX = new int[750];
    int[] snakeY = new int[750];

    Random random = new Random();
    int foodX = random.nextInt(34)*25+25;
    int foodY = random.nextInt(24)*25+75;

    int len = 3;
    int score = 0;
    String direction = "R";

    boolean isStarted = false;
    boolean isFailed = false;

    Timer timer = new Timer(500,this);
    Timer midlTimer = new Timer(300, this);
    Timer fastTimer = new Timer(200, this);

    public SnakePanel(){
        this.setFocusable(true);
        this.addKeyListener(this);
        setup();
        timer.start();
    }

    public void paint(Graphics g){
        this.setBackground(Color.GRAY);
        title.paintIcon(this, g, 25, 11);
        g.fillRect(25, 75, 850, 650);
        
        if(direction.equals("R"))
            right.paintIcon(this, g, snakeX[0], snakeY[0]);
        else if(direction.equals("L"))
            left.paintIcon(this, g, snakeX[0], snakeY[0]);
        else if(direction.equals("U"))
            up.paintIcon(this, g, snakeX[0], snakeY[0]);
        else if(direction.equals("D"))
            down.paintIcon(this, g, snakeX[0], snakeY[0]);

        for (int i = 1; i < len; i++)
            body.paintIcon(this, g, snakeX[i], snakeY[i]);

        if(!isStarted){
            g.setColor(Color.WHITE);
            g.setFont(new Font("arial", Font.BOLD, 30));
            g.drawString("Нажмите пробел для начала/паузы" ,200, 300);
        }

        if(isFailed){
            g.setColor(Color.WHITE);
            g.setFont(new Font("arial", Font.BOLD, 30));
            g.drawString("Игра окончена!!! Нажмите пробел!", 200, 300);
        }

        food.paintIcon(this, g, foodX, foodY);

        g.setColor(Color.WHITE);
        g.setFont(new Font("arial", Font.PLAIN, 15));
        g.drawString("Счет : " + score, 650, 37);
        g.drawString("Длина : " + len, 650, 57);
    }

    public void setup(){
        isStarted = false;
        isFailed = false;
        len = 3;
        score = 0;
        snakeX[0] = 100; snakeX[1] = 75; snakeX[2] = 50;
        snakeY[0] = 100; snakeY[1] = 100; snakeY[2] = 100;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int KeyCode = e.getKeyCode();
        if(KeyCode == KeyEvent.VK_SPACE){
            if (isFailed){
                setup();
            }else
                isStarted = !isStarted;
            } else if (KeyCode ==  KeyEvent.VK_UP && direction != "D")
                direction = "U";
            else if (KeyCode ==  KeyEvent.VK_DOWN && direction != "U" )
                direction = "D";
            else if (KeyCode ==  KeyEvent.VK_RIGHT && direction != "L")
                direction = "R";
            else if (KeyCode ==  KeyEvent.VK_LEFT && direction != "R")
                direction = "L";

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if(isStarted && !isFailed){
            for (int i = len; i > 0 ; i--) {
                snakeX[i] = snakeX[i-1];
                snakeY[i] = snakeY[i-1];
            }
            if(direction.equals("R")){
                snakeX[0] = snakeX[0]+25;
                if(snakeX[0] > 850) snakeX[0] = 25;
            }else if(direction.equals("L")){
                snakeX[0] = snakeX[0]-25;
                if (snakeX[0] < 25) snakeX[0] = 850;
            }else if(direction.equals("U")){
                snakeY[0] = snakeY[0] - 25;
                if (snakeY[0] < 75) snakeY[0] = 650;
            }else if(direction.equals("D")){
                snakeY[0] = snakeY[0] + 25;
                if (snakeY[0] > 650) snakeY[0] = 75;
            }

            if(snakeX[0] == foodX && snakeY[0] == foodY){
                len++;
                score++;
                foodX = random.nextInt(34)*25+25;
                foodY = random.nextInt(24)*25+75;

                if(score < 5){
                    midlTimer.stop();
                    fastTimer.stop();
                    timer.start();
                }
                if(score >= 5) {
                    timer.stop();
                    midlTimer.start();
                }
                if(score > 10){
                    midlTimer.stop();
                    fastTimer.start();
                }
            }
            for (int i = 1; i < len; i++) {
                if(snakeX[0] == snakeX[i] && snakeY[0] == snakeY[i]){
                    isFailed = true;
                }
            }
        }


        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}