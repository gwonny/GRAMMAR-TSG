import java.util.Scanner;
import java.io.PrintWriter;
import java.io.File;
import java.util.Random;
/**
 * Main class that is ran from and generates strings from grammar in input .txt file.
 * NOTES: Paths-per-symbol is randomized, but there still can be possible same string generations, depending on the grammar. 
 * Repeat strings are stopped from being stored again. By default, 999 attempts on generating strings is given.
 * Randomization may also choose non-terminating paths infinitely. A maximum default string length of 999 is enforced.
 * NOT A SMART GENERATOR: Will not detect rules that generate symbols into one another. Will pass as long as symbols can generate something.
 *
 * @author Johnny Huang
 * @version 1.0
 */
public class MainGenerator
{
    public static void main(String args[]) throws Exception
    {
        //initialize main variables used in generation
        Scanner mainReader = null;
        File toRead = null;
        String stringStore = "";
        String charToString = "";
        char charStore = ' ';
        final int MAX_RULE_SIZE = 999;
        Rule[] ruleStorage = null;
        int nextRuleIndex = 0;

        //input .txt file to read grammar from, exception thrown by main
        toRead = new File("input.txt");
        //reader for file
        mainReader = new Scanner(toRead);

        //initializes Rule array and Rules that stores all rules and corresponding generations
        ruleStorage = new Rule[MAX_RULE_SIZE];
        for(int i = 0; i < MAX_RULE_SIZE; i++)
        {
            ruleStorage[i] = new Rule();
        }

        //stores read grammar into ruleStorage Rules
        while(mainReader.hasNextLine()==true)
        {
            stringStore = mainReader.nextLine();
            charToString = ""+stringStore.charAt(0);
            if(charToString.matches("[A-Z]"))
            {
                ruleStorage[nextRuleIndex].setSymbol(stringStore.charAt(0));
            }
            else
            {
                System.out.println("Format not correct. Detected non-capital symbol at start of rule.");
                System.exit(1);
            }

            charToString = ""+stringStore.charAt(1)+stringStore.charAt(2);
            if((!charToString.equals("->")))
            {
                System.out.println("Format not correct. Detected non-transition arrow after symbol at start of rule.");
                System.exit(1);
            }

            for(int i = 3; i < stringStore.length(); i++)
            {
                charToString = ""+stringStore.charAt(i);
                if(charToString.matches("[A-Za-z]"))
                {
                    ruleStorage[nextRuleIndex].addTerminal(charToString);
                }
                else if(charToString.equals("|"))
                {
                    ruleStorage[nextRuleIndex].nextTerminal();
                }
                else
                {
                    System.out.println("Format not correct. Detected illegal character while parsing paths.");
                    System.exit(1);
                }
            }
            ruleStorage[nextRuleIndex].nextTerminal();
            nextRuleIndex++;
        }
        mainReader.close();
        
        for(int i = 0; i < nextRuleIndex; i++)
        {
            ruleStorage[i].printInfo();
        }
        generateStrings(ruleStorage,nextRuleIndex);
    }

    public static void generateStrings(Rule[] grammar, int numRules) throws Exception
    {
        //holds generated strings from grammar
        String[] holdGenerations = new String[10];
        
        //initialize all indexes with empty string
        for(int x = 0; x < 10; x++)
        {
            holdGenerations[x] = new String("");
        }
        int nextHoldIndex = 0;

        //stores starting symbol thru concatenation
        String gennedString;

        //copy of string with ending whitespace
        String newString;
        String firstSplit;
        String secondSplit;
        String extract;

        //prepare rng
        Random rng = new Random();
        System.out.println("Generating strings...");
        System.out.println();
        for(int n = 0; n < 999; n++)
        {
            //if 10 strings have been generated
            if(nextHoldIndex==10)
            {
                break;
            }
            //initialize strings to store/use
            gennedString = "" + grammar[0].getSymbol();
            newString = (new String(gennedString)) + " ";
            firstSplit = "";
            secondSplit = "";
            extract = "";
            int stringLen = 0;
            
            //while not entirety of string are terminals...
            while(!(gennedString.matches("[a-z]+")))
            {
                stringLen = gennedString.length();
                //runs for entire current length of string
                for(int i = 0; i < stringLen; i++)
                {
                    //if it's a symbol
                    if(("" + gennedString.charAt(i)).matches("[A-Z]"))
                    {
                        //runs thru all rules
                        for(int j = 0; j < numRules; j++)
                        {
                            //if you found the rule that corresponds to your current symbol
                            if(gennedString.charAt(i) == grammar[j].getSymbol())
                            {
                                //extracts symbol and splits from left and right side
                                firstSplit = gennedString.substring(0,i);
                                extract = gennedString.substring(i,i+1);
                                secondSplit = gennedString.substring(i+1,gennedString.length());

                                //brings back string with new replacement
                                newString = firstSplit + grammar[j].getTerminal(rng.nextInt(grammar[j].getNumTerminals())) + secondSplit;
                                
                                //breaks first for-loop
                                break;
                            }
                        }
                        //if here, no rules applied
                        if(gennedString == newString)
                        {
                            System.out.println("No path for symbol " + extract + " detected. No seen terminals. Exiting...");
                            System.exit(1);
                        }
                        //else, it worked, change new string to generated string
                        gennedString = newString.trim();
                        if(gennedString.length()>999)
                        {
                            break;
                        }
                    }
                }
                if(gennedString.length()>999)
                {
                    break;
                }
            }
            if(gennedString.length()>999)
            {
                continue;
            }
            //should array hold string?
            boolean yesHold = true;
            
            //iterates entire string array
            for(int m = 0; m < 10; m++)
            {
                //do not take string if it exists already
                if(holdGenerations[m].equals(gennedString))
                {
                    System.out.println("\"" + gennedString + "\" already exists!");
                    yesHold = false;
                    break;
                }
            }
            //should hold if iterates entire time and no string
            //string doesnt exist if here
            if(yesHold)
            {
                System.out.println("Adding " + "\"" + gennedString + "\"");
                holdGenerations[nextHoldIndex] = new String(gennedString);
                nextHoldIndex++;
            }
        }
        
        PrintWriter mainWriter = new PrintWriter("output.txt","UTF-8");
        System.out.println();
        System.out.println("Strings Generated (10 Attempted): ");
        mainWriter.println("Strings Generated (10 Attempted): ");
        for(int printAll = 0; printAll < 10; printAll++)
        {
            if(!(holdGenerations[printAll].equals("")))
            {
                System.out.println(holdGenerations[printAll]);
                mainWriter.println(holdGenerations[printAll]);
            }
        }
        mainWriter.close();
        System.exit(0);
    }
}