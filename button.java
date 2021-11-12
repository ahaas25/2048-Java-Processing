
public class button {
	int x1, y1, x2, y2, xLength, yLength, r = 16, fontSize;
	boolean enabled = false;
	String action, text;
	boolean activated = false;
	
	public button() {
		
	}
	
	public button(String a, String text) {
		action = a;
		this.text = text;
	}
	
	public void setValues(int x1, int y1, int xLength, int yLength, int fontSize) {
		this.x1 = x1;
		this.y1 = y1;
		this.xLength = xLength;
		this.yLength = yLength;
		x2 = (int) (x1 + xLength);
		y2 = (int) (y1 + yLength);
		
		this.fontSize = fontSize;
		
		activated = true; // Assumes values are being updated because button is active.
	}
	
	public void activate() {
		activated = true;
	}
	
	public void deactivate() {
		activated = false;
	}
	
}
