package main;

import entity.NPC1;
import monster.MON_CorruptedRat;
import object.*;

public class AssetSetter {
	
	GamePanel gp;
	
	public AssetSetter(GamePanel gp) {
		this.gp = gp;
	}
	
	public void setObject() { // sets up all the data from objects (type of object, position, ...)
		
//		gp.obj[0] = new OBJ_Door(gp);
//		gp.obj[0].worldX = 36 * gp.tileSize;
//		gp.obj[0].worldY = 16 * gp.tileSize;
		
		gp.obj[1] = new OBJ_Door(gp);
		gp.obj[1].worldX = 44 * gp.tileSize;
		gp.obj[1].worldY = 10 * gp.tileSize;
		
	}
	
	public void setNPC() {
		
		gp.npc[0] = new NPC1(gp);
		gp.npc[0].worldX = 34 * gp.tileSize;
		gp.npc[0].worldY = 12 * gp.tileSize;
	}
	
	public void setMonster() {
		
		gp.monster[0] = new MON_CorruptedRat(gp);
		gp.monster[0].worldX = 43 * gp.tileSize;
		gp.monster[0].worldY = 19 * gp.tileSize;
		
		gp.monster[1] = new MON_CorruptedRat(gp);
		gp.monster[1].worldX = 31 * gp.tileSize;
		gp.monster[1].worldY = 21 * gp.tileSize;
	}

}
