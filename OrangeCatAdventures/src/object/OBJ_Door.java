package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Door extends Entity {
	
	public OBJ_Door(GamePanel gp) { // sets the main variables
		
		super(gp);
			
		name = "door";
		down1 = setup("/objects/door_1", gp.tileSize, gp.tileSize);
		
		// collision settings
		collision = true; // has a default collision (door closed)
		solidArea.x = 0;
		solidArea.y = gp.tileSize*3/4;
		solidArea.width = gp.tileSize;
		solidArea.height = gp.tileSize/4;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
	}
}
