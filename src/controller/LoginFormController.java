package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class LoginFormController {
    public AnchorPane loginFormContext;
    public JFXTextField txtUserName;
    public JFXTextField txtPassword;

    // open Admin Form
    public void openAdminForm(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/LoginForm2.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) loginFormContext.getScene().getWindow();
        window.setScene(new Scene(load));
    }

    // open CashierDashBoard
    public void openCashierDashBoardForm(MouseEvent mouseEvent) throws IOException {

        /*if (txtUserName.getText().equals("cashier") && txtPassword.getText().equals("2021")) {
            URL resource = getClass().getResource("../view/CashierDashBoard.fxml");
            Parent load = FXMLLoader.load(resource);
            Stage window = (Stage) loginFormContext.getScene().getWindow();
            window.setScene(new Scene(load));
        } else {
            new Alert(Alert.AlertType.WARNING, "Please Check Your Username Or Password !", ButtonType.CLOSE).show();
        }*/

        URL resource = getClass().getResource("../view/CashierDashBoard.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) loginFormContext.getScene().getWindow();
        window.setScene(new Scene(load));
    }

    // move to password
    public void moveToPassword(ActionEvent actionEvent) {
    txtPassword.requestFocus();
    }
}
