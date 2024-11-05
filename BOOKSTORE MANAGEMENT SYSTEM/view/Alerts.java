package com.example.bookstore.view;

import com.example.bookstore.controller.BookController;
import com.example.bookstore.model.Librarian;
import com.example.bookstore.model.User;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

public class Alerts implements ShowView {
    private User selectedLibrarian;
    private ArrayList<User> allEmployees;
    private File file;

    public Alerts(){
        allEmployees = new ArrayList<>();
        file = new File("allEmployees.bin");

        if(file.exists()){
            readEmployees();
        }
    }


    private User findLibrarianByName(String LibrarianName) {
        for (User user : allEmployees) {
            String fullName = user.getFName() + " " + user.getLName();
            if (fullName.equals(LibrarianName)) {
                return user;
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")

    private void readEmployees(){
        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);

            allEmployees = (ArrayList<User>) ois.readObject();

            fis.close();
            ois.close();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }


    public Scene execute(Stage stage) {
        VBox screenBox=new VBox();
        screenBox.setStyle("-fx-background-color:antiquewhite");
        //***************************************************************************************** BOOK ALERTS

        VBox vbox = new VBox();
        vbox.setStyle("-fx-background-color:antiquewhite");

        Label booksData = new Label("BOOKS DATA");
        booksData.setStyle("-fx-font-family:'Verdana'; -fx-font-size:15; -fx-font-weight: bold;");
        BookController bc = new BookController();

        Label selectFilter = new Label("Select a Filter ");
        selectFilter.setStyle("-fx-font-family:'Verdana'; -fx-font-weight: bold; -fx-font-size:13;");
        ComboBox<String> comboboxFilters = new ComboBox<>();
        comboboxFilters.getItems().addAll("Today", "Total", "Monthly");
        LocalDate todayVal = LocalDate.now();
        Date today = java.sql.Date.valueOf(todayVal.atStartOfDay().toLocalDate());
        VBox filterVBox = new VBox();
        filterVBox.setStyle("-fx-background-color:antiquewhite");

        Button find = new Button("Display");
        find.setMinWidth(70);
        find.setStyle("-fx-background-color: darkorange; -fx-text-fill: white; -fx-font-size: 12;  -fx-effect:dropshadow(one-pass-box,GRAY,5,0.0,1,0); -fx-font-family:'Verdana';  -fx-font-weight: bold;");

        Text booksBought = new Text();
        booksBought.setStyle("-fx-font-family:'Verdana'; -fx-font-size:12; -fx-font-weight: bold;");

        Label startDateLabel = new Label("Start Date:");
        startDateLabel.setStyle("-fx-font-family:'Verdana'; -fx-font-weight: bold; -fx-font-size:13;");
        DatePicker startDatePicker = new DatePicker();

        Label endDateLabel = new Label("End Date:");
        endDateLabel.setStyle("-fx-font-family:'Verdana'; -fx-font-weight: bold; -fx-font-size:13;");
        DatePicker endDatePicker = new DatePicker();

        find.setOnAction(e -> {
            if (comboboxFilters.getValue().equals("Total")) {
                booksBought.setText("Total Nr of Books Sold: " + bc.nrOfBooksSold());
            } else if (comboboxFilters.getValue().equals("Monthly")) {
                if (startDatePicker.getValue() != null && endDatePicker.getValue() != null) {
                    LocalDate startOfMonthVal = startDatePicker.getValue();
                    Date startOfMonth = java.sql.Date.valueOf(startOfMonthVal);
                    Date endOfMonth = java.sql.Date.valueOf(endDatePicker.getValue());

                    booksBought.setText("Total Nr of Books Sold Monthly: " + bc.nrOfBooksSoldMonthly(startOfMonth, endOfMonth));
                } else {
                    booksBought.setText("Please select both start and end dates for Monthly filter.");
                }
            } else if (comboboxFilters.getValue().equals("Daily")) {
                Date date = Date.from(todayVal.atStartOfDay(ZoneId.systemDefault()).toInstant());
                booksBought.setText("Total Nr of Books Sold Daily: " + bc.nrOfBooksSoldDaily(date));
            } else {
                booksBought.setText("Total Nr of Books Sold: 0");
            }
        });

        comboboxFilters.valueProperty().addListener((obs, oldVal, newVal) -> {
            filterVBox.getChildren().clear();

            if ("Monthly".equals(newVal)) {
                filterVBox.getChildren().addAll(startDateLabel, startDatePicker, endDateLabel, endDatePicker);
            }
        });

        vbox.getChildren().addAll(booksData, selectFilter, comboboxFilters, filterVBox, find, booksBought);
        vbox.setPadding(new Insets(60,40,60,50));
        vbox.setSpacing(15);





        /*****************************LIBRARIAN DATA**********/
        VBox vbox2  = new VBox();
        vbox2.setStyle("-fx-background-color:antiquewhite");
        VBox filterVBox2 = new VBox();

        Label LibrarianData = new Label("LIBRARIAN DATA");
        LibrarianData.setStyle("-fx-font-family:'Verdana'; -fx-font-size:15; -fx-font-weight: bold;");
        Label selectLibrarian = new Label("Select a Librarian ");
        selectLibrarian.setStyle("-fx-font-family:'Verdana'; -fx-font-weight: bold; -fx-font-size:13;");
        ComboBox<String> libCombo = new ComboBox<>();

        Text libData = new Text();
        libData.setStyle("-fx-font-family:'Verdana'; -fx-font-size:12; -fx-font-weight: bold;");

        Text libData1 = new Text();
        libData1.setStyle("-fx-font-family:'Verdana'; -fx-font-size:12; -fx-font-weight: bold;");

        Text libData2 = new Text();
        libData2.setStyle("-fx-font-family:'Verdana'; -fx-font-size:12; -fx-font-weight: bold;");

        Text libData3 = new Text();
        libData3.setStyle("-fx-font-family:'Verdana'; -fx-font-size:12; -fx-font-weight: bold;");

        Label startDateLabel1 = new Label("Start Date:");
        startDateLabel1.setStyle("-fx-font-family:'Verdana'; -fx-font-weight: bold; -fx-font-size:13;");

        DatePicker startDatePicker1 = new DatePicker();
        ComboBox<String> datePick = new ComboBox<>();
        datePick.getItems().addAll("Today", "Monthly", "Total");

        Label endDateLabel1 = new Label("End Date:");
        endDateLabel1.setStyle("-fx-font-family:'Verdana'; -fx-font-weight: bold; -fx-font-size:13;");
        DatePicker endDatePicker1 = new DatePicker();

        //adding librarians in the combobox
        for(User user: allEmployees){
            if(user.getRole().toLowerCase().equals("librarian")) {
                String fullName = user.getFName() + " " + user.getLName();
                libCombo.getItems().add(fullName);
            }
        }

        Button select = new Button("Select");
        select.setMinWidth(70);
        select.setStyle("-fx-background-color: darkorange; -fx-text-fill: white; -fx-font-size: 12;  -fx-effect:dropshadow(one-pass-box,GRAY,5,0.0,1,0); -fx-font-family:'Verdana';  -fx-font-weight: bold;");

        select.setOnAction(e->{
            String selectedLibrarianName = libCombo.getValue();
            selectedLibrarian = findLibrarianByName(selectedLibrarianName);
            Librarian librarian= new Librarian(selectedLibrarian.getFName(),selectedLibrarian.getFName(),selectedLibrarian.getPhone(),selectedLibrarian.getEmail(),selectedLibrarian.getPassword(),selectedLibrarian.getRole(),selectedLibrarian.getSalary(),selectedLibrarian.getBirthday(),selectedLibrarian.getCanAddBook(),selectedLibrarian.getCanAddBill());
            if (selectedLibrarian != null) {
                if (datePick.getValue().equals("Total")) {
                    libData1.setText("Total Nr of Books Sold: " + librarian.nrOfBooks());
                    libData2.setText("Total Nr of Bills: " + librarian.nrOfBills());
                    libData3.setText("Total Amount of Money made: " + librarian.moneyMade());
                } else if (datePick.getValue().equals("Monthly")) {
                    if (startDatePicker1.getValue() != null && endDatePicker1.getValue() != null) {
                        LocalDate startOfMonthVal1 = startDatePicker1.getValue();
                        Date startOfMonth1 = java.sql.Date.valueOf(startOfMonthVal1);
                        Date endOfMonth1 = java.sql.Date.valueOf(endDatePicker1.getValue());

                        libData1.setText("Nr of Books Sold Monthly: " + librarian.nrOfBooks(startOfMonth1, endOfMonth1));
                        libData2.setText("Nr of Bills per Month: " + librarian.nrOfBills(startOfMonth1, endOfMonth1));
                        libData3.setText("Amount of Money made per Month: " + librarian.moneyMade(startOfMonth1, endOfMonth1));
                    } else {
                        libData1.setText("Please select both start and end dates for Monthly filter.");
                    }
                } else if (datePick.getValue().equals("Today")) {
                    Date date1 = Date.from(todayVal.atStartOfDay(ZoneId.systemDefault()).toInstant());
                    libData1.setText("Nr of Books Sold Today: " + librarian.nrOfBooks(date1));
                    libData2.setText("Total Nr of Bills Today: " + librarian.nrOfBills(date1));
                    libData3.setText("Total Amount of Money made Today: " + librarian.moneyMade(date1));
                } else {
                    libData1.setText("Nr of Books Sold: 0");
                    libData2.setText("Nr of Bills: 0");
                    libData3.setText("Amount of Money made: 0");
                }
            }

        });



        datePick.valueProperty().addListener((obs, oldVal, newVal) -> {
            filterVBox2.getChildren().clear();

            if ("Monthly".equals(newVal)) {
                filterVBox2.getChildren().addAll(startDateLabel1, startDatePicker1, endDateLabel1, endDatePicker1);
            }
        });



        vbox2.getChildren().addAll(LibrarianData,selectLibrarian, libCombo, datePick, filterVBox2, select,libData1, libData2, libData3);
        vbox2.setSpacing(15);
        vbox2.setPadding(new Insets(60,40,60,90));

        HBox hbox=new HBox();
        hbox.getChildren().addAll(vbox, vbox2);

        screenBox.getChildren().addAll(hbox);

        return new Scene(screenBox);

    }
}