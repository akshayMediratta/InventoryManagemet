package ePortfolio;

import java.io.*;
import java.util.*;
import java.util.regex.*;

public class Assignment2
{

    private static ePortfolio objPortfolio;
    private static Scanner keyboard;
    private static String nameOfFile;

    /**
     * 
     * @param args array of strings
     */
    public static void main ( String[] args ) 
    {
        keyboard = new Scanner ( System.in );
        objPortfolio = new ePortfolio ( );
        if ( args.length != 0 ) 
        {
            nameOfFile = args[0];
            loadFile ( nameOfFile );
        }
        begin ( );
    }
    

    /**
     * 
     * @param nameOfFile
     */
    private static void loadFile ( String nameOfFile ) 
    {
        try 
        {
            File tempFile = new File ( nameOfFile );
            if ( tempFile.exists ( ) ) 
            {
                BufferedReader temp = new BufferedReader ( new FileReader ( tempFile ) );
                String line;
                String type = "";
                String symbol = "";
                String name = "";
                String quantity = "";
                String price = "";
                String bookValue = "";
                int num = 10;
                for ( ;num > 0; ) 
                {
                    line = temp.readLine ( );
                    if ( line == null )
                    {
                        break;
                    }
                    String[] tempString = line.split ( " " );
                    if ( tempString[0].equalsIgnoreCase ( "type" ) ) 
                    {
                        type = returnValue ( line );
                    } 
                    else if ( tempString[0].equalsIgnoreCase ( "symbol" ) ) 
                    {
                        symbol = returnValue ( line );
                    } 
                    else if ( tempString[0].equalsIgnoreCase ( "name" ) ) 
                    {
                        name = returnValue ( line );
                    } 
                    else if ( tempString[0].equalsIgnoreCase ( "quantity" ) ) 
                    {
                        quantity = returnValue ( line );
                    } 
                    else if ( tempString[0].equalsIgnoreCase ( "price" ) ) 
                    {
                        price = returnValue ( line );
                    } 
                    else if ( tempString[0].equalsIgnoreCase ( "bookvalue" ) ) 
                    {
                        bookValue = returnValue ( line );
                        if ( type.equalsIgnoreCase ( "stock" ) ) 
                        {
                            Stock st = new Stock ( symbol, name, Integer.parseInt ( quantity ), Double.parseDouble ( price ), 9.99 );
                            st.setBookValue ( Double.parseDouble ( bookValue ) );
                            objPortfolio.addInvestment ( st );
                        } 
                        else if ( type.equalsIgnoreCase ( "mutualfund" ) ) 
                        {
                            MutualFund mf = new MutualFund ( symbol, name, Integer.parseInt ( quantity ), Double.parseDouble ( price ) );
                            mf.setBookValue ( Double.parseDouble ( bookValue ) );
                            objPortfolio.addInvestment ( mf );
                        }

                    }
                }
            } 
            else 
            {
                tempFile.createNewFile ( );
            }
        } 
        catch ( Exception exception ) 
        {
            System.err.println ( exception.toString ( ) );
        }
    }
    
    private static void saveInformation ( ) 
    {
        try 
        {
            FileWriter temp = new FileWriter ( nameOfFile );
            String line = "";
            for ( Investment investment : objPortfolio.returnInvestments ( ) ) 
            {
                line = line + investment.toString ( );
            }
            temp.write ( line );
            temp.close ( );
        } 
        catch ( Exception exception ) 
        {
            System.out.println ( exception );
        }
    }

    private static String returnValue ( String line ) 
    {
        Pattern tempPattern = Pattern.compile("\"([^\"]*)\"");
        Matcher tempMatcher = tempPattern.matcher ( line );
        for ( ;tempMatcher.find(); ) 
        {
            return tempMatcher.group(0).substring(1, tempMatcher.group(0).length() - 1);
        }
        return "";
    }


