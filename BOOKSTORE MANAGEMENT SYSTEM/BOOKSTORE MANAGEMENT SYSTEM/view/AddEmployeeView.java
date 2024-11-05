package com.example.bookstore.view;

import com.example.bookstore.controller.AdminController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.Date;

public class AddEmployeeView implements ShowView{
    @Override
    public Scene execute(Stage primaryStage) {
        ///////////////////////////////STYLING/////////////////////
        GridPane grid = new GridPane();
        grid.setStyle("-fx-background-color:antiquewhite");
        grid.setPadding(new Insets(60));
        grid.setVgap(13);
        grid.setHgap(13);

        Label firstName = new Label("First Name: ");
        firstName.setStyle("-fx-font-family:'Verdana'; -fx-font-size:14; -fx-font-weight: bold;");
        TextField firstNameF = new TextField();
        firstNameF.setStyle("-fx-font-size:14");
        grid.add(firstName,0,0);
        grid.add(firstNameF,1,0);

        Label lastName = new Label("Last Name: ");
        lastName.setStyle("-fx-font-family:'Verdana'; -fx-font-size:14; -fx-font-weight: bold;");
        TextField lastNameF = new TextField();
        lastNameF.setStyle("-fx-font-size:14");
        grid.add(lastName,0,1);
        grid.add(lastNameF,1,1);

        Label birthday = new Label("Birthday: ");
        birthday.setStyle("-fx-font-family:'Verdana'; -fx-font-size:14; -fx-font-weight: bold;");
        DatePicker birthdayF = new DatePicker();
        birthdayF.setStyle("-fx-font-size:14");
        grid.add(birthday,0,2);
        grid.add(birthdayF,1,2);

        Label phone =new Label("Phone: ");
        phone.setStyle("-fx-font-family:'Verdana'; -fx-font-size:14; -fx-font-weight: bold;");
        TextField phoneF= new TextField();
        phoneF.setStyle("-fx-font-size:14");
        grid.add(phone,0,3);
        grid.add(phoneF,1,3);

        Label email = new Label("Email: ");
        email.setStyle("-fx-font-family:'Verdana'; -fx-font-size:14; -fx-font-weight: bold;");
        TextField emailF= new TextField();
        emailF.setStyle("-fx-font-size:14");
        grid.add(email,0,4);
        grid.add(emailF,1,4);

        Label password = new Label("Password: ");
        password.setStyle("-fx-font-family:'Verdana'; -fx-font-size:14; -fx-font-weight: bold;");
        PasswordField passwordF = new PasswordField();
        passwordF.setStyle("-fx-font-size:14");
        grid.add(password,0,5);
        grid.add(passwordF, 1, 5);

        Label role = new Label("Role: ");
        role.setStyle("-fx-font-family:'Verdana'; -fx-font-size:14; -fx-font-weight: bold;");
        grid.add(role,0,6);
        ComboBox roleF = new ComboBox<String>();
        roleF.setStyle("-fx-font-size:14");
        roleF.getItems().addAll("Librarian", "Manager", "Admin");
        grid.add(roleF,1,6);

        Label salary =new Label("Salary: ");
        salary.setStyle("-fx-font-family:'Verdana'; -fx-font-size:14; -fx-font-weight: bold;");
        TextField salaryF= new TextField();
        salaryF.setStyle("-fx-font-size:14");
        grid.add(salary,0,7);
        grid.add(salaryF,1,7);

        /****NEW****/
        CheckBox canAddBookCheckBox = new CheckBox("Can Add Book");
        canAddBookCheckBox.setStyle("-fx-font-family:'Verdana'; -fx-font-size:14; -fx-font-weight: bold;");
        grid.add(canAddBookCheckBox, 0, 8);

        CheckBox canAddBillCheckBox = new CheckBox("Can Add Bill");
        canAddBillCheckBox.setStyle("-fx-font-family:'Verdana'; -fx-font-size:14; -fx-font-weight: bold;");
        grid.add(canAddBillCheckBox, 1, 8);
        /***********/

        Button addEmployeeBtn = new Button("ADD EMPLOYEE");
        addEmployeeBtn.setStyle("-fx-background-color: darkorange; -fx-text-fill: white; -fx-font-size: 12;  -fx-effect:dropshadow(one-pass-box,GRAY,5,0.0,1,0); -fx-font-family:'Verdana';  -fx-font-weight: bold;");
        grid.add(addEmployeeBtn, 1,9);

        ///////////////////////////////FUNCTION/////////////////////

        AdminController ac = new AdminController();

        addEmployeeBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String firstNameVal = firstNameF.getText();
                String lastNameVal = lastNameF.getText();
                LocalDate birthdayValInLocal= birthdayF.getValue();
                Date birthdayVal = java.sql.Date.valueOf(birthdayValInLocal.atStartOfDay().toLocalDate());
                String phoneVal = phoneF.getText();
                String emailVal = emailF.getText();
                String passwordVal = passwordF.getText();
                double salaryVal = Double.parseDouble(salaryF.getText());
                String roleVal = (String) roleF.getValue();

                boolean canAddBook = canAddBookCheckBox.isSelected();
                boolean canAddBill = canAddBillCheckBox.isSelected();

                boolean employeeIsAdded = ac.isAdded(
                        firstNameVal,
                        lastNameVal,
                        phoneVal,
                        emailVal,
                        passwordVal,
                        roleVal,
                        salaryVal,
                        birthdayVal,
                        canAddBook,
                        canAddBill
                );

                if(!employeeIsAdded){
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setHeaderText("There was an error adding the employee");
                    errorAlert.setContentText("Something went wrong");
                    errorAlert.show();
                } else {
                    Alert success = new Alert(Alert.AlertType.INFORMATION);
                    success.setHeaderText("Done");
                    success.showAndWait();
                    success.close();
                }
            }

        });

        return new Scene(grid);
    }
}