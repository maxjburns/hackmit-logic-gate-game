public class AndGate implements Gate
{
  private boolean bool1;
  private boolean bool2;
  
  public AndGate(boolean uno, boolean dos)
  {
    bool1 = uno;
    bool2 = dos;
  }  
  
  public boolean output(boolean a, boolean b)
  {
    return a && b;
  }  
  
  public Sprite setSprite(int xCoor, int yCoor, String s)
  {
    return new Sprite(xCoor, yCoor, s);
  }  
}  
  