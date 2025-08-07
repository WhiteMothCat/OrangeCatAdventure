package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{
	
	GamePanel gp;
	public boolean upPressed, downPressed, rightPressed, leftPressed, enterPressed, shotKeyPressed;
	// DEBUG
	public boolean showDebugText = false;

	public KeyHandler(GamePanel gp) {
		this.gp = gp;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		int code = e.getKeyCode(); // returns a number associated with the key pressed
		
		// GAME STATE
		if (gp.gameState == gp.titleState) { titleState(code); }
		else if (gp.gameState == gp.playState) { playState(code); }
		else if (gp.gameState == gp.pauseState) { pauseState(code); }
		else if (gp.gameState == gp.dialogueState) { dialogueState(code); }
		else if (gp.gameState == gp.characterState) { characterState(code); }
		
	}
	
	// TITLE STATE
	public void titleState(int code) {
		
		// SUB TITLE STATE
		if (gp.ui.titleScreenState == 0) {
			if (code == KeyEvent.VK_W) { // if "W" is pressed move up
				gp.ui.commandNum--;
				if (gp.ui.commandNum < 0) {
					gp.ui.commandNum = 2;
				}
				gp.playSE(6);
			}
			if (code == KeyEvent.VK_S) { // if "S" is pressed move down
				gp.ui.commandNum++;
				if (gp.ui.commandNum > 2) {
					gp.ui.commandNum = 0;
				}
				gp.playSE(6);
			}
			if (code == KeyEvent.VK_ENTER) { // if "ENTER" is pressed select item
				if(gp.ui.commandNum == 0) {
					gp.playSE(6);
					gp.ui.titleScreenState = 1;
				} else if (gp.ui.commandNum == 1) {
					gp.playSE(5);
					// nothing for now
				} else if (gp.ui.commandNum == 2) {
					System.exit(0);
				}
			}
			
		} else if (gp.ui.titleScreenState == 1) {
			if (code == KeyEvent.VK_ENTER) { // if "ENTER" is pressed select item
				gp.playSE(6);
				gp.gameState = gp.playState;
				gp.playMusic(0);
			}
		}
	}
	
	// PLAY STATE
	public void playState(int code) {
		
		if (code == KeyEvent.VK_W) { // if "W" is pressed move up
			upPressed = true;
		}
		if (code == KeyEvent.VK_S) { // if "S" is pressed move down
			downPressed = true;
		}
		if (code == KeyEvent.VK_A) { // if "A" is pressed move left
			leftPressed = true;
		}
		if (code == KeyEvent.VK_D) { // if "D" is pressed move right
			rightPressed = true;
		}
		if (code == KeyEvent.VK_C) { // if "C" is pressed change to character state
			gp.gameState = gp.characterState;
		}
		if (code == KeyEvent.VK_ENTER) { // if "ENTER" is pressed change enterPressed to true
			enterPressed = true;
		}
		if (code == KeyEvent.VK_F) { // if "F" is pressed shoot projectiles
			shotKeyPressed = true;
		}
		if (code == KeyEvent.VK_P) { // if "P" is pressed change to pause state
			gp.playSE(6);
	        gp.gameState = gp.pauseState;
		}
		
		// DEBUG
		if (code == KeyEvent.VK_T) { // if "T" is pressed show debug text
			if (showDebugText == true) {
				gp.playSE(6);
				showDebugText = false;
			} else {
				gp.playSE(6);
				showDebugText = true;
			}
		}
		if (code == KeyEvent.VK_R) { // if "T" is pressed reload the map
			gp.playSE(6);
			gp.tileM.loadMap("/maps/map01.txt");
		}
	}
	
	// PAUSE STATE
	public void pauseState(int code) {
		
		if (code == KeyEvent.VK_P) { // if "P" is pressed change to pause state
			gp.playSE(6);
	        gp.gameState = gp.playState;
		}
	}
	
	// DIALOGUE STATE
	public void dialogueState(int code) {
		
		if (code == KeyEvent.VK_ENTER) { // if "ENTER" is pressed pass dialogues
			gp.playSE(6);
			gp.gameState = gp.playState;
		}
	}
	
	// CHARACTER STATE
	public void characterState(int code) {
		
		if (code == KeyEvent.VK_C) { // if "C" is pressed change to play state
	        gp.gameState = gp.playState;
		}
		if (code == KeyEvent.VK_W) { // if "W" is pressed move cursor up
			if (gp.ui.slotRow != 0) {
				gp.ui.slotRow--;
			} else {
				gp.ui.slotRow = gp.ui.slotRowMax-1;
			}
			gp.playSE(6);
		}
		if (code == KeyEvent.VK_S) { // if "S" is pressed move cursor down
			if (gp.ui.slotRow != gp.ui.slotRowMax-1) {
				gp.ui.slotRow++;
			} else {
				gp.ui.slotRow = 0;
			}
			gp.playSE(6);
		}
		if (code == KeyEvent.VK_A) { // if "A" is pressed move cursor left
			if (gp.ui.slotCol != 0) {
				gp.ui.slotCol--;
			} else {
				gp.ui.slotCol = gp.ui.slotColMax-1;
			}
			gp.playSE(6);
		}
		if (code == KeyEvent.VK_D) { // if "D" is pressed move cursor right
			if (gp.ui.slotCol != gp.ui.slotColMax-1) {
				gp.ui.slotCol++;
			} else {
				gp.ui.slotCol = 0;
			}
			gp.playSE(6);
		}
		if (code == KeyEvent.VK_ENTER) { // if "ENTER" is pressed use selected item
	        gp.player.selectItem();
	        gp.playSE(6);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		int code = e.getKeyCode(); // returns a number associated with the key pressed
		
		if (code == KeyEvent.VK_W) { // if "W" is released
			upPressed = false;
		}
		if (code == KeyEvent.VK_S) { // if "S" is released
			downPressed = false;
		}
		if (code == KeyEvent.VK_A) { // if "A" is released
			leftPressed = false;
		}
		if (code == KeyEvent.VK_D) { // if "D" is released
			rightPressed = false;
		}
		if (code == KeyEvent.VK_F) { // if "F" is released
			shotKeyPressed = false;
		}
		
	}

}
