import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MineSweeper extends JFrame{

    private JMenu gameMenu;
    private JPanel gamePanel;
    private JPanel dataPanel;
    private JPanel menuPanel;
    private JButton restart;
    private JButton autoResolver;
    private final int IMAGE_SIZE = 40;
    private Game game;
    private Cell easySize = new Cell(9,9);
    private int easyBombNumber = 12;
    private JLabel bombs;

    MineSweeper() {
    }

    void restartGame(){
        generateNewGame();
        gamePanel.repaint();
        Size.setState(State.PLAYING);
    }

    private void generateNewGame(){
        game = new Game();
        game.play();
    }

    private void addGamePanel() {
        gamePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for (int i = 0; i < Size.getSize().x; i++) {
                    for (int j = 0; j < Size.getSize().y; j++) {
                        g.drawImage(game.getComponents(new Cell(i, j)).image, i * IMAGE_SIZE, j * IMAGE_SIZE, this);
                    }
                }
            }
        };
        gamePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Cell pressed = new Cell(e.getX() / IMAGE_SIZE, e.getY() / IMAGE_SIZE);
                if (e.getButton() == MouseEvent.BUTTON1) {
                    game.pressLeft(pressed);
                }
                if (e.getButton() == MouseEvent.BUTTON3) {
                    game.pressRight(pressed);
                    bombs.setText("" + (easyBombNumber - game.getFlagged()));
                   dataPanel.repaint();
                }
                if (Size.getState() == State.DEFEAT) {
                    restart.setIcon(new ImageIcon("images/defeat.png"));
                }
                if (game.getClosed() + game.getFlagged() == easyBombNumber) {
                    restart.setIcon(new ImageIcon("images/victory.png"));
                }
                gamePanel.repaint();

            }
        });
    }

    private void addDataPanel() {
        restart = new JButton();
        restart.setIcon(new ImageIcon("images/playing.png"));
        restart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                restartGame();
                restart.setIcon(new ImageIcon("images/playing.png"));
            }
        });
        autoResolver = new JButton();
        autoResolver.setText("Res");
        autoResolver.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                game.resolve();
                gamePanel.repaint();
            }
        });
        restart.setBorder(BorderFactory.createLoweredBevelBorder());
        bombs = new JLabel("" + easyBombNumber);
        bombs.setFont(new Font("DigtalFont.TTF", Font.BOLD, 35));
        bombs.setForeground(Color.RED);
        dataPanel = new JPanel();
        dataPanel.add(bombs);
        dataPanel.add(restart);
        dataPanel.add(autoResolver);
        dataPanel.setBorder(BorderFactory.createEtchedBorder());
        dataPanel.setBackground(Color.LIGHT_GRAY);
    }

    void start() {
        setImages();
        Size.setSize(easySize, easyBombNumber, State.PLAYING);
        generateNewGame();
        addGamePanel();
        addDataPanel();
        JButton beginner = new JButton("beginner");
        JButton intermediate = new JButton("intermediate");
        JButton expert = new JButton("expert");
        menuPanel = new JPanel();
        menuPanel.add(beginner);
        menuPanel.add(intermediate);
        menuPanel.add(expert);
        drawing();
    }


    private void setImages() {
        for (Components components : Components.values()){
            String fileName = "images/" + components.name().toLowerCase() + ".png";
            ImageIcon imageIcon = new ImageIcon(fileName);
            components.image = imageIcon.getImage();
        }
    }

    private void drawing() {
        gamePanel.setPreferredSize(new Dimension(Size.getSize().x * IMAGE_SIZE,Size.getSize().y * IMAGE_SIZE));
        dataPanel.setPreferredSize(new Dimension(Size.getSize().x * IMAGE_SIZE, IMAGE_SIZE / 2 * 3));
        menuPanel.setPreferredSize(new Dimension(Size.getSize().x * IMAGE_SIZE, IMAGE_SIZE / 4 * 3));
        add(menuPanel, BorderLayout.NORTH);
        add(dataPanel, BorderLayout.CENTER);
        add(gamePanel, BorderLayout.SOUTH);
        setTitle("SWEEPER");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);
        setIconImage(new ImageIcon("images/icon.png").getImage());
        pack();
        setLocationRelativeTo(null);
    }

}
