package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import object.OBJ_Life;
import entity.Entity;

public class UI { // takes care of the screen UI (inventory, life, ...)
	
	GamePanel gp;
	Graphics2D g2;
	Font byteBounce;
	UtilityTool uTool = new UtilityTool();
	// player life
	BufferedImage life_full, life_half, life_blank, titleImage;
	// notifications
	public boolean messageOn = false;
	public String message = "";
	int messageCounter = 0;
	// game state
	public boolean gameFinished = false;
	public String currentDialogue = "";
	// title screen
	public int commandNum = 0;
	public int titleScreenState = 0; // 0:firstScreen 1:secondScreen
	
	public UI(GamePanel gp) {
		this.gp = gp; // game panel
		
		//get font
		try {
			InputStream is = getClass().getResourceAsStream("/font/ByteBounce.ttf");
			byteBounce = Font.createFont(Font.TRUETYPE_FONT, is); // creates the new font
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// create HUD object
		Entity life = new OBJ_Life(gp);
		life_full = life.image;
		life_half = life.image2;
		life_blank = life.image3;
		// adds the image
		try {
			titleImage = ImageIO.read(getClass().getResourceAsStream("/font/Title.png"));
			titleImage = uTool.scaleImage(titleImage, gp.tileSize*9, gp.tileSize*9/2);
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
	}
	
	public void showMessage(String text) {
		
		message = text;
		messageOn = true;
	}
	
	public void draw(Graphics2D g2) { // draws the UI
		
		this.g2 = g2;
		
		g2.setFont(byteBounce);
		g2.setColor(Color.white);
		
		// GAME STATE
		if (gp.gameState == gp.titleState) { // title state
			drawTitleScreen();
		}
		if (gp.gameState == gp.playState) { // play state
			drawPlayerLife();
		}
		if (gp.gameState == gp.pauseState) { // pause state
			drawPlayerLife();
			drawPauseScreen();
		}
		if (gp.gameState == gp.dialogueState) { // dialogue state
			drawPlayerLife();
			drawDialogueScreen();
		}
	}
	
	public void drawPlayerLife() {
		
		int x = gp.tileSize/2;
		int y = gp.tileSize/4;
		int i = 0;
		// draw max life
		while (i < gp.player.maxLife/2) { 
			g2.drawImage(life_blank, x, y, null);
			i++;
			x += gp.tileSize + gp.tileSize/8;
		}
		// reset
		x = gp.tileSize/2;
		y = gp.tileSize/4;
		i = 0;
		// draw current life
		while (i < gp.player.life) { 
			g2.drawImage(life_half, x, y, null);
			i++;
			if ( i < gp.player.life) {
				g2.drawImage(life_full, x, y, null);
			}
			i++;
			x += gp.tileSize + gp.tileSize/8;
		}
	}
	
	public void drawTitleScreen() {
		
		if (titleScreenState == 0) {
			
			// BACKGROUND
			g2.setColor(new Color(115, 62, 57));
			g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
			g2.setColor(new Color(62, 39, 49));
			g2.fillRect(gp.screenWidth/2-140, gp.tileSize*31/5, 280, 135);
			String text = "";
			int x = 0, y = 0;
			
			/* // TITLE
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 70F)); // font settings
			text = "ORANGE CAT"; // title of the game 1
			x = getXForCenteredText(text);
			y = gp.tileSize*2;
			g2.setColor(new Color(254, 174, 52));
			g2.drawString(text, x, y); // draw text
			// other part:
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 70F)); // font settings
			g2.setColor(new Color(254, 231, 97));
			text = "ADVENTURES"; // title of the game 2
			x = getXForCenteredText(text);
			y += gp.tileSize;
			g2.drawString(text, x, y); // draw text */
			
			// IMAGES 
			/* x = gp.screenWidth/2 - gp.tileSize;
			y += gp.tileSize*3/2 - gp.tileSize;
			g2.drawImage(gp.player.down1, x, y, gp.tileSize*2, gp.tileSize*2, null); */
			x = gp.screenWidth/2 - gp.tileSize*9/2;
			y += gp.tileSize*2/3;
			g2.drawImage(titleImage, x, y, null);
			
			// MENU
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F)); // font settings
			y += gp.tileSize*17/6;
			
			text = "New Game";
			x = getXForCenteredText(text);
			y += gp.tileSize*10/3;
			g2.setColor(new Color(254, 174, 52));
			if (commandNum == 0) {
				g2.setColor(new Color(247, 118, 34));
				g2.drawString(">", x - gp.tileSize*3/4, y); // cursor
			}
			g2.drawString(text, x, y);
			
			text = "Load Game"; // no interaction (yet)
			x = getXForCenteredText(text);
			y += gp.tileSize;
			g2.setColor(new Color(254, 174, 52));
			if (commandNum == 1) { // cursor
				g2.setColor(new Color(247, 118, 34));
				g2.drawString(">", x - gp.tileSize*3/4, y); 
			}
			g2.drawString(text, x, y);
			
			text = "Quit";
			x = getXForCenteredText(text);
			y += gp.tileSize;
			g2.setColor(new Color(254, 174, 52));
			if (commandNum == 2) { // cursor
				g2.setColor(new Color(247, 118, 34));
				g2.drawString(">", x - gp.tileSize*3/4, y); 
			}
			g2.drawString(text, x, y);
			
		} else if (titleScreenState == 1) {
			
			// BACKGROUND
			g2.setColor(new Color(115, 62, 57));
			g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
			g2.setColor(new Color(62, 39, 49));
			g2.fillRect(gp.screenWidth/2-260, gp.tileSize*4/3, 520, 330);
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F)); // font settings
			g2.drawString(">", gp.screenWidth-gp.tileSize*2, gp.screenHeight-gp.tileSize);
			String text;
			int x, y;
			
