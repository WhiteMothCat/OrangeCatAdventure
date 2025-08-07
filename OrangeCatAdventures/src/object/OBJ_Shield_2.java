package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Shield_2 extends Entity{

	public OBJ_Shield_2(GamePanel gp) {
		super(gp);
		
		type = type_shield;
		name = "Blue Shield";
		down1 = setup("/objects/Shield_2", gp.tileSize, gp.tileSize);
		defenseValue = 2;
		description = "[" + name + "]\nDefense: +" + defenseValue;
	}

}
