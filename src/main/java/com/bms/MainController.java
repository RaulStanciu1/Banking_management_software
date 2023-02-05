package com.bms;

import com.bms.admin.AdminController;
import com.bms.auth.DBAuth;
import com.bms.auth.Validation;
import com.bms.banking.BankingController;
import com.bms.data.Admin;
import com.bms.data.Currency;
import com.bms.data.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {
    @FXML private TextField regUsername;
    @FXML private PasswordField regPassword;
    @FXML private PasswordField regConfirm;
    @FXML private ComboBox<Currency> currency;
    @FXML private Label regErrMsg;
    @FXML private Label successMsg;
    @FXML private TextField logUsername;
    @FXML private PasswordField logPassword;
    @FXML private Label logErrMsg;
    public void init(){
        currency.getItems().addAll(Currency.EURO,Currency.RON,Currency.USD);
        currency.getSelectionModel().selectFirst();
    }
    private void loginError(String msg){
        logErrMsg.setText(msg);
    }
    private void registerError(String msg){
        regErrMsg.setText(msg);
    }
    private void cleanMsg(){
        logErrMsg.setText("");
        regErrMsg.setText("");
        successMsg.setText("");
    }
    private void cleanInput(){
        logUsername.setText("");
        logPassword.setText("");
        regUsername.setText("");
        regPassword.setText("");
        regConfirm.setText("");
    }
    public void login(){
        cleanMsg();
        try{
            String username = logUsername.getText();
            String password = logPassword.getText();
            Validation.loginValidation(username,password);
            if(username.equals("administrator")) {
                try{
                    Admin admin = DBAuth.getAdmin(username);
                    Stage parent =(Stage) currency.getScene().getWindow();
                    FXMLLoader fxmlLoader = new FXMLLoader(AdminController.class.getResource("admin-view.fxml"));
                    Scene scene = new Scene(fxmlLoader.load());
                    AdminController controller = fxmlLoader.getController();
                    controller.init(admin);
                    parent.setScene(scene);
                }catch(Exception e){
                    e.printStackTrace();
                }
                return;
            }
            User loggedUser = DBAuth.getUser(username);
            try{
                Stage parent =(Stage) currency.getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(BankingController.class.getResource("banking-view.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                BankingController controller = fxmlLoader.getController();
                controller.init(loggedUser);
                parent.setScene(scene);
            }catch(Exception e){
                e.printStackTrace();
            }
        }catch(Exception e){
            loginError(e.getMessage());
        }
    }
    public void register(){
        cleanMsg();
        try{
            String username = regUsername.getText();
            String password = regPassword.getText();
            String confirm = regConfirm.getText();
            Currency userCurr = currency.getSelectionModel().getSelectedItem();
            Validation.usernameValidation(username);
            Validation.passwordValidation(password,confirm);
            DBAuth.checkUsernameExists(username);
            DBAuth.registerNewUser(username,password,userCurr);
            cleanInput();
            successMsg.setText("Your account has been created successfully");
        }catch(Exception e){
            registerError(e.getMessage());
        }
    }
}