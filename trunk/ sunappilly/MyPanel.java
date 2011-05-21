import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;


public class MyPanel extends JPanel implements ActionListener{
	private static final String SEND_MESSAGE = "SEND_MESSAGE_BUTTON_CLICK";
	private static final String LOGIN_CLICK = "LOGIN_ATTEMPT";
	private static final String LOGOUT_CLICK = "LOGOUT_CLICK";
	private JPanel loginPanel;
	private JPanel welcomePanel;
	private JTextPane messagePane;
	private JTextPane chatLog;
	private JTextPane nameList;
	private JButton sendButton;
	private JTextField userNameTextField;
	
	private String userName;
	
	public MyPanel() throws BadLocationException{
		setLayout(new BorderLayout());
		
		loginPanel = new JPanel();
		loginPanel.setLayout(new BorderLayout());
		loginPanel.add(new JLabel("Name: "), BorderLayout.WEST);
		userNameTextField = new JTextField();
		loginPanel.add(userNameTextField, BorderLayout.CENTER);
		
		JButton loginButton = new JButton("Login");
		loginButton.setActionCommand(LOGIN_CLICK);
		loginButton.addActionListener(this);
		
		loginPanel.add(loginButton, BorderLayout.EAST);
		
		//loginPanel.setMaximumSize(new Dimension(300, 26));
		//add(loginPanel, BorderLayout.NORTH);
		
		
		welcomePanel = new JPanel();
		welcomePanel = new JPanel();
		welcomePanel.setLayout(new BorderLayout());
		welcomePanel.add(new JLabel(), BorderLayout.WEST);
		JButton logoutButton = new JButton("Logout");
		
		logoutButton.setActionCommand(LOGOUT_CLICK);
		logoutButton.addActionListener(this);
		
		welcomePanel.add(logoutButton, BorderLayout.EAST);
		welcomePanel.setVisible(false);
		//add(welcomePanel, BorderLayout.NORTH);		
		
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BorderLayout());
		topPanel.add(loginPanel, BorderLayout.NORTH);
		topPanel.add(welcomePanel, BorderLayout.SOUTH);
		
		messagePane = createTextBox(true);
		chatLog = createTextBox(false);
		
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
				createPaneWithText(chatLog),
				createPaneWithText(messagePane));
		
		//JPanel left = new JPanel();
		//left.setLayout(new BorderLayout());
		//left.add(splitPane, BorderLayout.NORTH);
		
		sendButton = new JButton("Send"); 
		
		sendButton.setActionCommand(SEND_MESSAGE);
		sendButton.addActionListener(this);
		
		//left.add(sendButton, BorderLayout.SOUTH);
		JSplitPane leftSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
				splitPane, sendButton);
		sendButton.setMaximumSize(new Dimension(100, 30));
		leftSplit.setEnabled(false);
		leftSplit.setResizeWeight(1);
		nameList = createTextBox(false);
		JSplitPane horizontalSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				leftSplit, createPaneWithText(nameList));
		
		
		//add(horizontalSplit, BorderLayout.SOUTH);
		
		JSplitPane verticalSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
				topPanel, horizontalSplit);
		//verticalSplit.getComponents()
		verticalSplit.setEnabled(false);
		add(verticalSplit);
		System.out.println(loginPanel.getHeight());
		disableControls();
	}
	
	private JTextPane createTextBox(boolean editable) {
		JTextPane textPane = new JTextPane();
        StyledDocument doc = textPane.getStyledDocument();
        addStylesToDocument(doc);
        //ADD STYLES TODO
        textPane.setEditable(editable);
		
        return textPane;
	}
	
    private JScrollPane createPaneWithText(JTextPane textPane) {
        JScrollPane paneScrollPane = new JScrollPane(textPane);
        paneScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        paneScrollPane.setPreferredSize(new Dimension(250, 155));
        paneScrollPane.setMinimumSize(new Dimension(10, 100));
        
        return paneScrollPane;
    }
    
    private void disableControls() throws BadLocationException{
    	StyledDocument doc1 = messagePane.getStyledDocument();
    	doc1.remove(0, doc1.getLength());
    	
    	StyledDocument doc2 = chatLog.getStyledDocument();
    	doc2.remove(0, doc2.getLength());
    	
    	StyledDocument doc3 = nameList.getStyledDocument();
    	doc3.remove(0, doc3.getLength());
    	
    	messagePane.setEnabled(false);
    	chatLog.setEnabled(false);
    	nameList.setEnabled(false);
    	sendButton.setEnabled(false);
    }
	
    private void enableContols() {
    	messagePane.setEnabled(true);
    	chatLog.setEnabled(true);
    	nameList.setEnabled(true);
    	sendButton.setEnabled(true);
    }
    
    protected void addStylesToDocument(StyledDocument doc) {
        //Initialize some styles.
        Style def = StyleContext.getDefaultStyleContext().
                        getStyle(StyleContext.DEFAULT_STYLE);

        Style regular = doc.addStyle("regular", def);
        StyleConstants.setFontFamily(def, "SansSerif");

        Style s = doc.addStyle("italic", regular);
        StyleConstants.setItalic(s, true);

        s = doc.addStyle("bold", regular);
        StyleConstants.setBold(s, true);

        s = doc.addStyle("small", regular);
        StyleConstants.setFontSize(s, 10);

        s = doc.addStyle("large", regular);
        StyleConstants.setFontSize(s, 16);
    }

	
	@Override
	public void actionPerformed(ActionEvent e) {
		if( SEND_MESSAGE.equals(e.getActionCommand()) ){
			String message = messagePane.getText().trim();
			if(!message.equals("")){
				//TODO send message
				messagePane.setText("");
				StyledDocument doc2 = chatLog.getStyledDocument();
				try {
					doc2.insertString(doc2.getLength(), userName+": ", doc2.getStyle("bold"));
					doc2.insertString(doc2.getLength(), message+"\n", doc2.getStyle("regular"));
				} catch (BadLocationException e1) {
					e1.printStackTrace();
				}
			}
		}else if( LOGIN_CLICK.equals(e.getActionCommand()) ){
			userName = userNameTextField.getText().trim();
			if(!userName.equals("")){
				enableContols();
				loginPanel.setVisible(false);
				welcomePanel.setVisible(true);
				
				((JLabel)welcomePanel.getComponent(0)).setText("Welcome, "+userName);
			}
		}else if(LOGOUT_CLICK.equals(e.getActionCommand())){
			welcomePanel.setVisible(false);
			loginPanel.setVisible(true);
			try {
				disableControls();
			} catch (BadLocationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}

}
