public class ProductCalculations implements IProductCalculations {
    private double price;

    public ProductCalculations(double price) {
        this.price = price;
    }

    @Override
    public double GetVat() {
        return price * 0.15; // VAT: 15% of the product price
    }

    @Override
    public double GetCommission() {
        return price * 0.05; // Commission: 5% of the product price
    }

    @Override
    public double GetTotal() {
        return price + GetVat() + GetCommission(); // Total = Price + VAT + Commission
    }
}
