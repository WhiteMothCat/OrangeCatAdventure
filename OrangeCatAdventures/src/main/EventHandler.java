package main;

import java.awt.Rectangle;

public class EventHandler {

	GamePanel gp;
	EventRect eventRect[][];
	// avoid event happening repeatedly
	int previousEventX, previousEventY;
	boolean canTouchEvent = true;
	// IMPORTANT: Use the variable "eventDone" to avoid the event repeating again
	
	public EventHandler(GamePanel gp) {
		this.gp = gp;
		
		eventRect = new EventRect[gp.maxWorldCol][gp.maxWorldRow];
		
		int col = 0, row = 0;
		while(col < gp.maxWorldCol && row < gp.maxWorldRow) {
			
			eventRect[col][row] = new EventRect();
			eventRect[col][row].width = 2;
			eventRect[col][row].height = 2;
			eventRect[col][row].x = gp.tileSize/2 - (eventRect[col][row].width/2);
			eventRect[col][row].y = gp.tileSize/2 - (eventRect[col][row].height/2);
			eventRect[col][row].eventRectDefaultX = eventRect[col][row].x;
			eventRect[col][row].eventRectDefaultY = eventRect[col][row].y;
			
			col++;
			if (col == gp.maxWorldCol) {
				col = 0;
				row++;
			}
		}
	}
	
	public void checkEvent() {
		
		// Check if event happened before
		int xDistance = Math.abs(gp.player.worldX - previousEventX);
		int yDistance = Math.abs(gp.player.worldY - previousEventY);
		int distance = Math.max(xDistance,  yDistance);
		if (distance > gp.tileSize) {
			canTouchEvent = true;
		}
		
		// EVENTS
		if (canTouchEvent == true) {
			
			if (hit(45, 21, "any") == true) { damagePit(45, 21, gp.dialogueState); }
			if (hit(17, 30, "any") == true) { damagePit(17, 30, gp.dialogueState); }
			if (hit(14, 17, "up") == true) { healingPool(14, 17, gp.dialogueState); }
			if (hit(38, 23, "any") == true) { damageFromTileOrObject(38, 23, gp.playState, 1); }
			if (hit(38, 24, "any") == true) { damageFromTileOrObject(38, 24, gp.playState, 1); }
			if (hit(37, 25, "any") == true) { damageFromTileOrObject(37, 25, gp.playState, 1); }
			if (hit(37, 26, "any") == true) { damageFromTileOrObject(37, 26, gp.playState, 1); }
		
		}
	}
	
	public boolean hit(int col, int row, String reqDirection) { // checks if the event is hit
		
		boolean hit = false;
		
		// get player current solid area
		gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
		gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
		// get eventRect current solid area
		eventRect[col][row].x = col*gp.tileSize + eventRect[col][row].x;
		eventRect[col][row].y = row*gp.tileSize + eventRect[col][row].y;
		
		// check the collision with player
		if(gp.player.solidArea.intersects(eventRect[col][row]) && eventRect[col][row].eventDone == false) {
			// check the player's direction or "any"
			if(gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
				hit = true;
				
				previousEventX = gp.player.worldX;
				previousEventY = gp.player.worldY;
			}
		}
		
		// reset values
		gp.player.solidArea.x = gp.player.solidAreaDefaultX;
		gp.player.solidArea.y = gp.player.solidAreaDefaultY;
		eventRect[col][row].x = eventRect[col][row].eventRectDefaultX;
		eventRect[col][row].y = eventRect[col][row].eventRectDefaultY;
		// return the boolean
		return hit;
	}
	
	public void teleport(int gameState, int Col, int Row, String dialogue) { // player is teleported somewhere
		
		gp.gameState = gameState;
		gp.ui.currentDialogue = dialogue;
		gp.player.worldX = gp.tileSize*Col;
		gp.player.worldY = gp.tileSize*Row;
	}
	
	public void damagePit(int Col, int Row, int gameState) { // player falls into pit
		
		// knock back the player after falling
		switch(gp.player.direction) {
		case "up": gp.player.worldY += gp.tileSize*2;
		case "down": gp.player.worldY -= gp.tileSize;
		case "left": gp.player.worldX += gp.tileSize*2;
		case "right": gp.player.worldX -= gp.tileSize;
		}
		// rest of the code
		gp.gameState = gameState;
		gp.ui.currentDialogue = "You fell into a pit";
		gp.player.life--;
		gp.playSE(4);
	}
	
	public void damageFromTileOrObject(int Col, int Row, int gameState, int hurtAmount) {
		gp.gameState = gameState;
		gp.ui.currentDialogue = "You got hit";
		gp.player.life -= hurtAmount;
		gp.playSE(4);
		canTouchEvent = false;
	}
	
	public void healingPool(int Col, int Row, int gameState) { // player regenerates life
		
		if(gp.keyH.enterPressed == true) {
			gp.gameState = gameState;
			gp.ui.currentDialogue = "The mystical waters of the pond restores \nyour life";
			gp.player.life = gp.player.maxLife;
			gp.playSE(3);
		}
	}
}
