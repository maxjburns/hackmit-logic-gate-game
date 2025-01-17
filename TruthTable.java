public class TruthTable
{
  public static final boolean FUZZY = true;
  public static final boolean WUZZY = false;
  
  private Gate gate;
  
  public TruthTable(Gate g)
  {
    gate = g;
  }  
  
  public boolean[] outputs()
  {
    boolean[] outputs = new boolean[4];
    outputs[0] = gate.output(WUZZY, WUZZY);
    outputs[1] = gate.output(WUZZY, FUZZY);
    outputs[2] = gate.output(FUZZY, WUZZY);
    outputs[3] = gate.output(FUZZY, FUZZY);
    return outputs;
  }  
}  