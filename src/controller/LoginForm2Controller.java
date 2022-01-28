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

public class LoginForm2Controller {

    public AnchorPane loginForm2Context;
    public JFXTextField txtUserName;
    public JFXTextField txtPassword;

    // open CashierForm
    public void openCashierForm(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/LoginForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) loginForm2Context.getScene().getWindow();
        window.setScene(new Scene(load));
    }

    // open AdminDashBoard
    public void openAdminDashBoardForm(MouseEvent mouseEvent) throws IOException {
        /*if (txtUserName.getText().equals("admin") && txtPassword.getText().equals("2021")) {
            URL resource = getClass().getResource("../view/AdminDashBoardForm.fxml");
            Parent load = FXMLLoader.load(resource);
            Stage window = (Stage) loginForm2Context.getScene().getWindow();
            window.setScene(new Scene(load));
        } else {
            new Alert(Alert.AlertType.WARNING, "Please Check Your Username Or Password !", ButtonType.CLOSE).show();
        }*/

        URL resource = getClass().getResource("../view/AdminDashBoardForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) loginForm2Context.getScene().getWindow();
        window.setScene(new Scene(load));
    }

    // move to password
    public void moveToPassword(ActionEvent actionEvent) {
        txtPassword.requestFocus();
    }
}
