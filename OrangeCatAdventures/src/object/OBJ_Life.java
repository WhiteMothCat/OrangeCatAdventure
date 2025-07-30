package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Life extends Entity {
	
	public OBJ_Life(GamePanel gp) { // sets the main variables
		
		super(gp);
		
		name = "life";
		image = setup("/objects/life_full", gp.tileSize, gp.tileSize);
		image2 = setup("/objects/life_half", gp.tileSize, gp.tileSize);
		image3 = setup("/objects/life_blank", gp.tileSize, gp.tileSize);
	}
}
