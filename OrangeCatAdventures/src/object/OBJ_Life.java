package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Life extends Entity {
	
	GamePanel gp;
	
	public OBJ_Life(GamePanel gp) { // sets the main variables
		super(gp);
		this.gp = gp;
		
		type = type_pickUpOnly;
		name = "life";
		value = 2;
		down1 = setup("/objects/life_full", gp.tileSize, gp.tileSize);
		image = setup("/objects/life_full", gp.tileSize, gp.tileSize);
		image2 = setup("/objects/life_half", gp.tileSize, gp.tileSize);
		image3 = setup("/objects/life_blank", gp.tileSize, gp.tileSize);
	}
	
	public void use(Entity entity) {
		
		gp.playSE(3);
		gp.ui.addMessage("Health Points +" + value);
		entity.life += value;
	}
}
