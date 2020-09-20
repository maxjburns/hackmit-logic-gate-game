public class XorGate implements Gate
{
  private Sprite sprite;
  
  public XorGate(int x, int y, String s)
  {
      sprite = setSprite(x, y, s);
  }  
  
  public boolean output(boolean a, boolean b)
  {
    return a != b;
  }  
  
  public Sprite setSprite(int xCoor, int yCoor, String s)
  {
    return new Sprite(xCoor, yCoor, s);
  }  
  public Sprite getSprite()
  {
      return sprite;
  }
}  
  