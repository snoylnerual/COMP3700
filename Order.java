import java.sql.Date;
import java.util.List;
import java.util.ArrayList;


public class Order {
    public int orderID;
    public String customerName;
    public double totalCost;
    public double totalTax;
    public String date;

    public List<OrderLine> lines;

    public Order() {
        lines = new ArrayList<>();
    }

    public void addLine(OrderLine line) {
        lines.add(line);
    }

    public void removeLine(OrderLine line) {
        lines.remove(line);
    }

    public List<OrderLine> getLines() {
        return lines;
    }
}
