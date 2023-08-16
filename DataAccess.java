import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public interface DataAccess {
    void connect(String str);

    void saveProduct(ProductModel product);

    ProductModel loadProduct(int productID);

    List<ProductModel> loadAllProducts();

    Order loadOrder(int orderID);

    User loadUser(String username, String password);

    ProductModel updatePrice(Double price, int productID);

    ProductModel updateQuantity(int quantity, int productID);

    Order deleteOrder(int orderID, String username);

    List<User> loadAllUsers();

    List<Order> loadAllOrders();
}
