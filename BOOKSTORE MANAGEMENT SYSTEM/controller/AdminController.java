package com.example.bookstore.controller;

import com.example.bookstore.model.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;

public class AdminController {
    public ArrayList<User> allEmployees;
    private File file;

    public AdminController(){
        allEmployees = new ArrayList<>();
        file = new File("allEmployees.bin");

        if(file.exists()){
            readEmployees();
        }
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

    private void writeAllEmployees(){
        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(allEmployees);
            oos.close();
            fos.close();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void addInAllEmployees(User user){
        this.allEmployees.add(user);
    }

    public boolean isAdded(String firstName, String lastName, String phone, String email, String password, String role, double salary, Date birthday, boolean canAddBook, boolean canAddBill) {
        User newUser;
        if(isValidEmail(email) && salary>=0 && !isEmailExists(email)) {

            switch (role) {
                case "Librarian":
                    newUser = new Librarian(firstName, lastName, phone, email, password, role, salary, birthday, canAddBook, canAddBill);
                    break;
                case "Manager":
                    newUser = new Manager(firstName, lastName, phone, email, password, role, salary, birthday, canAddBook, canAddBill);
                    break;
                case "Admin":
                    newUser = new Administrator(firstName, lastName, phone, email, password, role, salary, birthday, canAddBook, canAddBill);
                    break;
                default:
                    newUser = new User(firstName, lastName, phone, email, password, role, salary, birthday, canAddBook, canAddBill);
                    break;
            }


            this.addInAllEmployees(newUser);
            writeAllEmployees();

            return true;
        }
        return false;
    }


    public boolean isDeleted(User employeeToDelete){
        allEmployees.removeIf(user -> user.equals(employeeToDelete));
        writeAllEmployees();
        return true;
    }

    public User findEmployee(User employeeToFind) {
        for(User employee : allEmployees){
            if(employee.equals(employeeToFind)){
                return employee;
            }
        }
        return null;
    }

    public boolean modifyFName(User employeeToEdit, String modifyFName) {
        this.findEmployee(employeeToEdit).setFName(modifyFName);
        writeAllEmployees();
        return true;
    }

    public boolean modifyLName(User employeeToEdit, String modifyLName) {
        this.findEmployee(employeeToEdit).setLName(modifyLName);
        writeAllEmployees();
        return true;
    }

    public boolean modifyBday(User employeeToEdit, Date modifyBday) {
        this.findEmployee(employeeToEdit).setBirthday(modifyBday);
        writeAllEmployees();
        return true;
    }

    public boolean modifyPhone(User employeeToEdit, String modifyPhone) {
        this.findEmployee(employeeToEdit).setPhone(modifyPhone);
        writeAllEmployees();
        return true;
    }

    public boolean modifyEmail(User employeeToEdit, String modifyEmail) {
        this.findEmployee(employeeToEdit).setEmail(modifyEmail);
        writeAllEmployees();
        return true;
    }

    public boolean modifyPassword(User employeeToEdit, String modifyPassword) {
        this.findEmployee(employeeToEdit).setPassword(modifyPassword);
        writeAllEmployees();
        return true;
    }

    public boolean modifySalary(User employeeToEdit, double modifySalary) {
        this.findEmployee(employeeToEdit).setSalary(modifySalary);
        writeAllEmployees();
        return true;
    }

    public boolean modifyRole(User employeeToEdit, String modifyRole) {
        this.findEmployee(employeeToEdit).setRole(modifyRole);
        writeAllEmployees();
        return true;
    }

    public boolean modifyCanAddBook(User employeeToEdit, boolean newPermission) {
        this.findEmployee(employeeToEdit).setCanAddBook(newPermission);
        writeAllEmployees();
        return true;
    }

    public boolean modifyCanAddBill(User employeeToEdit, boolean newPermission) {
        this.findEmployee(employeeToEdit).setCanAddBill(newPermission);
        writeAllEmployees();
        return true;
    }


    public ArrayList<User> getAllEmployees() {
        return allEmployees;
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    public boolean isEmailExists(String email) {
        for (User user : allEmployees) {
            if (user.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }
}