import javax.swing.*;
import java.awt.*;

public class LoginScreen extends JFrame {
    public JTextField txtUserName = new JTextField();
    public JTextField txtPassword = new JTextField();
    public JButton    btnLogin    = new JButton("Login");

    public LoginScreen() {


        this.setSize(1000, 800);
        this.setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.white);
        this.setTitle("Login Page");
        this.setLocation(new Point(400,100));

        this.setSize(new Dimension(1000,800));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panelTop = new JPanel();
        panelTop.setLayout(null);
        panelTop.setBounds(0, 0, 1000, 105);
        JLabel label = new JLabel ("Store Management System");
        label.setFont(new Font("Arial Rounded MT Bold",Font.PLAIN,60));
        label.setBounds(100, 0, 1000, 100);
        panelTop.add(label);
        panelTop.setBorder(BorderFactory.createLineBorder(Color.black));

        JPanel panelLogin = new JPanel();
        panelLogin.setLayout(null);
        panelLogin.setBounds(320,175,300,400);
        panelLogin.setBorder(BorderFactory.createLineBorder(Color.black));

        JLabel label1 = new JLabel("Username");
        label1.setFont(new Font("Arial",Font.PLAIN,20));
        label1.setBounds(50, 50, 200, 30);
        txtUserName.setBounds(50,100,193,30);
        txtUserName.setFont(new Font("Arial",Font.PLAIN,20));
        JLabel label2 = new JLabel("Password");
        label2.setFont(new Font("Arial",Font.PLAIN,20));
        label2.setBounds(50,150,200,30);
        txtPassword.setBounds(50,200,193,30);
        txtPassword.setFont(new Font("Arial",Font.PLAIN,20));

        btnLogin.setBounds(90,330,120,50);
        btnLogin.setFont(new Font("Arial",Font.PLAIN,20));

        panelLogin.add(label1);
        panelLogin.add(txtUserName);
        panelLogin.add(label2);
        panelLogin.add(txtPassword);
        panelLogin.add(btnLogin);

        panel.add(panelLogin);
        panel.add(panelTop);
        this.add(panel);

    }
}
