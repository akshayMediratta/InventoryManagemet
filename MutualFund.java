package ePortfolio;

public class MutualFund extends Investment 
{

    /**
     * 
     * @param symbol symbol
     * @param name name
     * @param quantity quantity
     * @param price price
     */
    public MutualFund(String symbol, String name, int quantity, double price) 
    {
        super(symbol, name, quantity, price);
    }

    /**
     * 
     * @return returns the book value
     */
    public double returnBookValue() 
    {
        return bookValue;
    }

    /**
     * 
     * @param bookValue bbokvalue
     */
    public void setBookValue(double bookValue) 
    {
        super.bookValue = bookValue;
    }

    /**
     * @return returns the string in the format to be written in the file
     */
    public String toString() 
    {
        return "type = \"mutualfund\"\n" + "symbol = \"" + symbol + "\"\n" + "name = \"" + name + "\"\n" + "quantity = \"" + quantity + "\"\n" + "price = \"" + price + "\"\n" + "bookValue = \"" + bookValue + "\"\n";
    }

}
