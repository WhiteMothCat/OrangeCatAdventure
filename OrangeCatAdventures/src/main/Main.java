package main;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // closes correctly
		window.setResizable(false); // window cannot be resized
		window.setTitle("RPG Adventure"); // game title
		
		GamePanel gamePanel = new GamePanel();
		window.add(gamePanel); // adds gamePanel to the window
		
		window.pack(); // causes the window to fit the layouts of gamePanel
		
		window.setLocationRelativeTo(null); // no specific location (null) = center
		window.setVisible(true); // the window can be seen
		
		gamePanel.setUpGame(); // sets the game (ex: objects)
		gamePanel.startGameThread();
		
	}

}
