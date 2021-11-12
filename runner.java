import java.util.ArrayList;
import java.util.Random;

import processing.core.PApplet;
import processing.core.PFont;

/* 
 * To-Do
 * Finish Options Menu
 * Create animations - Create a before and after grid to compare the movements? Need to animate the new piece too
 * Game mode: Timer
 * 
 * */

public class runner extends PApplet{
	Random r = new Random();
	
	int[] values = {2, 2, 2, 2, 2, 2, 2, 2, 2, 4};
	final double uiy = 0.25; 
	int yq;
	int gridSize = 4;
	boolean hasWon = false;
	tile grid[][], tempGrid[][];
	variables v;
	
	PFont BOLD, REGULAR;
	
	public void settings() {
		size(600,800);	
	}
	
	public void setup() {
		surface.setResizable(true); // Is it possible for an "onResize" function?
		surface.setTitle("2048");
		BOLD = createFont("Arial Bold", 16, true);
		REGULAR = createFont("Arial", 16, true);
		v = new variables();
		resetVars();
		frameRate(30);
	}
	
	public void draw() {
		background(255);
		drawGame();
		drawUI();
	}
	
	public void drawUI() {
		int r = 16;
		yq = (int) (uiy * height);
		
		fill(250,248,238);
		rect(0, 0, width, yq, r, r, r, r);
		
		textAlign(CENTER);
		
		textFont(BOLD);
		fill(119,110,101);
		drawText("2048", (int) (width * 0.1875), (int) (height * uiy * 0.5), (int) (width / 9.375));
		drawText("Score: " + v.gameScore, (int) (width * 0.1875), (int) (height * uiy * 0.7), (int) (width / 15));
		
		fill(255);
		v.buttons.get(1).setValues((int) (width * 0.5), (int)(height * uiy * 0.25), (int) (width / 4.8), (int) (height / 10.667), (int) (width / 30)); // This would be better with a window resized listener if the game is ported to a better engine
		drawButton(v.buttons.get(1)); // Make this a for loop later.
		fill(255);
		v.buttons.get(2).setValues((int) ((width * 0.525) + (width / 4.8)), (int)(height * uiy * 0.25), (int) (width / 4.8), (int) (height / 10.667), (int) (width / 30));
		drawButton(v.buttons.get(2)); // Make this a for loop later.
		
		if (v.gameOver) {
			drawGameOverUI();
		}
		else if (v.showOptionsUI) {
			drawOptionsUI();
		}
		else if (v.showGameWonUI) {
			drawGameWonUI();
		}
	}
	
	public void drawGameOverUI() {	
		noStroke();
		fill(0,0,0,100);
		rect(0, yq, width, height);
		
		drawText("Game Over!", width / 2, height / 2, (int) (width / 12.5), 255);
		fill(237,197, 63);
		v.buttons.get(0).setValues((int) (width * 0.25), (int) (height * 0.55), (int) (width * 0.5), (int) (height * 0.125), (int) (width / 18.75));
		drawButton(v.buttons.get(0));
	}
	
	public void drawGameWonUI() {
		noStroke();
		fill(0,0,0,100);
		rect(0, yq, width, height);
		
		drawText("2048!", width / 2, height / 2, (int) (width / 12.5), 255);

		fill(237,197, 63);
		v.buttons.get(0).setValues((int) (width * 0.25), (int) (height * 0.55), (int) (width * 0.5), (int) (height * 0.125), (int) (width / 18.75));
		drawButton(v.buttons.get(0));
		
		fill(237,197, 63);
		v.buttons.get(3).setValues((int) (width * 0.25), (int) (height * 0.7), (int) (width * 0.5), (int) (height * 0.125), (int) (width / 18.75));
		drawButton(v.buttons.get(3));
	}
	
