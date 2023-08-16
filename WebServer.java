import com.hp.gagawa.java.elements.*;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.time.LocalDateTime;
import java.util.List;

public class WebServer {

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8500), 0);
        HttpContext root = server.createContext("/");
        root.setHandler(WebServer::handleRequest);

        HttpContext context = server.createContext("/users");
        context.setHandler(WebServer::handleRequestUser);

        HttpContext product = server.createContext("/products");
        product.setHandler(WebServer::handleRequestOneProduct);

        HttpContext allproducts = server.createContext("/products/all");
        allproducts.setHandler(WebServer::handleRequestAllProducts);

        HttpContext orders = server.createContext("/orders");
        orders.setHandler(WebServer::handleRequestAllOrders);

        server.start();
    }

    private static void handleRequest(HttpExchange exchange) throws IOException {

        Html html = new Html();
        Head head = new Head();

        html.appendChild( head );

        Title title = new Title();
        title.appendChild( new Text("Online shopping web server") );
        head.appendChild( title );

        Body body = new Body();

        //html.appendChild( body );

        H1 h1 = new H1();
        h1.appendChild( new Text("Store Database Options") );
        body.appendChild( h1 );

        P para = new P();

        para.appendChild( new Text("\nProduct and User Operations\n\n") );
        Div div = new Div();
        para.appendChild(div);
        para.appendChild(div);
        A link = new A("/products/all", "/products/all");
        link.appendText("Entire Products List");
        para.appendChild(link);
        para.appendChild(div);
        para.appendChild(div);
        A link2 = new A("/orders", "/orders");
        link2.appendText("Entire Orders List");
        para.appendChild(link2);
        para.appendChild(div);
        para.appendChild(div);
        A link3 = new A("/users", "/users");
        link3.appendText("Entire Users List");
        para.appendChild(link3);

        body.appendChild(para);
        html.appendChild( body );
        String response = html.write();
        exchange.sendResponseHeaders(200, response.getBytes().length);//response code and length
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private static void handleRequestUser(HttpExchange exchange) throws IOException {
        String url = "jdbc:sqlite:store.db";

        SQLiteDataAdapter dao = new SQLiteDataAdapter();

        dao.connect(url);

        List<User> list = dao.loadAllUsers();

        Html html = new Html();
        Head head = new Head();

        html.appendChild( head );

        Title title = new Title();
        title.appendChild( new Text("Users") );
        head.appendChild( title );

        Body body = new Body();

        html.appendChild( body );

        H1 h1 = new H1();
        h1.appendChild( new Text("Users Listing") );
        body.appendChild( h1 );

        P para = new P();
        para.appendChild( new Text("The server time is " + LocalDateTime.now()) );
        body.appendChild(para);

        para = new P();
        para.appendChild( new Text("The server has " + list.size() + " users." ));
        body.appendChild(para);

        Table table = new Table();
        Tr row = new Tr();
        Th header = new Th(); header.appendText("UserID"); row.appendChild(header);
        header = new Th(); header.appendText("UserName"); row.appendChild(header);
        header = new Th(); header.appendText("Password"); row.appendChild(header);
        header = new Th(); header.appendText("DisplayName"); row.appendChild(header);
        header = new Th(); header.appendText("IsManager"); row.appendChild(header);
        table.appendChild(row);

        for (User user : list) {
            row = new Tr();
            Td cell = new Td(); cell.appendText(String.valueOf(user.userID)); row.appendChild(cell);
            cell = new Td(); cell.appendText(user.username); row.appendChild(cell);
            cell = new Td(); cell.appendText(String.valueOf(user.password)); row.appendChild(cell);
            cell = new Td(); cell.appendText(String.valueOf(user.DisplayName)); row.appendChild(cell);
            cell = new Td(); cell.appendText(String.valueOf(user.isManager)); row.appendChild(cell);
            table.appendChild(row);
        }

        table.setBorder("1");

        html.appendChild(table);
        String response = html.write();

        System.out.println(response);

        exchange.sendResponseHeaders(200, response.getBytes().length);//response code and length
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private static void handleRequestAllProducts(HttpExchange exchange) throws IOException {
//        String response =  "This simple web server is designed with help from ChatGPT!";

        String url = "jdbc:sqlite:store.db";

        SQLiteDataAdapter dao = new SQLiteDataAdapter();

        dao.connect(url);

        List<ProductModel> list = dao.loadAllProducts();

        Html html = new Html();
        Head head = new Head();

        html.appendChild( head );

        Title title = new Title();
        title.appendChild( new Text("Products") );
        head.appendChild( title );

        Body body = new Body();

        html.appendChild( body );

        H1 h1 = new H1();
        h1.appendChild( new Text("Products Listing") );
        body.appendChild( h1 );

        P para = new P();
        para.appendChild( new Text("The server time is " + LocalDateTime.now()) );
        body.appendChild(para);

        para = new P();
        para.appendChild( new Text("The server has " + list.size() + " products."));
        body.appendChild(para);

        Table table = new Table();
        Tr row = new Tr();
        Th header = new Th(); header.appendText("ProductID"); row.appendChild(header);
        header = new Th(); header.appendText("Product name"); row.appendChild(header);
        header = new Th(); header.appendText("Price"); row.appendChild(header);
        header = new Th(); header.appendText("Quantity"); row.appendChild(header);
        table.appendChild(row);

        for (ProductModel product : list) {
            row = new Tr();
            Td cell = new Td(); cell.appendText(String.valueOf(product.productID)); row.appendChild(cell);
            cell = new Td(); cell.appendText(product.name); row.appendChild(cell);
            cell = new Td(); cell.appendText(String.valueOf(product.price)); row.appendChild(cell);
            cell = new Td(); cell.appendText(String.valueOf(product.quantity)); row.appendChild(cell);
            table.appendChild(row);
        }

        table.setBorder("1");

        html.appendChild(table);
        String response = html.write();

        System.out.println(response);


        exchange.sendResponseHeaders(200, response.getBytes().length);//response code and length
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }


    private static void handleRequestOneProduct(HttpExchange exchange) throws IOException {
        String uri =  exchange.getRequestURI().toString();

        int id = Integer.parseInt(uri.substring(uri.lastIndexOf('/')+1));

        System.out.println(id);


        String url = "jdbc:sqlite:store.db";

        SQLiteDataAdapter dao = new SQLiteDataAdapter();

        dao.connect(url);

        Html html = new Html();
        Head head = new Head();

        html.appendChild( head );

        Title title = new Title();
        title.appendChild( new Text("Example Page Title") );
        head.appendChild( title );

        Body body = new Body();

        html.appendChild( body );

        H1 h1 = new H1();
        h1.appendChild( new Text("Example Page Header") );
        body.appendChild( h1 );

        P para = new P();
        para.appendChild( new Text("The server time is " + LocalDateTime.now()) );
        body.appendChild(para);

        ProductModel product = dao.loadProduct(id);

        if (product != null) {

            para = new P();
            para.appendText("ProductID:" + product.productID);
            html.appendChild(para);
            para = new P();
            para.appendText("Product name:" + product.name);
            html.appendChild(para);
            para = new P();
            para.appendText("Price:" + product.price);
            html.appendChild(para);
            para = new P();
            para.appendText("Quantity:" + product.quantity);
            html.appendChild(para);
        }
        else {
            para = new P();
            para.appendText("Product not found");
            html.appendChild(para);
        }

        String response = html.write();

        System.out.println(response);

        exchange.sendResponseHeaders(200, response.getBytes().length);//response code and length
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }


    private static void handleRequestAllOrders(HttpExchange exchange) throws IOException {
        String url = "jdbc:sqlite:store.db";

        SQLiteDataAdapter dao = new SQLiteDataAdapter();

        dao.connect(url);

        List<Order> list = dao.loadAllOrders();

        Html html = new Html();
        Head head = new Head();

        html.appendChild( head );

        Title title = new Title();
        title.appendChild( new Text("Orders") );
        head.appendChild( title );

        Body body = new Body();

        html.appendChild( body );

        H1 h1 = new H1();
        h1.appendChild( new Text("Orders Listing") );
        body.appendChild( h1 );

        P para = new P();
        para.appendChild( new Text("The server time is " + LocalDateTime.now()) );
        body.appendChild(para);

        para = new P();
        para.appendChild( new Text("The server has " + list.size() + " orders." ));
        body.appendChild(para);

        for (Order order: list) {
            Table table = new Table();
            Tr row = new Tr();
            Th header = new Th();
            header.appendText("OrderID");
            row.appendChild(header);
            header = new Th();
            header.appendText("ProductID");
            row.appendChild(header);
            header = new Th();
            header.appendText("Quantity");
            row.appendChild(header);
            header = new Th();
            header.appendText("Cost");
            row.appendChild(header);
            table.appendChild(row);

            for (OrderLine line : order.lines) {
                row = new Tr();
                Td cell = new Td();
                cell.appendText(String.valueOf(line.orderID));
                row.appendChild(cell);
                cell = new Td();
                cell.appendText(String.valueOf(line.productID));
                row.appendChild(cell);
                cell = new Td();
                cell.appendText(String.valueOf(line.quantity));
                row.appendChild(cell);
                cell = new Td();
                cell.appendText(String.valueOf(line.cost));
                row.appendChild(cell);
                table.appendChild(row);
            }

            para = new P();
            para.appendChild( new Text("OrderID: " + order.orderID + "<br>Order Date: " + order.date
                    + "<br>Customer: " + order.customerName + "<br>Total Cost: " + order.totalCost + "<br>Total Tax: " + order.totalTax));

            table.setBorder("1");
            Div d = new Div();
            html.appendChild(table);
            html.appendChild(para);
            html.appendChild(d);
        }

        String response = html.write();

        System.out.println(response);
        exchange.sendResponseHeaders(200, response.getBytes().length);//response code and length
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}

