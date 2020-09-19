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
    
    public static void main(String[] args) throws IOException
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        WIDTH = (int) (screenSize.getWidth()*3/4);
        HEIGHT = (int) (screenSize.getHeight()*3/4); //scales to size of screen

        Display d = new Display(WIDTH, HEIGHT);
    }

    public Game(Monster[] ms, Monster m)
    {
        monsters = ms;
        output = m;
    }  
}  