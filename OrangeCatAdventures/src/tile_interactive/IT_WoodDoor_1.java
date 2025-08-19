package tile_interactive;

import entity.Entity;
import main.GamePanel;

public class IT_WoodDoor_1 extends InteractiveTile{

	GamePanel gp;

	public IT_WoodDoor_1(GamePanel gp, int col, int row) {
		super(gp, col, row);
		this.gp = gp;
		
		this.worldX = gp.tileSize * col;
		this.worldY = gp.tileSize * row;
		
		name = "Wood Door";
		down1 = setup("/tiles_interactive/door_1", gp.tileSize, gp.tileSize);
		destructible = true;
		life = 1;
	}
	
	public boolean isCorrectItem(Entity entity) {
		boolean isCorrectItem = false;
		
		if (entity == gp.player) {
			for (int i = 0; i < gp.player.inventory.size(); i++) {
				if (gp.player.inventory.get(i).name == "Key") {
					gp.player.inventory.remove(i);
					gp.ui.addMessage("Key was removed from inventory");
					gp.ui.addMessage(name + " was opened");
					isCorrectItem = true;
					break;
				}
			}
		}
		
		return isCorrectItem;
	}
	
	public void playSE() {
		gp.playSE(2);
	}
	
	public InteractiveTile getDestroyedForm() {
		InteractiveTile tile = new IT_WoodDoor_2(gp, worldX/gp.tileSize, worldY/gp.tileSize);
		return tile;
	}
}
