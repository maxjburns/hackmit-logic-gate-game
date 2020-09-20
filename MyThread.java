public class MyThread extends Thread
{
  private Display d;
  
  public MyThread(Display display)
  {
    d = display;
  }
  
  public void run()
  {
    while (true)
    {
      d.repaint();
    }  
    //System.out.println("running");
  }
}