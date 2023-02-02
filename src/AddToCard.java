import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class AddToCard {
    int addToCartWithOutParameter(int customerId, int orderId, int productId) throws IOException {
        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            CallableStatement statement = connection.prepareCall(
                    "call AddToCart(?,?,?,?)");
            statement.setInt(1,customerId);
            statement.setInt(2,orderId);
            statement.setInt(3,productId);
            statement.registerOutParameter(4, Types.INTEGER);
            statement.executeQuery();
            return statement.getInt(4);


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



}