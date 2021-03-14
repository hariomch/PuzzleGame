package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.JPanel;

public class GameOfFifteen extends JPanel { // our grid will be drawn in a dedicated Panel
	  
	// Size of our Game of Fifteen instance
	private int size;
	// Number of tiles
	private int nbTiles;
	// Grid UI Dimension
	private int dimension;
	// Foreground Color
	private static final Color FOREGROUND_COLOR = new Color(239, 83, 80); // we use arbitrary color
	// Random object to shuffle tiles
	private static final Random RANDOM = new Random();
	// Storing the tiles in a 1D Array of integers
	private int[] tiles;
	// Size of tile on UI
	private int tileSize;
	// Position of the blank tile
	private int blankPos;
	// Margin for the grid on the frame
	private int margin;
	// Grid UI Size
	private int gridSize;
	//counter to count the no of moves
	private int noMoves = 0;
	//max no of moves
	private int maxMoves;
	//name of user
	private String name;
	private boolean gameOver; // true if game over, false otherwise
	
	public GameOfFifteen(int size, int dim, int mar,String name) {
	this.size = size;
	dimension = dim;
	margin = mar;
	this.name = name;
	
	if(size ==3) 
		maxMoves = 50;
	else if(size == 4)
		maxMoves = 150;
	else if(size == 5)
		maxMoves = 250;
	else maxMoves = 400;
	
	// init tiles 
	nbTiles = size * size - 1; // -1 because we don't count blank tile
	tiles = new int[size * size];
	
	// calculate grid size and tile size
	gridSize = (dim - 2 * margin);
	tileSize = gridSize / size;
	
	setPreferredSize(new Dimension(dimension, dimension + margin));
	setBackground(Color.WHITE);
	setForeground(Color.ORANGE);
	setFont(new Font("SansSerif", Font.BOLD, 60));
	
	gameOver = true;
	
	addMouseListener(new MouseAdapter() {
		@Override
		public void mousePressed(MouseEvent e) {
		// used to let users to interact on the grid by clicking
		// it's time to implement interaction with users to move tiles to solve the game !
		if (gameOver) {
			newGame();
		} else {
			// get position of the click
			int ex = e.getX() - margin;
			int ey = e.getY() - margin;
			
			// click in the grid ?
			if (ex < 0 || ex > gridSize  || ey < 0  || ey > gridSize)
			return;
			
			// get position in the grid
			int c1 = ex / tileSize;
			int r1 = ey / tileSize;
			
			// get position of the blank cell
			int c2 = blankPos % size;
			int r2 = blankPos / size;
			
			// we convert in the 1D coord 
			int clickPos = r1 * size + c1;
			
			int dir = 0;
			
			// we search direction for multiple tile moves at once
			if (c1 == c2  &&  Math.abs(r1 - r2) > 0)
			dir = (r1 - r2) > 0 ? size : -size;
			else if (r1 == r2 && Math.abs(c1 - c2) > 0)
			dir = (c1 - c2) > 0 ? 1 : -1;
			
			if (dir != 0) {
			// we move tiles in the direction
			do {
				int newBlankPos = blankPos + dir;
				tiles[blankPos] = tiles[newBlankPos];
				blankPos = newBlankPos;
				noMoves++;
			} while(blankPos != clickPos);
			
			tiles[blankPos] = 0;
			}
			
			//check if the no of moves has exceeded
			if(noMoves >= maxMoves) {
				gameOver = true;
			}
			else
				gameOver = isSolved();	// we check if game is solved
		}
		
		// we repaint panel
		repaint();
		}
	});
	
	newGame();
	}
	
	private void newGame() {
	do {
		reset(); // reset in intial state
		shuffle(); // shuffle
	} while(!isSolvable()); // make it until grid be solvable
	
	noMoves = 0;
	gameOver = false;
	}
	
	private void reset() {
	for (int i = 0; i < tiles.length; i++) {
		tiles[i] = (i + 1) % tiles.length;
	}
	
	// we set blank cell at the last
	blankPos = tiles.length - 1;
	}
	
