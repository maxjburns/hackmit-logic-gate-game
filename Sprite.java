import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.*;

public class Sprite
{
    private int x;
    private int y;
    private Image image;
    private String s;

    public Sprite(int xCoor, int yCoor, String s)
    {
        x = xCoor;
        y = yCoor;
        image = loadSprite(s);
        this.s = s;
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
    
    public void resize(int width, int height)
    {
        image = image.getScaledInstance(width, height, Image.SCALE_DEFAULT);
    }

    public void setLocation(int xNew, int yNew)
    {
        x = xNew;
        y = yNew;
    }  

    public Image getImage()
    {
        return image;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;   
    }
    
    public String getString()
    {
        return s;
    }
   
}  