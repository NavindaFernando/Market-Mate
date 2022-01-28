package controller;

import bo.BoFactory;
import bo.custom.CustomerBO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import dto.CustomerDTO;
import view.tm.CustomerTM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class CashierDashBoardController {
    private final CustomerBO customerBO = (CustomerBO) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.CUSTOMER);
    public Label lblTime;
    public Label lblDate;
    public JFXTextField txtCustomerId;
    public JFXTextField txtCustomerName;
    public JFXTextField txtCustomerAddress;

    public TableView<CustomerTM> tblCustomers;
    public AnchorPane cashierDashBoardContext;
    public JFXButton btnSave;
    public JFXButton btnDelete;
    public JFXButton btnAddNewCustomer;
    public JFXTextField txtCustomerSalary;

    public void initialize() {

        tblCustomers.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblCustomers.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblCustomers.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("address"));
        tblCustomers.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("salary"));

        load();

        tblCustomers.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                btnDelete.setDisable(newValue == null);
                btnSave.setText(newValue != null ? "Update" : "Save");
                btnSave.setDisable(newValue == null);
            if (newValue != null) {
                txtCustomerId.setText(newValue.getId());
                txtCustomerName.setText(newValue.getName());
                txtCustomerAddress.setText(newValue.getAddress());
                txtCustomerSalary.setText(newValue.getSalary());
                txtCustomerId.setDisable(false);
                txtCustomerName.setDisable(false);
                txtCustomerAddress.setDisable(false);
                txtCustomerSalary.setDisable(false);
            }
        });
        txtCustomerAddress.setOnAction(event -> btnSave.fire());
        loadAllCustomers();
    }

    private void loadAllCustomers() {
        tblCustomers.getItems().clear();
        try {
            ArrayList<CustomerDTO> allCustomers = customerBO.getAllCustomer();
            for (CustomerDTO customer : allCustomers) {
                tblCustomers.getItems().add(new CustomerTM(customer.getId(), customer.getName(), customer.getAddress(), customer.getSalary()));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void load() {
        txtCustomerId.clear();
        txtCustomerName.clear();
        txtCustomerAddress.clear();
        txtCustomerSalary.clear();

        txtCustomerId.setDisable(true);
        txtCustomerName.setDisable(true);
        txtCustomerAddress.setDisable(true);
        txtCustomerSalary.setDisable(true);

        txtCustomerId.setEditable(false);
        btnSave.setDisable(true);
        btnDelete.setDisable(true);
    }

    public void btnDisplayTextField(ActionEvent actionEvent) {
        txtCustomerId.setDisable(false);
        txtCustomerName.setDisable(false);
        txtCustomerAddress.setDisable(false);
        txtCustomerSalary.setDisable(false);
        txtCustomerId.clear();
        txtCustomerId.setText(generateNewId());
        txtCustomerName.clear();
        txtCustomerAddress.clear();
        txtCustomerSalary.clear();
        txtCustomerName.requestFocus();
        btnSave.setDisable(false);
        btnSave.setText("Save");
        tblCustomers.getSelectionModel().clearSelection();
    }

    // save on action
    public void btnSave_OnAction(ActionEvent actionEvent) {
        String id = txtCustomerId.getText();
        String name = txtCustomerName.getText();
        String address = txtCustomerAddress.getText();
        String salary = txtCustomerSalary.getText();
        if (!name.matches("[A-z ]+")) {
            new Alert(Alert.AlertType.ERROR, "Invalid name").show();
            txtCustomerName.requestFocus();
            return;
        } else if (!address.matches(".{2,}")) {
            new Alert(Alert.AlertType.ERROR, "Invalid address").show();
            txtCustomerAddress.requestFocus();
            return;
        } else if (!salary.matches(".{2,}")) {
            new Alert(Alert.AlertType.ERROR, "Invalid salary").show();
            txtCustomerSalary.requestFocus();
            return;
        }

        if (btnSave.getText().equalsIgnoreCase("save")) {
            try {
                if (existCustomer(id)) {
                    new Alert(Alert.AlertType.ERROR, id + " already exists").show();
                }
                CustomerDTO customerDTO = new CustomerDTO(id, name, address, salary);
                customerBO.addCustomer(customerDTO);
                tblCustomers.getItems().add(new CustomerTM(id, name, address, salary));
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Failed to save the customer " + e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        // Update customer
        } else {
            try {
                if (!existCustomer(id)) {
                    new Alert(Alert.AlertType.ERROR, "No such customer associated. " + id).show();
                }
                CustomerDTO customerDTO = new CustomerDTO(id, name, address,salary);
                customerBO.updateCustomer(customerDTO);
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Failed to update the customer " + id + e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            CustomerTM selectedCustomer = tblCustomers.getSelectionModel().getSelectedItem();
            selectedCustomer.setName(name);
            selectedCustomer.setAddress(address);
            selectedCustomer.setSalary(salary);
            tblCustomers.refresh();
        }
        btnAddNewCustomer.fire();
    }

    boolean existCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerBO.ifCustomerExist(id);
    }

    // Delete Customer
    public void btnDelete_OnAction(ActionEvent actionEvent) {
        String id = tblCustomers.getSelectionModel().getSelectedItem().getId();
        try {
            if (!existCustomer(id)) {
                new Alert(Alert.AlertType.ERROR, "No such customer associated. " + id).show();
            }
            customerBO.deleteCustomer(id);
            tblCustomers.getItems().remove(tblCustomers.getSelectionModel().getSelectedItem());
            tblCustomers.getSelectionModel().clearSelection();
            load();

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to delete the customer " + id).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // generate Id
    private String generateNewId() {
        try {
            return customerBO.generateNewID();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate a new id " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        if (tblCustomers.getItems().isEmpty()) {
            return "C001";
        } else {
            String id = getLastCustomerId();
            int newCustomerId = Integer.parseInt(id.replace("C", "")) + 1;
            return String.format("C%03d", newCustomerId);
        }
    }

        private String getLastCustomerId() {
            List<CustomerTM> tempCustomersList = new ArrayList<>(tblCustomers.getItems());
            Collections.sort(tempCustomersList);
            return tempCustomersList.get(tempCustomersList.size() - 1).getId();
        }

        // date & time
        private void loadDateAndTime () {
            Date date = new Date();
            SimpleDateFormat f = new SimpleDateFormat("yyyy - MM - dd");
            lblDate.setText(f.format(date));

            Timeline time = new Timeline(new KeyFrame(Duration.ZERO, event -> {
                LocalTime currentTime = LocalTime.now();
                lblTime.setText(
                        currentTime.getHour() + " : " + currentTime.getMinute() + " : " + currentTime.getSecond()
                );
            }),
                    new KeyFrame(Duration.seconds(1)));
            time.setCycleCount(Animation.INDEFINITE);
            time.play();
        }

        // open Place Order Form
        public void openPlaceOrderForm(ActionEvent actionEvent) throws IOException {
            URL resource = getClass().getResource("../view/PlaceOrderForm.fxml");
            Parent load = FXMLLoader.load(resource);
            Stage window = (Stage) cashierDashBoardContext.getScene().getWindow();
            window.setScene(new Scene(load));
        }

        // open Login Form
        public void openLogin (MouseEvent mouseEvent) throws IOException {
            URL resource = getClass().getResource("../view/LoginForm.fxml");
            Parent load = FXMLLoader.load(resource);
            Stage window = (Stage) cashierDashBoardContext.getScene().getWindow();
            window.setScene(new Scene(load));
        }

        // clear Text Field
        public void clearTextField (MouseEvent mouseEvent){
            txtCustomerId.clear();
            txtCustomerName.clear();
            txtCustomerAddress.clear();

        }
}


