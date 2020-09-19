import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.*;

public class Sprite
{
<<<<<<< HEAD
    private int x;
    private int y;
    private BufferedImage image;

    public Sprite(int xCoor, int yCoor, String s)
    {
        x = xCoor;
        y = yCoor;
        image = loadSprite(s);
    }  

    public BufferedImage loadSprite(String file) {
        
        BufferedImage sprite = null;

        try {
            sprite = ImageIO.read(getClass().getResource(file+".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sprite;
    }

    public void setLocation(int xNew, int yNew)
    {
        x = xNew;
        y = yNew;
    }  
=======
  private int x;
  private int y;
  private String image;
  
  public Sprite(int xCoor, int yCoor, String s)
  {
    //please commit
    x = xCoor;
    y = yCoor;
    image = s;
  }  
  
  public void setLocation(int xNew, int yNew)
  {
    x = xNew;
    y = yNew;
  }  
>>>>>>> 863232a16a613158a043fa05d022289505aac586
}  