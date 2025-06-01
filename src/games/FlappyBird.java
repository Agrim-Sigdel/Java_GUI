

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class FlappyBird {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Flappy Bird");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(new GamePanel());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    static class GamePanel extends JPanel implements ActionListener, KeyListener {
        private static final int WIDTH = 400, HEIGHT = 600;
        private static final int GROUND_HEIGHT = 100;
        private static final int BIRD_SIZE = 40;
        private static final int PIPE_WIDTH = 70, PIPE_GAP = 160;
        private static final int PIPE_SPEED = 4;
        private static final Color SKY = new Color(135, 206, 250);
        private static final Color GROUND = new Color(222, 184, 135);

        private int birdY = HEIGHT / 2, birdVel = 0, gravity = 1, jump = -12;
        private ArrayList<Rectangle> pipes = new ArrayList<>();
        private int score = 0, highScore = 0;
        private boolean gameOver = false, started = false;
        private Timer timer;
        private Random rand = new Random();

        public GamePanel() {
            setPreferredSize(new Dimension(WIDTH, HEIGHT));
            setBackground(SKY);
            setFocusable(true);
            addKeyListener(this);
            timer = new Timer(20, this);
            timer.start();
            reset();
        }

        private void reset() {
            birdY = HEIGHT / 2;
            birdVel = 0;
            score = 0;
            pipes.clear();
            for (int i = 0; i < 3; i++) {
                addPipe(true);
            }
            gameOver = false;
            started = false;
        }

        private void addPipe(boolean start) {
            int x = start ? WIDTH + pipes.size() * 200 : pipes.get(pipes.size() - 1).x + 200;
            int h = 50 + rand.nextInt(HEIGHT - GROUND_HEIGHT - PIPE_GAP - 100);
            pipes.add(new Rectangle(x, 0, PIPE_WIDTH, h));
            pipes.add(new Rectangle(x, h + PIPE_GAP, PIPE_WIDTH, HEIGHT - GROUND_HEIGHT - h - PIPE_GAP));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (started && !gameOver) {
                birdVel += gravity;
                birdY += birdVel;

                for (int i = 0; i < pipes.size(); i++) {
                    Rectangle pipe = pipes.get(i);
                    pipe.x -= PIPE_SPEED;
                }

                if (!pipes.isEmpty() && pipes.get(0).x + PIPE_WIDTH < 0) {
                    pipes.remove(0);
                    pipes.remove(0);
                    addPipe(false);
                }

                for (int i = 0; i < pipes.size(); i++) {
                    Rectangle pipe = pipes.get(i);
                    if (pipe.y == 0 && pipe.x + PIPE_WIDTH == WIDTH / 2 - BIRD_SIZE / 2) {
                        score++;
                        if (score > highScore) highScore = score;
                    }
                    if (pipe.intersects(new Rectangle(WIDTH / 2 - BIRD_SIZE / 2, birdY, BIRD_SIZE, BIRD_SIZE))) {
                        gameOver = true;
                    }
                }

                if (birdY + BIRD_SIZE > HEIGHT - GROUND_HEIGHT || birdY < 0) {
                    gameOver = true;
                }
            }
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Sky
            g.setColor(SKY);
            g.fillRect(0, 0, WIDTH, HEIGHT);

            // Pipes
            for (Rectangle pipe : pipes) {
                g.setColor(new Color(34, 139, 34));
                g.fillRect(pipe.x, pipe.y, pipe.width, pipe.height);
                g.setColor(new Color(0, 100, 0));
                g.fillRect(pipe.x, pipe.y, pipe.width, 20);
            }

            // Ground
            g.setColor(GROUND);
            g.fillRect(0, HEIGHT - GROUND_HEIGHT, WIDTH, GROUND_HEIGHT);

            // Bird (with shading)
            int bx = WIDTH / 2 - BIRD_SIZE / 2;
            int by = birdY;
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.YELLOW);
            g2.fillOval(bx, by, BIRD_SIZE, BIRD_SIZE);
            g2.setColor(Color.ORANGE);
            g2.fillOval(bx + BIRD_SIZE / 2, by + BIRD_SIZE / 2, BIRD_SIZE / 2, BIRD_SIZE / 2);
            g2.setColor(Color.WHITE);
            g2.fillOval(bx + 25, by + 10, 10, 10);
            g2.setColor(Color.BLACK);
            g2.fillOval(bx + 28, by + 13, 5, 5);
            Polygon beak = new Polygon();
            beak.addPoint(bx + BIRD_SIZE, by + BIRD_SIZE / 2);
            beak.addPoint(bx + BIRD_SIZE + 10, by + BIRD_SIZE / 2 + 5);
            beak.addPoint(bx + BIRD_SIZE, by + BIRD_SIZE / 2 + 10);
            g2.setColor(Color.ORANGE);
            g2.fillPolygon(beak);

            // Score
            g.setFont(new Font("Arial", Font.BOLD, 32));
            g.setColor(Color.WHITE);
            String scoreText = String.valueOf(score);
            int sw = g.getFontMetrics().stringWidth(scoreText);
            g.drawString(scoreText, WIDTH / 2 - sw / 2, 80);

            // Game Over
            if (!started) {
                g.setFont(new Font("Arial", Font.BOLD, 28));
                g.setColor(Color.BLACK);
                String msg = "Press SPACE to Start";
                int mw = g.getFontMetrics().stringWidth(msg);
                g.drawString(msg, WIDTH / 2 - mw / 2, HEIGHT / 2 - 50);
            }
            if (gameOver) {
                g.setFont(new Font("Arial", Font.BOLD, 36));
                g.setColor(Color.RED);
                String msg = "Game Over";
                int mw = g.getFontMetrics().stringWidth(msg);
                g.drawString(msg, WIDTH / 2 - mw / 2, HEIGHT / 2 - 20);

                g.setFont(new Font("Arial", Font.BOLD, 22));
                g.setColor(Color.BLACK);
                String restart = "Press SPACE to Restart";
                int rw = g.getFontMetrics().stringWidth(restart);
                g.drawString(restart, WIDTH / 2 - rw / 2, HEIGHT / 2 + 20);

                String hs = "High Score: " + highScore;
                int hsw = g.getFontMetrics().stringWidth(hs);
                g.drawString(hs, WIDTH / 2 - hsw / 2, HEIGHT / 2 + 50);
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                if (!started) {
                    started = true;
                }
                if (gameOver) {
                    reset();
                }
                if (!gameOver) {
                    birdVel = jump;
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {}

        @Override
        public void keyTyped(KeyEvent e) {}
    }
}
