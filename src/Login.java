import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
                DatabaseConnection dbConnection = DatabaseConnection.getInstance();
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
                            System.out.println("7. Skriv ut alla skor");
                            System.out.println("8. Skriv ut alla skor under 700kr");
                            System.out.println("9. Skriv ut endast rosa skor");
                            System.out.println("10. Avsluta");

                            int choice = scanner.nextInt();
                            String category = "";
                            switch (choice) {
                                case 1:
                                    category = "Boots";
                                    break;
                                case 2:
                                    category = "Crocs";
                                    break;
                                case 3:
                                    category = "Sandals";
                                    break;
                                case 4:
                                    category = "Sneackers";
                                    break;
                                case 5:
                                    category = "Uggs";
                                    break;
                                case 6:
                                    if (order.getShoes().size() > 0) {
                                        order.getShoes().forEach(e -> System.out.println(e.toStringWithoutQuantity()));
                                    } else {
                                        System.out.println("Du har inte lagt in produkt in order");
                                    }
                                    break;
                                case 7:
                                    List<Shoe> shoes = ShoeRepository.getAllShoes();
                                    shoes.forEach(shoe -> System.out.println(shoe));
                                    break;
                                case 8:
                                    List<Shoe> shoesUnderSevenhundred = ShoeRepository.getAllShoes();
                                    shoesUnderSevenhundred.stream()
                                            .filter(shoe -> shoe.getPrice() <= 700)
                                            .forEach(System.out::println);
                                    break;
                                case 9:
                                    List<Shoe> shoesPink = ShoeRepository.getAllShoes();
                                    shoesPink.stream()
                                            .filter(shoe -> shoe.getColor().equals("pink"))
                                            .forEach(System.out::println);
                                    break;
                                case 10:
                                    exitMenu = true;
                                    System.out.println("Välkommen åter " + firstName + "!");
                                    break;
                                default:
                                    System.out.println("Ogiltigt val!");
                                    break;
                            }
                            if(choice > 0 && choice < 9) {
                                shoeList = ShoeRepository.getAllShoesByCategory(category);
                                AtomicInteger b = new AtomicInteger(1);
                                shoeList.forEach(ae -> {
                                    System.out.println(b + ". " + ae.toString());
                                    b.getAndIncrement();

                                });
                                choice = scanner.nextInt();
                                if (choice - 1 < 0 || choice - 1 > shoeList.size()) {
                                    System.out.println("Fel val! Du måste välja skor.");
                                } else {
                                    order.addShoe(shoeList.get(choice - 1));
                                    int orderId = addToCard.addToCartWithOutParameter(customer.getId(), order.getId(), shoeList.get(choice - 1).getId());
                                    if (orderId > 0) {
                                        order.setId(orderId);
                                        System.out.println("Du har lagt till " + shoeList.get(choice - 1).toString() + " i din order.");
                                    }
                                }
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



