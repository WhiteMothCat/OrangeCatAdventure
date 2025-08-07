package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Mushroom extends Entity {
	
	GamePanel gp;
	int value = 1;
	
	public OBJ_Mushroom(GamePanel gp) { // sets the main variables
		
		super(gp);
		this.gp = gp;
		
		type = type_consumable;
		name = "Mushroom";
		down1 = setup("/objects/mushroom_1", gp.tileSize, gp.tileSize);
		description = "[" + name + "]\nA feral mushroom only\nfound in the forest\nStrength: +" + value;
	}
	
	public void use(Entity entity) {
		
		gp.gameState = gp.dialogueState;
		gp.ui.currentDialogue = "The " + name + " stregthens your body by " + value + "\nStrength Points";
		entity.strength += value;
		gp.playSE(3);
	}

}
