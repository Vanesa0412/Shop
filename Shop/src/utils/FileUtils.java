package utils;

import receipt.Receipt;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

    public static synchronized void write(Receipt receipt, int day, int count) {

        File file = new File("resources/day-" + day);
        if(!file.exists()) {
            if (!file.mkdirs()) {
                System.out.println("Problem with creating the working path.");
                return;
            }
        }

        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("resources/day-" + day + "/Receipt-" + count))) {
            oos.writeObject(receipt);
        } catch (FileNotFoundException e) {
            e.getStackTrace();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.getCause();
            e.getStackTrace();
        }

    }

    public static synchronized List<Receipt> readAllReceiptsForGivenDay(int day) {

        int counter = 0;
        boolean reading = true;
        List<Receipt> receipts = new ArrayList<>();

        while(reading) {
            try(ObjectInputStream ois = new ObjectInputStream((new FileInputStream("resources/day-" + day + "/Receipt-" + counter++)))) {
                Receipt receipt = (Receipt) ois.readObject();
                receipts.add(receipt);
            } catch (FileNotFoundException e) {
                reading = false;
            } catch (IOException e) {
                e.getStackTrace();
            } catch (ClassNotFoundException e) {
                System.out.println("Unknown class in the given file");
                e.printStackTrace();
            }
        }

        return receipts;

    }


}
