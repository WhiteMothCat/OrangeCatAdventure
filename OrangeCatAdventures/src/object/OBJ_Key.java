package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Key extends Entity {
	
	GamePanel gp;
	
	public OBJ_Key(GamePanel gp) { // sets the main variables
		
		super(gp);
		this.gp = gp;
		
		type = type_consumable;
		name = "Key";
		down1 = setup("/objects/key_1", gp.tileSize, gp.tileSize);
		description = "[" + name + "]\nAn old key able to open\nnormal doors";
	}
	
	public void use(Entity entity) {
		
		gp.gameState = gp.dialogueState;
		gp.ui.currentDialogue = "ERROR: This is a bug to be changed";
		gp.playSE(5);
	}

}
