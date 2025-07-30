package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JPanel;
import entity.Entity;
import entity.Player;
import tile.TileManager;

// COLOR PALETTE: endesga 32 palette
// LIST OF THINGS TO ADD:
// - Animation when there is no movement
// - Fix the problem regarding the solid area of superObject
// - Create dialogue sprites
// - Player animation when hit (not transparent)
// - Different sound effect depending on which monster is hit
// - When some monsters die they become NPCs (ex: CorruptedRat into Rat) or just change the dying animation (new sprites)
// - Make a better character status window

public class GamePanel extends JPanel implements Runnable{
	
	// SCREEN SETTINGS
	public final int originalTileSize = 16; // pixels of the tile (map tiles, NPCs, player, ...) (default: 16)
	public final int scale = 3; // scale * tileSize (default: 3)
	
	public final int tileSize = originalTileSize * scale; // makes the tiles bigger so its easier to see
	public final int maxScreenCol = 14; // tiles horizontally (default: 16)
	public final int maxScreenRow = 10; // tiles vertically (default: 12)
	public final int screenWidth = tileSize * maxScreenCol; // calculates the width of the screen
	public final int screenHeight = tileSize * maxScreenRow; // calculates the height of the screen
	
	// WORLD SETTINGS
	public final int maxWorldCol = 50;
	public final int maxWorldRow = 50;
	// public final int worldWidth = tileSize * maxWorldCol;
	// public final int worldHeight = tileSize * maxWorldRow;
	
	// FPS
	public int FPS = 60;
	
	// SYSTEM
	TileManager tileM = new TileManager(this);
	public KeyHandler keyH = new KeyHandler(this);
	Sound music = new Sound();
	Sound se = new Sound();
	public CollisionChecker cChecker = new CollisionChecker(this);
	public AssetSetter aSetter = new AssetSetter(this);
	public UI ui = new UI(this);
	public EventHandler eHandler = new EventHandler(this);
	Thread gameThread;
	
	// ENTITY AND OBJECT
	public Player player = new Player(this, keyH);
	public Entity obj[] = new Entity[10]; // size depending on the number of objects that can be displayed
	public Entity npc[] = new Entity[10]; // table for storing entities
	public Entity monster[] = new Entity[20]; 
	ArrayList<Entity> entityList = new ArrayList<>(); // list of all entities to sort the order of drawing
	
	// GAME STATE
	public int gameState; // sets the state of the game
	public final int titleState = 0;
	public final int playState = 1;
	public final int pauseState = 2;
	public final int dialogueState = 3;
	public final int characterState = 4;
	
	// CONSTRUCTOR
	public GamePanel() {
		
		this.setPreferredSize(new Dimension(screenWidth, screenHeight)); // sets the size of JPanel
		this.setBackground(Color.black); // sets background color to black
		this.setDoubleBuffered(true); // all the drawing for this component will be done in an offscreen painting buffer (improves game rendering performance)
		this.addKeyListener(keyH); // adds the keyhandler
		this.setFocusable(true); // gamePanel is focused on receiving key imputs
		
	}
	
	public void setUpGame() {
		
		aSetter.setObject();
		aSetter.setNPC();
		aSetter.setMonster();
		// playMusic(0);
		gameState = titleState;
	}
	
	public void startGameThread() {
		
		gameThread = new Thread(this);
		gameThread.start(); // starts the thread calling the method run()
	}

	@Override
	/* public void run() { // GAME LOOP
		
		double drawInterval = 1000000000 / FPS; // 0.0166666 seconds
		double nextDrawTime = System.nanoTime() + drawInterval;
		
		while (gameThread != null) {
			
			update(); // update information such as character position
			
			repaint(); // draw the screen with the updated information (calls the paintComponent method)
			
			try {
				
				double remainingTime = nextDrawTime - System.nanoTime(); // calculates the remaining time until next draw
				remainingTime = remainingTime / 1000000; //changes the value to milliseconds
				
				if (remainingTime < 0) {
					remainingTime = 0;
				}
				
				Thread.sleep((long) remainingTime); // waits for the next time to draw
				
				nextDrawTime += drawInterval;
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	} */
	
	public void run() { // GAME LOOP (for the specified FPS)
		
		double drawInterval = 1000000000 / FPS; // 0.0166666 seconds
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		int drawCount = 0;
		
		while (gameThread != null) {
			
			currentTime = System.nanoTime();
			
			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;
			
			if (delta >= 1) {
				update();
				repaint();
				delta--;
				drawCount++;
			}
			
			if (timer > 1000000000) {
				// System.out.println("FPS: " + drawCount); // see the current FPS
				drawCount = 0;
				timer = 0;
			}
			
		}
		
	}
	
	public void update() {
		
		if (gameState == playState) { // while the game goes on
			// PLAYER
			player.update();
			// NPC
			for (int i = 0; i < npc.length; i++) {
				if (npc[i] != null) { // if there is a registered NPC (avoids NullPointer)
					npc[i].update(); // update the NPC
				}
			}
			// MONSTER
			for (int i = 0; i < monster.length; i++) {
				if (monster[i] != null) { // if there is a registered monster (avoids NullPointer)
					if (monster[i].alive == true && monster[i].dying == false) {
						monster[i].update(); // update the monster
					}
					if (monster[i].alive == false) {
						monster[i] = null; // monster died
					}
				}
			}
		}
		if (gameState == pauseState) {
			// nothing
		}
	}
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		//DRAWING LAYERS
		
		//DEBUG
		long drawStart = 0;
		if (keyH.checkDrawTime == true) {
			drawStart = System.nanoTime();
		}
		// TITLE SCREEN
		if (gameState == titleState) {
			
			ui.draw(g2);
			
		} else {
			
			// TILES
			tileM.draw(g2);
			
			// ENTITIES
			// add the components
			entityList.add(player); // player
			for(int i = 0; i < npc.length; i++) { // NPCs
				if (npc[i] != null) {
					entityList.add(npc[i]);
				}
			}
			for(int i = 0; i < obj.length; i++) { // objects
				if (obj[i] != null) {
					entityList.add(obj[i]);
				}
			}
			for(int i = 0; i < monster.length; i++) { // objects
				if (monster[i] != null) {
					entityList.add(monster[i]);
				}
			}
			// sort the components
			Collections.sort(entityList, new Comparator<Entity>() {
				@Override
				public int compare(Entity e1, Entity e2) { // orders the list depending on WorldY
					int result = Integer.compare(e1.worldY, e2.worldY);
					return result;
				}
			});
			// draw the components
			for (int i = 0; i < entityList.size(); i++) {
				entityList.get(i).draw(g2);
			}
			// empty the list
			entityList.clear();
			
			// UI SCREEN
			ui.draw(g2);
		}
			
		// check time passed
		if (keyH.checkDrawTime == true) {
			long drawEnd = System.nanoTime();
			long passed = drawEnd - drawStart;
			g2.setColor(Color.white);
			g2.drawString("DrawTime: " + passed, 10, 400);
		}
		
		
		g2.dispose(); // releases any system resources that is using
	}
	
	
	
	// MUSIC AND SOUND METHODS
	
	public void playMusic(int i) { // plays the background / loop music
		
		// first we set the file, play the song and loop it
		music.setFile(i);
		music.play();
		music.loop();
	}
	
	public void stopMusic() {
		
		music.stop();
	}
	
	public void playSE(int i) {
		
		se.setFile(i);
		se.play();
	}

}