	public void drawOptionsUI() {
		noStroke();
		
		// Hidden buttons for the UI
		
		
		fill(0,0,0,100);
		rect(0, yq, width, height);
		
		int xPadding = (int) (width * 0.1);
		int yPadding = (int) (height * 0.05);
		int r = 16;
		
		int xOptionsArea = (int) (width * 0.05) + xPadding;
		int yOptionsArea = (int) (height * 0.1) + yq + yPadding;
		
		fill(255);
		rect(xPadding, yq + yPadding, width - (int) (xPadding * 2), (int) (height - yq - yPadding * 2), r, r, r, r);
		
		drawText(v.language[2], xPadding + (int) (width * 0.15), yPadding + yq + (int) (height * 0.05), (int) (width / 18.75), 0);
		stroke(5);
		line(xPadding + (int) (width * 0.05), yPadding + yq + (int) (height * 0.1), width - (xPadding + (int) (width * 0.05)), yPadding + yq + (int) (height * 0.1));
		
		updateOptionsContext();
		// For loop of each option
		for (int i = 0; i < v.optionsLanguage.length; i++) {
			textAlign(LEFT, TOP);
			drawText(v.optionsLanguage[i], xOptionsArea, yOptionsArea + (int) (i * (height * 0.05)), (int) (width * 0.04), 0);
			textAlign(RIGHT, TOP);
			drawText(v.optionsContext[i], width - xOptionsArea, yOptionsArea + (int) (i * (height * 0.05)), (int) (width * 0.04), 0);
		}
		
		fill(0);
		for (int i = 0; i < v.optionMenuButtons.size(); i++) {
			v.optionMenuButtons.get(i).setValues(xOptionsArea, yOptionsArea + (int) (i * (height * 0.05)), (int) (width - xOptionsArea * 2), (int) (height * 0.04), 1);
		}
		
	}
	
	public void updateOptionsContext() {
		v.optionsContext[0] = gridSize + " x " + gridSize;
	}
	
	/*
	 * Parameters: Area start X, Area Start Y, Area end X, Area end Y
	 */
	public boolean isMouseInArea(int x1, int y1, int x2, int y2) {
		return (mouseX >= x1 && mouseY >= y1 && mouseX <= x2 && mouseY <= y2);
	}
	
	public void mouseClicked() {
		for(button x : v.buttons) {
			if(isMouseInArea(x.x1, x.y1, x.x2, x.y2)) {
				buttonHandler(x);
			}
		}
		for (button x : v.optionMenuButtons) {
			if(isMouseInArea(x.x1, x.y1, x.x2, x.y2)) {
				buttonHandler(x);
			}
		}
	}
	
	public void buttonHandler(button x) {
		if (x.activated) {
			if(x.action == "Retry") {
				resetVars();
			}
			else if (x.action == "New Game") {
				resetVars();
			}
			else if (x.action == "Options") {
				v.showOptionsUI = !v.showOptionsUI;
			}
			else if (x.action == "Continue") {
				v.showGameWonUI = false;
			}
			else if (x.action == "Grid Size") {
				flipGridSize();
			}
		}
	}
	
	public void flipGridSize() {
		if(gridSize == 4) {
			setGridSize(5);
		}
		else {
			setGridSize(4);
		}
		resetVars();
		
	}
	
	public void drawButton(button x) {
		rect(x.x1, x.y1, x.xLength, x.yLength, x.r, x.r, x.r, x.r);
		textAlign(CENTER, CENTER);
		drawText(x.text, (int) ((x.x2 + x.x1)/2), (int) ((x.y1 + x.y2)/2.025), (int) x.fontSize, 0);
	}
	
	public void drawGame() {
		v.gameXSize = width / gridSize;
		v.gameYSize = (height - yq) / gridSize;
		double spacing = 0.1;
		int xSpacing = (int) (v.gameXSize * spacing);
		int ySpacing = (int) (v.gameYSize * spacing);
		
		noStroke();
		fill(187, 173, 160);
		rect(0, 0, width, height);
		strokeWeight(v.gameStrokeSize);
		stroke(1);
		
		for(int i = 0; i < grid.length; i++) {
			for(int k = 0; k < grid[0].length; k++) {
				tile temp = grid[i][k];
				int cl = tileColor(temp);
				fill(v.colors[cl].r, v.colors[cl].g, v.colors[cl].b); // Figure out how Processing generates color code to simplify this.
				rect((int) (v.gameXSize * i + xSpacing * 0.5), (int) ((v.gameYSize * k) + yq + ySpacing * 0.5), v.gameXSize - xSpacing, v.gameYSize - ySpacing, 8, 8, 8, 8);
				if(!temp.isEmpty()) {
					fill(0);
					textAlign(CENTER);
					drawText(temp.value + "", (int) (v.gameXSize * i + v.gameXSize * 0.5 + xSpacing * 0.5), (int) ((v.gameYSize * k + v.gameYSize * 0.5) + yq + ySpacing * 0.5), 32);
				}
			}
		}
	}
	
