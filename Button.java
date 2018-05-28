/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coursework;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenuItem;

/**
 *
 * @author waseem
 */
public class Button {

    private Object calledBy;

    Button() {

    }

    Button(ActionListener call) {
        calledBy = call;
    }

    protected JMenuItem makeMenuItem(String txt, String actionCommand, String toolTipText, Font fnt) {

        JMenuItem mnuItem = new JMenuItem();
        mnuItem.setText(txt);
        mnuItem.setActionCommand(actionCommand);
        mnuItem.setToolTipText(toolTipText);
        mnuItem.setFont(fnt);
        mnuItem.addActionListener((ActionListener) calledBy);

        return mnuItem;

    }

    protected JButton makeButton(String imageName, String actionCommand, String toolTipText, String altText, Font fnt) {
        // create and initialize the button.

        // button object created
        JButton button = new JButton();
        button.setToolTipText(toolTipText); // tooltip added
        button.setActionCommand(actionCommand); 
        button.addActionListener((ActionListener) calledBy);
        button.setText(altText);
        button.setFont(fnt);
        // look for the image.
        String imgLocation = System.getProperty("user.dir")
                + "/icons/"
                + imageName
                + ".png";

        File fyle = new File(imgLocation);
        if (fyle.exists() && !fyle.isDirectory()) {

            // image found
            Icon img;
            img = new ImageIcon(imgLocation);
            button.setIcon(img);
        } else {
            // image not found

            button.setText(altText);
            System.err.println("Resource not found: " + imgLocation);
        }

        return button;
    }

}
