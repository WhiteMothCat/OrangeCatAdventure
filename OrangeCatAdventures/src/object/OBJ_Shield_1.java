package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Shield_1 extends Entity {

	public OBJ_Shield_1(GamePanel gp) {
		super(gp);
		
		name = "WoodShield";
		down1 = setup("/objects/shield_1", gp.tileSize, gp.tileSize);
		defenseValue = 1;
	}

}
