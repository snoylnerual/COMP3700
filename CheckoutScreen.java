import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CheckoutScreen extends JFrame {

    private JButton btnAdd = new JButton("Add a New Item");
    private JButton btnPay = new JButton("Finish and Pay");

    private DefaultTableModel items = new DefaultTableModel(); // store information for the table!

    private JTable tblItems = new JTable(items); // null, new String[]{"ProductID", "Product Name", "Price", "Quantity", "Cost"});
    private JLabel labTotal = new JLabel("Total: ");

    public CheckoutScreen() {
        this.setTitle("Buyer Checkout Screen");
        this.setSize(new Dimension(900, 700));
        this.setLocation(new Point(400,100));
        this.setTitle("Checkout");
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

        items.addColumn("Product ID");
        items.addColumn("Name");
        items.addColumn("Price");
        items.addColumn("Quantity");
        items.addColumn("Cost");
        tblItems.setFont(new Font("Arial",Font.PLAIN,25));
        tblItems.setRowHeight(25);

        btnAdd.setFont(new Font("Arial",Font.PLAIN,25));
        btnPay.setFont(new Font("Arial",Font.PLAIN,25));
        labTotal.setFont(new Font("Arial",Font.PLAIN,25));

        JPanel panelOrder = new JPanel();
        panelOrder.setPreferredSize(new Dimension(400, 450));
        panelOrder.setLayout(new BoxLayout(panelOrder, BoxLayout.PAGE_AXIS));
        tblItems.setBounds(0, 0, 400, 350);
        panelOrder.add(tblItems.getTableHeader());
        panelOrder.add(tblItems);
        panelOrder.add(labTotal);
        tblItems.setFillsViewportHeight(true);
        this.getContentPane().add(panelOrder);

        JPanel panelButton = new JPanel();
        panelButton.setPreferredSize(new Dimension(400, 100));
        panelButton.add(btnAdd);
        panelButton.add(btnPay);
        this.getContentPane().add(panelButton);

    }

    public JButton getBtnAdd() {
        return btnAdd;
    }

    public JButton getBtnPay() {
        return btnPay;
    }

    public JLabel getLabTotal() {
        return labTotal;
    }

    public void addRow(Object[] row) {
        items.addRow(row);
        // add a row to list of item!
        //    items.fireTableDataChanged();
    }
}
