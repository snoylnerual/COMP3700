import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class RemoteDataAdapter implements DataAccess {
    Gson gson = new Gson();
    Socket s = null;
    DataInputStream dis = null;
    DataOutputStream dos = null;

    @Override
    public void connect() {
        try {
            s = new Socket("localhost", 5056);
            dis = new DataInputStream(s.getInputStream());
            dos = new DataOutputStream(s.getOutputStream());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void saveProduct(ProductModel product) {
        RequestModel req = new RequestModel();
        req.code = req.SAVE_PRODUCT_REQUEST;
        req.body = gson.toJson(product);

        String json = gson.toJson(req);
        try {
            dos.writeUTF(json);

            String received = dis.readUTF();

            System.out.println("Server response:" + received);

            ResponseModel res = gson.fromJson(received, ResponseModel.class);

            if (res.code == ResponseModel.UNKNOWN_REQUEST) {
                System.out.println("The request is not recognized by the Server");
            }
            else         // this is a JSON string for a product information
                if (res.code == ResponseModel.DATA_NOT_FOUND) {
                    System.out.println("The Server could not correctly save the product!");
                }
                else {
                    System.out.println("The Product was saved!");
                }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public ProductModel loadProduct(int productID) {
        RequestModel req = new RequestModel();
        req.code = req.LOAD_PRODUCT_REQUEST;
        req.body = String.valueOf(productID);

        String json = gson.toJson(req);
        try {
            dos.writeUTF(json);

            String received = dis.readUTF();

            System.out.println("Server response:" + received);

            ResponseModel res = gson.fromJson(received, ResponseModel.class);

            if (res.code == ResponseModel.UNKNOWN_REQUEST) {
                System.out.println("The request is not recognized by the Server");
                return null;
            }
            else         // this is a JSON string for a product information
                if (res.code == ResponseModel.DATA_NOT_FOUND) {
                    System.out.println("The Server could not find a product with that ID!");
                    return null;
                }
                else {
                    ProductModel model = gson.fromJson(res.body, ProductModel.class);
                    System.out.println("Receiving a ProductModel object");
                    System.out.println("ProductID = " + model.productID);
                    System.out.println("Product name = " + model.name);
                    return model; // found this product and return!!!
                }



        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    @Override
    public LoginModel loadUser(String username, String password) {
        RequestModel req = new RequestModel();
        req.code = req.USER_LOGIN_REQUEST;
        req.body = username + "," + password;
        String json = gson.toJson(req);
        try {
            dos.writeUTF(json);
            String received = dis.readUTF();

            System.out.println("Server response:" + received);

            ResponseModel res = gson.fromJson(received, ResponseModel.class);

            if (res.code == ResponseModel.UNKNOWN_REQUEST) {
                System.out.println("The request is not recognized by the Server");
                return null;
            }
            else         // this is a JSON string for a product information
                if (res.code == ResponseModel.DATA_NOT_FOUND) {
                    System.out.println("The Server could not find a user with either that username or password!");
                    return null;
                }
                else {
                    LoginModel model = gson.fromJson(res.body, LoginModel.class);
                    System.out.println("Receiving a LoginModel object");
                    System.out.println("username = " + model.username);
                    System.out.println("Display Name = " + model.DisplayName);
                    System.out.println("User Type = " + model.UserType);
                    return model; // found this product and return!!!
                }



        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    @Override
    public void saveOrder(Order order){
        RequestModel req = new RequestModel();
        req.code = req.ORDER_REQUEST;
        req.body = gson.toJson(order);

        String json = gson.toJson(req);
        try {
            dos.writeUTF(json);

            String received = dis.readUTF();

            System.out.println("Server response:" + received);

            ResponseModel res = gson.fromJson(received, ResponseModel.class);

            if (res.code == ResponseModel.UNKNOWN_REQUEST) {
                System.out.println("The request is not recognized by the Server");
            }
            else         // this is a JSON string for a product information
                if (res.code == ResponseModel.DATA_NOT_FOUND) {
                    System.out.println("The Server could not correctly save the product!");
                }
                else {
                    System.out.println("The Product was saved!");
                }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
