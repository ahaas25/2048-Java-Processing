
public class tile {
	public int value, x , y;
	
	public tile(int x, int y, int value) {
		this.x = x;
		this.y = y;
		this.value = value;
	}
	
	
	// Could make these all into one function changing x + 1 and y + 1 or whatever into a variable
	public int moveUp(tile[][] grid, int moves, int val) {
		if(y > 0) {
			if (grid[x][y-1].isEmpty())
				return grid[x][y-1].moveUp(grid, moves + 1, val);
			else if (grid[x][y-1].value == val) {
				return -(moves + 1);
			}
		}
		return moves;
	}
	
	public int moveDown(tile[][] grid, int moves, int val) {
		if(y + 1 < grid[0].length) {
			if (grid[x][y+1].isEmpty())
				return grid[x][y+1].moveDown(grid, moves + 1, val);
			else if (grid[x][y+1].value == val) {
				return -(moves + 1);
			}
		}
		return moves;
	}
	
	public int moveRight(tile[][] grid, int moves, int val) {
		if(x + 1 < grid.length) {
			if (grid[x+1][y].isEmpty())
				return grid[x+1][y].moveRight(grid, moves + 1, val);
			else if (grid[x+1][y].value == val) {
				return -(moves + 1);
			}
		}
		return moves;
	}
	
	public int moveLeft(tile[][] grid, int moves, int val) {
		if(x > 0) {
			if (grid[x-1][y].isEmpty())
				return grid[x-1][y].moveLeft(grid, moves + 1, val);
			else if (grid[x-1][y].value == val) {
				return -(moves + 1);
			}
		}
		return moves;
	}
	
	
	public void getValue() {
		System.out.println("Value: " + value);
	}
	
	public void setValue(int val) {
		value = val;
	}
	
	public boolean isEmpty() {
		return value == 0;
	}
	/*
	 * Inefficient way of getting tile level
	 */
	public int colorLevel() {
		if(isEmpty()) {
			return 0;
		}
		else {
			return (int)(Math.log(value) / Math.log(2));
		}
		
	}
}
