package products.electronics;

import products.Product;
import products.ProductCategory;

public class Batteries extends Product {

    private static final long id = 563456;

    public Batteries() {
        super(id, "Turbo Duracell", 11.99, ProductCategory.NONFOOD);
    }
}
