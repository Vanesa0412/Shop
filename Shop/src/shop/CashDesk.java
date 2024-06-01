package shop;

import clients.Client;
import employees.Cashier;

import java.util.ArrayDeque;
import java.util.Queue;

public class CashDesk {

    private final Queue<Client> clients;
    private Cashier cashier;

    public CashDesk(Cashier cashier) {
        this.clients = new ArrayDeque<>();
        this.cashier = cashier;
        this.cashier.bindToCashDesk(this);
    }

    public void addClient(Client client) {
        this.clients.add(client);
        cashier.serveNewClient();
    }

    public Client next() {
        return clients.poll();
    }

    public void start() {
        cashier.startWorking();
    }

    public void stop() {
        try {
            cashier.closeCashDesk();
            cashier.join();
        } catch (InterruptedException e) {
            System.out.println("Shop is forced to close.");
            e.getStackTrace();
        }
    }

    public Cashier getCashier() {
        return cashier;
    }

    public void setCashier(Cashier cashier) {
        this.cashier = cashier;
    }
}