	public void checkWinConditions() {
		for (int i = 0; i < grid.length; i++) {
			for (int k = 0; k < grid[0].length; k++) {
				if (grid[i][k].value == 2048 && !hasWon && true) {// Replace true with the game mode
					hasWon = true;
					v.showGameWonUI = true;
				}
			}
		}
	}
	
	public int tileColor(tile temp) {
		int cl = temp.colorLevel();
		if(cl >= v.colors.length)
			cl = v.colors.length - 1;
		return cl;
	}
	
	public int isZero(int x) {
		if(x == 0)
			return 1;
		else
			return 0;
	}
	
	public void drawText(String stringText, int x, int y, int size) {
		textSize(size);
		text(stringText, x, y);
	}
	public void drawText(String stringText, int x, int y, int size, int color) {
		fill(color);
		textSize(size);
		text(stringText, x, y);
	}
	
	// Gamey Stuff \/
	
	public void keyPressed() {
		if (key == 'r' || key == 'R') {
			resetVars();
		}
		else if (key == 'f' || key == 'F') {
			undoMove();
		}
		else if (key == 't') {
			v.showGameWonUI = true;
		}
		else {
			if(!v.gameOver)
				moveTiles();
		}
	}
	
	public void resetVars() {
		v.gameScore = 0;
		hasWon = false; // Used for checking if win condition has been met before
		setGridSize(gridSize);
		fillGrid();
		resetTempGrid();
		createNewTile();
		v.gameOver = false;
		v.showGameWonUI = false;
		v.buttons = new ArrayList<button>();
		v.optionMenuButtons = new ArrayList<button>();
		for(int i = 0; i < 10; i++) {
			if (i < v.language.length) {
				v.buttons.add(new button(v.actions[i], v.language[i]));
			}
			else
				v.buttons.add(new button());
		}
		
		for(int i = 0; i < v.optionsLanguage.length; i++) {
			v.optionMenuButtons.add(new button(v.optionsLanguage[i], ""));
		}
		
		
		v.buttons.get(0).deactivate();
		
	}
	
	public void setGridSize(int x) {
		gridSize = x;
		grid = new tile[x][x];
	}

	public void fillGrid() {
		for(int i = 0; i < grid.length; i++) {
			for(int k = 0; k < grid[0].length; k++) {
				grid[i][k] = new tile(i, k, 0);
			}
		}
	}
	
	public void resetTempGrid() {
		tempGrid = new tile[grid.length][grid[0].length];
		for(int i = 0; i < grid.length; i++) {
			for(int k = 0; k < grid[0].length; k++) {
				tempGrid[i][k] = new tile(i, k, 0);
			}
		}
	}
	
	public void undoMove() {
		if(grid == tempGrid) {
			System.out.println(tempGrid[0][0].value);
		}
		grid = tempGrid;
	}
	
	public void createNewTile() {
		int value = values[r.nextInt(values.length)];
		ArrayList<tile> possibleTiles = new ArrayList<>();
		for(int i = 0; i < grid.length; i++) {
			for(int k = 0; k < grid[0].length; k++) {
				if(grid[i][k].isEmpty()) {
					possibleTiles.add(grid[i][k]);	
				}
			}
		}
		int selectedTile = r.nextInt(possibleTiles.size());
		int i = possibleTiles.get(selectedTile).x;
		int k = possibleTiles.get(selectedTile).y;
		setTile(i, k, value);
	}
	
	public boolean canMove() {
		return canMoveUp() || canMoveDown() || canMoveRight() || canMoveLeft();
	}
	
	public boolean canMoveUp() {
		for(int i = 0; i < grid.length; i++) {
			for(int k = 0; k < grid[0].length; k++) {
				if (grid[i][k].moveUp(grid, 0, grid[i][k].value) != 0)
						return true;
			}
		}
		return false;
	}

	public boolean canMoveDown() {
		for(int i = 0; i < grid.length; i++) {
			for(int k = 0; k < grid[0].length; k++) {
				if (grid[i][k].moveDown(grid, 0, grid[i][k].value) != 0)
						return true;
			}
		}
		return false;
	}
	
