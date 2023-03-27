package Day10;

import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;



public class LifePanel extends JPanel implements ActionListener, 
MouseListener, MouseMotionListener, KeyListener{

	int xPanel = 1000;
	int yPanel = 1000;
	int size = 10;
	int xWidth = xPanel/size;
	int yHeight = yPanel/size;
	int[][] currentLife = new int[xWidth][yHeight];
	int[][] nextLife = new int[xWidth][yHeight];
	int initial = -1;
	Timer timer;
	
	public LifePanel() {
		setSize(xPanel, yPanel);
		setLayout(null);
		
		addMouseListener(this);
		addMouseMotionListener(this);
		addKeyListener(this);
		setFocusable(true);
		
		setBackground(Color.black);
		
		timer = new Timer(80, this);
	
		System.out.println("\n---------Conway's Game of Life---------\n");
		System.out.println("Press 'R' for random input");
		System.out.println("Press 'G' for Gosper glider gun");
		System.out.println("Use mouse to click the grid and choose alive squares");
		System.out.println("Press 'Enter' to start");
		System.out.println("Press 'C' to clear");
		System.out.println("Press 'Space' to stop");
		
	}
	
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		grid(g);
		display(g);
	}
	
	private void grid(Graphics g) {
		g.setColor(Color.DARK_GRAY);
		for (int i = 0; i < currentLife.length; i++) {
			g.drawLine(0, i * size, xPanel, i * size); //row
			g.drawLine(i * size, 0, i * size+1, yPanel); //column
		}
	}
	private void gun() {
		nextLife[10][10] = 1;
		nextLife[11][10] = 1;
		nextLife[10][11] = 1;
		nextLife[11][11] = 1;
		
		nextLife[20][10] = 1;
		nextLife[20][11] = 1;
		nextLife[20][12] = 1;
		nextLife[21][9] = 1;
		nextLife[21][13] = 1;
		nextLife[22][8] = 1;
		nextLife[22][14] = 1;
		nextLife[23][8] = 1;
		nextLife[23][14] = 1;
		nextLife[24][11] = 1;
		nextLife[25][9] = 1;
		nextLife[25][13] = 1;
		nextLife[26][10] = 1;
		nextLife[26][11] = 1;
		nextLife[26][12] = 1;
		nextLife[27][11] = 1;
		
		nextLife[30][8] = 1;
		nextLife[30][9] = 1;
		nextLife[30][10] = 1;
		nextLife[31][8] = 1;
		nextLife[31][9] = 1;
		nextLife[31][10] = 1;
		nextLife[32][7] = 1;
		nextLife[32][11] = 1;
		nextLife[34][6] = 1;
		nextLife[34][7] = 1;
		nextLife[34][6] = 1;
		nextLife[34][7] = 1;
		nextLife[34][11] = 1;
		nextLife[34][12] = 1;
		
		nextLife[44][8] = 1;
		nextLife[44][9] = 1;
		nextLife[45][8] = 1;
		nextLife[45][9] = 1;
	}
	
	private void spawn() {
		
			for (int x = 0; x < xWidth; x++) {
				for (int y = 0; y < yHeight; y++) {
					if ((int)(Math.random() * 5) == 0) {
						nextLife[x][y] = 1;
					}
				}
			}
	}
	
	private void display(Graphics g) {
		Color c1 = new Color(135,206,250);
		g.setColor(c1);
		copyArray();
		
		for (int x = 0; x < xWidth; x++) {
			for (int y = 0; y < yHeight; y++) {
				if(currentLife[x][y] == 1) {
					g.fillRect(x * size, y * size, size, size);
				}
			}
		}
		
	}
	
	private void copyArray() {
		for (int x = 0; x < xWidth; x++) {
			for (int y = 0; y < yHeight; y++) {
				currentLife[x][y] = nextLife[x][y];
			}
		}
	}
	
	private int check(int x, int y) {
		int alive = 0;
		alive += currentLife[(x + xWidth - 1) % xWidth][(y + yHeight - 1) % yHeight];
		alive += currentLife[(x + xWidth) % xWidth][(y + yHeight - 1) % yHeight];
		
		alive += currentLife[(x + xWidth + 1) % xWidth][(y + yHeight - 1) % yHeight];
		alive += currentLife[(x + xWidth - 1) % xWidth][(y + yHeight) % yHeight];
		
		alive += currentLife[(x + xWidth + 1) % xWidth][(y + yHeight) % yHeight];
		alive += currentLife[(x + xWidth - 1) % xWidth][(y + yHeight + 1) % yHeight];
		
		alive += currentLife[(x + xWidth) % xWidth][(y + yHeight + 1) % yHeight];
		alive += currentLife[(x + xWidth + 1) % xWidth][(y + yHeight + 1) % yHeight];
		return alive;
	}
	
	
	private void clear() {
		for (int x = 0; x < xWidth; x++) {
			for (int y = 0; y < yHeight; y++) {
				nextLife[x][y] = 0;
			}
		}
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		int alive;
		
		for (int x = 0; x < xWidth; x++) {
			for (int y = 0; y < yHeight; y++) {
				alive = check(x,y);
				if (alive == 3) {
					nextLife[x][y] = 1;
				} else if (alive == 2 && currentLife[x][y] == 1) {
					nextLife[x][y] = 1;
				} else {
					nextLife[x][y] = 0;
				}
			}
		}
		repaint();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		int x = e.getX() / size;
		int y = e.getY() / size;
		
		if (currentLife[x][y] == 0 && initial == 0) {
			nextLife[x][y] = 1;
		} else if (currentLife[x][y] == 1 && initial == 1) {
			nextLife[x][y] = 0;
		} 
		
		repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
		//timer.stop();
		
		int x = e.getX() / size;
		int y = e.getY() / size;
		
		
		if (currentLife[x][y] == 0) {
			initial = 0;
			
		} else {
			initial = 1;;
		} 
		
		repaint();
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
		initial = -1;
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		int key = e.getKeyCode();
		if (key == e.VK_R) { //for random input
			spawn();
			timer.start();
		} else if (key == e.VK_C) { //clear
			clear();
			timer.stop();
		}else if (key == e.VK_ENTER) { //start
			timer.start();
			
		}else if (key == e.VK_SPACE) { //abandon, pause, stop
			timer.stop();
		}else if (key == e.VK_G) {
			gun();
			timer.start();
		}
		
		repaint();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
