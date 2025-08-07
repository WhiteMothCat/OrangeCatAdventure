package main;

import entity.*;
import monster.*;
import object.*;
import tile_interactive.*;

public class AssetSetter {
	
	GamePanel gp;
	
	public AssetSetter(GamePanel gp) {
		this.gp = gp;
	}
	
	public void setObject() { // sets up all the data from objects (type of object, position, ...)
		
		int i = 0;
		
		// chests
//		gp.obj[i] = new OBJ_Chest(gp);
//		gp.obj[i].worldX = 44 * gp.tileSize;
//		gp.obj[i].worldY = 7 * gp.tileSize;
//		gp.obj[i].collision = true;
//		i++;
		// keys
		gp.obj[i] = new OBJ_Key(gp);
		gp.obj[i].worldX = 26 * gp.tileSize;
		gp.obj[i].worldY = 10 * gp.tileSize;
		i++;
		gp.obj[i] = new OBJ_Key(gp);
		gp.obj[i].worldX = 16 * gp.tileSize;
		gp.obj[i].worldY = 30 * gp.tileSize;
		i++;
		// weapons and shields
		gp.obj[i] = new OBJ_Weapon_2(gp);
		gp.obj[i].worldX = 33 * gp.tileSize;
		gp.obj[i].worldY = 14 * gp.tileSize;
		i++;
		gp.obj[i] = new OBJ_Shield_2(gp);
		gp.obj[i].worldX = 30 * gp.tileSize;
		gp.obj[i].worldY = 31 * gp.tileSize;
		i++;
		// yarn
		gp.obj[i] = new OBJ_Yarn_1(gp);
		gp.obj[i].worldX = 19 * gp.tileSize;
		gp.obj[i].worldY = 24 * gp.tileSize;
		i++;
		gp.obj[i] = new OBJ_Yarn_1(gp);
		gp.obj[i].worldX = 25 * gp.tileSize;
		gp.obj[i].worldY = 32 * gp.tileSize;
		i++;
		gp.obj[i] = new OBJ_Yarn_1(gp);
		gp.obj[i].worldX = 14 * gp.tileSize;
		gp.obj[i].worldY = 28 * gp.tileSize;
		i++;
		// consumable items
		gp.obj[i] = new OBJ_Mushroom(gp);
		gp.obj[i].worldX = 22 * gp.tileSize;
		gp.obj[i].worldY = 37 * gp.tileSize;
		i++;
		gp.obj[i] = new OBJ_Potion_1(gp);
		gp.obj[i].worldX = 5 * gp.tileSize;
		gp.obj[i].worldY = 23 * gp.tileSize;
		i++;
		gp.obj[i] = new OBJ_TunaCan(gp);
		gp.obj[i].worldX = 39 * gp.tileSize;
		gp.obj[i].worldY = 11 * gp.tileSize;
		
	}
	
	public void setNPC() {
		
		int i = 0;
		
		gp.npc[i] = new NPC1(gp);
		gp.npc[i].worldX = 34 * gp.tileSize;
		gp.npc[i].worldY = 12 * gp.tileSize;
	}
	
	public void setMonster() {
		
		int i = 0;
		
		// corrupted rats
		gp.monster[i] = new MON_CorruptedRat(gp);
		gp.monster[i].worldX = 43 * gp.tileSize;
		gp.monster[i].worldY = 19 * gp.tileSize;
		i++;
		gp.monster[i] = new MON_CorruptedRat(gp);
		gp.monster[i].worldX = 31 * gp.tileSize;
		gp.monster[i].worldY = 21 * gp.tileSize;
		i++;
		gp.monster[i] = new MON_CorruptedRat(gp);
		gp.monster[i].worldX = 15 * gp.tileSize;
		gp.monster[i].worldY = 18 * gp.tileSize;
		i++;
		// wasps
		gp.monster[i] = new MON_Wasp(gp);
		gp.monster[i].worldX = 45 * gp.tileSize;
		gp.monster[i].worldY = 25 * gp.tileSize;
	}
	
	public void setInteractiveTile() {
		int i = 0;
		
		//dry trees
		gp.iTile[i] = new IT_DryTree(gp, 24, 18); i++;
		gp.iTile[i] = new IT_DryTree(gp, 25, 18); i++;
		gp.iTile[i] = new IT_DryTree(gp, 44, 28); i++;
		gp.iTile[i] = new IT_DryTree(gp, 44, 29); i++;
		// doors
		gp.iTile[i] = new IT_WoodDoor_1(gp, 36, 16); i++;
		gp.iTile[i] = new IT_WoodDoor_1(gp, 44, 10); i++;
	}
}
