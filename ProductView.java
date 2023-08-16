import javax.swing.*;
import java.awt.*;

public class ProductView extends JFrame {

    public JTextField txtProductID = new JTextField(30);
    public JTextField txtProductName = new JTextField(30);
    public JTextField txtProductPrice = new JTextField(30);
    public JTextField txtProductQuantity = new JTextField(30);

    public JButton btnLoad = new JButton("Load Product");
    public JButton btnSave = new JButton("Add Product");

    public ProductView() {
        this.setTitle("Seller View");
        this.setSize(new Dimension(900, 700));
        this.setLocation(new Point(400,100));
        this.setLayout(null);
        this.setBackground(Color.white);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(150,220,600,500);

        JPanel panelInfo = new JPanel();
        panelInfo.setLayout(null);
        panelInfo.setBounds(0,0,900,200);
        JLabel label = new JLabel("<html>  To update already offered products, enter in a Product ID<br/> and press the button labeled 'Load Product'" +
                ", change any<br/>information needed, and then press 'Save Product'. To<br/> create a new product, fill in the information and <br/>click 'Save Product'<html>");
        label.setFont(new Font("Arial",Font.PLAIN,25));
        label.setBounds(75, 0, 900, 200);
        panelInfo.add(label);
        panelInfo.setBorder(BorderFactory.createLineBorder(Color.black));

        JLabel label1 = new JLabel("Product ID");
        label1.setFont(new Font("Arial",Font.PLAIN,20));
        label1.setBounds(150, 0, 200, 30);
        panel.add(label1);
        txtProductID.setFont(new Font("Arial",Font.PLAIN,20));
        txtProductID.setBounds(150, 35, 200, 30);
        panel.add(txtProductID);

        JLabel label2 = new JLabel("Product Name");
        label2.setFont(new Font("Arial",Font.PLAIN,20));
        label2.setBounds(150, 85, 200, 30);
        panel.add(label2);
        txtProductName.setFont(new Font("Arial",Font.PLAIN,20));
        txtProductName.setBounds(150, 120, 200, 30);
        panel.add(txtProductName);

        JLabel label3 = new JLabel("Product Price");
        label3.setFont(new Font("Arial",Font.PLAIN,20));
        label3.setBounds(150, 170, 200, 30);
        panel.add(label3);
        txtProductPrice.setFont(new Font("Arial",Font.PLAIN,20));
        txtProductPrice.setBounds(150, 205, 200, 30);
        panel.add(txtProductPrice);

        JLabel label4 = new JLabel("Product Quantity");
        label4.setFont(new Font("Arial",Font.PLAIN,20));
        label4.setBounds(150, 255, 200, 30);
        panel.add(label4);
        txtProductQuantity.setFont(new Font("Arial",Font.PLAIN,20));
        txtProductQuantity.setBounds(150, 290, 200, 30);
        panel.add(txtProductQuantity);

        btnLoad.setBounds(50,355,200,50);
        btnLoad.setFont(new Font("Arial",Font.PLAIN,20));
        btnSave.setBounds(275,355,200,50);
        btnSave.setFont(new Font("Arial",Font.PLAIN,20));

        panel.add(label1);
        panel.add(txtProductID);
        panel.add(label2);
        panel.add(txtProductName);
        panel.add(label3);
        panel.add(txtProductPrice);
        panel.add(label4);
        panel.add(txtProductQuantity);
        panel.add(btnSave);
        panel.add(btnLoad);

        this.add(panel);
        this.add(panelInfo);

    }

}
