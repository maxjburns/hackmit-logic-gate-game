public class NotGate implements Gate
{
  private Sprite sprite;
  
  public NotGate(int x, int y, String s)
  {
      sprite = setSprite(x, y, s);
  }  
  
  public boolean output(boolean a, boolean b) //b is irrelevant
  {
    return !a;
  }  
  
  public Sprite setSprite(int xCoor, int yCoor, String s)
  {
    return new Sprite(xCoor, yCoor, s);
  }  
}  
  