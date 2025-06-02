import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import java.util.Random;

// Multiplayer Snake: Two players, one food, separate controls and scores

// Player 1: Arrow keys (as before)
// Player 2: WASD (W=up, A=left, S=down, D=right)
class MultiplayerGamePanel extends JPanel implements ActionListener {
    private static final int TILE_SIZE = 40; // Bigger tiles for bigger characters
    private static final int WIDTH = 30; // Adjusted for screen fit
    private static final int HEIGHT = 20; // Adjusted for screen fit
    private static final int DELAY = 150; // Slower speed

    private final Player player1 = new Player(new Point(WIDTH / 4, HEIGHT / 2), 'R', new Color(80, 200, 120),
            new Color(60, 160, 100));
    private final Player player2 = new Player(new Point(3 * WIDTH / 4, HEIGHT / 2), 'L', new Color(120, 120, 240),
            new Color(80, 80, 200));
    private java.util.List<Point> foods = new java.util.ArrayList<>();
    private java.util.List<Point> dangers = new java.util.ArrayList<>();
    private boolean running = false;
    private Timer timer;

    public MultiplayerGamePanel() {
        setPreferredSize(new Dimension(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE));
        setBackground(new Color(30, 30, 30));
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                // Player 1: Arrow keys
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        if (player1.direction != 'R')
                            player1.direction = 'L';
                        break;
                    case KeyEvent.VK_RIGHT:
                        if (player1.direction != 'L')
                            player1.direction = 'R';
                        break;
                    case KeyEvent.VK_UP:
                        if (player1.direction != 'D')
                            player1.direction = 'U';
                        break;
                    case KeyEvent.VK_DOWN:
                        if (player1.direction != 'U')
                            player1.direction = 'D';
                        break;
                }
                // Player 2: WASD
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_A:
                        if (player2.direction != 'R')
                            player2.direction = 'L';
                        break;
                    case KeyEvent.VK_D:
                        if (player2.direction != 'L')
                            player2.direction = 'R';
                        break;
                    case KeyEvent.VK_W:
                        if (player2.direction != 'D')
                            player2.direction = 'U';
                        break;
                    case KeyEvent.VK_S:
                        if (player2.direction != 'U')
                            player2.direction = 'D';
                        break;
                }
            }
        });
        startGame();
    }

    private void startGame() {
        player1.snake.clear();
        player1.snake.add(new Point(WIDTH / 4, HEIGHT / 2));
        player1.direction = 'R';
        player1.score = 0;

        player2.snake.clear();
        player2.snake.add(new Point(3 * WIDTH / 4, HEIGHT / 2));
        player2.direction = 'L';
        player2.score = 0;

        placeFoods(10); // 10 foods
        placeDangers(15); // 15 dangers
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    private void placeFoods(int count) {
        foods.clear();
        Random rand = new Random();
        while (foods.size() < count) {
            Point p = new Point(rand.nextInt(WIDTH), rand.nextInt(HEIGHT));
            if (!player1.snake.contains(p) && !player2.snake.contains(p) && !foods.contains(p) && !dangers.contains(p))
                foods.add(p);
        }
    }

    private void placeDangers(int count) {
        dangers.clear();
        Random rand = new Random();
        while (dangers.size() < count) {
            Point p = new Point(rand.nextInt(WIDTH), rand.nextInt(HEIGHT));
            if (!player1.snake.contains(p) && !player2.snake.contains(p) && !foods.contains(p) && !dangers.contains(p))
                dangers.add(p);
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (running) {
            move(player1);
            move(player2);

            // Head-to-head collision
            if (player1.snake.getFirst().equals(player2.snake.getFirst())) {
                running = false;
                timer.stop();
            }

            checkCollision(player1, player2);
            checkCollision(player2, player1);
            checkFood(player1);
            checkFood(player2);
        }
        repaint();
    }

    private void move(Player player) {
        Point head = new Point(player.snake.getFirst());
        switch (player.direction) {
            case 'L':
                head.x--;
                break;
            case 'R':
                head.x++;
                break;
            case 'U':
                head.y--;
                break;
            case 'D':
                head.y++;
                break;
        }
        // Wrap-around logic
        if (head.x < 0)
            head.x = WIDTH - 1;
        if (head.x >= WIDTH)
            head.x = 0;
        if (head.y < 0)
            head.y = HEIGHT - 1;
        if (head.y >= HEIGHT)
            head.y = 0;

        player.snake.addFirst(head);
        player.snake.removeLast();
    }

    private void checkCollision(Player player, Player other) {
        Point head = player.snake.getFirst();
        // Self collision
        if (player.snake.subList(1, player.snake.size()).contains(head)) {
            running = false;
            timer.stop();
        }
        // Collision with other snake's body
        if (other.snake.contains(head)) {
            running = false;
            timer.stop();
        }
        // Collision with dangers
        if (dangers.contains(head)) {
            running = false;
            timer.stop();
        }
    }

    private void checkFood(Player player) {
        Point head = player.snake.getFirst();
        for (int i = 0; i < foods.size(); i++) {
            if (head.equals(foods.get(i))) {
                player.snake.addLast(new Point(player.snake.getLast()));
                player.score += 10;
                // Replace eaten food
                Random rand = new Random();
                Point newFood;
                do {
                    newFood = new Point(rand.nextInt(WIDTH), rand.nextInt(HEIGHT));
                } while (player1.snake.contains(newFood) || player2.snake.contains(newFood) || foods.contains(newFood)
                        || dangers.contains(newFood));
                foods.set(i, newFood);
                break;
            }
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw grid (optional, can comment out for cleaner look)
        g.setColor(new Color(50, 50, 50));
        for (int i = 0; i <= WIDTH; i++)
            g.drawLine(i * TILE_SIZE, 0, i * TILE_SIZE, HEIGHT * TILE_SIZE);
        for (int i = 0; i <= HEIGHT; i++)
            g.drawLine(0, i * TILE_SIZE, WIDTH * TILE_SIZE, i * TILE_SIZE);

        // Draw foods (alternate colors)
        for (int i = 0; i < foods.size(); i++) {
            g.setColor(i % 2 == 0 ? player1.headColor : player2.headColor);
            Point f = foods.get(i);
            g.fillOval(f.x * TILE_SIZE, f.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
        }

        // Draw dangers (red)
        g.setColor(Color.RED);
        for (Point d : dangers) {
            g.fillRect(d.x * TILE_SIZE, d.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
        }

        // Draw player 1 snake
        for (int i = 0; i < player1.snake.size(); i++) {
            g.setColor(i == 0 ? player1.headColor : player1.bodyColor);
            Point p = player1.snake.get(i);
            g.fillRoundRect(p.x * TILE_SIZE, p.y * TILE_SIZE, TILE_SIZE, TILE_SIZE, 10, 10);
        }
        // Draw player 2 snake
        for (int i = 0; i < player2.snake.size(); i++) {
            g.setColor(i == 0 ? player2.headColor : player2.bodyColor);
            Point p = player2.snake.get(i);
            g.fillRoundRect(p.x * TILE_SIZE, p.y * TILE_SIZE, TILE_SIZE, TILE_SIZE, 10, 10);
        }

        // Draw scores
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.drawString("P1: " + player1.score, 10, 22);
        g.drawString("P2: " + player2.score, getWidth() - 90, 22);

        // Game over
        if (!running) {
            g.setColor(new Color(255, 255, 255, 200));
            g.setFont(new Font("Arial", Font.BOLD, 40));
            String msg = "Game Over";
            FontMetrics fm = g.getFontMetrics();
            int x = (getWidth() - fm.stringWidth(msg)) / 2;
            int y = getHeight() / 2;
            g.drawString(msg, x, y);
            g.setFont(new Font("Arial", Font.PLAIN, 20));
            String restart = "Press any key to restart";
            x = (getWidth() - g.getFontMetrics().stringWidth(restart)) / 2;
            g.drawString(restart, x, y + 30);

            addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    removeKeyListener(this);
                    startGame();
                }
            });
        }
    }
}

class Player {
    LinkedList<Point> snake = new LinkedList<>();
    char direction;
    int score = 0;
    Color headColor, bodyColor;

    public Player(Point start, char dir, Color headColor, Color bodyColor) {
        snake.add(new Point(start));
        direction = dir;
        this.headColor = headColor;
        this.bodyColor = bodyColor;
    }
}

public class SnakeGame extends JFrame {
    public SnakeGame() {
        setTitle("Snake Game");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        add(new MultiplayerGamePanel());
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SnakeGame::new);
    }
}

