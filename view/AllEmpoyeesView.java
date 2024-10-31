package com.example.bookstore.view;

import com.example.bookstore.controller.AdminController;
import com.example.bookstore.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;

import java.time.LocalDate;
import java.util.Date;

public class AllEmpoyeesView implements com.example.bookstore.view.ShowView {
    private AdminController adminController;
    TableView<User> allEmployeesTable = new TableView<>();

    public AllEmpoyeesView(AdminController adminController) {
        this.adminController = adminController;
    }

    public AllEmpoyeesView() {}

    public void editSelectedCell(TablePosition<User, ?> position, TableView<User> allEmployeesTable) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Edit Cell");
        dialog.setHeaderText(null);

        User selectedUser = allEmployeesTable.getSelectionModel().getSelectedItem();



        if (selectedUser != null) {
            int rowIndex = position.getRow();
            String columnHeader = position.getTableColumn().getText();

            switch (columnHeader) {
                case "First Name":
                    dialog.setContentText("Enter new first name:");
                    dialog.showAndWait().ifPresent(newValue -> {
                        adminController.modifyFName(selectedUser, newValue);
                    });
                    break;
                case "Last Name":
                    dialog.setContentText("Enter new last name:");
                    dialog.showAndWait().ifPresent(newValue -> {
                        adminController.modifyLName(selectedUser, newValue);
                    });
                    break;
                case "Birthday":
                    DatePicker datePicker = new DatePicker();
                    dialog.getDialogPane().setContent(datePicker);
                    dialog.setTitle("Edit Birthday");

                    dialog.showAndWait().ifPresent(result -> {
                        LocalDate newDate = datePicker.getValue();
                        if (newDate != null) {
                            Date newValue = java.sql.Date.valueOf(newDate);
                            adminController.modifyBday(selectedUser, newValue);
                        }
                    });
                    break;
                case "Phone":
                    dialog.setContentText("Enter new phone number:");
                    dialog.showAndWait().ifPresent(newValue -> {
                        adminController.modifyPhone(selectedUser, newValue);
                    });
                    break;
                case "Email":
                    dialog.setContentText("Enter new email address:");
                    dialog.showAndWait().ifPresent(newValue -> {
                        adminController.modifyEmail(selectedUser, newValue);
                    });
                    break;
                case "Salary":
                    dialog.setContentText("Enter new salary:");
                    dialog.showAndWait().ifPresent(newValue -> {
                        adminController.modifySalary(selectedUser, Double.parseDouble(newValue));
                    });
                    break;
                case "Password":
                    dialog.setContentText("Enter new password:");
                    dialog.showAndWait().ifPresent(newValue -> {
                        adminController.modifyPassword(selectedUser, newValue);
                    });
                    break;
                case "Access Level":
                    dialog.setContentText("Enter new access level:");
                    dialog.showAndWait().ifPresent(newValue -> {
                        adminController.modifyRole(selectedUser, newValue);
                    });
                    break;
                case "Adds Bills":
                    dialog.setContentText("Enter if user is permitted to add BILLS or not:");
                    dialog.showAndWait().ifPresent(newValue -> {
                        adminController.modifyCanAddBill(selectedUser, Boolean.parseBoolean(newValue));
                    });
                    break;
                case "Adds Books":
                    dialog.setContentText("Enter if user is permitted to add BOOKS or not:");
                    dialog.showAndWait().ifPresent(newValue -> {
                        adminController.modifyCanAddBook(selectedUser, Boolean.parseBoolean(newValue));
                    });
                    break;
            }
        }

        allEmployeesTable.refresh();
    }

    @Override
    public Scene execute(Stage primaryStage) {
        //EDITABLE
        allEmployeesTable.setEditable(true);

        ObservableList<User> employees = FXCollections.observableArrayList(adminController.getAllEmployees());
        allEmployeesTable.setItems(employees);

        TableColumn<User, String> empName = new TableColumn<>("First Name");
        empName.setCellValueFactory(new PropertyValueFactory<>("FName"));

        TableColumn<User, String> empLName = new TableColumn<>("Last Name");
        empLName.setCellValueFactory(new PropertyValueFactory<>("LName"));

        TableColumn<User, LocalDate> empBday = new TableColumn<>("Birthday");
        empBday.setCellValueFactory(new PropertyValueFactory<>("birthday"));

        TableColumn<User, String> empPhone = new TableColumn<>("Phone");
        empPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));

        TableColumn<User, String> empEmail = new TableColumn<>("Email");
        empEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<User, String> empPassword = new TableColumn<>("Password");
        empPassword.setCellValueFactory(new PropertyValueFactory<>("password"));

        TableColumn<User, Double> empSalary = new TableColumn<>("Salary");
        empSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));

        TableColumn<User, String> empAccessLevel = new TableColumn<>("Access Level");
        empAccessLevel.setCellValueFactory(new PropertyValueFactory<>("role"));

        TableColumn<User, String> empCanAddBook = new TableColumn<>("Adds Books");
        empCanAddBook.setCellValueFactory(new PropertyValueFactory<>("canAddBook"));

        TableColumn<User, String> empCanAddBill = new TableColumn<>("Adds Bills");
        empCanAddBill.setCellValueFactory(new PropertyValueFactory<>("canAddBill"));

        allEmployeesTable.getColumns().addAll(empName, empLName, empBday, empPhone, empEmail, empSalary, empPassword, empAccessLevel, empCanAddBook, empCanAddBill);
        allEmployeesTable.setStyle("-fx-background-color:transparent; -fx-font-size: 13; -fx-font-family:'Verdana';");
        VBox vbox = new VBox();
        vbox.getChildren().add(allEmployeesTable);
        vbox.setPadding(new Insets(50,20,20,20));
        vbox.setStyle("-fx-background-color:antiquewhite");

        Button editButton = new Button("Edit");
        editButton.setStyle("-fx-background-color: darkorange; -fx-text-fill: white; -fx-font-size: 13;  -fx-effect:dropshadow(one-pass-box,GRAY,5,0.0,1,0); -fx-font-family:'Verdana';  -fx-font-weight: bold;");
        editButton.setMinWidth(70);

        Button deleteButton = new Button("Delete");
        deleteButton.setStyle("-fx-background-color: darkorange; -fx-text-fill: white; -fx-font-size: 13;  -fx-effect:dropshadow(one-pass-box,GRAY,5,0.0,1,0); -fx-font-family:'Verdana';  -fx-font-weight: bold;");
        deleteButton.setMinWidth(70);

        deleteButton.setOnAction(e -> {
            User selectedUser = allEmployeesTable.getSelectionModel().getSelectedItem();
            if (selectedUser != null) {
                adminController.isDeleted(selectedUser);
                employees.remove(selectedUser);
            }
        });

        editButton.setOnAction(e -> {
            TablePosition<User, ?> position = allEmployeesTable.getSelectionModel().getSelectedCells().get(0);
            editSelectedCell(position, allEmployeesTable);
        });

        HBox buttonBox = new HBox();
        buttonBox.setPadding(new Insets(40,0,0,0));
        buttonBox.setSpacing(30);
        buttonBox.getChildren().addAll(editButton, deleteButton);
        vbox.getChildren().add(buttonBox);

        return new Scene(vbox);
    }
}