import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {

        Repository r = new Repository();

        Scanner scan = new Scanner(System.in);

        System.out.println("Skriv in customerId: ");
        int customerId = Integer.parseInt(scan.nextLine());

        System.out.println("Skriv in orderId: ");
        int orderId = Integer.parseInt(scan.nextLine());

        System.out.println("Skriv in productId: ");
        int productId = Integer.parseInt(scan.nextLine());


        //r.addToCart(customerId, orderId, productId);

        r.addToCartWithOutParameter(customerId,orderId,productId);

        //r.addToCartWithSelect(customerId, orderId, productId);


    }
    }

