/*
 * Project 1
*/

import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.Object.*;
import javax.swing.*;
import java.io.*;
import java.util.*;
import javax.imageio.ImageIO;


public class readImage
{
  int imageCount = 1;
  double intensityBins [] = new double[26];
  double intensityMatrix [][] = new double[100][26];
  double colorCodeBins [] = new double [64];
  double colorCodeMatrix [][] = new double[100][64];

  /*Each image is retrieved from the file.  The height and width are found for the image and the getIntensity and
   * getColorCode methods are called.
  */
  public readImage()
  {
    while(imageCount < 101){
      try
      {
        // the line that reads the image file
        while (imageCount <= 100) {
          BufferedImage image = ImageIO.read(new File("images/" + imageCount + ".jpg"));
          int height = image.getHeight();
          int width = image.getWidth();
          getIntensity(image, height, width);
          getColorCode(image, height, width);
          imageCount++;
        }
      } 
      catch (IOException e)
      {
        System.out.println("Error occurred when reading the file.");
      }
    }
    
    writeIntensity();
    writeColorCode();
    
  }
  
  //intensity method 

  public void getIntensity(BufferedImage image, int height, int width){
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
          int pixelColor = image.getRGB(j , i);
          Color c = new Color(pixelColor);
          double intensity = (0.299 * c.getRed()) + (0.587 * c.getGreen()) + (0.114 * c.getBlue());

          intensityBins[(int)intensity / 10]++;
          intensityMatrix[imageCount - 1][(int)intensity / 10]++;
        }
      }
  }
  
  //color code method
  public void getColorCode(BufferedImage image, int height, int width){
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
          int pixelColor = image.getRGB(j , i);
          Color c = new Color(pixelColor);
          int red = c.getRed();
          int green = c.getGreen();
          int blue = c.getBlue();
          String binaryRed = String.format("%02d", Integer.parseInt(Integer.toBinaryString(red)));
          String binaryGreen = String.format("%02d", Integer.parseInt(Integer.toBinaryString(green)));
          String binaryBlue = String.format("%02d", Integer.parseInt(Integer.toBinaryString(blue)));
          binaryRed = binaryRed.substring(0,2);
          binaryGreen = binaryGreen.substring(0,2);
          binaryBlue = binaryBlue.substring(0,2);
          String cc = binaryRed + binaryGreen + binaryBlue;
          int bin = convertBinary(cc);
          colorCodeBins[bin]++;
          colorCodeMatrix[imageCount - 1][bin]++;
        }
      }
  }

  public int convertBinary(String binary) {
      int num = 0;
      for (int i = 0; i < binary.length(); i++) {
          char c = binary.charAt(i);
              if (c == '1') {
                  num += Math.pow(2, binary.length() - 1 - i);
              }
      }
      return num;
  }
  ///////////////////////////////////////////////
  //add other functions you think are necessary//
  ///////////////////////////////////////////////
  
  //This method writes the contents of the colorCode matrix to a file named colorCodes.txt.
  public void writeColorCode(){
    try {
      FileWriter codeWrite = new FileWriter("colorCode.txt");

      for (int i = 0; i < colorCodeMatrix.length; i++) {
        for (int j = 0; j < colorCodeMatrix[i].length; j++) {
          codeWrite.write(Integer.toString((int)colorCodeMatrix[i][j]));
          codeWrite.write(" ");
        }
          codeWrite.write('\n');
      }
    } catch (IOException e) {
      System.out.println("Error occurred when write the colorCode file.");
    }
  }
  
  //This method writes the contents of the intensity matrix to a file called intensity.txt
  public void writeIntensity(){
    try {
      FileWriter intensityWrite = new FileWriter("intensity.txt");

      for (int i = 0; i < intensityMatrix.length; i++) {
        for (int j = 0; j < intensityMatrix[i].length; j++) {
            intensityWrite.write(Integer.toString((int)intensityMatrix[i][j]));
            intensityWrite.write(" ");
        }
        intensityWrite.write('\n');
      }
    } catch (IOException e) {
      System.out.println("Error occurred when write the intensity file.");
    }
  }
  
  public static void main(String[] args)
  {
    new readImage();
  }

}
