package world;

import clients.Client;
import employees.Cashier;
import products.Product;
import products.caffe.Cappuccino;
import products.caffe.JavaCaffe;
import products.caffe.Latte;
import products.electronics.Batteries;
import products.electronics.Charger;
import shop.CashDesk;
import shop.Shop;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class World {

    private static final int MAX_PRODUCTS = 10;
    private static final int MIN_PRODUCTS = 1;

    private static final int MIN_CLIENTS = 2;
    private static final int MAX_CLIENTS = 8;

    public static int day;
    public static int receiptCount;

    private static final Random random = new Random();

    public static void simulate(Shop shop, int days) {

        clearCache(new File("resources"));

        for(int i = 0; i < days; i++) {

            day = i;
            receiptCount = 0;
            shop.removeExpiredProducts();
            shop.setTotalMoney(0);
            shop.setOverpriceMoney(0);

            System.out.println("\n=========================== START OF DAY " + i + " ==========================\n");

            Iterator<Cashier> cashiers = shop.getCashiers().iterator();
            CashDesk cashDeskA = null;
            CashDesk cashDeskB = null;

            if(shop.getCashiers().size() >= 2) {
                cashDeskA = new CashDesk((Cashier) cashiers.next().clone());
                cashDeskB = new CashDesk((Cashier) cashiers.next().clone());
            } else throw new RuntimeException("Not enough sellers to open the shop.");

            cashDeskA.start();
            cashDeskB.start();

            CashDesk cashDesk = cashDeskA;
            int totalClientsToday = random.nextInt(MAX_CLIENTS - MIN_CLIENTS) + MIN_CLIENTS;
            for(int j = 0; j < totalClientsToday; j++) {
                cashDesk.addClient(generateClient());
                cashDesk = cashDesk == cashDeskA ? cashDeskB : cashDeskA;
            }

            cashDeskA.stop();
            cashDeskB.stop();

            System.out.println("\n============================ END OF DAY " + i + " ===========================");
            System.out.println("========================= TOTAL MONEY: " + String.format("%.2f", shop.getTotalMoney()) + " ========================");
            System.out.println("======================= OVERPRICE MONEY: " + String.format("%.2f", shop.getOverpriceMoney()) + " ======================\n");

        }
    }

    private static void clearCache(File file) {
        File[] contents = file.listFiles();
        if (contents != null) {
            for (File f : contents) {
                clearCache(f);
            }
        }
        file.delete();
    }


    private static Client generateClient() {
        List<Product> productList = new ArrayList<>();
        int productCount = random.nextInt(MAX_PRODUCTS) + MIN_PRODUCTS;
        for(int i = 0; i < productCount; i++)
            productList.add(getRandomProduct());
        return new Client(productList);
    }

    private static Product getRandomProduct() {

        switch (random.nextInt(5)) {
            case 0:
                return new Cappuccino();
            case 1:
                return new Latte();
            case 2:
                return new JavaCaffe();
            case 3:
                return new Batteries();
            case 4:
                return new Charger();
        }

        throw new RuntimeException("Something went terribly wrong!");

    }

}
