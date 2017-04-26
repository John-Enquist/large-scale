import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
/*
 * This class is in charge of initializing an applet that takes the players name and records it.
 * 
 * written by John Enquist 3/8/17
 */
public class PlayerInfo implements ActionListener{
	   JPanel textPanel, panelForTextFields, completionPanel;
	   JLabel titleLabel, usernameLabel, passwordLabel, userLabel, passLabel;
	   JTextField usernameField, loginField;
	   JButton loginButton;
	   Gameboard aBoard;
	   
	   private String playerName;
	
	   public PlayerInfo(Gameboard gbd){
		   aBoard = gbd;
		   this.createAndShowGUI();
	   }
	   
	   
	   public JPanel createContentPane (){

	        // We create a bottom JPanel to place everything on.
	        JPanel playerInfoGUI = new JPanel();
	        playerInfoGUI.setLayout(null);

	        titleLabel = new JLabel("PlayerInfo");
	        titleLabel.setLocation(0,0);
	        titleLabel.setSize(290, 30);
	        titleLabel.setHorizontalAlignment(0);
	        playerInfoGUI.add(titleLabel);

	        // Creation of a Panel to contain the JLabels
	        textPanel = new JPanel();
	        textPanel.setLayout(null);
	        textPanel.setLocation(10, 35);
	        textPanel.setSize(70, 80);
	        playerInfoGUI.add(textPanel);

	        // Username Label
	        usernameLabel = new JLabel("Name:");
	        usernameLabel.setLocation(0, 0);
	        usernameLabel.setSize(70, 40);
	        usernameLabel.setHorizontalAlignment(4);
	        textPanel.add(usernameLabel);


	        // TextFields Panel Container
	        panelForTextFields = new JPanel();
	        panelForTextFields.setLayout(null);
	        panelForTextFields.setLocation(110, 40);
	        panelForTextFields.setSize(100, 70);
	        playerInfoGUI.add(panelForTextFields);

	        // Username Textfield
	        usernameField = new JTextField(8);
	        usernameField.setLocation(0, 0);
	        usernameField.setSize(100, 30);
	        panelForTextFields.add(usernameField);


	        // Creation of a Panel to contain the completion JLabels
	        completionPanel = new JPanel();
	        completionPanel.setLayout(null);
	        completionPanel.setLocation(240, 35);
	        completionPanel.setSize(70, 80);
	        playerInfoGUI.add(completionPanel);


	        // Button for Logging in
	        loginButton = new JButton("Login");
	        loginButton.setLocation(130, 120);
	        loginButton.setSize(80, 30);
	        loginButton.addActionListener(this);
	        playerInfoGUI.add(loginButton);

	        playerInfoGUI.setOpaque(true);    
	        return playerInfoGUI;
	    }

	    // With this action performed, we simply check to see if the username and 
	    // password match "Bob" as the username and "Robert" as the password.
	    // If they do, we set the labels ajacent to them to "Correct!" and color
	    // them green.
	    // At the end, we check if both labels are green. If they are, we set the
	    // screen to be 'Logging In'.

	   public String getName(){
		   return playerName;
	   }
	    public void actionPerformed(ActionEvent e) {

	        if(e.getSource() == loginButton){
	            if(usernameField.getText().trim().length() <= 2){
	            	return;
	            }
	           titleLabel.setText("Thank you. You may now close this panel and begin playing");
	           playerName = usernameField.getText().substring(0, 3);
	           loginButton.setEnabled(false);
	        }
	    }


	    public void createAndShowGUI() {
	    	 SwingUtilities.invokeLater(new Runnable() {
		            public void run() {
					        JFrame.setDefaultLookAndFeelDecorated(true);
					        JFrame frame = new JFrame("Who are you? who who, who who");
					        frame.setContentPane(createContentPane());
					        frame.setSize(310, 200);
					        frame.setVisible(true);
					
		            	}
		        	});
	    }
	    
}