    private static void begin ( ) 
    {
        int loop = 10;
        while ( loop > 0 ) 
        {
            System.out.println ( "******** Welcome to the Investment management program! ********" );
            System.out.println ( "Enter one of the following operations : \n" + "Buy\n" + "Sell\n" + "Update\n" + "GetGain\n" + "Search\n" + "Quit" );
            String input = keyboard.nextLine ( );
            if ( input.toUpperCase ( ).contains ( "BUY" ) || input.toUpperCase ( ).equals ( "B" ) ) 
            {
                buyInvestment ( );
            } 
            else if ( input.toUpperCase ( ).contains ( "SELL" ) ) 
            {
                sellInvestment ( );
            } 
            else if ( input.toUpperCase ( ).contains ( "UPDATE" ) ) 
            {
                updateStocks ( );
            } 
            else if ( input.toUpperCase ( ).contains ( "GETGAIN" ) ) 
            {
                calcGain ( );
            } 
            else if ( input.toUpperCase ( ).contains ( "SEARCH" ) ) 
            {
                searchStocks ( );
            } 
            else if ( input.toUpperCase ( ).contains ( "QUIT" ) ||  input.toUpperCase ( ).equals ( "Q" ) ) 
            {
                saveInformation ( );
                loop = -10;
            }

            if ( objPortfolio.returnInvestments ( ) != null ) 
            {
                System.out.println ( "\n******** All Purchased Investments ********\n" );
                for ( Investment investment : objPortfolio.returnInvestments ( ) ) 
                {
                    System.out.println ( investment.toString ( ) );
                }
            }
        }
    }

    private static void buyInvestment ( ) 
    {
        System.out.println ("Enter stock/mutualfund followed by its symbol : ");
        String garbage = keyboard.nextLine ();
        String[] line = garbage.split (" ");
        String investmentType = line[0];
        String tempSymbol = line[1];
        if ( investmentType.equalsIgnoreCase ( "mutualfund" ) ) 
        {
            Investment mf = objPortfolio.checkThroughSymbol ( tempSymbol );
            if ( mf == null ) 
            {
                buyMutualFund ( tempSymbol );
            } 
            else if ( mf instanceof MutualFund ) 
            {
                System.out.println ( mf.toString ( ) );
                addQntInMutualFund ( ( MutualFund ) mf );
            } 
            else 
            {
                System.out.println ( "\nThe symbol you entered is a stock\n" );
            }
        }
        if ( investmentType.equalsIgnoreCase ( "stock" ) ) 
        {
            Investment st = objPortfolio.checkThroughSymbol ( tempSymbol );
            if ( st == null ) 
            {
                buyStock ( tempSymbol );
            } 
            else if ( st instanceof Stock ) 
            {
                System.out.println ( st.toString ( ) );
                addQntInStock ( ( Stock ) st );
            } 
            else 
            {
                System.out.println ( "\nThe symbol you entered is a mutual fund\n" );
            }
        } 
        
    }

    private static void addQntInStock ( Stock st ) 
    {
        System.out.print ( "Enter quantity : " );
        int investementQuantity = keyboard.nextInt ( );
        System.out.print ( "Enter price : " );
        double price = keyboard.nextDouble ( );
        double bookValue = ( ( price * investementQuantity ) + 9.99 ) + st.returnBookValue ( );
        st.setQuantity ( st.returnQuantity ( ) + investementQuantity );
        st.setBookValue ( bookValue );
        st.setPrice ( price );
    }

    private static void addQntInMutualFund ( MutualFund mf ) 
    {
        System.out.print ( "Enter quantity : " );
        int investementQuantity = keyboard.nextInt ( );
        System.out.print ( "Enter price : " );
        double price = keyboard.nextDouble ( );
        double bookValue = ( ( price * investementQuantity ) + mf.returnBookValue ( ) );
        mf.setQuantity ( mf.returnQuantity ( ) + investementQuantity );
        mf.setBookValue ( bookValue );
        mf.setPrice ( price );
    }

