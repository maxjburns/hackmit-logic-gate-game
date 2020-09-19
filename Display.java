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

public class Display
{
    private Image img;
    private static int WIDTH, HEIGHT;
    
    public static void main(String[] args) throws IOException
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        WIDTH = (int) (screenSize.getWidth()*3/4);
        HEIGHT = (int) (screenSize.getHeight()*3/4);
        
        
    }

    public Display() throws IOException {
        img = ImageIO.read(getClass().getResource("Background.png"));
        
    }
}
