import javax.imageio.ImageIO;
import javax.sound.midi.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.Font;
import java.io.IOException;
import javax.swing.Timer;
import java.util.*;
import java.io.*;

public class Display extends JPanel implements ActionListener, MouseListener
{
    private Image background;
    private boolean[][] map; //30x40
    private int height;
    private int width;
    
    
    public Display(int WIDTH, int HEIGHT) {
        JFrame frame = new JFrame();

        frame.setPreferredSize(new Dimension(WIDTH,HEIGHT));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.getContentPane().add();
        frame.add(this);
        frame.setVisible(true);
        System.out.println("here2");

        BufferedImage rawBackground = null;
        try {
            rawBackground = ImageIO.read(getClass().getResource("BackgroundHack.png"));
        }
        catch (Exception e)
        {
            throw new RuntimeException("Image not found");
        }

        background = rawBackground.getScaledInstance(WIDTH, HEIGHT, Image.SCALE_DEFAULT);
        height = HEIGHT;
        width = WIDTH;

        addMouseListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        repaint();
    }

    public void loadLevel(int numInputs, boolean[] truthTable, Location[] gateLocs)
    {

    }

    public void paintComponent(Graphics g)
    {
        Graphics2D g2d = (Graphics2D) g;

        super.paintComponent(g);
        //g.setColor(new Color(0,0,0));
        //g.drawLine(0,0,100,100);
        //System.out.println("here");
        g.drawImage(background, 0, 0, this); //paints background
        // System.out.println("here");
    }

    public void actionPerformed(ActionEvent actionEvent)
    {
        //not implemented
    }

    public void mousePressed(MouseEvent e) {
        
    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    public void mouseClicked(MouseEvent e) 
    {
      int x = e.getX();
      int y = e.getY();
      //AND, OR, NAND, NOR
      if (y > height - 100)
      {
        if (x < width/4) //AND
        {
        }  
        else if (x < width/2) //OR
        {
        }
        else if (x < 3*width/4) //NAND
        {
        }  
        else //NOR
        {
        }  
      }
      
    }
}
