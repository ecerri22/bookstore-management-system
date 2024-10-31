package com.example.bookstore.view;

import com.example.bookstore.controller.AdminController;
import com.example.bookstore.controller.ManagerController;
import com.example.bookstore.controller.TransactionController;
import com.example.bookstore.model.Book;
import com.example.bookstore.model.Transaction;
import com.example.bookstore.model.User;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class StatsView implements ShowView {
    //FOR COSTS
    private DatePicker startDatePickerCost;
    private DatePicker endDatePickerCost;
    //FOR INCOMES
    private DatePicker startDatePickerInc;
    private DatePicker endDatePickerInc;

    public StatsView() {}

    @Override
    public Scene execute(Stage primaryStage) {
        AdminController adminController = new AdminController();
        ManagerController managerController = new ManagerController();
        TransactionController transactionController = new TransactionController();

        VBox vbox = new VBox();
        vbox.setStyle("-fx-background-color:antiquewhite");

        /*****************CALCULATE COSTS - SALARIES*******************/
        ArrayList<User> allEmployees = adminController.getAllEmployees();

        double totalSalary = calculateTotalSalary(allEmployees);
        Text totalSalaryText = new Text("Total Salary: " + totalSalary);
        totalSalaryText.setStyle("-fx-font-family:'Verdana'; -fx-font-size:15; -fx-font-weight: bold;");

        /***************CALCULATE COSTS - BOOKS PURCHASES*************/
        ArrayList<Book> allBooks = managerController.getAllBooks();

        /**********FILTER BY DATE*************/
        startDatePickerCost = new DatePicker();
        startDatePickerCost.setPromptText("Select Start Date");
        startDatePickerCost.setStyle("-fx-font-size:12;");

        endDatePickerCost = new DatePicker();
        endDatePickerCost.setPromptText("Select End Date");
        endDatePickerCost.setStyle("-fx-font-size:12;");

        Button submitButton = new Button("Submit");
        submitButton.setStyle("-fx-background-color: darkorange; -fx-text-fill: white; -fx-font-size: 12;  -fx-effect:dropshadow(one-pass-box,GRAY,5,0.0,1,0); -fx-font-family:'Verdana';  -fx-font-weight: bold;");

        Text totalPurchasesByDateText = new Text();
        totalPurchasesByDateText.setStyle("-fx-font-family:'Verdana'; -fx-font-size:12;");

        submitButton.setOnAction(e -> {
            LocalDate startDate = startDatePickerCost.getValue();
            LocalDate endDate = endDatePickerCost.getValue();

            if (startDate != null && endDate != null) {
                Date start = java.sql.Date.valueOf(startDate);
                Date end = java.sql.Date.valueOf(endDate);
                double totalPurchasesByDate = calculateTotalPurchasesByDate(allBooks, start, end);

                totalPurchasesByDateText.setText("Total Purchases from " +
                        start +
                        " until " +
                        end + " is " +
                        totalPurchasesByDate);
                totalPurchasesByDateText.setStyle("-fx-font-family:'Verdana'; -fx-font-size:13;");

                startDatePickerCost.setValue(null);
                endDatePickerCost.setValue(null);

                if (!vbox.getChildren().contains(totalPurchasesByDateText)) {
                    vbox.getChildren().add(totalPurchasesByDateText);
                }
            } else {
                System.out.println("Please select both start and end dates.");
            }
        });

        /***************CALCULATE INCOMES - TRANSACTIONS*************/
        ArrayList<Transaction> allTransactions = transactionController.getAllTransactions();

        /**********FILTER BY DATE*************/
        startDatePickerInc = new DatePicker();
        startDatePickerInc.setPromptText("Select Start Date");
        startDatePickerInc.setStyle("-fx-font-size:12;");

        endDatePickerInc = new DatePicker();
        endDatePickerInc.setPromptText("Select End Date");
        startDatePickerInc.setStyle("-fx-font-size:12;");

        Button submitButtonInc = new Button("Submit");
        submitButtonInc.setStyle("-fx-background-color: darkorange; -fx-text-fill: white; -fx-font-size: 12;  -fx-effect:dropshadow(one-pass-box,GRAY,5,0.0,1,0); -fx-font-family:'Verdana';  -fx-font-weight: bold;");

        Text totalPurchasesByDateTextInc = new Text();
        totalPurchasesByDateTextInc.setStyle("-fx-font-family:'Verdana'; -fx-font-size:12; -fx-font-weight: bold;");

        submitButtonInc.setOnAction(e -> {
            LocalDate startDate = startDatePickerInc.getValue();
            LocalDate endDate = endDatePickerInc.getValue();

            if (startDate != null && endDate != null) {
                Date start = java.sql.Date.valueOf(startDate);
                Date end = java.sql.Date.valueOf(endDate);
                double totaIncomesByDate = calculateTotalIncomesByDates(allTransactions, start, end);

                totalPurchasesByDateTextInc.setText("Total Incomes from " +
                        start +
                        " until " +
                        end + " is " +
                        totaIncomesByDate);
                totalPurchasesByDateTextInc.setStyle("-fx-font-family:'Verdana'; -fx-font-size:13;");


                startDatePickerInc.setValue(null);
                endDatePickerInc.setValue(null);

                if (!vbox.getChildren().contains(totalPurchasesByDateTextInc)) {
                    vbox.getChildren().add(totalPurchasesByDateTextInc);
                }
           } else {
               System.out.println("Please select both start and end dates.");
            }
        });


        // VBox for costs
        VBox costVBox = new VBox();
        Label purchases=new Label("Purchases");
        purchases.setStyle("-fx-font-family:'Verdana'; -fx-font-size:15; -fx-font-weight: bold;");
        costVBox.setPadding(new Insets(150,10,10,10));
        costVBox.setSpacing(20);
        costVBox.getChildren().addAll(purchases,startDatePickerCost, endDatePickerCost, submitButton, totalPurchasesByDateText,totalSalaryText);

        // VBox for incomes
        VBox incomeVBox = new VBox();
        Label incomes=new Label("Incomes");
        incomes.setStyle("-fx-font-family:'Verdana'; -fx-font-size:15; -fx-font-weight: bold;");
        incomeVBox.setPadding(new Insets(150,10,10,10));
        incomeVBox.setSpacing(20);
        incomeVBox.getChildren().addAll(incomes,startDatePickerInc, endDatePickerInc, submitButtonInc, totalPurchasesByDateTextInc);

        costVBox.setPrefWidth(primaryStage.getWidth() / 2);
        incomeVBox.setPrefWidth(primaryStage.getWidth() / 2);


        HBox hbox = new HBox();
        hbox.getChildren().addAll(costVBox, incomeVBox);

        vbox.getChildren().addAll(hbox);

        return new Scene(vbox);
    }

    /***********FOR BOOKS PURCHASES***********/
    private double calculateTotalPurchasesByDate(ArrayList<Book> allBooks, Date startDate, Date endDate) {
        double totalCost = 0;

        for (Book book : allBooks) {
            Date purchasedDate = book.getPurchasedDate();
            if (purchasedDate != null && !purchasedDate.before(startDate) && !purchasedDate.after(endDate)) {
                totalCost += (book.getPurchasedPrice() * book.getStock());
            }
        }

        return totalCost;
    }

    /***********FOR SALARIES***********/
    private double calculateTotalSalary(ArrayList<User> allEmployees) {
        double totalSalary = 0;

        for (User employee : allEmployees) {
            totalSalary += employee.getSalary();
        }

        return totalSalary;
    }

    /***********FOR TRANSACTIONS INCOMES***********/
    private double calculateTotalIncomesByDates(ArrayList<Transaction> allTransactions, Date startDate, Date endDate) {
        double totalIncomes = 0;

        for (Transaction transaction : allTransactions) {
            Date purchasedDate = transaction.getTransactionDate();
            if (purchasedDate != null && !purchasedDate.before(startDate) && !purchasedDate.after(endDate)) {
                totalIncomes += transaction.getPrice();
            }
        }
        return totalIncomes;
    }
}