package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Potion_1 extends Entity{
	
	GamePanel gp;

	public OBJ_Potion_1(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		type = type_consumable;
		name = "Pink Potion";
		value = 3;
		down1 = setup("/objects/potion_1", gp.tileSize, gp.tileSize);
		description = "[" + name + "]\nRegenerates some of your\nmana\nMana Points: +" + value;
	}
	
	public void use(Entity entity) {
		
		gp.gameState = gp.dialogueState;
		gp.ui.currentDialogue = "The " + name + " restores your mana by " + value + "\nMana Points";
		entity.mana += value;
		gp.playSE(3);
	}

}
