package edu;
import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class MobileTracker extends Applet implements ActionListener
{

    TextField inputLine1 = new TextField(50);
    TextField inputLine2 = new TextField(50);
    JLabel label1 = new JLabel("Enter a Start Address: (eg. Street, City, State, Zip) ");
    JLabel label2 = new JLabel("Enter a Finish Address: (eg. Street, City, State, Zip) ");

    Button clear;
    Button enter;

   public void init ()
   {
       clear = new Button("Clear");
       add(clear);


      enter = new Button("Submit");
      add(enter);
   }


    public MobileTracker()
    {
        add(label1);
        add(inputLine1);
        add(label2);
        add(inputLine2);
        inputLine1.addActionListener(this);
        inputLine2.addActionListener(this);


    }

  public boolean action (Event e, Object args)
  {
    if (e.target == clear)
    {
        inputLine1.setText("");
        inputLine2.setText("");
    }
    if (e.target == enter)
    {
        String s1 = inputLine1.getText();
        String s2 = inputLine2.getText();
    }
    return true;
   }
  

    public void actionPerformed(ActionEvent e) 
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }




}