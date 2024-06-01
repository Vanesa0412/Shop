package exceptions;

import products.Product;

public class SoldOutException extends Exception {
    public SoldOutException(Product product) {
        super("Sold out product: " + product.getName());
    }
}