	private void shuffle() {
	// don't include the blank tile in the shuffle, leave in the solved position
	int n = nbTiles;
	
	while (n > 1) {
		int r = RANDOM.nextInt(n--);
		int tmp = tiles[r];
		tiles[r] = tiles[n];
		tiles[n] = tmp;
	}
	}
	
	// Only half permutations o the puzzle are solvable
	// Whenever a tile is preceded by a tile with higher value it counts
	// as an inversion. In our case, with the blank tile in the solved position,
	// the number of inversions must be even for the puzzle to be solvable
	private boolean isSolvable() {
	int countInversions = 0;
	
	for (int i = 0; i < nbTiles; i++) {
		for (int j = 0; j < i; j++) {
		if (tiles[j] > tiles[i])
			countInversions++;
		}
	}
	
	return countInversions % 2 == 0;
	}
	
	private boolean isSolved() {
	if (tiles[tiles.length - 1] != 0) // if blank tile is not in the solved position ==> not solved
		return false;
	
	for (int i = nbTiles - 1; i >= 0; i--) {
		if (tiles[i] != i + 1)
		return false;      
	}
	
	return true;
	}
	
	private void drawGrid(Graphics2D g) {
	for (int i = 0; i < tiles.length; i++) {
		// we convert 1D coords to 2D coords given the size of the 2D Array
		int r = i / size;
		int c = i % size;
		// we convert in coords on the UI
		int x = margin + c * tileSize;
		int y = margin + r * tileSize;
		
		// check special case for blank tile
		if(tiles[i] == 0) {
		if (gameOver && (noMoves < maxMoves)) {
			g.setColor(FOREGROUND_COLOR);
			drawCenteredString(g, "\u2713", x, y);
		}
		
		continue;
		}
		
		// for other tiles
		g.setColor(getForeground());
		g.fillRoundRect(x, y, tileSize, tileSize, 25, 25);
		g.setColor(Color.BLACK);
		g.drawRoundRect(x, y, tileSize, tileSize, 25, 25);
		g.setColor(Color.WHITE);
		
		drawCenteredString(g, String.valueOf(tiles[i]), x , y);
	}
	}
	
	private void drawStartMessage(Graphics2D g) {
	if (gameOver && (noMoves >= maxMoves)) {
		g.setFont(getFont().deriveFont(Font.BOLD, 18));
		g.setColor(FOREGROUND_COLOR);
		String s = "You Lose: Click to start new game";
		g.drawString(s, (getWidth() - g.getFontMetrics().stringWidth(s)) / 2,
			getHeight() - margin);
		g.drawString(name, (getWidth() - g.getFontMetrics().stringWidth(name)) / 2,
				getHeight() - margin);
		
	}
	
	else if(gameOver) {
		g.setFont(getFont().deriveFont(Font.BOLD, 18));
		g.setColor(FOREGROUND_COLOR);
		String s = "Click to start new game";
		g.drawString(s, (getWidth() - g.getFontMetrics().stringWidth(s)) / 2,
			getHeight() - margin);
		g.drawString(name, (getWidth() - g.getFontMetrics().stringWidth(name)) / 2,
					getHeight() - (margin/5));
			
	}
	
	else {
		g.setFont(getFont().deriveFont(Font.BOLD, 18));
		g.setColor(FOREGROUND_COLOR);
		String moves = String.valueOf(maxMoves-noMoves);
		String s = "Moves Left :" + moves;
		g.drawString(s, (getWidth() - g.getFontMetrics().stringWidth(s)) / 2,
			getHeight() - margin);
		g.drawString(name, (getWidth() - g.getFontMetrics().stringWidth(name)) / 2,
				getHeight() - (margin/5));
		
	}
	}
	
	private void drawCenteredString(Graphics2D g, String s, int x, int y) {
	// center string s for the given tile (x,y)
	FontMetrics fm = g.getFontMetrics();
	int asc = fm.getAscent();
	int desc = fm.getDescent();
	g.drawString(s,  x + (tileSize - fm.stringWidth(s)) / 2, 
		y + (asc + (tileSize - (asc + desc)) / 2));
	}
	
	@Override
	protected void paintComponent(Graphics g) {
	super.paintComponent(g);
	Graphics2D g2D = (Graphics2D) g;
	g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	drawGrid(g2D);
	drawStartMessage(g2D);
	}
}