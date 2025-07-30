package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Key extends Entity {
	
	public OBJ_Key(GamePanel gp) { // sets the main variables
		
		super(gp);
		
		name = "key";
		down1 = setup("/objects/key_1", gp.tileSize, gp.tileSize);
	}

}
