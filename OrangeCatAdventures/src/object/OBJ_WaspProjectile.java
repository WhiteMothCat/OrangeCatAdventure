package object;

import entity.Entity;
import entity.Projectile;
import main.GamePanel;

public class OBJ_WaspProjectile extends Projectile {

	GamePanel gp;

	public OBJ_WaspProjectile(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		// main attributes
		name = "Wasp Projectile";
		speed = 8;
		maxLife = 80;
		life = maxLife;
		attack = 3;
		useCost = 1;
		alive = false;
		getImage();
	}
	
	public void getImage() {
		
		// PLAYER SPRITES
		// (imageName, width, height)
		up1 = setup("/projectile/waspProjectile_up", gp.tileSize, gp.tileSize);
		up2 = setup("/projectile/waspProjectile_up", gp.tileSize, gp.tileSize);
		down1 = setup("/projectile/waspProjectile_down", gp.tileSize, gp.tileSize);
		down2 = setup("/projectile/waspProjectile_down", gp.tileSize, gp.tileSize);
		left1 = setup("/projectile/waspProjectile_left", gp.tileSize, gp.tileSize);
		left2 = setup("/projectile/waspProjectile_left", gp.tileSize, gp.tileSize);
		right1 = setup("/projectile/waspProjectile_right", gp.tileSize, gp.tileSize);
		right2 = setup("/projectile/waspProjectile_right", gp.tileSize, gp.tileSize);
	}
	
	public boolean haveResource(Entity user) { // checks that the user can use the attack
		
		boolean haveResource = false;
		if (user.ammo >= useCost) {
			haveResource = true;
		}
		return haveResource;
	}
	
	public void subtractResource(Entity user) {
		user.ammo -= useCost;
	}
}
