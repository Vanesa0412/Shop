package shop;

import employees.Cashier;
import products.Product;
import products.ProductCategory;
import products.caffe.Expirable;
import receipt.Receipt;

import java.util.*;
import java.util.stream.Collectors;

public class Shop {

    private final String name;
    private final List<Product> storage;
    private final Set<Cashier> cashiers;
    private final List<Receipt> receipts;
    private final double nonFoodOverprice;
    private final double foodOverprice;
    private final int daysPastExpiration;
    private final double expirationDiscount;

    private double totalMoney;
    private double overpriceMoney;

    private Shop(String name, Set<Cashier> cashiers,
                 double nonFoodOverprice, double foodOverprice,
                 int daysPastExpiration, double expirationDiscount) {
        this.name = name;
        this.cashiers = cashiers;
        this.nonFoodOverprice = nonFoodOverprice;
        this.foodOverprice = foodOverprice;
        this.daysPastExpiration = daysPastExpiration;
        this.expirationDiscount = expirationDiscount;
        this.storage = new ArrayList<>();
        this.receipts = new ArrayList<>();
    }


    public static class Builder {

        private final String name;
        private final Set<Cashier> cashiers;
        private double nonFoodOverprice;
        private double foodOverprice;
        private int daysPastExpiration;
        private double expirationDiscount;

        public Builder(String name) {
            this.name = name;
            this.cashiers = new HashSet<>();
        }

        public Builder hireCashiers(Cashier... cashiers) {
            this.cashiers.addAll(Arrays.asList(cashiers));
            return this;
        }

        public Builder nonFoodOverprice(double value) {
            this.nonFoodOverprice = value;
            return this;
        }

        public Builder foodOverprice(double value) {
            this.foodOverprice = value;
            return this;
        }

        public Builder expirationDiscount(double value) {
            this.expirationDiscount = value;
            return this;
        }

        public Builder setDaysPastExpiration(int days) {
            this.daysPastExpiration = days;
            return this;
        }

        public Shop build() {
            return new Shop(name, cashiers,
                    nonFoodOverprice, foodOverprice,
                    daysPastExpiration, expirationDiscount);
        }

    }

    public boolean has(Product product) {
        for(Product storageProduct : storage)
            if(storageProduct.getId() == product.getId())
                return true;
        return false;
    }

    public void sell(Product product) {
        int foundIndex = -1;
        for(int i = 0; i < storage.size(); i++) {
            if(product.getId() == storage.get(i).getId()) {
                foundIndex = i;
                break;
            }
        }

        if(foundIndex != -1)
            storage.remove(foundIndex);
    }

    public void removeExpiredProducts() {
        List<Product> expiredProducts = new ArrayList<>();
        for(Product product : storage)
            if(product.getCategory() == ProductCategory.FOOD)
                if(((Expirable)product).hasExpired())
                    expiredProducts.add(product);
        for(Product product : expiredProducts)
            storage.remove(product);
    }

    public void showStorage() {
        for(Product product : storage)
            System.out.println(product);
    }

    public void addProduct(Product product, int count) {
        for(int i = 0; i < count; i++)
            this.storage.add(product);
    }

    public void addTotalMoney(double amount) {
        this.totalMoney += amount;
    }

    public void addOverpriceMoney(double amount) {
        this.overpriceMoney += amount;
    }

    public double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(double totalMoney) {
        this.totalMoney = totalMoney;
    }

    public double getOverpriceMoney() {
        return overpriceMoney;
    }

    public void setOverpriceMoney(double overpriceMoney) {
        this.overpriceMoney = overpriceMoney;
    }

    public String getName() {
        return name;
    }

    public List<Product> getStorage() {
        return storage;
    }

    public Set<Cashier> getCashiers() {
        return cashiers;
    }

    public List<Receipt> getReceipts() {
        return receipts;
    }

    public double getNonFoodOverprice() {
        return nonFoodOverprice;
    }

    public double getFoodOverprice() {
        return foodOverprice;
    }

    public int getDaysPastExpiration() {
        return daysPastExpiration;
    }

    public double getExpirationDiscount() {
        return expirationDiscount;
    }
}
