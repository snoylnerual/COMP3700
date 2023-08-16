import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLiteDataAdapter implements DataAccess {
    Connection conn = null;

    @Override
    public void connect(String url) {
        try {
            // db parameters
            // create a connection to the database
            Class.forName("org.sqlite.JDBC");

            conn = DriverManager.getConnection(url);

            if (conn == null)
                System.out.println("Cannot make the connection!!!");
            else
                System.out.println("The connection object is " + conn);

            System.out.println("Connection to SQLite has been established.");

            /* Test data!!!
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Product");

            while (rs.next())
                System.out.println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4));
            */

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void saveProduct(ProductModel product) {
        try {
            Statement stmt = conn.createStatement();

            if (loadProduct(product.productID) == null) {           // this is a new product!
                stmt.execute("INSERT INTO Product(productID, name, price, quantity) VALUES ("
                        + product.productID + ","
                        + '\'' + product.name + '\'' + ","
                        + product.price + ","
                        + product.quantity + ")"
                );
            }
            else {
                stmt.execute("UPDATE Product SET "
                        + "productID = " + product.productID + ","
                        + "name = " + '\'' + product.name + '\'' + ","
                        + "price = " + product.price + ","
                        + "quantity = " + product.quantity +
                        " WHERE productID = " + product.productID
                );

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try{ conn.close(); }
        catch (Exception ex) { ex.printStackTrace(); }
    }

    @Override
    public ProductModel loadProduct(int productID) {
        ProductModel product = null;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Product WHERE ProductID = " + productID);
            if (rs.next()) {
                product = new ProductModel();
                product.productID = rs.getInt(1);
                product.name = rs.getString(2);
                product.price = rs.getDouble(3);
                product.quantity = rs.getDouble(4);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return product;
    }

    @Override
    public List<ProductModel> loadAllProducts() {
        List<ProductModel> list = new ArrayList<>();
        ProductModel product = null;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Product ");
            while (rs.next()) {
                product = new ProductModel();
                product.productID = rs.getInt(1);
                product.name = rs.getString(2);
                product.price = rs.getDouble(3);
                product.quantity = rs.getDouble(4);
                list.add(product);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try{ conn.close(); }
        catch (Exception ex) { ex.printStackTrace(); }
        return list;
    }

    @Override
    public Order loadOrder(int orderID) {
        Order order = null;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Orders WHERE OrderID = " + orderID);
            while (rs.next()) {
                order = new Order();
                order.orderID = rs.getInt(1);
                order.date =  rs.getString(2);//rs.getDate(2);
                order.customerName = rs.getString(3);
                order.totalCost = rs.getDouble(4);
                order.totalTax = rs.getDouble(5);
            }
            System.out.print(order);
            rs = stmt.executeQuery("SELECT * FROM OrderLine WHERE OrderID = " + orderID);

            while (rs.next()) {
                OrderLine line = new OrderLine();
                line.orderID = rs.getInt(1);
                line.productID = rs.getInt(2);
                line.quantity = rs.getDouble(3);
                line.cost = rs.getDouble(4);
                order.addLine(line);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try{ conn.close(); }
        catch (Exception ex) { ex.printStackTrace(); }
        return order;
    }

    @Override
    public List<Order> loadAllOrders() {
        List<Order> list = new ArrayList<>();
        Order order = null;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Orders");
            while (rs.next()) {
                order = new Order();
                order.orderID = rs.getInt(1);
                order.date =  rs.getString(2);//rs.getDate(2);
                order.customerName = rs.getString(3);
                order.totalCost = rs.getDouble(4);
                order.totalTax = rs.getDouble(5);

                list.add(order);
            }

            for (Order ord: list) {
                ResultSet rs2 = stmt.executeQuery("SELECT * FROM OrderLine WHERE OrderID = " + ord.orderID);

                while (rs2.next()) {
                    OrderLine line = new OrderLine();
                    line.orderID = rs2.getInt(1);
                    line.productID = rs2.getInt(2);
                    line.quantity = rs2.getDouble(3);
                    line.cost = rs2.getDouble(4);
                    ord.addLine(line);
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try{ conn.close(); }
        catch (Exception ex) { ex.printStackTrace(); }
        return list;
    }

    @Override
    public User loadUser(String username, String password) {
        User user = null;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM User WHERE UserName = \"" + username + "\" AND Password = \"" + password + "\"");
            if (rs.next()) {
                user = new User();
                user.userID = rs.getInt("UserID");
                user.username = rs.getString("UserName");
                user.password = rs.getString("Password");
                user.DisplayName = rs.getString("DisplayName");
                user.isManager = rs.getBoolean("IsManager");
            }

        } catch (SQLException e) {
            System.out.println("Database access error!");
            e.printStackTrace();
        }
        try{ conn.close(); }
        catch (Exception ex) { ex.printStackTrace(); }
        return user;
    }

    @Override
    public List<User> loadAllUsers() {
        List<User> list = new ArrayList<>();
        User user = null;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM User ");
            while (rs.next()) {
                user = new User();
                user.userID = rs.getInt(1);
                user.username = rs.getString(2);
                user.password = rs.getString(3);
                user.DisplayName = rs.getString(4);
                user.isManager = rs.getBoolean(4);;
                list.add(user);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try{ conn.close(); }
        catch (Exception ex) { ex.printStackTrace(); }
        return list;
    }

    @Override
    public ProductModel updatePrice(Double price, int productID) {
        ProductModel model = null;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Product WHERE ProductID = " + productID);
            if (rs.next()) {
                model = new ProductModel();
                model.productID = rs.getInt(1);
                model.name = rs.getString(2);
                model.quantity = rs.getDouble(4);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        model.price = price;
        saveProduct(model);
        return model;
    }

    @Override
    public ProductModel updateQuantity(int quantity, int productID) {
        ProductModel product = null;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Product WHERE ProductID = " + productID);
            if (rs.next()) {
                product = new ProductModel();
                product.productID = rs.getInt(1);
                product.name = rs.getString(2);
                product.price = rs.getDouble(3);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        product.quantity = quantity;
        saveProduct(product);
        return product;
    }

    @Override
    public Order deleteOrder(int orderID, String username) {
        Order order = null;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM User WHERE UserName = \"" + username + "\"");
            String name = "";
            if (rs.next()) {
                name = rs.getString("DisplayName");
            }

            if (name != "") {
                stmt = conn.createStatement();
                rs = stmt.executeQuery("SELECT * FROM Orders WHERE OrderID = " + orderID +
                        " AND Customer = \"" + name + "\"");
                while (rs.next()) {
                    order = new Order();
                    order.orderID = rs.getInt(1);
                    order.date = rs.getString(2);
                    order.customerName = rs.getString(3);
                    order.totalCost = rs.getDouble(4);
                    order.totalTax = rs.getDouble(5);
                }

                if (rs != null) {
                    rs = stmt.executeQuery("SELECT * FROM OrderLine WHERE OrderID = " + orderID);

                    while (rs.next()) {
                        OrderLine line = new OrderLine();
                        line.orderID = rs.getInt(1);
                        line.productID = rs.getInt(2);
                        line.quantity = rs.getDouble(3);
                        line.cost = rs.getDouble(4);
                        order.addLine(line);
                    }

                    try {
                        for (OrderLine line : order.lines) {
                            stmt.executeQuery("DELETE FROM OrderLine WHERE OrderID = " + line.orderID);
                        }
                    }
                    catch (Exception ex) {
                            ex.printStackTrace();
                    }
                    stmt.executeQuery("DELETE FROM Orders WHERE OrderID = " + orderID);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try{ conn.close(); }
        catch (Exception ex) { ex.printStackTrace(); }
        return order;
    }

}
