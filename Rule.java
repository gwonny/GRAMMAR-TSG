
/**
 * Main structure used to hold rules.
 * Contains the symbol, the number of paths, the generating symbols/terminals from path.
 * Default max number of paths of 999.
 *
 * @author Johnny Huang
 * @version 1.0
 */
public class Rule
{
    private int numTerminals;
    private char symbol;
    private String[] terminals;
    private final int MAX_TERMINAL_SIZE = 999;
    
    public Rule()
    {
        numTerminals = 0;
        symbol = 'x';
        terminals = new String[MAX_TERMINAL_SIZE];
        for(int i = 0; i < MAX_TERMINAL_SIZE; i++)
        {
            terminals[i] = "";
        }
    }
    
    //getters and setters of all variables for ease and debugging
    public int getNumTerminals()
    {
        return this.numTerminals;
    }
    
    public void setNumTerminals(int set)
    {
        this.numTerminals = set;
    }
    
    public char getSymbol()
    {
        return this.symbol;
    }
    
    public void setSymbol(char set)
    {
        this.symbol = set;
    }
    
    public String getTerminal(int index)
    {
        return this.terminals[index];
    }
    
    public void setTerminal(int index, String toSet)
    {
        this.terminals[index] = toSet;
    }
    
    public void addTerminal(String terminal)
    {
        terminals[numTerminals] = terminals[numTerminals]+terminal;
    }
    
    public void nextTerminal()
    {
        this.numTerminals++;
    }
    
    //checks for existance of a specified terminal of a rule
    public boolean existsTerminal(String terminal)
    {
        for(int i = 0; i < numTerminals; i++)
        {
            if(terminals[i].equals(terminal))
            {
                return true;
            }
        }
        return false;
    }
    
    //exactly existsTerminal, but returns the index of the path
    public int indexOfTerminal(String terminal)
    {
        for(int i = 0; i < numTerminals; i++)
        {
            if(terminals[i].equals(terminal))
            {
                return i;
            }
        }
        return -1;
    }
    
    //prints out information about Rule
    public void printInfo()
    {
        System.out.printf("Symbol: %c\n",this.getSymbol());
        System.out.printf("Number of paths: %d\n", this.getNumTerminals());
        System.out.printf("Possible generations: ");
        for(int i = 0; i < numTerminals; i++)
        {
            System.out.printf("%s ", this.getTerminal(i));
        }
        System.out.println();
        System.out.println();
    }
}
