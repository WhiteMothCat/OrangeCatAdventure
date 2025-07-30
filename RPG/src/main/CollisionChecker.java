package main;

import entity.Entity;

public class CollisionChecker {
	
	GamePanel gp;
	
	public CollisionChecker(GamePanel gp) {
		this.gp = gp;
	}
	
	public void checkTile(Entity entity) {
		
		// calculations of the position of the collision box
		int entityLeftWorldX = entity.worldX + entity.solidArea.x;
		int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
		int entityTopWorldY = entity.worldY + entity.solidArea.y;
		int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;
		
		int entityLeftCol = entityLeftWorldX/gp.tileSize;
		int entityRightCol = entityRightWorldX/gp.tileSize;
		int entityTopRow = entityTopWorldY/gp.tileSize;
		int entityBottomRow = entityBottomWorldY/gp.tileSize;
		
		int tileNum1, tileNum2;
		
		switch (entity.direction) { // checks if any tile around each side has any effect over the collision box and entity
		case "up": // in case of going up
			entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
			
			if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) { // checks if there is any collision
				entity.collisionOn = true;
			}
			
			break;
		
		case "down": // in case of going down
			entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
			
			if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) { // checks if there is any collision
				entity.collisionOn = true;
			}
			
			break;
		
		case "left": // in case of going left
			entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
			
			if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) { // checks if there is any collision
				entity.collisionOn = true;
			}
			
			break;
		
		case "right": // in case of going up
			entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
			
			if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) { // checks if there is any collision
				entity.collisionOn = true;
			}
			
			break;
		}
		
	}
	
	public int checkObject(Entity entity, boolean player) { // checks the collision with an object
		
		int index = -1;
		
		for (int i = 0; i < gp.obj.length; i++) {
			
			if (gp.obj[i] != null) { // checks if there is an object in the index
				
				// get the entity's solid area position
				entity.solidArea.x += entity.worldX;
				entity.solidArea.y += entity.worldY;
				// get the object's solid area position
				gp.obj[i].solidArea.x += gp.obj[i].worldX;
				gp.obj[i].solidArea.y += gp.obj[i].worldY;
				
				switch (entity.direction) { // calculating the future position
				case "up": entity.solidArea.y -= entity.speed; break;
				case "down": entity.solidArea.y += entity.speed; break;
				case "left": entity.solidArea.x -= entity.speed; break;
				case "right": entity.solidArea.x += entity.speed; break;
				}
				
				if (entity.solidArea.intersects(gp.obj[i].solidArea)) { // checks the collision
					if (gp.obj[i].collision == true) { // checks if entity can collide
						entity.collisionOn = true;
					}
					if (player == true) { // checks if the player is the one colliding
						index = i;
					}
				}
				
				entity.solidArea.x = entity.solidAreaDefaultX;
				entity.solidArea.y = entity.solidAreaDefaultY;
				gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
				gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;
				
			}
		}
		return index; // returns the index of the object 
	}
	
	public int checkEntity(Entity entity, Entity[] target) { // check NPC or monster collision
		
		int index = -1;
		
		for (int i = 0; i < target.length; i++) {
			
			if (target[i] != null) { // checks if there is an object in the index
				
				// get the entity's solid area position
				entity.solidArea.x += entity.worldX;
				entity.solidArea.y += entity.worldY;
				// get the object's solid area position
				target[i].solidArea.x += target[i].worldX;
				target[i].solidArea.y += target[i].worldY;
				
				switch (entity.direction) { // calculating the future position
				case "up": entity.solidArea.y -= entity.speed; break;
				case "down": entity.solidArea.y += entity.speed; break;
				case "left": entity.solidArea.x -= entity.speed; break;
				case "right": entity.solidArea.x += entity.speed; break;
				}
				
				if (entity.solidArea.intersects(target[i].solidArea)) { // checks the collision
					if (target[i] != entity) {
						entity.collisionOn = true;
						index = i;
					}
				}
				
				entity.solidArea.x = entity.solidAreaDefaultX;
				entity.solidArea.y = entity.solidAreaDefaultY;
				target[i].solidArea.x = target[i].solidAreaDefaultX;
				target[i].solidArea.y = target[i].solidAreaDefaultY;
				
			}
		}
		return index; // returns the index of the NPC
	}
	
	public boolean checkPlayer(Entity entity) { 
		
		boolean contactPlayer = false; // checks the collision
		
		// get the entity's solid area position
		entity.solidArea.x += entity.worldX;
		entity.solidArea.y += entity.worldY;
		// get the object's solid area position
		gp.player.solidArea.x += gp.player.worldX;
		gp.player.solidArea.y += gp.player.worldY;
		
		switch (entity.direction) { // calculating the future position
		case "up": entity.solidArea.y -= entity.speed; break;
		case "down": entity.solidArea.y += entity.speed; break;
		case "left": entity.solidArea.x -= entity.speed; break;
		case "right": entity.solidArea.x += entity.speed; break;
		}
		
		if (entity.solidArea.intersects(gp.player.solidArea)) { // checks the collision
			entity.collisionOn = true;
			contactPlayer = true;
		}
		
		entity.solidArea.x = entity.solidAreaDefaultX;
		entity.solidArea.y = entity.solidAreaDefaultY;
		gp.player.solidArea.x = gp.player.solidAreaDefaultX;
		gp.player.solidArea.y = gp.player.solidAreaDefaultY;
		
		return contactPlayer; 
	}

}
