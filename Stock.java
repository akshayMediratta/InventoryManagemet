package ePortfolio;

public class Stock extends Investment 
{

    /**
     * 
     * @param symbol symbol
     * @param name name
     * @param quantity quantity
     * @param price price
     * @param commission comission
     */
    public Stock(String symbol, String name, int quantity, double price, double commission) 
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
     * @param bookValue book value
     */
    public void setBookValue(double bookValue) 
    {
        this.bookValue = bookValue;
    }

    /**
     * @return returns the string in the format to be written in the file
     */
    public String toString() 
    {
        return "type = \"stock\"\n" + "symbol = \"" + symbol + "\"\n" + "name = \"" + name + "\"\n" + "quantity = \"" + quantity + "\"\n" + "price = \"" + price + "\"\n" + "bookValue = \"" + bookValue + "\"\n";
    }

}
