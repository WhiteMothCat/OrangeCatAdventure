package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Chest extends Entity{
	
	public OBJ_Chest(GamePanel gp) { // sets the main variables
		
		super(gp);
		
		type = type_blockade;
		name = "chest";
		down1 = setup("/tiles_interactive/chest_1", gp.tileSize, gp.tileSize);
		
		// collision settings
		collision = true;
		solidArea.x = 0;
		solidArea.y = gp.tileSize/2;
		solidArea.width = gp.tileSize;
		solidArea.height = gp.tileSize/2;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
	}
}
