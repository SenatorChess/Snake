import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener{
	private final int B_WIDTH = 600;
	private final int B_HEIGHT = 600;
	private final int DOT_SIZE = 10;
	private final int ALL_DOTS = B_WIDTH * B_HEIGHT / DOT_SIZE * DOT_SIZE;
	private final int RAND_POS = 59;
	private final int DELAY = 75;
	
	private final int x[] = new int[ALL_DOTS];
	private final int y[] = new int[ALL_DOTS];
	
	private int dots;
	private int apple_x;
	private int apple_y;
	private int dotsplayer1;
	
	private boolean leftDirection = false;
	private boolean rightDirection = true;
	private boolean upDirection = false;
	private boolean downDirection = false;
	private boolean inGame = true;
	
	private Timer timer;
	private Image bodygreen;
	private Image apple;
	private Image head;
	private Image bodyYellow;
	boolean isGreen = false;
	public Board() {
	
		initBoard();
	}
	private void initBoard() {
		
		addKeyListener(new TAdapter());
		setBackground(Color.black);
		setFocusable(true);
		setDoubleBuffered(true);
		
		setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
		loadImages();
		initGame();
		
	}
	
	private void loadImages() {
		ImageIcon iih = new ImageIcon("src/Resources/Head of snake.png");
		head = iih.getImage();
		
		ImageIcon iia = new ImageIcon("src/Resources/Apple.png");
		apple = iia.getImage();
		
		ImageIcon iib = new ImageIcon("src/Resources/Part of snake 1.png");
		bodygreen = iib.getImage();
		
		ImageIcon iic = new ImageIcon("src/Resources/Part of snake 2.png");
		
		ImageIcon iid = new ImageIcon("src/Resources/part of snake 3.png");
		bodyYellow = iid.getImage();
	}
	
private void initGame() {
	dots = 5;
			
	for (int z = 0; z < dots; z++) {
		x[z] = 50 - z * 10;
		y[z] = 50;
	}
	
	locateApple();
	
	timer = new Timer(DELAY, this);
	timer.start();
}
@Override
public void paintComponent(Graphics g) {
	super.paintComponent(g);
	
	doDrawing(g);
}
private void doDrawing(Graphics g) {
	if (inGame) {
		g.drawImage(apple, apple_x, apple_y, this);
		
		for (int z = 0; z < dots; z++) {
			if (z == 0) {
				g.drawImage(head, x[z], y[z], this);
				Player1Score(g);
			} else {
				//load alternating
				
				if(!isGreen){
				g.drawImage(bodygreen,  x[z],  y[z],  this);
				Player1Score(g);
				isGreen = true;
				}else if(isGreen) {
					g.drawImage(bodyYellow, x[z], y[z], this);
					Player1Score(g);
					isGreen = false;
				}
			}
		}
		
		Toolkit.getDefaultToolkit().sync();
	
	} else {
		
		gameOver(g);
	}
}

private void gameOver(Graphics g) {
	
	String msg = "Game Over";
	Font small = new Font("Helvetica", Font.BOLD, 14);
	FontMetrics metr = getFontMetrics(small);
	
	g.setColor(Color.white);
	g.setFont(small);
	g.drawString(msg, (B_WIDTH - metr.stringWidth(msg)) / 2, B_HEIGHT / 2);
}

private void checkApple() {
	
	if ((x[0] == apple_x) && (y[0] == apple_y)) {
		
		dots++;
		locateApple();
	}
}

private void move() {
	for (int z = dots; z > 0; z--) {
		x[z] = x[(z - 1)];
		y[z] = y[(z - 1)];
	}
	
	if (leftDirection) {
		x[0] -= DOT_SIZE;
	}
	
	if (rightDirection) {
		x[0] += DOT_SIZE;
	}
	
	if (upDirection) {
		y[0] -= DOT_SIZE;
	}
	
	if (downDirection) {
		y[0] += DOT_SIZE;
	}
}
private void Player1Score(Graphics g){
	int player1score = dotsplayer1 - 3;
	String Player1Score = Integer.toString(player1score);
	Font small = new Font("Helvetica", Font.BOLD, 9);
	FontMetrics met = getFontMetrics(small);
	g.setColor(Color.MAGENTA);
	g.setFont(small);
	g.drawString(Player1Score, (B_WIDTH - met.stringWidth(Player1Score)) / 5, B_HEIGHT / 5 );}

private void checkCollision() {
	
	for (int z = dots; z > 0; z--) {
		
		if ((z > 4) && (x[0] == x[z]) && (y[0] == y[z])) {
		}
	}
	
	if (y[0] >= B_HEIGHT) {
		inGame = false;
	}
	
	if (y[0] < 0) {
		inGame = false;
	}
	
	if (x[0] >= B_WIDTH) {
		inGame = false;
	}
	
	if (x[0] < 0) {
		inGame = false;
	}
	
	if (!inGame) {
		timer.stop();
	}
}
	

private void locateApple() {
	
	int r = (int) (Math.random() * RAND_POS);
	apple_x = ((r * DOT_SIZE));
	
	r = (int) (Math.random() * RAND_POS);
	apple_y = ((r * DOT_SIZE));
}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (inGame) {
			
			checkApple();
			checkCollision();
			move();
		}
		
		repaint();
	}public class TAdapter implements KeyListener {

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			
			int key = e.getKeyCode();
			
			if ((key == KeyEvent.VK_LEFT)&& (!rightDirection)) {
				leftDirection = true;
				upDirection = false;
				downDirection = false;
			}
			
			if ((key == KeyEvent.VK_RIGHT) && (!leftDirection)) {
				rightDirection = true;
				upDirection = false;
				downDirection = false;
			}
			
			if ((key == KeyEvent.VK_UP) && (!downDirection)) {
				upDirection = true;
				rightDirection = false;
				leftDirection = false;
			}
			
			if ((key == KeyEvent.VK_DOWN) && (!upDirection)) {
				downDirection = true;
				rightDirection = false;
				leftDirection = false;
			}
			}
			
			
			

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub

		}

	}
}
	
