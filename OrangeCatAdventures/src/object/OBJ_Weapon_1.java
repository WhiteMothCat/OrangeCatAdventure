package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Weapon_1 extends Entity {
	
	public OBJ_Weapon_1(GamePanel gp) {
		super(gp);
		
		name = "NormalSword";
		down1 = setup("/objects/weapon_1", gp.tileSize, gp.tileSize);
		attackValue = 1;
	}
}
