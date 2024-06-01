package receipt;

import clients.Client;
import employees.Cashier;
import exceptions.ClientOutOfMoney;
import exceptions.SoldOutException;
import products.Product;
import products.ProductCategory;
import products.caffe.Expirable;
import shop.Shop;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Receipt implements Serializable {

    public static Shop shop;

    private Exception problem;
    private final Cashier cashier;
    private final List<Product> productList;
    private transient final Client client;

    public Receipt(Cashier cashier, Client client) {
        productList = new ArrayList<>();
        this.cashier = cashier;
        this.problem = null;
        this.client = client;
    }

    public void add(Product product) {
        this.productList.add(product);
    }

    public void print(boolean realtime) {

        System.out.println("\n============================== RECEIPT ==============================");
        System.out.println("= Date: " + LocalDate.now().toString() + "\t\t Served by: " + cashier.getEmployeeName() + " at "+ shop.getName() +" =");
        System.out.println("=====================================================================");
        System.out.println("= Time: " + LocalTime.now().toString().split("\\.")[0] + "\t\t\t\t\t\t\t\t\t Cashier id: " + cashier.getId() + " =");
        System.out.println("=====================================================================");

        double totalSum = 0;
        double totalOverprice = 0;

        if(realtime) {
            for (Product product : productList) {
                if (!shop.has(product)) {
                    this.problem = new SoldOutException(product);
                }
            }
        }


        for (Product product : productList) {

            double singlePrice = product.getPrice();

            double overprice =
                    product.getCategory() == ProductCategory.FOOD ?
                            shop.getFoodOverprice() :
                            shop.getNonFoodOverprice();

            double discount = shop.getExpirationDiscount();

            if (product.getCategory() == ProductCategory.FOOD) {
                boolean isExpirationClose = ((Expirable) product).willExpireSoon(shop.getDaysPastExpiration());
                if (isExpirationClose) {
                    singlePrice *= discount;
                    totalSum += singlePrice;
                } else {
                    singlePrice *= overprice;
                    totalSum += singlePrice;
                    totalOverprice += singlePrice - product.getPrice();
                }
            } else {
                singlePrice *= overprice;
                totalSum += singlePrice;
                totalOverprice += singlePrice - product.getPrice();
            }

            System.out.println(product.toString());

        }

        if(this.problem == null && realtime)
            if (client.getMoney() < totalSum)
                this.problem = new ClientOutOfMoney();

        if(this.problem == null && realtime)
            for (Product product : productList)
                shop.sell(product);

        if(this.problem == null && realtime) {
            shop.addTotalMoney(totalSum);
            shop.addOverpriceMoney(totalOverprice);
        }

        if(problem == null) {
            System.out.println("=====================================================================");
            System.out.println("= Total Price: " + String.format("%02.2f", totalSum) + "\t\t\t\t\t\t\t\t\t\t\t\t=");
            System.out.println("=====================================================================\n");
        } else {
            System.out.println("=====================================================================");
            System.out.println("= Invalid Receipt: " + problem.getMessage() + "\t\t\t\t\t=");
            System.out.println("=====================================================================\n");
        }

    }



}
