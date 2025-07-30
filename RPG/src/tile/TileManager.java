package tile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class TileManager {
	
	GamePanel gp;
	public Tile[] tile;
	public int mapTileNum[][];
	
	public TileManager(GamePanel gp) {
		
		this.gp = gp;
		tile = new Tile[100]; // the size of the table means the number of different tiles (water, brick, grass, ...)
		mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
		
		getTileImage();
		loadMap("/maps/map01.txt");
	}
	
	public void getTileImage() {
			
		// TILE SET
		// (index, imageName, collision)
		
		// Grass
		setUp(0, "grass_1", false);
		setUp(1, "grass_2", false);
		setUp(2, "flowers_1", false);
		setUp(3, "bush_1", false);
		setUp(4, "bush_2", false);
		setUp(5, "tree_1", true);
		setUp(55, "flowers_2", false);
		setUp(56, "pit_1", false);
		setUp(57, "venomousPlant_1", false);
		// Paths
		setUp(6, "Paths_1", false);
		setUp(7, "Paths_2", false);
		setUp(8, "Paths_3", false);
		setUp(9, "Paths_4", false);
		setUp(10, "Paths_5", false);
		setUp(11, "Paths_6", false);
		setUp(12, "Paths_7", false);
		setUp(13, "Paths_8", false);
		setUp(14, "Paths_9", false);
		setUp(15, "Paths_10", false);
		setUp(16, "Paths_11", false);
		setUp(17, "Paths_12", false);
		setUp(18, "Paths_13", false);
		setUp(19, "Paths_14", false);
		setUp(20, "Paths_15", false);
		setUp(21, "Paths_16", false);
		// Bricks
		setUp(22, "brick_3", true);
		setUp(23, "brick_4", true);
		setUp(24, "brick_5", true);
		setUp(25, "brick_6", true);
		setUp(26, "brick_7", true);
		setUp(27, "brick_8", true);
		setUp(28, "brick_9", true);
		setUp(29, "brick_10", true);
		setUp(30, "brick_11", true);
		setUp(31, "brick_1", true);
		setUp(32, "brick_2", true);
		// Water
		setUp(33, "water_3", true);
		setUp(34, "water_4", true);
		setUp(35, "water_5", true);
		setUp(36, "water_6", true);
		setUp(37, "water_7", true);
		setUp(38, "water_8", true);
		setUp(39, "water_9", true);
		setUp(40, "water_10", true);
		setUp(41, "water_11", true);
		setUp(42, "water_12", true);
		setUp(43, "water_13", true);
		setUp(44, "water_14", true);
		setUp(45, "water_1", true);
		setUp(46, "water_2", true);
		// Floor
		setUp(47, "floor_3", true);
		setUp(48, "floor_4", true);
		setUp(49, "floor_5", true);
		setUp(50, "floor_6", true);
		setUp(51, "floor_7", true);
		setUp(52, "floor_8", true);
		setUp(53, "floor_1", false);
		setUp(54, "floor_2", true);
		
		// last one used: 57 by venomousPlant_1 in grass
	}
	
	public void setUp(int index, String imageName, boolean collision) {
		
		UtilityTool uTool = new UtilityTool(); // creates the tool class to use the method
		
		try {
			
			tile[index] = new Tile(); // creates the new tile
			tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imageName + ".png")); // creates the image
			tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize); // scales the image
			tile[index].collision = collision; // sets the collision
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void loadMap(String filePath) { //loads the map from the text file
		
		try {
			
			InputStream is = getClass().getResourceAsStream(filePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(is)); //gets the map file
			
			int col = 0;
			int row = 0;
			
			while (col < gp.maxWorldCol && row < gp.maxWorldRow) { //reads the map file
				
				String line = br.readLine();
				
				while (col < gp.maxWorldCol) {
					
					String numbers[] = line.split(" "); // splits the rows into values
					int num = Integer.parseInt(numbers[col]);
					
					mapTileNum[col][row] = num; // we transform the file into a table
					col++;
				}
				if (col == gp.maxWorldCol) {
					col = 0;
					row++;
				}
				
			}
			br.close();
			
		} catch (Exception e) {
			
		}
		
	}
	
	public void draw(Graphics2D g2) { // draws the tile set from the map
		
		int worldCol = 0;
		int worldRow = 0;
		
		while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
			
			int tileNum = mapTileNum[worldCol][worldRow];
			
			int worldX = worldCol * gp.tileSize;
			int worldY = worldRow * gp.tileSize;
			int screenX = worldX - gp.player.worldX + gp.player.screenX;
			int screenY = worldY - gp.player.worldY + gp.player.screenY;
			
			if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX && 
				worldX - gp.tileSize  < gp.player.worldX + gp.player.screenX &&
				worldY + gp.tileSize  > gp.player.worldY - gp.player.screenY &&
				worldY - gp.tileSize  < gp.player.worldY + gp.player.screenY) { //only draws the tiles inside the player's screen
			
				g2.drawImage(tile[tileNum].image, screenX, screenY, null);
			
			}
			
			worldCol++;
			if (worldCol == gp.maxWorldCol) {
				worldCol = 0;
				worldRow++;
			}
		}
	}

}
