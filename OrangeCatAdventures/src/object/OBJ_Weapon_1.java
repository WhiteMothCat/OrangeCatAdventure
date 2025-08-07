package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Weapon_1 extends Entity {
	
	public OBJ_Weapon_1(GamePanel gp) {
		super(gp);
		
		type = type_sword;
		name = "Normal Sword";
		down1 = setup("/objects/Weapon_1", gp.tileSize, gp.tileSize);
		attackValue = 1;
		description = "[" + name + "]\nAttack: +" + attackValue;
		attackArea.width = gp.tileSize*3/4;
		attackArea.height = gp.tileSize*3/4;
	}
}
