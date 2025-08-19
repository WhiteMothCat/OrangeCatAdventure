package monster;

import java.util.Random;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Life;
import object.OBJ_Mana;
import object.OBJ_WaspProjectile;
import object.OBJ_Yarn_1;
import object.OBJ_Yarn_2;

public class MON_Wasp extends Entity {

	GamePanel gp;
	
	public MON_Wasp(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		// STATS
		type = type_monster;
		name = "Wasp";
		speed = 3;
		maxLife = 4;
		life = maxLife;
		attack = 3;
		defense = 1;
		exp = 3;
		projectile = new OBJ_WaspProjectile(gp);
		// COLLISON
		solidArea.x = gp.tileSize*1/16;
		solidArea.y = gp.tileSize/2;
		solidArea.width = gp.tileSize*7/8;
		solidArea.height = gp.tileSize/2;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		// SPRITES
		getImage();
	}
	
	public void getImage() { // loads and scales the images
		
		up1 = setup("/monster/wasp_up_1", gp.tileSize, gp.tileSize);
		up2 = setup("/monster/wasp_up_2", gp.tileSize, gp.tileSize);
		down1 = setup("/monster/wasp_left_1", gp.tileSize, gp.tileSize);
		down2 = setup("/monster/wasp_left_2", gp.tileSize, gp.tileSize);
		right1 = setup("/monster/wasp_right_1", gp.tileSize, gp.tileSize);
		right2 = setup("/monster/wasp_right_2", gp.tileSize, gp.tileSize);
		left1 = setup("/monster/wasp_left_1", gp.tileSize, gp.tileSize);
		left2 = setup("/monster/wasp_left_2", gp.tileSize, gp.tileSize);
	}
	
	public void setAction() { // monster "AI"
		
		actionLockCounter++;
		
		if (actionLockCounter == 120) { // avoids the NPC tornadoing
			Random random = new Random();
			int i = random.nextInt(100) + 1; // picks a random int from 1 to 100
		
			if (i <= 25) {
				direction = "up";
			} else if (i > 25 && i <= 50) {
				direction = "down";
			} else if (i > 50 && i <= 75) {
				direction = "left";
			} else if (i > 75 && i <= 100) {
				direction = "right";
			}
			
			actionLockCounter = 0;
		}
		
		// throw projectile
		int i = new Random().nextInt(100)+1; // random number between 1 and 100
		if (i > 0 && projectile.alive == false && shotAvaliableCounter == 30) { // change the comparator of i depending on the rarity of shooting
			
			projectile.set(worldX, worldY, direction, true, this);
			gp.projectileList.add(projectile);
			shotAvaliableCounter = 0;
		}
	}
	
	public void damageReaction() { // when the monster is hit
		
		actionLockCounter = 0;
		switch(gp.player.direction) { // the monster goes for the player
		case "up": direction = "down"; break;
		case "down": direction = "up"; break;
		case "left": direction = "right"; break;
		case "right": direction = "left"; break;
		}
//		direction = gp.player.direction; // the monster gets away from the player
		
	}
	
	public void checkDrop() {
		
		int i = new Random().nextInt(100)+1; // random number between 1 and 100
		
		// set monster drop
		if (i < 75) {
			dropItem(new OBJ_Yarn_2(gp));
		}
		if (i >= 75) {
			dropItem(new OBJ_Mana(gp));
		}
	}
}
