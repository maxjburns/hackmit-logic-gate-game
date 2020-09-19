public class Monster
{
  public static final boolean FUZZY = true;
  public static final boolean WUZZY = false;
  
  
  private boolean nature;
  private Sprite sprite;
  
  public Monster(boolean b, int x, int y, String s)
  {
    nature = b;
    sprite = setSprite(x, y, s);
  }  
  
  public Sprite setSprite(int xCoor, int yCoor, String s)
  {
    return new Sprite(xCoor, yCoor, s);
  }  
}  