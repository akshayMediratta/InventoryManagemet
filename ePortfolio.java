package ePortfolio;

import java.util.*;

public class ePortfolio 
{

    private ArrayList<Investment> investments;
    private Map<String, ArrayList<Integer>> nameKeywordindex;

    /**
     * 
     * @return returns the investments
     */
    public ArrayList<Investment> returnInvestments ( ) 
    {
        return investments;
    }

    /**
     * 
     * @param investment investment
     */
    public void addInvestment ( Investment investment ) 
    {
        if ( investments == null ) 
        {
            investments = new ArrayList<>();
        }
        investments.add ( investment );
        String[] tempString = investment.returnName ( ) .split ( " " );
        for ( String str : tempString ) 
        {
            addKeyword ( str );
        }
    }

    /**
     * 
     * @param word word
     */
    private void addKeyword ( String word ) 
    {
        if ( nameKeywordindex == null ) 
        {
            nameKeywordindex = new HashMap<> ();
        }
        if ( nameKeywordindex.containsKey ( word ) ) 
        {
            ArrayList<Integer> val = nameKeywordindex.get ( word );
            val.add ( investments.size ( ) - 1 );
            nameKeywordindex.replace ( word, val );
        } 
        else 
        {
            ArrayList<Integer> val = new ArrayList<> ();
            val.add ( investments.size () - 1 );
            nameKeywordindex.put ( word, val );
        }
    }

    /**
     * 
     * @param tempSymbol symbol
     * @return returns null or investment
     */
    public Investment checkThroughSymbol ( String tempSymbol ) 
    {
        if ( investments == null )
        {
            return null;
        }
        if ( investments != null ) 
        {
            for ( Investment investment : investments ) 
            {
                if ( investment.returnSymbol ( ) .equalsIgnoreCase ( tempSymbol ) ) 
                {
                    return investment;
                }
            }
        }  
        return null;      
    }

    /**
     * 
     * @param word word
     * @return returns the list of stocks
     */
    public ArrayList<Investment> checkThroughName(String word) 
    {
        ArrayList<Investment> temp = new ArrayList<>();
        if ( investments == null )
        {
            return null;
        }
        if ( investments != null ) 
        {
            for ( Investment investment : investments ) 
            {
                if (investment.returnName ( ).toLowerCase ( ).contains ( word ) ) 
                {
                    temp.add ( investment );
                }
            }
        }
        return temp;
    }

    /**
     * 
     * @param temp temp array of string
     * @return returns the investment
     */
    ArrayList<Investment> combinedSearch ( String[] temp ) 
    {
        ArrayList<Investment> investments = new ArrayList<>();
        if ( returnInvestments () != null ) 
        {
            if ( temp.length == 2 ) 
            {
                double lowerValue = Double.parseDouble ( temp[0] );
                double upperValue = Double.parseDouble ( temp[1] );
                for (Investment investment : returnInvestments()) 
                {
                    if ( investment.returnPrice () >= lowerValue && investment.returnPrice () <= upperValue) 
                    {
                        investments.add ( investment );
                    }
                }
            }
        }

        return investments;
    }

}
