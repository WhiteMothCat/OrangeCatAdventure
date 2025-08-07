package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Weapon_2 extends Entity{

	public OBJ_Weapon_2(GamePanel gp) {
		super(gp);
		
		type = type_axe;
		name = "Woodcutter's Axe";
		down1 = setup("/objects/Weapon_2", gp.tileSize, gp.tileSize);
		attackValue = 3;
		description = "[" + name + "]\nAttack: +" + attackValue;
		attackArea.width = gp.tileSize*5/8;
		attackArea.height = gp.tileSize*5/8;
	}

}
