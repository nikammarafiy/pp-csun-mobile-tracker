package io;
import edu.GEOPoint;
import java.io.File;
import java.io.FilenameFilter;
import java.util.*;
import java.io.*;


public class Reader
{
    File[] listOfFiles;
    ArrayList <GEOPoint> listofpoints;
    
    public Reader()
    {
      File folder = new File("./routes");
      listOfFiles = folder.listFiles(new FilenameFilter() {
           public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".txt");
                }
           }
        );
      
      
      listofpoints = new ArrayList<GEOPoint>();
    }


 public File[] printFiles()
    {
     
    for (int i = 0; i < listOfFiles.length; i++)
    {
      if (listOfFiles[i].isFile())
      {
        System.out.println(listOfFiles[i].getName());
      }
      else if (listOfFiles[i].isDirectory())
      {
        
      }
    }
      return listOfFiles;
   }

 public ArrayList<GEOPoint> getinitialinput(String filename) throws Exception
 {
    String line;
    String[] piecesofdata;
    Scanner finput = new Scanner(new File(filename));

 while (finput.hasNext())
 {
      line = finput.nextLine();
      piecesofdata = line.split(",");
      
      listofpoints.add(new GEOPoint(piecesofdata[0], piecesofdata[1]));
      
 }

   return listofpoints;
 
 }

  }
