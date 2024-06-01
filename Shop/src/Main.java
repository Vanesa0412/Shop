import employees.Cashier;
import products.caffe.Cappuccino;
import products.caffe.JavaCaffe;
import products.caffe.Latte;
import products.electronics.Batteries;
import products.electronics.Charger;
import receipt.Receipt;
import shop.Shop;
import utils.FileUtils;
import world.World;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        Shop shop = new Shop.Builder("Vanessa's shop")
                .nonFoodOverprice(1.50)
                .foodOverprice(1.25)
                .expirationDiscount(0.70)
                .hireCashiers(
                        new Cashier("Lilly Johnson", 1200),
                        new Cashier("Stefan Petrov", 1450))
                .build();
        Receipt.shop = shop;

        shop.addProduct(new Cappuccino(), 5);
        shop.addProduct(new JavaCaffe(), 10);
        shop.addProduct(new Latte(), 15);
        shop.addProduct(new Batteries(), 5);
        shop.addProduct(new Charger(), 5);
        shop.showStorage();

        World.simulate(shop, 2);
        List<Receipt> receipts = FileUtils.readAllReceiptsForGivenDay(0);
        for(Receipt receipt : receipts)
            receipt.print(false);

    }

}
