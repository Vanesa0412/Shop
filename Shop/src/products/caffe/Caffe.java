package products.caffe;

import products.Product;
import products.ProductCategory;

import java.time.LocalDate;
import java.time.Period;

public class Caffe extends Product implements Expirable {

    private static final int expirationDays = 30;

    public Caffe(long id, String name, double price) {
        super(id, name, price, ProductCategory.FOOD);
    }

    @Override
    public boolean willExpireSoon(int limit) {
        return Period.between(getDeliveryDate(), LocalDate.now()).getDays() > expirationDays - limit;
    }

    @Override
    public boolean hasExpired() {
        return Period.between(getDeliveryDate(), LocalDate.now()).getDays() > expirationDays;
    }
}
