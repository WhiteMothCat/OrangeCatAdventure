package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_TunaCan extends Entity {

	GamePanel gp;

	public OBJ_TunaCan(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		type = type_consumable;
		name = "Tuna Can";
		value = 4;
		down1 = setup("/objects/tunaCan_3", gp.tileSize, gp.tileSize);
		description = "[" + name + "]\nRegenerates some of your\nlife\nHealth Points: +" + value;
	}
	
	public void use(Entity entity) {
		
		gp.gameState = gp.dialogueState;
		gp.ui.currentDialogue = "The " + name + " restores your life by " + value + "\nHealth Points";
		entity.life += value;
		gp.playSE(3);
	}
}
