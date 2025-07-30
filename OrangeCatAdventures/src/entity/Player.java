package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity{
	
	KeyHandler keyH;
	public final int screenX, screenY;
	
	public Player(GamePanel gp, KeyHandler keyH) {
		
		super(gp); // calling the constructor of the super class Entity
		this.gp = gp;
		this.keyH = keyH;
		// player position
		screenX = gp.screenWidth/2 - (gp.tileSize/2);
		screenY = gp.screenHeight/2 - (gp.tileSize/2);
		// collision box size
		solidArea = new Rectangle();
		solidArea.x = (int) (gp.tileSize/4);
		solidArea.y = (int) (gp.tileSize/2);
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		solidArea.width = (int) (gp.tileSize/2);
		solidArea.height = (int) (gp.tileSize*3/7); // gp.tileSize*2/5 to offset a pixel down
		// attack collision box
		attackArea.width = gp.tileSize*3/4;
		attackArea.height = gp.tileSize*3/4;
		
		setDefaultValues();
		getPlayerImage();
		getPlayerAttackImage();
	}
	
	public void setDefaultValues() {
		
		worldX = gp.tileSize * 9; // x position on the world map
		worldY = gp.tileSize * 25; // y position on the world map
		speed = 4;
		direction = "down";
		// player status
		maxLife = 6;
		life = maxLife;
	}
	
	public void getPlayerImage() {
		
		// PLAYER SPRITES
		// (imageName)
		up1 = setup("/player/cat_up_1", gp.tileSize, gp.tileSize);
		up2 = setup("/player/cat_up_2", gp.tileSize, gp.tileSize);
		down1 = setup("/player/cat_down_1", gp.tileSize, gp.tileSize);
		down2 = setup("/player/cat_down_2", gp.tileSize, gp.tileSize);
		left1 = setup("/player/cat_left_1", gp.tileSize, gp.tileSize);
		left2 = setup("/player/cat_left_2", gp.tileSize, gp.tileSize);
		right1 = setup("/player/cat_right_1", gp.tileSize, gp.tileSize);
		right2 = setup("/player/cat_right_2", gp.tileSize, gp.tileSize);
	}
	
public void getPlayerAttackImage() {
		
		// PLAYER ATTACK SPRITES
		// (imageName)
		attackUp1 = setup("/player/cat_attack_up_1", gp.tileSize, gp.tileSize*2);
		attackUp2 = setup("/player/cat_attack_up_2", gp.tileSize, gp.tileSize*2);
		attackDown1 = setup("/player/cat_attack_down_1", gp.tileSize, gp.tileSize*2);
		attackDown2 = setup("/player/cat_attack_down_2", gp.tileSize, gp.tileSize*2);
		attackLeft1 = setup("/player/cat_attack_left_1", gp.tileSize*2, gp.tileSize);
		attackLeft2 = setup("/player/cat_attack_left_2", gp.tileSize*2, gp.tileSize);
		attackRight1 = setup("/player/cat_attack_right_1", gp.tileSize*2, gp.tileSize);
		attackRight2 = setup("/player/cat_attack_right_2", gp.tileSize*2, gp.tileSize);
	}
	
	public void update() {
		
		if (attacking == true) { // if the player is attacking
			attacking();
		}
		else if (keyH.downPressed == true || keyH.upPressed == true || keyH.rightPressed == true || keyH.leftPressed == true || keyH.enterPressed == true) { //if any key is pressed (avoids animation to continue while not pressing keys)
		
			if (keyH.upPressed == true) { // if key to move up is pressed
				direction = "up";
			}
			else if (keyH.downPressed == true) { // if key to move down is pressed
				direction = "down";
			}
			else if (keyH.leftPressed == true) { // if key to move left is pressed
				direction = "left";
			}
			else if (keyH.rightPressed == true) { // if key to move right is pressed
				direction = "right";
			}
			
			// CHECK THE TILE COLLISION
			collisionOn = false;
			gp.cChecker.checkTile(this);
			
			// CHECK OBJECT COLLISION
			int objIndex = gp.cChecker.checkObject(this, true);
			pickUpObject(objIndex);
			
			// CHECK NPC COLLISION
			int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
			interactNPC(npcIndex);
			
			// CHECK MONSTER COLLISION
			int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
			contactMonster(monsterIndex);
			
			// CHECK EVENT
			gp.eHandler.checkEvent();
			
			gp.keyH.enterPressed = false;
			
			// if the collision is false, the player can move, so we change the variables for movement (worldX and worldY)
			if (collisionOn == false && keyH.enterPressed == false) {
				
				switch (direction) {
				case "up": worldY -= speed; break;
				case "down": worldY += speed; break;
				case "left": worldX -= speed; break;
				case "right": worldX += speed; break;
				}
			}
			gp.keyH.enterPressed = false;
			
			spriteCounter++; // animation and sprite number
			if (spriteCounter > 8) {
				if (spriteNum == 1) {
					spriteNum = 2;
				} else if (spriteNum == 2) {
					spriteNum = 1;
				}
				spriteCounter = 0;
			}
		
		}
		
		// avoiding player being bullied by enemies
		if (invincible == true) {
			invincibleCounter++;
			if (invincibleCounter > 60) {
				invincibleCounter = 0;
				invincible = false;
			}
		}
		
	}
	
	public void attacking() { // all interaction while player is attacking
		
		spriteCounter++;
		
		// ATTACK ANIMATION
		if (spriteCounter <= 5) { spriteNum = 1; }
		if (spriteCounter > 5 && spriteCounter <= 25) { // attacking
			
			spriteNum = 2;
			
			// save current data
			int currentWorldX = worldX, currentWorldY = worldY;
			int solidAreaWidth = solidArea.width, solidAreaHeight = solidArea.height;
			// adjust the values for the attack area
			switch (direction) {
			case "up": worldY -= attackArea.height; break;
			case "down": worldY += attackArea.height; break;
			case "left": worldX -= attackArea.width; break;
			case "right": worldX += attackArea.width; break;
			}
			// attackArea becomes solidArea
			solidArea.width = attackArea.width;
			solidArea.height = attackArea.height;
			//check monster collision and manage the result
			int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
			damageMonster(monsterIndex);
			// reset values
			worldX = currentWorldX; worldY = currentWorldY; 
			solidArea.width = solidAreaWidth; solidArea.height = solidAreaHeight; 
			
		}
		if (spriteCounter > 25) { spriteNum = 1; spriteCounter = 0; attacking = false; }
		
	}
	
	public void pickUpObject(int i) { // interaction with objects
		
		if (i != -1) { // in the video puts 999 instead of -1
			
		}
	}
	
	public void contactMonster(int i) {
		
		if (i != -1) {
			
			if (invincible == false) {
				life -= 1;
				gp.playSE(4);
				invincible = true;
			}
		}
		
	}
	
	public void damageMonster(int i) {
		
		if(i != -1) {
			if (gp.monster[i].invincible == false) { // if the monster is not invincible
				
				gp.monster[i].life -= 1;
				gp.monster[i].invincible = true;
				gp.playSE(7);
				gp.monster[i].damageReaction();
				
				if (gp.monster[i].life <= 0) { // if the monster dies
					gp.monster[i].dying = true;
					
				}
				
			}
		}
	}
	
	public void interactNPC(int i) {
		
		if(gp.keyH.enterPressed == true) { // when you press enter
			if (i != -1) { // in the video puts 999 instead of -1
				gp.gameState = gp.dialogueState; // shows the dialogue
				gp.npc[i].speak();
			}
			else { // the player attacks
				attacking = true;
				gp.playSE(8);
			}
		}
	}
	
	public void draw(Graphics2D g2) {
		
		/* g2.setColor(Color.white);
		g2.fillRect(x, y, gp.tileSize, gp.tileSize); // draws a rectangle on the screen */
		
		BufferedImage image = null;
		int tempScreenX = screenX; // centers the player on the right x value
		int tempScreenY = screenY; // centers the player on the right x value
		
		switch (direction) { //chooses the correct sprite depending on the direction, sprite number and attack (to create the animation)
		case "down":
			if (attacking == false) {
				if (spriteNum == 1) { image = down1; } 
				if (spriteNum == 2) { image = down2; }
			}
			if (attacking == true) {
				if (spriteNum == 1) { image = attackDown1; } 
				if (spriteNum == 2) { image = attackDown2; }
			}
			break;
			
		case "up":
			if (attacking == false) {
				if (spriteNum == 1) { image = up1; } 
				if (spriteNum == 2) { image = up2; }
			}
			if (attacking == true) {
				tempScreenY = screenY - gp.tileSize;
				if (spriteNum == 1) { image = attackUp1; } 
				if (spriteNum == 2) { image = attackUp2; }
			}
			break;
			
		case "left":
			if (attacking == false) {
				if (spriteNum == 1) { image = left1; } 
				if (spriteNum == 2) { image = left2; }
			}
			if (attacking == true) {
				tempScreenX = screenX - gp.tileSize;
				if (spriteNum == 1) { image = attackLeft1; } 
				if (spriteNum == 2) { image = attackLeft2; }
			}
			break;
			
		case "right":
			if (attacking == false) {
				if (spriteNum == 1) { image = right1; } 
				if (spriteNum == 2) { image = right2; }
			}
			if (attacking == true) {
				if (spriteNum == 1) { image = attackRight1; } 
				if (spriteNum == 2) { image = attackRight2; }
			}
			break;
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
		}
		
		// draws the sprite / image
		g2.drawImage(image, tempScreenX, tempScreenY, null); // prints the image
		
		// reset invincibility state
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		
		// DEBUG
		//System.out.println(invincibleCounter); // checks player invincibility
		
	}

}








