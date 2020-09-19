public class Sprite
{
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
}  