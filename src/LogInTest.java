import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class LogInTest {

    static List<Shoe> shoeList = new ArrayList<>();
    static AddToCard addToCard = new AddToCard();
    static Order order = new Order();
    static Customer customer;

    public static void main(String[] args) throws SQLException, IOException {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter your firstname:");
        String firstName = scanner.nextLine();
        System.out.println("Please enter your password:");
        String password = scanner.nextLine();
        customer = ShoeRepository.getCustomerByLogin(firstName,password);

        try {
            DatabaseConnection dbConnection = DatabaseConnection.getInstance(); // anropar metoden DatabaseConnection
            try (Connection connection = dbConnection.getConnection();
                 Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery("select firstName,passwords from Customer")) {

                boolean isValidUser = false;
                while (resultSet.next()) {
                    if (firstName.equals(resultSet.getString("firstName")) &&
                            password.equals(resultSet.getString("passwords"))) {
                        isValidUser = true;
                        break;
                    }
                }

                if (isValidUser) {
                    System.out.println("Välkommen" + " " + firstName);
                    boolean exitMenu = false;
                    while (!exitMenu) {
                        System.out.println("Välj ett alternativ:");
                        System.out.println("1. Boots");
                        System.out.println("2. Crocs");
                        System.out.println("3. Sandals");
                        System.out.println("4. Sneackers");
                        System.out.println("5. Uggs");
                        System.out.println("6. Orders");
                        System.out.println("7. Avsluta");

                        int choice = scanner.nextInt();
                        switch (choice) {
                            case 1:
                                showShoes("Boots",choice);
                                break;
                            case 2:
                                showShoes("Crocs",choice);
                                break;
                            case 3:
                                showShoes("Sandals",choice);
                                break;
                            case 4:
                                showShoes("Sneackers",choice);
                                break;
                            case 5:
                                showShoes("Uggs",choice);
                                break;
                            case 6:
                                if(order.getShoes().size() > 0){
                                    order.getShoes().forEach(e-> System.out.println(e.toString()));
                                }
                                else{
                                    System.out.println("Du har inte lagt in produkt in order");
                                }
                                break;
                            case 7:
                                exitMenu = true;
                                System.out.println("Välommen åter " + firstName + "!");
                                break;
                            default:
                                System.out.println("Ogiltigt val!");
                                break;
                        }
                    }


                } else {
                    System.out.println("Access denied!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showShoes(String category, int choice) throws SQLException, IOException {
        List<Shoe> shoesList = ShoeRepository.getAllShoesByCategory(category);
        AtomicInteger b = new AtomicInteger(1);
        shoeList.forEach(ae -> {
            System.out.println(b + ". " + ae.toString());
            b.getAndIncrement();
        });
        if(choice -1 < 0 || choice -1 > shoeList.size()){
            System.out.println("Fel val!");
        }
        else {
            order.addShoe(shoeList.get(choice -1));
            int orderId = addToCard.addToCartWithOutParameter(customer.getId(), order.getId(), shoeList.get(choice -1).getId());
            if(orderId > 0){
                order.setId(orderId);
            }

        }
    }
}
