import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class TestClient {


    public static void main(String[] args) throws IOException {

        // ask for service from Registry
        Socket socket = new Socket("localhost", 5000);

        ServiceMessageModel req = new ServiceMessageModel();
        req.code = ServiceMessageModel.SERVICE_DISCOVER_REQUEST;
        //req.data = String.valueOf(ServiceInfoModel.PRODUCT_INFO_SERVICE);

        Scanner readIn = new Scanner(System.in);  // Create a Scanner object
        System.out.print("Microservices Offered\n" +
                "-----------------------------------------------------------\n" +
                "1: Load product information for a product given its id\n" +
                "2: Load information for an order given its id\n" +
                "3: Update price of a product given its id\n" +
                "4: Update quantity of a product given its id\n" +
                "5: Check if a pair of username and password is valid\n" +
                "6: Check if an user can cancel an order, \n\t\tgiven the username and order id\n" +
                "-----------------------------------------------------------\n" +
                "Enter the number (1-6) for a microservice: ");

        int service = Integer.parseInt(readIn.nextLine());  // Read user input
        String username = "", password = "";
        int id = 1, quantity = 0;
        Double price = 0.0;
        if (service == 1 || service == 2 || service == 3 || service == 4)
        {
            System.out.print("\nEnter the ID number: ");
            id = Integer.parseInt(readIn.nextLine());
            if (service == 3)
            {
                System.out.print("\nEnter new price: $");
                price = Double.parseDouble(readIn.nextLine());
            }
            else if (service == 4)
            {
                System.out.print("\nEnter new quantity: ");
                quantity = Integer.parseInt(readIn.nextLine());
            }
        }
        else if (service == 5)
        {
            System.out.print("\nEnter the Username: ");
            username = readIn.nextLine();
            System.out.print("\nEnter the Password: ");
            password = readIn.nextLine();
        }
        else if (service == 6)
        {
            System.out.print("\nEnter the Username: ");
            username = readIn.nextLine();
            System.out.print("\nEnter the ID number: ");
            id = Integer.parseInt(readIn.nextLine());
        }

        req.data = String.valueOf(service);

        Gson gson = new Gson();

        DataOutputStream printer = new DataOutputStream(socket.getOutputStream());
        printer.writeUTF(gson.toJson(req));
        printer.flush();


        DataInputStream reader = new DataInputStream(socket.getInputStream());
        String msg = reader.readUTF();

        System.out.println("Message from server: " + msg);

        printer.close();
        reader.close();
        socket.close();

        ServiceMessageModel res = gson.fromJson(msg, ServiceMessageModel.class);

        if (res.code == ServiceMessageModel.SERVICE_DISCOVER_OK) {
            System.out.println(res.data);
        }
        else {
            System.out.println("Service not found");
            return;
        }

        ServiceInfoModel info = gson.fromJson(res.data, ServiceInfoModel.class);
        
        //System.out.println(info);

        Socket socket2 = new Socket("localhost", info.serviceHostPort);

        DataOutputStream printer2 = new DataOutputStream(socket2.getOutputStream());
        String reqq = "";

        if (service == 1 || service == 2)
        {
            reqq = String.valueOf(id);
        }
        else if (service == 3)
        {
            reqq = id + "," + price;
        }
        else if (service == 4)
        {
            reqq = id + "," + quantity;
        }
        else if (service == 5)
        {
            reqq = username + "," + password;
        }
        else if (service == 6)
        {
            reqq = username + "," + id;
        }

        printer2.writeUTF(gson.toJson(reqq));
        printer2.flush();
        DataInputStream reader2 = new DataInputStream(socket2.getInputStream());
        String msg2 = reader2.readUTF();
        System.out.print("\nThe Microservice returns:\n");
        System.out.println(msg2);
        printer2.close();
        reader2.close();
        socket2.close();
    }
}
