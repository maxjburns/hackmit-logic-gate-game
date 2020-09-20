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
    private int[][] map = new int[60][30]; //60x30
    private int numInputs, gridSize;
    private boolean[] truthTable;
    private Location[] gateLocs;
    private ArrayList<Monster> monsters;
    private ArrayList<Gate> gates;
    private int width, height;
    
    public Display(int WIDTH, int HEIGHT) {
        JFrame frame = new JFrame();

        gridSize = WIDTH/map.length;
        //HEIGHT = gridSize*map[0].length;
        HEIGHT = WIDTH/2+100;
        frame.setPreferredSize(new Dimension(WIDTH,HEIGHT));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.getContentPane().add();
        frame.add(this);
        frame.setVisible(true);

        BufferedImage rawBackground = null;
        try {
            rawBackground = ImageIO.read(getClass().getResource("BackgroundHack.png"));
        }
        catch (Exception e)
        {
            throw new RuntimeException("Image not found");
        }

        background = rawBackground.getScaledInstance(WIDTH, HEIGHT-100, Image.SCALE_DEFAULT);
        monsters = new ArrayList<Monster>();
        gates = new ArrayList<Gate>();
        
        height = HEIGHT;
        width = WIDTH;
        
        addMouseListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        repaint();
    }

    public void loadLevel(int numInputs, boolean[] truthTable, Location[] gateLocs)
    {
        map = new int[60][30];
        this.numInputs = numInputs;
        this.truthTable = truthTable;
        this.gateLocs = gateLocs;
        for (Location gate: gateLocs)
        {
            for (int i = 0; i < 3; i++)
            {
                for (int j = 0; j < 3; j++)
                {
                    map[gate.getX()-i][gate.getY()-j] = 2;
                }
            }
        }

        Location[] nodes = new Location[numInputs+gateLocs.length];
        for (int i= 0; i < numInputs; i++)
        {
            nodes[i] = new Location(-1, map[0].length*(i+1)/(numInputs+1));
        }
        for (int i = 0; i < gateLocs.length; i++)
        {
            nodes[i+numInputs] = gateLocs[i];
        }
        boolean[] connected = new boolean[nodes.length];
        for (int i = nodes.length-1; i>=numInputs; i--)
        {
            int j = i-1;
            while (connected[j]){
                j--;
            }
            connected[j] = true;
            //System.out.println(j+" "+i);
            drawLine(new Location(nodes[j].getX()+1, nodes[j].getY()-1), new Location(nodes[i].getX()-3,nodes[i].getY()));
            while (connected[j]){
                j--;
            }
            connected[j] = true;
            //System.out.println(j+" "+i);
            drawLine(new Location(nodes[j].getX()+1, nodes[j].getY()-1), new Location(nodes[i].getX()-3,nodes[i].getY()-2));

        }
        drawLine(new Location(nodes[nodes.length-1].getX()+1, nodes[nodes.length-1].getY()-1), new Location(59, nodes[nodes.length-1].getY()-1));

        /*for (int y = 0; y < map[0].length; y++)
        {
            for (int x= 0; x < map.length; x++)
            {
                if (map[x][y] ==0)
                    System.out.print(". ");
                else
                    System.out.print(map[x][y] + " ");
            }
            System.out.println();
        }*/
    }

    public void drawLine(Location loc1, Location loc2)
    {
        int halfX = (loc2.getX()+loc1.getX())/2;
        if (loc2.getX()<loc1.getX())
        {
            Location temp = loc2;
            loc2 = loc1;
            loc1 = temp;
        }
        //System.out.println(loc1+" "+loc2);
        for (int i = loc1.getX(); i <= loc2.getX(); i++)
        {
            if (i < halfX)
            {   
                map[i][loc1.getY()]=1;
            }
            if (i == halfX)
            {
                for (int j = Math.min(loc1.getY(), loc2.getY()); j <=Math.max(loc1.getY(), loc2.getY()); j++)
                {
                    map[i][j] = 1;
                }
            }
            if (i > halfX)
            {
                map[i][loc2.getY()]=1;
            }
        }
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

        for (int x= 0; x < map.length; x++)
        {
            for (int y = 0; y < map[0].length; y++)
            {
                int dx = (x+1)*width/60 -x*width/60;
                int dy = (y+1)*height/30 -y*height/30;
                if (map[x][y]==2)
                {
                    g.setColor(new Color(170,170,170,100));
                    g.fillRect(x*width/60,y*height/30, dx, dy);
                }
                if (map[x][y]==1)
                {
                    g.setColor(new Color(237,214,80));
                    g.fillRect(x*width/60,y*height/30, dx,dy);
                }
            }
        }
        try {
            for (Monster m : monsters)
            {
                g.drawImage(m.getSprite().getImage(), m.getSprite().getX(), m.getSprite().getY(), this);
            }

            for (Gate gate: gates)
            {
                g.drawImage(gate.getSprite().getImage(), gate.getSprite().getX(), gate.getSprite().getY(), this);
            }
        }
        catch (Exception e)
        {

        }
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
      
      JFrame f = new JFrame(); //creates jframe f
      
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); //this is your screen size
      
      f.setUndecorated(true); //removes the surrounding border
      
      ImageIcon image = new ImageIcon("filtOR-500.png"); //imports the image
      
      JLabel lbl = new JLabel(image); //puts the image into a jlabel
      
      f.getContentPane().add(lbl); //puts label inside the jframe
      
      f.setSize(500, 500); //gets h and w of image and sets jframe to the size
      
      int xd = (screenSize.width - f.getSize().width)/2; //These two lines are the dimensions
      int yd = (screenSize.height - f.getSize().height)/2;//of the center of the screen
      
      f.setLocation(xd, yd); //sets the location of the jframe
      f.setVisible(true); //makes the jframe visible
        
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
