package ePortfolio;

public class Investment 
{

    public String symbol;
    public String name;
    public int quantity;
    public double price;
    public double bookValue;

    /**
     * 
     * @param symbol symbol
     * @param name name
     * @param quantity qunatity
     * @param price price
     */
    public Investment(String symbol, String name, int quantity, double price) 
    {
        this.symbol = symbol;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }
    
    /**
     * 
     * @return returns symbol
     */
    public String returnSymbol() 
    {
        return symbol;
    }

    /**
     * 
     * @param symbol symbol
     */
    public void setSymbol(String symbol) 
    {
        this.symbol = symbol;
    }

    /**
     * 
     * @return returns name
     */
    public String returnName() 
    {
        return name;
    }

    /**
     * 
     * @param name name
     */
    public void setName(String name) 
    {
    	this.name = name;
    }

    /**
     * 
     * @return returns quantity
     */
    public int returnQuantity() 
    {
        return quantity;
    }

    /**
     * 
     * @param quantity quantity
     */
    public void setQuantity(int quantity) 
    {
        this.quantity = quantity;
    }
    
    /**
     * 
     * @return returns price
     */
    public double returnPrice() 
    {
        return price;
    }

    /**
     * 
     * @param price price
     */
    public void setPrice(double price) 
    {
    	this.price = price;
    }



}
