package entity;

import main.GamePanel;

public class Projectile extends Entity{
	
	Entity user;

	public Projectile(GamePanel gp) {
		super(gp);
		
		
	}
	
	public void set(int worldX, int worldY, String direction, boolean alive, Entity user) {
		
		this.user = user;
		this.worldX = worldX;
		this.worldY = worldY;
		this.direction = direction;
		this.alive = alive;
		this.life = this.maxLife;
	}
	
	public void update() {
		
		// if player is the user
		if (user == gp.player) {
			
			// collision with monster
			int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
			if (monsterIndex != -1) {
				gp.player.damageMonster(monsterIndex, attack);
				alive = false;
			}
		}
		// if player is not the user
		if (user != gp.player) {
			// check player collision to deal damage
			boolean contactPlayer = gp.cChecker.checkPlayer(this);
			if (gp.player.invincible == false && contactPlayer == true) {
				damagePlayer(attack);
				alive = false;
			}
		}
		
		// movement
		switch (direction) {
		case "up": worldY -= speed; break;
		case "down": worldY += speed; break;
		case "left": worldX -= speed; break;
		case "right": worldX += speed; break;
		}
		
		// fade after some time
		life--;
		if (life <= 0) {
			alive = false;
		}
		
		// sprite management
		spriteCounter++;
		if (spriteCounter > 8) {
			if (spriteNum == 1) {
				spriteNum = 2;
			} else if (spriteNum == 2) {
				spriteNum = 1;
			}
			spriteCounter = 0;
		}
	}
	
	public boolean haveResource(Entity user) { // checks that the user can use the attack
		
		boolean haveResource = false;
		return haveResource;
	}
	
	public void subtractResource(Entity user) {} // changes the value depending on the cost and resource

}
