package employees;

import clients.Client;
import exceptions.SoldOutException;
import products.Product;
import receipt.Receipt;
import shop.CashDesk;
import utils.FileUtils;
import world.World;

import java.io.Serializable;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Cashier extends Thread implements Serializable {

    private static transient final int SECONDS = 1000;
    private static transient long id = 0x00001515;

    private final transient Lock lock;
    private final transient Condition serveClient;


    private transient CashDesk currentCashDesk;
    private final long employeeId;
    private final String name;
    private transient double salary;
    private transient boolean isWorking;

    public Cashier(String name, double salary) {
        this.employeeId = id++;
        this.name = name;
        this.salary = salary;
        this.lock = new ReentrantLock();
        serveClient = lock.newCondition();
    }

    public Cashier(String name, double salary, long id) {
        this.employeeId = id;
        this.name = name;
        this.salary = salary;
        this.lock = new ReentrantLock();
        serveClient = lock.newCondition();
    }


    @Override
    public void run() {

        isWorking = true;
        Client client;

        while((client = currentCashDesk.next()) != null || isWorking) {
            lock.lock();
            try {
                if(client != null) {
                    // Something is wrong here
                    Receipt receipt = scanProducts(client);
                    receipt.print(true);
                    FileUtils.write(receipt, World.day, World.receiptCount++);
                } else if(isWorking) {
                    serveClient.await();
                } else break;
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (SoldOutException e) {
                System.out.println(e.getMessage());
            }
            lock.unlock();
        }
    }

    private Receipt scanProducts(Client client) throws InterruptedException, SoldOutException {
        int totalTimeNeeded = client.getCart().size() * SECONDS;
        Receipt receipt = new Receipt(this, client);
        for(Product product : client.getCart())
            receipt.add(product);
        Thread.sleep(totalTimeNeeded);
        return receipt;
    }

    public void bindToCashDesk(CashDesk cashDesk) {
        this.currentCashDesk = cashDesk;
    }

    public void startWorking() {
        this.start();
    }

    public void serveNewClient() {
        if(lock.tryLock()) {
            serveClient.signal();
            lock.unlock();
        }
    }

    public void closeCashDesk() {
        if (lock.tryLock()) {
            this.isWorking = false;
            serveClient.signal();
            lock.unlock();
        } else this.isWorking = false;
    }

    public String getEmployeeName() {
        return name;
    }

    @Override
    public Object clone() {
        Cashier cashier = new Cashier(name, salary, id);
        cashier.currentCashDesk = currentCashDesk;
        return cashier;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}
