package io;

import java.util.Scanner;
import java.io.*;
import java.util.Date;


public class Tracker
{
  static double longitude = 78.090998;
  static double latitude = 9080.00909;
  static String date = "23.1.90";

public static void main(String[] args) throws Exception
   {

    MakeFile file = new MakeFile();

    file.input(longitude, latitude, date);

    file.closefile();
 
    }




}
