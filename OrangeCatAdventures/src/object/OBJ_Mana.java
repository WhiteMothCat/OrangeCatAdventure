package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Mana extends Entity {

	GamePanel gp;
	
	public OBJ_Mana(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		type = type_pickUpOnly;
		name = "Mana";
		value = 1;
		down1 = setup("/objects/mana_full_2", gp.tileSize, gp.tileSize);
		image = setup("/objects/mana_full_2", gp.tileSize, gp.tileSize);
		image2 = setup("/objects/mana_blank", gp.tileSize, gp.tileSize);
	}
	
public void use(Entity entity) {
		
		gp.playSE(3);
		gp.ui.addMessage("Health Points +" + value);
		entity.mana += value;
	}

}