    private static void buyStock ( String tempSymbol ) 
    {
        System.out.print ( "Enter stock name : " );
        String name = keyboard.nextLine ( );
        System.out.print ( "Enter quantity : " );
        int investementQuantity = keyboard.nextInt ( );
        System.out.print ( "Enter price : " );
        double price = keyboard.nextDouble ( );
        keyboard.nextLine ( );
        Stock st = new Stock ( tempSymbol, name, investementQuantity, price, 9.99 );
        double ans = ( price * investementQuantity ) + 9.99;
        st.setBookValue ( ans );
        objPortfolio.addInvestment ( st );
    }

    private static void buyMutualFund ( String tempSymbol ) 
    {
        System.out.print ( "Enter mutual fund name : " );
        String name = keyboard.nextLine ( );
        System.out.print ( "Enter quantity : " );
        int investementQuantity = keyboard.nextInt ( );
        System.out.print ( "Enter price : " );
        double price = keyboard.nextDouble ( );
        keyboard.nextLine ( );
        MutualFund mf = new MutualFund ( tempSymbol, name, investementQuantity, price );
        double ans = price * investementQuantity;
        mf.setBookValue ( ans );
        objPortfolio.addInvestment ( mf );
    }

    private static void sellInvestment ( ) 
    {
        System.out.println ( "Enter investment symbol : " );
        String tempSymbol = keyboard.nextLine ( );
        Investment investment = objPortfolio.checkThroughSymbol ( tempSymbol );
        if ( investment != null && investment instanceof MutualFund ) 
        {
            sellMutualFund ( ( MutualFund ) investment );
        } 
        else if ( investment != null && investment instanceof Stock ) 
        {
            sellStock ( ( Stock ) investment );
        } 
        else 
        {
            System.out.println ( "Investment not found!" );
        }
    }

    private static void sellStock ( Stock stock ) 
    {
        System.out.println ( "Enter selling price : " );
        double price = keyboard.nextDouble ( );
        System.out.println ( "Enter selling quantity: " );
        int quantity = keyboard.nextInt ( );
        if( quantity > stock.returnQuantity ( ) ) 
        {
            System.out.println ( "\nYou don't own sufficient stocks!!!" );
        }
        else
        {
            int num = stock.returnQuantity ( ) - quantity;
            if ( num == 0)
            {
                objPortfolio.returnInvestments ( ).remove ( stock );
            }
            else
            {
                double bookValue = stock.returnBookValue ( ) * num / stock.returnQuantity ( );
                stock.setBookValue ( bookValue );
                stock.setQuantity ( num );
            }   
            keyboard.nextLine ( );
            System.out.println ( "\nStocks sold successfully!!!" );
            double afterPrice = ( price * quantity ) - 9.99;
            System.out.println ( "\nAmount you receive after selling the Stocks is : " + afterPrice );
        } 
    }

    private static void sellMutualFund ( MutualFund mutualFund ) 
    {
        System.out.println ( "Enter sell price : " );
        double price = keyboard.nextDouble ( );
        System.out.println ( "Enter sell quantity: " );
        int quantity = keyboard.nextInt ( );
        if ( quantity > mutualFund.returnQuantity ( ) ) 
        {
            System.out.println ( "\nYou don't own sufficient Mutual Funds!!!" );
        }
        else
        {
            int num = mutualFund.returnQuantity ( ) - quantity;
            if ( num == 0) 
            {
                objPortfolio.returnInvestments ( ).remove ( mutualFund );
            }
            else 
            {
                double bookValue = mutualFund.returnBookValue ( ) * num / mutualFund.returnQuantity ( );
                mutualFund.setBookValue ( bookValue );
                mutualFund.setQuantity ( num );
            } 
            keyboard.nextLine ( );
            System.out.println ( "\nMutual Funds sold successfully!!!" );
            double afterPrice = ( price * quantity ) - 45;
            System.out.println ( "\nAmount you receive after selling the Mutual Funds is : " + afterPrice );
        }
    }

