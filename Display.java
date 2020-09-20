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
    public static final boolean FUZZY = true;
    public static final boolean WUZZY = false;
  
    private Image background;
    private int[][] map = new int[60][30]; //60x30
    private int numInputs, gridSize;
    private boolean[] truthTable;
    private Location[] gateLocs;
    private ArrayList<Monster> monsters;
    private ArrayList<Gate> gates;
    private int width, height;
    private Gate selectedGate;
    private int level;
    private boolean checking;
    
    
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
        JButton checkSolution = new JButton("Check Solution");
        checkSolution.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
             // this makes sure the button you are pressing is the button variable
             if(e.getSource() == checkSolution) {
                 checking = true;
              }
            }
        }
        );
        this.add(checkSolution);
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
        int dx = 60;//3*WIDTH/map.length;
        int dy = 60;//3*WIDTH/map.length;
        gates.add(new AndGate(WIDTH/8-dx/2, HEIGHT-50-dy/2, "col-AND-er"));
        gates.get(gates.size()-1).getSprite().resize(dx, dy);
        gates.add(new OrGate(3*WIDTH/8-dx/2, HEIGHT-50-dy/2, "filtOR-500"));
        gates.get(gates.size()-1).getSprite().resize(dx, dy);
        gates.add(new NandGate(5*WIDTH/8-dx/2, HEIGHT-50-dy/2, "NOT_colANDer"));
        gates.get(gates.size()-1).getSprite().resize(dx, dy);
        gates.add(new NorGate(7*WIDTH/8-dx/2, HEIGHT-50-dy/2, "NOT_filtOR-500"));
        gates.get(gates.size()-1).getSprite().resize(dx, dy);
        
        height = HEIGHT;
        width = WIDTH;
        selectedGate = null;

        addMouseListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        repaint();
        
        level = 1;
    }

    public void loadLevel(int numInputs, boolean[] truthTable, Location[] gateLocs)
    {
        selectedGate = null;
        map = new int[60][30];
        this.numInputs = numInputs;
        this.truthTable = truthTable;
        this.gateLocs = gateLocs;
        for (Location gate: gateLocs)
        {
          System.out.println(gate);
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
        for (int i= 0; i < numInputs; i++)
        {
           Monster m = new Monster(truthTable[i], 0, height*(i+1)/(numInputs+1)-50, truthTable[i]?"Fuzzy":"Wuzzy");
           monsters.add(m);
        }
        monsters.add(new Monster(truthTable[numInputs], width-100, gateLocs[gateLocs.length-1].getY()*(height)/map[0].length-50, truthTable[numInputs]?"Fuzzy":"Wuzzy"));
        for (Monster m : monsters)
        {
            m.getSprite().resize(100,100);
        }
        /*try 
        {
          BufferedReader in = new BufferedReader(new FileReader("level" + level + ".txt"));
          level++;
          String line = in.readLine(); //line is the number of inputs
          line = in.readLine(); //line is now the inputs
          for (int i =0; i < line.length()-2; i = i + 2)
          {
            int input = Integer.parseInt(line.substring(i,i+1)); //this is the type of monster, 0 for wuzzy, 1 for fuzzy
            System.out.println("input " + input);
            Location loc = nodes[i];
            System.out.println("location " + loc);
            Monster m;
            if (input == 1)
            {
              m = new Monster(FUZZY, 0, loc.getY(), "Fuzzy");
            }  
            else
            {
              m = new Monster(WUZZY, 0, loc.getY(), "Wuzzy");
            }  
            monsters.add(m);
            m.getSprite().resize(100,100);
          } 
          int output = Integer.parseInt(line.substring(line.length()-1, line.length()));
          Monster outMons;
          if (output == 1)
            {
              outMons = new Monster(FUZZY, width-1, nodes[nodes.length-1].getY(), "Fuzzy");
            }  
            else
            {
              outMons = new Monster(WUZZY, width-1, nodes[nodes.length-1].getY(), "Wuzzy");
            } 
            monsters.add(outMons);
            outMons.getSprite().resize(100,100);
          in.close();
        }
        catch (Exception e)
        {
          System.out.println(e);
        }  */

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
        if (checking)
        {
            
        }
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

        System.out.println("bruh are you here");
        JFrame f = new JFrame(); //creates jframe f

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); //this is your screen size

        //f.setUndecorated(true); //removes the surrounding border
        ImageIcon imageIcon;

        for (int i = 0; i < gates.size(); i++)
        {
            Gate gate = gates.get(i);
            if (y<height - 100 && x>gate.getSprite().getX()-30 &&x<gate.getSprite().getX()+90 && y>gate.getSprite().getY()-30 && y<gate.getSprite().getY()+90)
            {
                gates.remove(i);
            }
        }
        
        if (y > height - 100)
        {
            if (x < width/4) //AND
            {
                selectedGate = new AndGate(width/8-30, height-50-30, "col-AND-er");
                selectedGate.getSprite().resize(60, 60);
                imageIcon = new ImageIcon("AndTruth Table.png"); //imports the image
            }  
            else if (x < width/2) //OR
            {
                selectedGate = new AndGate(3*width/8-30, height-50-30, "filtOR-500");
                selectedGate.getSprite().resize(60, 60);
                imageIcon = new ImageIcon("OrTruthTable.png"); //imports the image
            }
            else if (x < 3*width/4) //NAND
            {
                selectedGate = new AndGate(5*width/8-30, height-50-30, "NOT_colANDer");
                selectedGate.getSprite().resize(60, 60);
                imageIcon = new ImageIcon("NOTcolANDer-TruthTable.png"); //imports the image
            }  
            else //NOR
            {
                selectedGate = new AndGate(7*width/8-30, height-50-30, "NOT_filtOR-500");
                selectedGate.getSprite().resize(60, 60);
                imageIcon = new ImageIcon("NOTfiltOR500-TruthTable.png"); //imports the image
            }  

            Image image = imageIcon.getImage(); // transform it 
            Image newimg = image.getScaledInstance(550, 660,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
            imageIcon = new ImageIcon(newimg);  // transform it back

            JLabel lbl = new JLabel(imageIcon); //puts the image into a jlabel

            f.getContentPane().add(lbl); //puts label inside the jframe

            f.setSize(500, 600); //gets h and w of image and sets jframe to the size

            int xd = (screenSize.width - f.getSize().width)/2; //These two lines are the dimensions
            int yd = (screenSize.height - f.getSize().height)/2;//of the center of the screen

            f.setLocation(xd, yd); //sets the location of the jframe
            f.setVisible(true); //makes the jframe visible

            System.out.println("bruh where'd you go");
        }
        else if (selectedGate!= null && map[x*map.length/width][y*map[0].length/height]==2)
        {
            System.out.println("clicked square");
            int gridX = x*map.length/width;
            int gridY = y*map[0].length/height;
            while (map[gridX-1][gridY]==2)
            {
                gridX--;
            }
            while (map[gridX][gridY-1]==2)
            {
                gridY--;
            }
            
            selectedGate.getSprite().setLocation(gridX*width/map.length, gridY*height/map[0].length);
            gates.add(selectedGate); 
            selectedGate = null;
        }
      
    }
}