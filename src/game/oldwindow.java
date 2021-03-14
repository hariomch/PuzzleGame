package game;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
 
public class oldwindow extends JFrame 
{
	private static final Color FOREGROUND_COLOR = new Color(239, 83, 80);
	private JPanel contentPane;
	private javax.swing.JTextField textName;
	private javax.swing.JTextField textName1;
	public String name;
	String lvl = null;
    public int n = 4;
	/**
	 * Launch the application.
	 */
	
	public static void main(String[] args)
	{
		/* It posts an event (Runnable)at the end of Swings event list and is
		processed after all other GUI events are processed.*/
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try 
				{
					oldwindow frame = new oldwindow();
					frame.setVisible(true);					
				} 
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Create the frame.
	 */
	public oldwindow() //constructor 
	{
		setTitle("User info");
		//set default close operation
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		//set bounds of the frame
		setBounds(100, 100, 450, 300);
		
		contentPane = new JPanel();
		//set border
	    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setOpaque(true);
		contentPane.setBackground(Color.WHITE);
		
		//set ContentPane
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textName = new javax.swing.JTextField();
		textName.setBounds(207, 90, 160, 19);
		contentPane.add(textName);
		
		textName1 = new javax.swing.JTextField();
		textName1.setBounds(207, 150, 160, 19);
		contentPane.add(textName1);
		
		//create object of JButton and set label on it
		JButton btnNewFrame = new JButton("START");
		//add actionListener
		btnNewFrame.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				//call the object of NewWindow and set visible true
				name  = textName.getText();
				lvl =  textName1.getText();
		        if(!lvl.equals("")) {
		        	n = Integer.valueOf(lvl);
		        }
		       	
		        Newwindow fame = new Newwindow(n,name);
		       	fame.setVisible(true);
		       	//set default close operation
		       	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		    }
		});
		//set font of the Button
		btnNewFrame.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 12));
		//set bounds of the Button
		btnNewFrame.setBounds(180, 195, 78, 25);
		//add Button into contentPane
		contentPane.add(btnNewFrame);
		
		//set Label in the frame
		JLabel lblThisIsOld = new JLabel("ENTER YOUR NAME:");
	
		lblThisIsOld.setForeground(FOREGROUND_COLOR);
		JLabel lblThisIsnew  = new JLabel("LEVEL:");
		
		lblThisIsnew.setForeground(FOREGROUND_COLOR);
		//set font of that label
		lblThisIsOld.setFont(new Font("LATO", Font.BOLD | Font.ITALIC, 10));
		lblThisIsnew.setFont(new Font("LATO", Font.BOLD | Font.ITALIC, 10));
		//set bound of the label
		lblThisIsOld.setBounds(97, 82, 160, 39);
		lblThisIsnew.setBounds(97,132 , 180, 59);
		//add label to the contentPane
		contentPane.add(lblThisIsOld);
		contentPane.add(lblThisIsnew);
		
	}
}
