package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Mushroom extends Entity {
	
	public OBJ_Mushroom(GamePanel gp) { // sets the main variables
		
		super(gp);
		
		name = "mushroom";
		down1 = setup("/objects/mushroom_1", gp.tileSize, gp.tileSize);
	}

}
