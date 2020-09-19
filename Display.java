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

public class Display extends JPanel implements ActionListener, KeyListener
{
    private Image background;

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
            throw new RuntimeException("probably file not found, if not try getting latest one from github");
        }
        
        background = rawBackground.getScaledInstance(WIDTH, HEIGHT, Image.SCALE_DEFAULT);;
 
        
        addKeyListener(this);

        setFocusable(true);

        setFocusTraversalKeysEnabled(false);

        repaint();
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

    public void keyTyped(KeyEvent keyEvent)
    {

    }

    public void keyPressed(KeyEvent e)
    {

    }

    public void keyReleased(KeyEvent keyEvent)
    {

    }
}
