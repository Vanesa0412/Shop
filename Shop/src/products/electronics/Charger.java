package products.electronics;

import products.Product;
import products.ProductCategory;

public class Charger extends Product {

    private static final long id = 324589;

    public Charger() {
        super(id, "Super Charger 40W", 16.99, ProductCategory.NONFOOD);
    }

}
