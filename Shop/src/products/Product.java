package products;

import java.io.Serializable;
import java.time.LocalDate;

public class Product implements Serializable {

    private final long id;
    private final String name;
    private final double price;
    private final ProductCategory category;
    private final LocalDate deliveryDate;

    protected Product(
            long id, String name,
            double price, ProductCategory category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.deliveryDate = LocalDate.now();
    }

    @Override
    public String toString() {
        return "Product\t" + id + "\t" + price + "\t" + category.name() + "\t" + deliveryDate.toString() + "\t" + name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }
}
