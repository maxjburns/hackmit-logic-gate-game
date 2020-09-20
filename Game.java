import javax.imageio.ImageIO;
import javax.sound.midi.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.awt.Font;
import java.io.IOException;
import javax.swing.Timer;
import java.util.*;
import java.io.*;

public class Game
{
    private Monster[] monsters;
    private Monster output;
    private static int WIDTH, HEIGHT;
    private static int level;
    private static Display d;
    
    public static void main(String[] args) throws IOException
    {    
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      WIDTH = (int) (screenSize.getWidth()*3/4);
      HEIGHT = (int) (screenSize.getHeight()*3/4); //scales to size of screen
      
      level = 1;
      d = new Display(WIDTH, HEIGHT);
      new Game("sample.txt");
      Thread t = new MyThread(d);
      t.start(); 
    }

    public Game(Monster[] ms, Monster m)
    {
        monsters = ms;
        output = m;
    }  

    public Game(String levelFile) {
        int numInputs;
        boolean[] truthTable;
        int numGates;
        Location[] gateLocs;
        try {
            BufferedReader br = new BufferedReader(new FileReader(levelFile));
            numInputs = Integer.parseInt(br.readLine());
            
            /*truthTable = new boolean[(int) Math.pow(2, numInputs)];
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int i = 0; i < truthTable.length; i++)
            {
                truthTable[i] = (1 == Integer.parseInt(st.nextToken()));
            }*/
            truthTable = new boolean[numInputs+1];
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int i = 0; i < truthTable.length; i++)
            {
                truthTable[i] = (1 == Integer.parseInt(st.nextToken()));
            }
            
            
            numGates = Integer.parseInt(br.readLine());
            
            gateLocs = new Location[numGates];
            for (int i = 0; i < gateLocs.length; i++)
            {
                st = new StringTokenizer(br.readLine());
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());
                gateLocs[i] = new Location(a, b);
            }
            br.close();
        }
        catch (Exception e)
        {
            throw new RuntimeException("Level file: "+ levelFile + " not found");
        }
        
        d.loadLevel(numInputs, truthTable, gateLocs);
    }
}  