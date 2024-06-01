package clients;

import products.Product;

import java.util.List;
import java.util.Random;

public class Client {

    private static final Random random = new Random();
    private static final double MONEY_MIN = 30;
    private static final double MONEY_MAX = 200;

    private List<Product> cart;
    private double money;

    public Client(List<Product> productList) {
        this.cart = productList;
        this.money = MONEY_MIN + (MONEY_MAX - MONEY_MIN) * random.nextDouble();
    }

    public List<Product> getCart() {
        return cart;
    }

    public double getMoney() {
        return money;
    }

}
