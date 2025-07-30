package entity;

import java.util.Random;

import main.GamePanel;

public class NPC1 extends Entity{
	
	public NPC1(GamePanel gp) {
		super(gp);
		
		direction = "down"; // default direction
		speed = 2;
		
		getImage();
		setDialogue();
	}
	
	public void getImage() {
		
		// NPC1 SPRITES
		// (imageName)
		up1 = setup("/NPC/NPC1_up_1", gp.tileSize, gp.tileSize);
		up2 = setup("/NPC/NPC1_up_2", gp.tileSize, gp.tileSize);
		down1 = setup("/NPC/NPC1_down_1", gp.tileSize, gp.tileSize);
		down2 = setup("/NPC/NPC1_down_2", gp.tileSize, gp.tileSize);
		left1 = setup("/NPC/NPC1_right_1", gp.tileSize, gp.tileSize);
		left2 = setup("/NPC/NPC1_right_2", gp.tileSize, gp.tileSize);
		right1 = setup("/NPC/NPC1_left_1", gp.tileSize, gp.tileSize);
		right2 = setup("/NPC/NPC1_left_2", gp.tileSize, gp.tileSize);
	}
	
	public void setDialogue() {
		
		dialogues[0] = "Hello, you must be new around here, \nright?";
		dialogues[1] = "It's been a long time since I saw anyone \nroming in this island";
		dialogues[2] = "Are you searching for the great treasure?";
		dialogues[3] = "Well, if you are then you'd have to deal \nwith some monsters in your way there";
		dialogues[4] = "Then good luck!";
	}
	
	public void setAction() { // AI of the NPC
		
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
	}
	
	public void speak() { // makes the NPC speak
		super.speak();
	}
}
