package Day10;

import javax.swing.JFrame;

public class LifeFrame extends JFrame{

	public LifeFrame() {
		
		setSize(1000, 1000);
		setTitle("Game of Life");
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(new LifePanel());
		
	}
	
	public static void main(String[] args) {
		new LifeFrame();

	}

}
