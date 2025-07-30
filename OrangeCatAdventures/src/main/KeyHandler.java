package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{
	
	GamePanel gp;
	public boolean upPressed, downPressed, rightPressed, leftPressed, enterPressed;
	// DEBUG
	public boolean checkDrawTime = false;

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
			}
			if (code == KeyEvent.VK_S) { // if "S" is pressed move down
				gp.ui.commandNum++;
				if (gp.ui.commandNum > 2) {
					gp.ui.commandNum = 0;
				}
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
		if (code == KeyEvent.VK_P) { // if "P" is pressed change to pause state
	        gp.gameState = gp.pauseState;
		}
		
		// DEBUG
		if (code == KeyEvent.VK_T) { // if "T" is pressed check draw time
			if (checkDrawTime == true) {
				checkDrawTime = false;
			} else {
				checkDrawTime = true;
			}
		}
	}
	
	// PAUSE STATE
	public void pauseState(int code) {
		
		if (code == KeyEvent.VK_P) { // if "P" is pressed change to pause state
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
		
	}

}
