import javax.swing.*;
import javax.swing.text.*;

import java.awt.*;              //for layout managers and more
import java.awt.event.*;        //for action events

import java.net.URL;
import java.io.IOException;

public class FrontEnd {
	public FrontEnd() throws BadLocationException{
		UIManager.put("swing.boldMetal", Boolean.FALSE);
		
		//Create and set up the window.
        JFrame frame = new JFrame("Sunappilly Chat Client");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add content to the window.
        frame.add(new MyPanel());

        //Display the window.
        frame.pack();
        frame.setVisible(true);

	}
}
