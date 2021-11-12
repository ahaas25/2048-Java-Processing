import java.util.ArrayList;


public class variables {
	color[] colors = {new color(250), new color(238, 228, 218), new color(237, 224, 200), new color(242, 177, 121), new color(245, 149, 99), new color(246, 124, 96), new color(246, 95, 59), new color(237, 207, 115), new color(237, 204, 98), new color(237, 200, 80), new color(237,197, 63), new color(237, 194, 45)}; // Colors for each level of tile [0] would be level one, for example.
	int gameScore = 0;
	int gameXSize, gameYSize;
	int gameStrokeSize = 2;
	int popupUIStrokeSize = 3;
	boolean gameOver = false, showOptionsUI = false, showGameWonUI;
	ArrayList<button> buttons = new ArrayList<>(); // Temporary fix - 0 = restart button
	ArrayList<button> optionMenuButtons = new ArrayList<>();
	String[] language = {"Retry", "New Game", "Options", "Continue"};
	String[] actions = {"Retry", "New Game", "Options", "Continue"};
	String[] optionsLanguage = {"Grid Size", "", ""};
	String[] optionsContext = {"4x4", "", ""};
	
	public variables() {
		
	}
}

class color {
	public int r, g, b;
	
	public color(int a, int b, int c) {
		r = a;
		g = b;
		this.b = c;
	}
	
	public color(int a) {
		r = g = b = a;
	}
}