import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController implements ActionListener {
    private LoginScreen myLoginScreen;
    private DataAccess myDAO;


    public LoginController(LoginScreen loginScreen, DataAccess dao) {
        this.myLoginScreen = loginScreen;
        this.myDAO = dao;
        this.myLoginScreen.btnLogin.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == myLoginScreen.btnLogin) {
            String username = myLoginScreen.txtUserName.getText().trim();
            String password = myLoginScreen.txtPassword.getText().trim();
            LoginModel user = myDAO.loadUser(username, password);
            if (user == null) {
                JOptionPane.showMessageDialog(null, "This user does not exist!");
            }
            else {
                if (user.UserType == 0) {
                    StoreManager.getInstance().getProductView().setVisible(true);
                    StoreManager.getInstance().getLoginScreen().setVisible(false);
                }
                else if (user.UserType == 1) {
                    StoreManager.getInstance().getCheckoutScreen().setVisible(true);
                    StoreManager.getInstance().getLoginScreen().setVisible(false);
                }
            }
        }
    }
}
