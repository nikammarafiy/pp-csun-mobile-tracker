package edu;
import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import net.AddressClass;

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
	  String s1 ="";
	  String s2 ="";
	  String street;
	  String city;
	  String state;
	  String zip;
	  String street2;
	  String city2;
	  String state2;
	  String zip2;
	  
    if (e.target == clear)
    {
        inputLine1.setText("");
        inputLine2.setText("");
    }
    if (e.target == enter)
    {
        s1 = inputLine1.getText();
        s2 = inputLine2.getText();
    }
    
    String[] piecesofdata1;
    String[] piecesofdata2;
    
    piecesofdata1 = s1.split(",");
    piecesofdata2 = s2.split(",");
    
    street = piecesofdata1[0];
	city = piecesofdata1[1];
	state = piecesofdata1[2];
	zip = piecesofdata1[3];
	
	street2 = piecesofdata2[0];
	city2 = piecesofdata2[1];
	state2 = piecesofdata2[2];
	zip2 = piecesofdata2[3];
    
   
    AddressClass add1 = new AddressClass();
    add1.setStartAddress(street, city, state, zip);
    add1.setEndAddress(street2, city2, state2, zip2);
    
    return true;
   }
  

    public void actionPerformed(ActionEvent e) 
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }




}