    private static void updateStocks ( ) 
    {
        if ( objPortfolio.returnInvestments ( ) != null ) 
        {
            for ( Investment investment : objPortfolio.returnInvestments ( ) ) 
            {
                System.out.println ( investment.toString ( ) );
                System.out.print ( "Enter new price " );
                double tempPrice = keyboard.nextDouble ( );
                investment.setPrice ( tempPrice );
            }
        }
    }

    private static void calcGain ( ) 
    {
        if ( objPortfolio.returnInvestments ( ) != null ) 
        {
            for ( Investment investment : objPortfolio.returnInvestments ( ) ) 
            {
                if ( investment instanceof MutualFund ) 
                {
                    MutualFund mutualFund = ( MutualFund ) investment;
                    System.out.println ( mutualFund.toString ( ) );
                    double gainValue = ( ( mutualFund.returnQuantity ( ) * mutualFund.returnPrice ( ) ) - 45.00 ) - mutualFund.returnBookValue ( );
                    System.out.print ( "Gain earned for the mutualfund " + mutualFund.returnName( ) + " is : " + gainValue + "\n" );

                }
                if ( investment instanceof Stock ) 
                {
                    Stock stock = ( Stock ) investment;
                    System.out.println ( stock.toString ( ) );
                    double gainValue = ( ( stock.returnQuantity ( ) * stock.returnPrice ( ) ) - 9.99 ) - stock.returnBookValue ( );
                    System.out.print ( "Gain earned for the stock " + stock.returnName( ) +  " is : " + gainValue + "\n" );
                } 
            }
        }
    }

    private static void searchStocks ( ) 
    {
        System.out.println ( "Select from the following options : " );
        System.out.println ( "Enter 1 to search by symbol" );
        System.out.println ( "Enter 2 to search by keyword" );
        System.out.println ( "Enter 3 to search by symbol and price-range" );
        int number = keyboard.nextInt ( );
        keyboard.nextLine ( );
        if ( number == 1 ) 
        {
            System.out.print ( "Enter symbol : " );
            String input = keyboard.nextLine ( );
            Investment investment = objPortfolio.checkThroughSymbol ( input );
            if ( investment == null) 
            {
                System.out.println ( "No investment found!" );
            }
            else
            {
                if ( investment instanceof MutualFund ) 
                {
                    System.out.println ( ( ( MutualFund ) investment ).toString ( ) );
                }
                if ( investment instanceof Stock ) 
                {
                    System.out.println ( ( ( Stock ) investment ).toString ( ) );
                }            
            }       
        } 
        if ( number == 2 ) 
        {
            System.out.print ( "Enter keyword : " );
            String input = keyboard.nextLine ( );
            ArrayList<Investment> investments = objPortfolio.checkThroughName ( input );
            if (investments == null) 
            {
                System.out.println ( "No investment found!" );
            }
            else
            {
                for ( Investment i : investments ) 
                {
                    if ( i instanceof MutualFund )
                    {
                        System.out.println ( ( ( MutualFund ) i ).toString ( ) );
                    }
                    if ( i instanceof Stock ) 
                    {
                        System.out.println ( ( ( Stock ) i ).toString ( ) );
                    } 
                }
            } 
        } 
        if ( number == 3 ) 
        {
            System.out.print ( "Enter price range : " );
            String input = keyboard.nextLine ( );
            String[] priceArr = input.split ( "-" );
            ArrayList<Investment> tempInvestments = objPortfolio.combinedSearch ( priceArr );
            if ( tempInvestments == null)
            {
                System.out.println ( "No investment found!" );
            }
            else
            {
                for ( Investment i : tempInvestments ) 
                {
                    if ( i instanceof MutualFund ) 
                    {
                        System.out.println ( ( ( MutualFund ) i ).toString ( ) );
                    }
                    if ( i instanceof Stock ) 
                    {
                        System.out.println ( ( ( Stock ) i ).toString ( ) );
                    }  
                }
            }    
        }
    }
}
