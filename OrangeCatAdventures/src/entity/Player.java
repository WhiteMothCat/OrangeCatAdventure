package entity;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import main.GamePanel;
import main.KeyHandler;
import object.*;

public class Player extends Entity{
	
	KeyHandler keyH;
	public final int screenX, screenY;
	public boolean attackCanceled = false;
	public ArrayList<Entity> inventory = new ArrayList<>();
	public final int maxInventorySize = gp.ui.slotRowMax * gp.ui.slotColMax;
	
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
//		attackArea.width = gp.tileSize*3/4;
//		attackArea.height = gp.tileSize*3/4;
		
		setDefaultValues();
		getPlayerImage();
		getPlayerAttackImage();
		setItems();
	}
	
	public void setDefaultValues() {
		
		worldX = gp.tileSize * 9; // x position on the world map
		worldY = gp.tileSize * 25; // y position on the world map
		speed = 4;
		direction = "down";
		// player attributes
		maxLife = 6;
		life = maxLife;
		maxMana = 3;
		mana = maxMana;
//		ammo = 10; // not used by player
		level = 1;
		strength = 0;
		dexterity = 0;
		exp = 0;
		nextLevelExp = 5;
		yarn = 0; // broke :(
		currentWeapon = new OBJ_Weapon_1(gp);
		currentShield = new OBJ_Shield_1(gp);
		projectile = new OBJ_Fireball(gp);
		attack = getAttack();
		defense = getDefense();
	}
	
	public void setItems() {
		
		inventory.add(currentWeapon);
		inventory.add(currentShield);
//		inventory.add(new OBJ_Key(gp));
//		inventory.add(new OBJ_Mushroom(gp));
	}
	
	public int getAttack() { // calculates the attack value
		attackArea = currentWeapon.attackArea;
		return attack = strength + currentWeapon.attackValue; // in the video multiplies the value
	}
	
	public int getDefense() { // calculates the defense value
		return defense = dexterity + currentShield.defenseValue; // in the video multiplies the value
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
		
		if (currentWeapon.type == type_sword) {
			attackUp1 = setup("/player/cat_attack_up_1", gp.tileSize, gp.tileSize*2);
			attackUp2 = setup("/player/cat_attack_up_2", gp.tileSize, gp.tileSize*2);
			attackDown1 = setup("/player/cat_attack_down_1", gp.tileSize, gp.tileSize*2);
			attackDown2 = setup("/player/cat_attack_down_2", gp.tileSize, gp.tileSize*2);
			attackLeft1 = setup("/player/cat_attack_left_1", gp.tileSize*2, gp.tileSize);
			attackLeft2 = setup("/player/cat_attack_left_2", gp.tileSize*2, gp.tileSize);
			attackRight1 = setup("/player/cat_attack_right_1", gp.tileSize*2, gp.tileSize);
			attackRight2 = setup("/player/cat_attack_right_2", gp.tileSize*2, gp.tileSize);
		}
		else if (currentWeapon.type == type_axe) {
			attackUp1 = setup("/player/cat_axe_up_1", gp.tileSize, gp.tileSize*2);
			attackUp2 = setup("/player/cat_axe_up_2", gp.tileSize, gp.tileSize*2);
			attackDown1 = setup("/player/cat_axe_down_1", gp.tileSize, gp.tileSize*2);
			attackDown2 = setup("/player/cat_axe_down_2", gp.tileSize, gp.tileSize*2);
			attackLeft1 = setup("/player/cat_axe_left_1", gp.tileSize*2, gp.tileSize);
			attackLeft2 = setup("/player/cat_axe_left_2", gp.tileSize*2, gp.tileSize);
			attackRight1 = setup("/player/cat_axe_right_1", gp.tileSize*2, gp.tileSize);
			attackRight2 = setup("/player/cat_axe_right_2", gp.tileSize*2, gp.tileSize);
		}
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
			
			// CHECK INTERACTIVE TILE COLLISION
			int iTileIndex = gp.cChecker.checkEntity(this, gp.iTile);
			
			// CHECK EVENT
			gp.eHandler.checkEvent();
			
			// if the collision is false, the player can move, so we change the variables for movement (worldX and worldY)
			if (collisionOn == false && keyH.enterPressed == false) {
				
				switch (direction) {
				case "up": worldY -= speed; break;
				case "down": worldY += speed; break;
				case "left": worldX -= speed; break;
				case "right": worldX += speed; break;
				}
			}
			
			// PLAYER ATTACK
			if (gp.keyH.enterPressed == true && attackCanceled == false) {
				gp.playSE(8);
				attacking = true;
				spriteCounter = 0;
			}
			
			// reset values
			attackCanceled = false;
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
		
		// SHOOT PROJECTILES
		if (gp.keyH.shotKeyPressed == true && projectile.alive == false 
				&& shotAvaliableCounter == 30 && projectile.haveResource(this) == true) { // projectile.alive == false means you can only shoot one projectile at a time
			
			// set default values
			projectile.set(worldX, worldY, direction, true, this);
			shotAvaliableCounter = 0;
			// subtract the resource (if it has one)
			projectile.subtractResource(this);
			// add it to the list
			gp.projectileList.add(projectile);
			gp.playSE(10);
		}
		
		// avoiding player being bullied by enemies
		if (invincible == true) {
			invincibleCounter++;
			if (invincibleCounter > 60) {
				invincibleCounter = 0;
				invincible = false;
			}
		}
		if (shotAvaliableCounter < 30) {
			shotAvaliableCounter++;
		}
		
		// UPDATE PLAYER LIFE AND MANA
		if (life > maxLife) {
			life = maxLife;
		}
		if (mana > maxMana) {
			mana = maxMana;
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
			//check monster or interactiveTile collision and manage the result
			int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
			damageMonster(monsterIndex, attack);
			int iTileIndex = gp.cChecker.checkEntity(this, gp.iTile);
			damageInteractiveTile(iTileIndex);
			// reset values
			worldX = currentWorldX; worldY = currentWorldY; 
			solidArea.width = solidAreaWidth; solidArea.height = solidAreaHeight; 
			
		}
		if (spriteCounter > 25) { spriteNum = 1; spriteCounter = 0; attacking = false; }
		
	}
	
	public void pickUpObject(int i) { // interaction with objects
		
		if (i != -1 ) { // in the video puts 999 instead of -1
			
			// PICKUP ONLY ITEMS
			if (gp.obj[i].type == type_pickUpOnly) { // pickup only items
				
				gp.obj[i].use(this);
				gp.obj[i] = null;
			
			} else if (gp.obj[i].type == type_consumable || gp.obj[i].type == type_sword || gp.obj[i].type == type_axe || gp.obj[i].type == type_shield) { // inventory items
				String text;
				
				if (inventory.size() != maxInventorySize) {
					
					inventory.add(gp.obj[i]);
					gp.playSE(1);
					text = gp.obj[i].name + " was added to inventory";
					gp.obj[i] = null;
					
				} else { // not enough space
					text = "Inventory full";
				}
				gp.ui.addMessage(text);
			}
		}
	}
	
	public void contactMonster(int i) {
		
		if (i != -1) {
			
			if (invincible == false && gp.monster[i].dying == false) {
				
				int damage = gp.monster[i].attack - defense; // calculate damage
				if (damage < 0) { damage = 0; }
				
				life -= damage;
				gp.playSE(4);
				invincible = true;
			}
		}
		
	}
	
	public void damageMonster(int i, int attack) {
		
		if(i != -1) {
			if (gp.monster[i].invincible == false) { // if the monster is not invincible
				
				int damage = attack - gp.monster[i].defense; // calculate the damage
				if (damage < 0) { damage = 0; }
				
				gp.monster[i].life -= damage;
				gp.monster[i].invincible = true;
				gp.playSE(7);
				gp.monster[i].damageReaction();
				
				if (gp.monster[i].life <= 0) { // if the monster dies
					exp += gp.monster[i].exp;
					gp.ui.addMessage(gp.monster[i].name + " was killed");
					gp.ui.addMessage("You gained " + gp.monster[i].exp + " exp");
					gp.monster[i].dying = true;
					checkLevelUp();
				}
				
			}
		}
	}
	
	public void damageInteractiveTile(int i) {
		
		if (i != -1) {
			
			if (gp.iTile[i].destructible == true && gp.iTile[i].isCorrectItem(this) == true 
					&& gp.iTile[i].invincible == false) {
				
				gp.iTile[i].playSE();
				gp.iTile[i].life -= attack;
				gp.iTile[i].invincible = true;
				
				if(gp.iTile[i].life <= 0) {
					gp.iTile[i] = gp.iTile[i].getDestroyedForm();
				}
			}
		}
	}
	
	public void checkLevelUp() {
		
		if (exp >= nextLevelExp) { // if leveled up
			
			// stats
			level++;
			nextLevelExp = (int) (nextLevelExp*3.5);
			maxLife += 2;
			life = maxLife;
			strength++;
			dexterity++;
			attack = getAttack();
			defense = getDefense();
			gp.playSE(9);
			// message
			gp.gameState = gp.dialogueState;
			gp.ui.currentDialogue = "You got to level " + level + "\nYour stats have been increased";
		}
	}
	
	public void selectItem() {
		
		int itemIndex = gp.ui.getItemIndexOnSlot();
		
		if(itemIndex < inventory.size()) {
			
			Entity selectedItem = inventory.get(itemIndex);
			// check type and do a different action depending on the item type
			
			if (selectedItem.type == type_sword || selectedItem.type == type_axe) { // equip weapon
				currentWeapon = selectedItem;
				attack = getAttack();
				getPlayerAttackImage();
			}
			if (selectedItem.type == type_shield) { // equip shield
				currentShield = selectedItem;
				defense = getDefense();
			}
			if (selectedItem.type == type_consumable) { // use item
				selectedItem.use(this);
				inventory.remove(itemIndex);
				attack = getAttack();
				defense = getDefense();
			}
		}
	}
	
	public void interactNPC(int i) {
		
		if(gp.keyH.enterPressed == true) { // when you press enter
			if (i != -1) { // in the video puts 999 instead of -1
				attackCanceled = true;
				gp.gameState = gp.dialogueState; // shows the dialogue
				gp.npc[i].speak();
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
				changeAlpha(g2, 1f);
			} else if (invincibleCounter >= 10 && invincibleCounter < 20) {
				changeAlpha(g2, 0.4f);
			} else if (invincibleCounter >= 20 && invincibleCounter < 30) {
				changeAlpha(g2, 1f);
			} else if (invincibleCounter >= 30 && invincibleCounter < 40) {
				changeAlpha(g2, 0.4f);
			} else if (invincibleCounter >= 40 && invincibleCounter < 50) {
				changeAlpha(g2, 1f);
			} else if (invincibleCounter >= 50 && invincibleCounter < 60) {
				changeAlpha(g2, 0.4f);
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








