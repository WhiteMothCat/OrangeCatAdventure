package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Shield_1 extends Entity {

	public OBJ_Shield_1(GamePanel gp) {
		super(gp);
		
		type = type_shield;
		name = "Wooden Shield";
		down1 = setup("/objects/Shield_1", gp.tileSize, gp.tileSize);
		defenseValue = 1;
		description = "[" + name + "]\nDefense: +" + defenseValue;
	}

}
