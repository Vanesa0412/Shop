package exceptions;

public class ClientOutOfMoney extends Exception {
    public ClientOutOfMoney() {
        super("Client doesn't have enough money");
    }
}
