
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.Writer;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;


public class MakeFile
{
       String data = "";
       Writer writer = null;
       
    public MakeFile()
    {
       String time = "";
       Calendar cal = Calendar.getInstance();

       time = cal.getTime().toString();
       time = time.replace(" ", "_");
       time = time.replace(":", "");

       System.out.println(time);

       try
       {
           File file = new File(time + ".txt");
           writer = new BufferedWriter(new FileWriter(file));
       }
       catch (FileNotFoundException e)
       {
           e.printStackTrace();
       }
       catch (IOException e)
       {
            e.printStackTrace();
       }
      
    }
    
    public void input(double x, double y, String d)
    {
       data = x + "," + y + "," + d + "\n";

       try
       {
       writer.write(data);
       }
       catch (FileNotFoundException e)
       {
           e.printStackTrace();
       }
       catch (IOException e)
       {
            e.printStackTrace();
       }
     }

    public void closefile()
    {
            try
            {
               if (writer != null)
               {
                    writer.close();
               }
            }
            catch (IOException e)
            {
               e.printStackTrace();
            }
    }

}



