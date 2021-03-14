package game;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Newwindow extends JFrame 
{
	private JPanel contentPane;
	//private int level;
	//private String name;
	/**
	 * Launch the application.
	 */
	
	public Newwindow (int level, String name) {
		//this.level = level;
		//this.name = name;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("NUMBER GAME");
		setResizable(false);
		add(new GameOfFifteen(level, 550, 30,name), BorderLayout.CENTER);
		pack();
		// center on the screen
		setLocationRelativeTo(null);
	}
	
	public static void main(String[] args) {
		/* It posts an event (Runnable)at the end of Swings event list and is
		processed after all other GUI events are processed.*/
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Newwindow frame = new Newwindow();
					frame.setVisible(true);					
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Newwindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("NUMDER GAME");
		setResizable(false);
		add(new GameOfFifteen(4, 550, 30, null), BorderLayout.CENTER);
		pack();
		// center on the screen
		setLocationRelativeTo(null);
	}
}