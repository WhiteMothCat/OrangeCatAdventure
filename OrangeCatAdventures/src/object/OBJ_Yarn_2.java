package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Yarn_2 extends Entity{

	GamePanel gp;

	public OBJ_Yarn_2(GamePanel gp) { // sets the main variables
		
		super(gp);
		this.gp = gp;
		
		type = type_pickUpOnly;
		name = "Blue Yarn";
		value = 5;
		down1 = setup("/objects/yarn_2", gp.tileSize, gp.tileSize);
	}
	
	public void use(Entity entity) {
		
		gp.playSE(1);
		gp.ui.addMessage("Yarn +" + value);
		gp.player.yarn += value;
	}
}