class GamePanel extends JPanel implements ActionListener {
    private static final int TILE_SIZE = 40; // Bigger tiles
    private static final int WIDTH = 30; // More columns
    private static final int HEIGHT = 22; // More rows
    private static final int DELAY = 100;

    private final LinkedList<Point> snake = new LinkedList<>();
    private Point food;
    private char direction = 'R';
    private boolean running = false;
    private Timer timer;
    private int score = 0;

    public GamePanel() {
        setPreferredSize(new Dimension(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE));
        setBackground(new Color(30, 30, 30));
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        if (direction != 'R')
                            direction = 'L';
                        break;
                    case KeyEvent.VK_RIGHT:
                        if (direction != 'L')
                            direction = 'R';
                        break;
                    case KeyEvent.VK_UP:
                        if (direction != 'D')
                            direction = 'U';
                        break;
                    case KeyEvent.VK_DOWN:
                        if (direction != 'U')
                            direction = 'D';
                        break;
                }
            }
        });
        startGame();
    }

    private void startGame() {
        snake.clear();
        snake.add(new Point(WIDTH / 2, HEIGHT / 2));
        direction = 'R';
        score = 0;
        placeFood();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    private void placeFood() {
        Random rand = new Random();
        do {
            food = new Point(rand.nextInt(WIDTH), rand.nextInt(HEIGHT));
        } while (snake.contains(food));
    }

    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkCollision();
            checkFood();
        }
        repaint();
    }

    private void move() {
        Point head = new Point(snake.getFirst());
        switch (direction) {
            case 'L':
                head.x--;
                break;
            case 'R':
                head.x++;
                break;
            case 'U':
                head.y--;
                break;
            case 'D':
                head.y++;
                break;
        }
        snake.addFirst(head);
        snake.removeLast();
    }

    private void checkCollision() {
        Point head = snake.getFirst();
        // Wall or self collision
        if (head.x < 0 || head.x >= WIDTH || head.y < 0 || head.y >= HEIGHT ||
                snake.subList(1, snake.size()).contains(head)) {
            running = false;
            timer.stop();
        }
    }

    private void checkFood() {
        if (snake.getFirst().equals(food)) {
            snake.addLast(new Point(snake.getLast()));
            score += 10;
            placeFood();
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw grid
        g.setColor(new Color(50, 50, 50));
        for (int i = 0; i <= WIDTH; i++)
            g.drawLine(i * TILE_SIZE, 0, i * TILE_SIZE, HEIGHT * TILE_SIZE);
        for (int i = 0; i <= HEIGHT; i++)
            g.drawLine(0, i * TILE_SIZE, WIDTH * TILE_SIZE, i * TILE_SIZE);

        // Draw food
        g.setColor(new Color(255, 80, 80));
        g.fillOval(food.x * TILE_SIZE, food.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);

        // Draw snake
        for (int i = 0; i < snake.size(); i++) {
            if (i == 0)
                g.setColor(new Color(80, 200, 120));
            else
                g.setColor(new Color(60, 160, 100));
            Point p = snake.get(i);
            g.fillRoundRect(p.x * TILE_SIZE, p.y * TILE_SIZE, TILE_SIZE, TILE_SIZE, 10, 10);
        }

        // Draw score
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.drawString("Score: " + score, 10, 22);

        // Game over
        if (!running) {
            g.setColor(new Color(255, 255, 255, 200));
            g.setFont(new Font("Arial", Font.BOLD, 40));
            String msg = "Game Over";
            FontMetrics fm = g.getFontMetrics();
            int x = (getWidth() - fm.stringWidth(msg)) / 2;
            int y = getHeight() / 2;
            g.drawString(msg, x, y);
            g.setFont(new Font("Arial", Font.PLAIN, 20));
            String restart = "Press any key to restart";
            x = (getWidth() - g.getFontMetrics().stringWidth(restart)) / 2;
            g.drawString(restart, x, y + 30);

            addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    removeKeyListener(this);
                    startGame();
                }
            });
        }
    }
}