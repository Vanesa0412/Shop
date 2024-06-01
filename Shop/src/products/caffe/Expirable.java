package products.caffe;

public interface Expirable {
    boolean willExpireSoon(int limit);
    boolean hasExpired();
}
