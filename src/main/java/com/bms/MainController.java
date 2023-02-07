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

/**
 * MainController: A class used for event handling for the main window
 */
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

    /**
     * Function that runs upon starting the application
     */
    public void init(){
        currency.getItems().addAll(Currency.EURO,Currency.RON,Currency.USD);
        currency.getSelectionModel().selectFirst();
    }

    /**
     * Helper method for displaying a login error message
     * @param msg login error message
     */
    private void loginError(String msg){
        logErrMsg.setText(msg);
    }

    /**
     * Helper method used for displaying a register error message
     * @param msg register error message
     */
    private void registerError(String msg){
        regErrMsg.setText(msg);
    }

    /**
     * Helper method used for cleaning user error or success messages
     */
    private void cleanMsg(){
        logErrMsg.setText("");
        regErrMsg.setText("");
        successMsg.setText("");
    }

    /**
     * Helper method used for cleaning user input
     */
    private void cleanInput(){
        logUsername.setText("");
        logPassword.setText("");
        regUsername.setText("");
        regPassword.setText("");
        regConfirm.setText("");
    }

    /**
     * On Action event for the login button
     */
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

    /**
     * On Action event for the register button
     */
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