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
            try
            {  
                Thread.sleep(60);
            }
            catch(Exception e) {}
            d.repaint();
        }  
        //System.out.println("running");
    }
}