			// TEXT
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F)); // font settings
			g2.setColor(new Color(254, 174, 52));
			// story
			text = "You didn't know were you";
			x = getXForCenteredText(text);
			y = gp.tileSize*2;
			g2.drawString(text, x, y);
			text = "were, or what had happened,";
			x = getXForCenteredText(text);
			y += gp.tileSize;
			g2.drawString(text, x, y);
			text = "not even your own name.";
			x = getXForCenteredText(text);
			y += gp.tileSize;
			g2.drawString(text, x, y);
			text = "All except for a couple of";
			x = getXForCenteredText(text);
			y += gp.tileSize;
			g2.drawString(text, x, y);
			text = "meaningless words deep inside";
			x = getXForCenteredText(text);
			y += gp.tileSize;
			g2.drawString(text, x, y);
			text = "your lost memories: 'adventure'";
			x = getXForCenteredText(text);
			y += gp.tileSize;
			g2.drawString(text, x, y);
			text = "and 'great treasure'";
			x = getXForCenteredText(text);
			y += gp.tileSize;
			g2.drawString(text, x, y);
		}
	}
	
	public void drawPauseScreen() { // what to draw when the game state is paused
		
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 100F));
		String text = "GAME PAUSED";
		int x = getXForCenteredText(text);
		int y = gp.screenHeight/2;
		
		g2.drawString(text, x, y);
	}
	
	public void drawDialogueScreen() {
		
		//WINDOW
		int x = gp.tileSize*2; // position
		int y = gp.tileSize/2;
		int width = gp.screenWidth - (x*2); // scale
		int height = gp.tileSize*3;
		drawSubWindow(x, y, width, height); // creates the dialogue window
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F)); // font settings
		g2.drawString(">", x+width-gp.tileSize, y+height-gp.tileSize/2);
		// DIALOGUE
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 28F));
		x += gp.tileSize/2;
		y += gp.tileSize/2+g2.getFontMetrics().getHeight()/2; // centers the text
		
		for (String line : currentDialogue.split("\n")) {
			g2.drawString(line, x, y); // prints the dialogue
			y += gp.tileSize/2+g2.getFontMetrics().getHeight()/2; // space for the next line
		}
	}
	
	public void drawSubWindow(int x, int y, int width, int height) {
		
		Color c = new Color(0,0,0,200); // sets a color in RGB + transparency
		g2.setColor(c);
		g2.fillRoundRect(x, y, width, height, 0, 0); // creates a filled rectangle with round borders
		
		int widthStroke = gp.scale*2; // not in video, used as shortcut
		c = new Color(255,255,255); // white
		g2.setColor(c);
		g2.setStroke(new BasicStroke(widthStroke)); // defines the width of outlines rendered by graphics 2D
		g2.drawRoundRect(x, y, width, height, 0, 0);
	}
	
	public int getXForCenteredText(String text) { //calculate the x value to display the text in the center of the screen
		
		int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gp.screenWidth/2 - length/2;
		return x;
	}

}