	public boolean canMoveRight() {
		for(int i = 0; i < grid.length; i++) {
			for(int k = 0; k < grid[0].length; k++) {
				if (grid[i][k].moveRight(grid, 0, grid[i][k].value) != 0)
						return true;
			}
		}
		return false;
	}
	
	public boolean canMoveLeft() {
		for(int i = 0; i < grid.length; i++) {
			for(int k = 0; k < grid[0].length; k++) {
				if (grid[i][k].moveLeft(grid, 0, grid[i][k].value) != 0)
						return true;
			}
		}
		return false;
	}
	
	public void setTile(int i, int k, int val) {
		grid[i][k].value = val;
	}
	
	public void moveTileUp(int x, int y, int moves, int val) {
		setTile(x, y - moves, val);
		setTile(x, y, 0);
	}
	
	public void moveTileDown(int x, int y, int moves, int val) {
		setTile(x, y + moves, val);
		setTile(x, y, 0);
	}
	
	public void moveTileRight(int x, int y, int moves, int val) {
		setTile(x + moves, y, val);
		setTile(x, y, 0);
	}
	
	public void moveTileLeft(int x, int y, int moves, int val) {
		setTile(x - moves, y, val);
		setTile(x, y, 0);
	}
	
	public void moveTiles() {
		boolean moved = false;
		int moves = 0;
		
		// Redo this later, Make two tempGrid, if a move is made make this the new temp grid... u know what i mean
		
		for(int i = 0; i < grid.length; i++) {
			for(int k = 0; k < grid[0].length; k++) {
				tempGrid[i][k].value = grid[i][k].value;
			}
		}

		
		if(keyCode == UP) {
			if (canMoveUp())
				for(int i = 0; i < grid.length; i++) {
					for(int k = 0; k < grid[0].length; k++) {
						moves = grid[i][k].moveUp(grid, 0, grid[i][k].value);
						if(moves != 0 && grid[i][k].value != 0) {
							moved = true;
							if(moves > 0) {
								moveTileUp(i, k, moves, grid[i][k].value);
							}
							else {
								v.gameScore += grid[i][k].value * 2;
								moveTileUp(i, k, -moves, grid[i][k].value * 2);
							}
						}
					}
				}
			}
		if (canMoveDown())
			if (keyCode == DOWN) {
				for(int i = grid.length - 1; i >= 0; i--) {
					for(int k = grid[0].length - 1; k >= 0; k--) {
						moves = grid[i][k].moveDown(grid, 0, grid[i][k].value);
						if(moves != 0 && grid[i][k].value != 0) {
							moved = true;
							if(moves > 0) {
								moveTileDown(i, k, moves, grid[i][k].value);
							}
							else {
								v.gameScore += grid[i][k].value * 2;
								moveTileDown(i, k, -moves, grid[i][k].value * 2);
							}
						}
					}
				}
			}
		if (canMoveRight())
			if (keyCode == RIGHT) {
				for(int i = grid.length - 1; i >= 0; i--) {
					for(int k = grid[0].length - 1; k >= 0; k--) {
						moves = grid[i][k].moveRight(grid, 0, grid[i][k].value);
						if(moves != 0 && grid[i][k].value != 0) {
							moved = true;
							if(moves > 0) {
								moveTileRight(i, k, moves, grid[i][k].value);
							}
							else {
								v.gameScore += grid[i][k].value * 2;
								moveTileRight(i, k, -moves, grid[i][k].value * 2);
							}
						}
					}
				}
			}
		if (canMoveLeft())
			if (keyCode == LEFT) {
				for(int i = 0; i < grid.length; i++) {
					for(int k = 0; k < grid[0].length; k++) {
						moves = grid[i][k].moveLeft(grid, 0, grid[i][k].value);
						if(moves != 0 && grid[i][k].value != 0) {
							moved = true;
							if(moves > 0) {
								moveTileLeft(i, k, moves, grid[i][k].value);
							}
							else {
								v.gameScore += grid[i][k].value * 2;
								moveTileLeft(i, k, -moves, grid[i][k].value * 2);
							}
						}
					}
				}
			}
		
		if (moved) {
			createNewTile();
			checkWinConditions();
		}
		
		if(!canMove()) {
			v.gameOver = !canMove();
			v.buttons.get(0).activate();
		}
	}
		
	public static void main(String[] args) {
		PApplet.main("runner");		
	}

}
