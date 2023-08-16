import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.LocalDate;

import static java.sql.Date.valueOf;

public class CheckoutController implements ActionListener {
    private CheckoutScreen view;
    private DataAccess dao; // to save and load product
    private Order order = null;

    public CheckoutController(CheckoutScreen view, DataAccess dataAdapter) {
        this.dao = dataAdapter;
        this.view = view;

        view.getBtnAdd().addActionListener(this);
        view.getBtnPay().addActionListener(this);

        order = new Order();

    }


    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == view.getBtnAdd())
            addProduct();
        else
        if (e.getSource() == view.getBtnPay())
            saveOrder();
    }

    private void saveOrder() {
        String id = JOptionPane.showInputDialog("Enter OrderID for your order: ");
        this.order.setOrderID(Integer.parseInt(id));
        this.order.setDate(valueOf(java.time.LocalDate.now()));
        String usern = JOptionPane.showInputDialog("Enter a Name for your order: ");
        this.order.setCustomerName(usern);
        this.order.setTotalTax(((this.order.getTotalCost())*0.09));
        this.order.setTotalCost(((this.order.getTotalCost())*1.09));
        dao.saveOrder(this.order);
        JOptionPane.showMessageDialog(null, "This order has been completed!\nYour total with tax is $" + String.format("%.2f",this.order.getTotalCost()));
    }

    private void addProduct() {
        String id = JOptionPane.showInputDialog("Enter ProductID: ");
        ProductModel product = dao.loadProduct(Integer.parseInt(id));
        if (product == null) {
            JOptionPane.showMessageDialog(null, "This product does not exist!");
            return;
        }

        double quantity = Double.parseDouble(JOptionPane.showInputDialog(null,"Enter quantity: "));

        if (quantity < 0 || quantity > product.quantity) {
            JOptionPane.showMessageDialog(null, "This quantity is not valid!");
            return;
        }

        OrderLine line = new OrderLine();
        line.setOrderID(this.order.getOrderID());
        line.setProductID(product.productID);
        line.setQuantity(quantity);
        line.setCost(quantity * product.price);

        product.quantity -= quantity; // update new quantity!!
        dao.saveProduct(product); // and store this product back right away!!!

        order.getLines().add(line);
        order.setTotalCost(order.getTotalCost() + line.getCost());

        Object[] row = new Object[5];
        row[0] = line.getProductID();
        row[1] = product.name;
        row[2] = product.price;
        row[3] = line.getQuantity();
        row[4] = line.getCost();

        this.view.addRow(row);
        this.view.getLabTotal().setText("Total: $" + order.getTotalCost());
        this.view.invalidate();
    }

}