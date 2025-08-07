package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Yarn_1 extends Entity{

	GamePanel gp;

	public OBJ_Yarn_1(GamePanel gp) { // sets the main variables
		
		super(gp);
		this.gp = gp;
		
		type = type_pickUpOnly;
		name = "Red Yarn";
		value = 1;
		down1 = setup("/objects/yarn_1", gp.tileSize, gp.tileSize);
	}
	
	public void use(Entity entity) {
		
		gp.playSE(1);
		gp.ui.addMessage("Yarn +" + value);
		gp.player.yarn += value;
	}
}
