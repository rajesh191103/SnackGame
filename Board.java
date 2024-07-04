import javax.swing.*;
import java.awt.*;
import java.awt.event.*;



class Board extends JPanel implements ActionListener {

    private Image apple;
    private Image dot;
    private Image head;

    private final int ALL_DOTS = 900;
    private final int DOT_SIZE = 10;
    private final int x[] = new int[ALL_DOTS];
    private final int y[] = new int[ALL_DOTS];

    private final int RANDOM_POSITION = 29;
    private int apple_x;
    private int apple_y;
    private int dots;
    private Timer timer;

    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;

    private boolean inGame=true;

    Board() {
        addKeyListener(new TAdapter());
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(300,300));
        setFocusable(true);
        initGame();
        loadImages();
    }

    public void loadImages() {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            ImageIcon i1 = new ImageIcon(classLoader.getResource("icons/apple.png"));
            apple = i1.getImage();
            System.out.println("Apple image loaded successfully");

            ImageIcon i2 = new ImageIcon(classLoader.getResource("icons/dot.png"));
            dot = i2.getImage();
            System.out.println("Dot image loaded successfully");

            ImageIcon i3 = new ImageIcon(classLoader.getResource("icons/head.png"));
            head = i3.getImage();
            System.out.println("Head image loaded successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initGame() {
        dots = 3;
        for (int i = 0; i < dots; i++) {
            y[i] = 50;
            x[i] = 50 - i * DOT_SIZE;
        }

        locateApple();
        timer = new Timer(140, this);
        timer.start();
    }

    public void locateApple() {
        int r = (int) (Math.random() * RANDOM_POSITION);
        apple_x = r * DOT_SIZE;

        r = (int) (Math.random() * RANDOM_POSITION);
        apple_y = r * DOT_SIZE;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void gameOver(Graphics g){
        String msg="Gave Over";
        Font font=new Font("SAN SERIF",Font.BOLD,14);
        FontMetrics metrices =getFontMetrics(font);
        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString(msg,(300-metrices.stringWidth(msg)/2),300/2);
    }

    public void draw(Graphics g) {
        if(inGame){
            g.drawImage(apple, apple_x, apple_y, this);
        for (int i = 0; i < dots; i++) {
            if (i == 0) {
                g.drawImage(head, x[i], y[i], this);
            } else {
                g.drawImage(dot, x[i], y[i], this);
            }
        }
        Toolkit.getDefaultToolkit().sync();
        }else{
            gameOver(g);
        }
        
    }

    public void move() {
        for (int i = dots; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        if (leftDirection) {
            x[0] = x[0] - DOT_SIZE;
        }
        if (rightDirection) {
            x[0] = x[0] + DOT_SIZE;
        }
        if (upDirection) {
            y[0] = y[0] - DOT_SIZE;
        }
        if (downDirection) {
            y[0] = y[0] + DOT_SIZE;
        }
    }
    public void checkApple(){
        if((x[0]==apple_x) && (y[0]==apple_y)){
            dots++;
            locateApple();
        }
    }

    public void checkCollision(){
        for(int i=dots;i>0;i--){
            if((i>4) && (x[0]==x[i]) && (y[0]==y[i])){
                inGame=false;
            }
        }
        if(y[0]>=300){
            inGame=false;
        }
        if(x[0]>=300){
            inGame=false;
        }
        if(y[0]<0){
            inGame=false;
        }
        if(x[0]<0){
            inGame=false;
        }

        if(!inGame){
            timer.stop();
        }
    }


    @Override
    public void actionPerformed(ActionEvent ae) {
        if(inGame){
            checkApple();
            checkCollision();
            move();
        }
        
        repaint();
    }

    public class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_LEFT && !rightDirection) {
                leftDirection = true;
                upDirection = false;
                downDirection = false;
            }
            if (key == KeyEvent.VK_RIGHT && !leftDirection) {
                rightDirection = true;
                upDirection = false;
                downDirection = false;
            }
            if (key == KeyEvent.VK_UP && !downDirection) {
                upDirection = true;
                rightDirection = false;
                leftDirection = false;
            }
            if (key == KeyEvent.VK_DOWN && !upDirection) {
                downDirection = true;
                leftDirection = false;
                rightDirection = false;
            }
        }
    }

    public static void main(String[] args) {
        // The main method is not needed here, the main method in SnackGames will call the Board class.
    }
}