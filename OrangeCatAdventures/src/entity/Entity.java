package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class Entity { // stores variables that will be used in player, monster and NPC classes
	
	GamePanel gp;
	// POSITION
	public int worldX, worldY;
	// SPRITES
	public BufferedImage up1, up2, left1, left2, right1, right2, down1, down2; // describes an image with an accessible buffer of image data (used to store image files)
	public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackRight1, attackRight2, attackLeft1, attackLeft2; // sprites for attack animation
	public String direction = "down"; // default direction
	public int spriteNum = 1;
	// COLLISION
	public Rectangle solidArea;
	public int solidAreaDefaultX, solidAreaDefaultY;
	public boolean collisionOn = false;
	public Rectangle attackArea = new Rectangle(0, 0, 0, 0);
	public boolean collision = false; // for setting a collision
	// DIALOGUE
	public String dialogues[] = new String[20];
	int dialogueIndex = 0;
	// ENTITY ATTRIBUTES
	public int type; // 0 -> player, 1 -> npc, 2 -> monster, 3 -> object
	public int maxLife;
	public int life;
	public int speed;
	public int level;
	public int strength;
	public int dexterity;
	public int attack;
	public int defense;
	public int exp;
	public int nextLevelExp;
	public int coin;
	public Entity currentWeapon;
	public Entity currentShield;
	// ENTITY STATUS
	public boolean invincible = false;
	public boolean attacking = false;
	public boolean alive = true;
	public boolean dying = false;
	public boolean hpBarOn = false;
	// COUNTERS
	public int actionLockCounter = 0;
	public int spriteCounter = 0;
	public int invincibleCounter = 0;
	int dyingCounter = 0;
	int hpBarCounter = 0;
	// OBJECT ATTRIBUTES
	public BufferedImage image, image2, image3; // adding an image
	public String name; // name of the object
	// ITEM ATTRIBUTES
	public int attackValue;
	public int defenseValue;
	
	
	public Entity(GamePanel gp) {
		this.gp = gp;
		solidArea = new Rectangle(0, 0, gp.tileSize, gp.tileSize);
	}
	
	public void setAction() {} // "AI" or interaction setter
	public void damageReaction() {} // what entity does when is being damaged
	
	public void speak() { // method for dialogue system
		
		if (dialogues[dialogueIndex] == null) { // avoid NullPointer
			dialogueIndex = 0;
		}
		gp.ui.currentDialogue = dialogues[dialogueIndex]; // chooses the dialogue to speak
		dialogueIndex++;
		
		switch(gp.player.direction) {
		case "up":
			direction = "down";
			break;
		case "down":
			direction = "up";
			break;
		case "left":
			direction = "right";
			break;
		case "right":
			direction = "left";
			break;
		}
	} 
	
	public void update() { // updates the entities status
		
		setAction(); // implements the "AI"
		
		// COLLISION
		collisionOn = false;
		gp.cChecker.checkTile(this);
		gp.cChecker.checkObject(this, false);
		gp.cChecker.checkEntity(this, gp.npc);
		gp.cChecker.checkEntity(this, gp.monster);
		boolean contactPlayer = gp.cChecker.checkPlayer(this);
		
		// if a monster makes contact with the player, the player is hit
		if (this.type == 2 && contactPlayer == true) {
			if (gp.player.invincible == false) {
				gp.player.life -= 1;
				gp.playSE(4);
				gp.player.invincible = true;
			}
			
		}
		
		// if the collision is false, the player can move, so we change the variables for movement (worldX and worldY)
		if (collisionOn == false) {
			switch (direction) {
			case "up": worldY -= speed; break;
			case "down": worldY += speed; break;
			case "left": worldX -= speed; break;
			case "right": worldX += speed; break;
			}
		}
		spriteCounter++; // animation and sprite number
		if (spriteCounter > 8) {
			if (spriteNum == 1) {
				spriteNum = 2;
			} else if (spriteNum == 2) {
				spriteNum = 1;
			}
			spriteCounter = 0;
		}
					
		if (invincible == true) {
			invincibleCounter++;
			if (invincibleCounter > 20) { // change value depending on the duration of the invincibility
				invincibleCounter = 0;
				invincible = false;
			}
		}
	}
	
	public void draw(Graphics2D g2) {
		
		BufferedImage image = null;
		int screenX = worldX - gp.player.worldX + gp.player.screenX;
		int screenY = worldY - gp.player.worldY + gp.player.screenY;
		
		if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX && 
			worldX - gp.tileSize  < gp.player.worldX + gp.player.screenX &&
			worldY + gp.tileSize  > gp.player.worldY - gp.player.screenY &&
			worldY - gp.tileSize  < gp.player.worldY + gp.player.screenY) { //only draws the tiles inside the player's screen
		
			switch (direction) { //chooses the correct sprite depending on the direction and sprite number (to create the animation)
			case "down":
				if (spriteNum == 1) { image = down1; }
				if (spriteNum == 2) { image = down2; }
				break;
			case "up":
				if (spriteNum == 1) { image = up1; }
				if (spriteNum == 2) { image = up2; }
				break;
			case "left":
				if (spriteNum == 1) { image = left1; }
				if (spriteNum == 2) { image = left2; }
				break;
			case "right":
				if (spriteNum == 1) { image = right1; }
				if (spriteNum == 2) { image = right2; }
				break;
				
			}
			
			// Monster HP bar
			if (type == 2 && hpBarOn == true) {
				
				double oneScale = (double) (gp.tileSize - gp.scale*2) / maxLife;
				double hpBarValue = oneScale*life;
				// draws the bar
				g2.setColor(new Color(62, 49, 39));
				g2.fillRect(screenX, screenY - gp.tileSize/4, gp.tileSize, gp.tileSize/5);
				g2.setColor(new Color(255, 0, 68));
				g2.fillRect(screenX + gp.scale, screenY - gp.tileSize/4 + gp.scale, (int)hpBarValue, gp.tileSize/5 - gp.scale*2);
				// bar counter
				hpBarCounter++;
				if (hpBarCounter > gp.FPS*10) {
					hpBarCounter = 0;
					hpBarOn = false;
				}
			}
			
			// makes the entity transparent when invincible (animation)
			if (invincible == true) {
				if (invincibleCounter >= 0 && invincibleCounter < 10) {
					changeAlpha(g2, 0.4f);
				} else if (invincibleCounter >= 10 && invincibleCounter < 20) {
					changeAlpha(g2, 1f);
				} else if (invincibleCounter >= 20 && invincibleCounter < 30) {
					changeAlpha(g2, 0.4f);
				} else if (invincibleCounter >= 30 && invincibleCounter < 40) {
					changeAlpha(g2, 1f);
				} else if (invincibleCounter >= 40 && invincibleCounter < 50) {
					changeAlpha(g2, 0.4f);
				} else if (invincibleCounter >= 50 && invincibleCounter < 60) {
					changeAlpha(g2, 1f);
				}
				
				hpBarOn = true;
				hpBarCounter = 0;
			}
			
			// animation when entity dies
			if (dying == true) {
				dyingAnimation(g2);
			}
			
			// draw entity image
			g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
			
			// reset invincibility state
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		
		}
	}
	
	public void dyingAnimation(Graphics2D g2) {
		
		dyingCounter++;
		int i = gp.FPS/12;
		
		if (dyingCounter <= i) { g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0f)); }
		if (dyingCounter > i && dyingCounter <= i*2) { changeAlpha(g2, 0f); }
		if (dyingCounter > i*2 && dyingCounter <= i*3) { changeAlpha(g2, 1f); }
		if (dyingCounter > i*3 && dyingCounter <= i*4) { changeAlpha(g2, 0f); }
		if (dyingCounter > i*4 && dyingCounter <= i*5) { changeAlpha(g2, 1f); }
		if (dyingCounter > i*5 && dyingCounter <= i*6) { changeAlpha(g2, 0f); }
		if (dyingCounter > i*6 && dyingCounter <= i*7) { changeAlpha(g2, 1f); }
		if (dyingCounter > i*7 && dyingCounter <= i*8) { changeAlpha(g2, 0f); }
		if (dyingCounter > i*8) {
			dying = false;
			alive = false;
		}
	}
	
	public void changeAlpha(Graphics2D g2, float alphaValue) {
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
	}
	
	public BufferedImage setup(String imagePath, int width, int height) {
		
		UtilityTool uTool = new UtilityTool(); // create the uTool for the method
		BufferedImage image = null;
		
		try {
			
			image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
			image = uTool.scaleImage(image, width, height); // scale the image
			 
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return image; // returns the scaled image
		
	}
}
