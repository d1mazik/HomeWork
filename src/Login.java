import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;


public class Login {
    public static void main(String[] args) throws SQLException, IOException {

        Order order = new Order();

        Customer customer;
        AddToCard addToCard = new AddToCard();
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
                    List<Shoe> shoeList = new ArrayList<>();
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
                                shoeList = ShoeRepository.getAllShoesByCategory("Boots");
                                AtomicInteger b = new AtomicInteger(1);
                                shoeList.forEach(ae -> {
                                    System.out.println(b + ". " + ae.toString());
                                    b.getAndIncrement();
                                });
                                choice = scanner.nextInt();
                                if(choice -1 < 0 || choice -1 > shoeList.size()){
                                    System.out.println("Fel val!");
                                }
                                else{order.addShoe(shoeList.get(choice -1));
                                   int orderId = addToCard.addToCartWithOutParameter(customer.getId(), order.getId(), shoeList.get(choice -1).getId());
                                   if(orderId > 0){
                                       order.setId(orderId);
                                   }
                                }

                                break;
                            case 2:
                                shoeList = ShoeRepository.getAllShoesByCategory("Crocs");
                                AtomicInteger c = new AtomicInteger(1);
                                shoeList.forEach(ae -> {
                                    System.out.println(c + ". " + ae.toString());
                                    c.getAndIncrement();
                                });
                                choice = scanner.nextInt();
                                if(choice -1 < 0 || choice -1 > shoeList.size()){
                                    System.out.println("Fel val!");
                                }
                                else{order.addShoe(shoeList.get(choice -1));
                                    int orderId = addToCard.addToCartWithOutParameter(customer.getId(), order.getId(), shoeList.get(choice -1).getId());
                                    if(orderId > 0){
                                        order.setId(orderId);
                                    }
                                }
                                break;
                            case 3:
                                shoeList = ShoeRepository.getAllShoesByCategory("Sandals");
                                AtomicInteger s = new AtomicInteger(1);
                                shoeList.forEach(ae -> {
                                    System.out.println(s + ". " + ae.toString());
                                    s.getAndIncrement();
                                });
                                choice = scanner.nextInt();
                                if(choice -1 < 0 || choice -1 > shoeList.size()){
                                    System.out.println("Fel val!");
                                }
                                else{order.addShoe(shoeList.get(choice -1));
                                    int orderId = addToCard.addToCartWithOutParameter(customer.getId(), order.getId(), shoeList.get(choice -1).getId());
                                    if(orderId > 0){
                                        order.setId(orderId);
                                    }
                                }
                                break;
                            case 4:
                                shoeList = ShoeRepository.getAllShoesByCategory("Sneackers");
                                AtomicInteger sn = new AtomicInteger(1);
                                shoeList.forEach(ae -> {
                                    System.out.println(sn + ". " + ae.toString());
                                    sn.getAndIncrement();
                                });
                                choice = scanner.nextInt();
                                if(choice -1 < 0 || choice -1 > shoeList.size()){
                                    System.out.println("Fel val!");
                                }
                                else{order.addShoe(shoeList.get(choice -1));
                                    int orderId = addToCard.addToCartWithOutParameter(customer.getId(), order.getId(), shoeList.get(choice -1).getId());
                                    if(orderId > 0){
                                        order.setId(orderId);
                                    }
                                }
                                break;
                            case 5:
                                shoeList = ShoeRepository.getAllShoesByCategory("Uggs");
                                AtomicInteger u = new AtomicInteger(1);
                                shoeList.forEach(ae -> {
                                    System.out.println(u + ". " + ae.toString());
                                    u.getAndIncrement();
                                });
                                choice = scanner.nextInt();
                                if(choice -1 < 0 || choice -1 > shoeList.size()){
                                    System.out.println("Fel val!");
                                }
                                else{order.addShoe(shoeList.get(choice -1));
                                    int orderId = addToCard.addToCartWithOutParameter(customer.getId(), order.getId(), shoeList.get(choice -1).getId());
                                    if(orderId > 0){
                                        order.setId(orderId);
                                    }
                                }
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


}

