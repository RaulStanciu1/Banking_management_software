package com.bms.banking;

import com.bms.Main;
import com.bms.MainController;
import com.bms.data.Transaction;
import com.bms.data.User;
import com.bms.history.DepositHistoryController;
import com.bms.history.LoanHistoryController;
import com.bms.history.TransactionHistoryController;
import com.bms.history.WithdrawHistoryController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * BankingController: A class used for handling events on the Banking window
 */
public class BankingController {
    @FXML private Text name;
    @FXML private ImageView currencyFlag;
    @FXML private Text currBalance;
    @FXML private Text bankingCode;
    @FXML private Text infoMsg;
    @FXML private TextField amountToSend;
    @FXML private TextField receiverBankingCode;
    private User user;

    /**
     * Helper method for displaying error message
     * @param msg the error message
     */
    private void transactionError(String msg){
        infoMsg.setStyle("-fx-fill: RED");
        infoMsg.setText(msg);
    }

    /**
     * Helper method for displaying success message
     */
    private void transactionSuccess(){
        infoMsg.setStyle("-fx-fill: GREEN");
        infoMsg.setText("Transaction was made successfully");
    }

    /**
     * Helper method used for clearing input
     */
    private void transactionClear(){
        amountToSend.setText("");
        receiverBankingCode.setText("");
    }

    /**
     * User getter
     * @return the user
     */
    public User getUser(){
        return user;
    }

    /**
     * Helper method used for updating balance upon any changes made
     */
    public void updateBalance(){
        currBalance.setText(this.user.getAmount()+" "+this.user.getCurrency().getSymbol());
    }

    /**
     * On Action Event for the logout button
     */
    public void logout(){
        try {
            this.user = null;
            Stage stage = (Stage) name.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            MainController controller = fxmlLoader.getController();
            controller.init();
            stage.setTitle("BMS");
            stage.setResizable(false);
            stage.setScene(scene);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Method used upon opening the window
     * @param user the user object
     */
    public void init(User user){
        this.user=user;
        name.setText(this.user.getUsername());
        String currPath = "";
        switch(user.getCurrency()){
            case RON -> currPath+="ro.png";
            case USD -> currPath+="us.png";
            case EURO -> currPath+="eu.png";
        }
        currencyFlag.setImage(new Image(Objects.requireNonNull(BankingController.class.getResource(currPath)).toString()));
        currBalance.setText(this.user.getAmount()+" "+this.user.getCurrency().getSymbol());
        bankingCode.setText(this.user.getBankingCode());
    }

    /**
     * On Action event for the copy to clipboard button
     */
    public void copyToClipboard(){
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent content = new ClipboardContent();
        content.putString(bankingCode.getText());
        clipboard.setContent(content);
    }

    /**
     * On Action event for the make a transaction button
     */
    public void commitTransaction(){
        try{
            infoMsg.setText("");
            double amount;
            try{
                amount = Double.parseDouble(amountToSend.getText());
            }catch(Exception e){
                throw new Exception("Amount is invalid");
            }
            String code = receiverBankingCode.getText();
            Transaction newTransaction = this.user.createNewTransaction(amount,code);
            DBBanking.insertTransaction(this.user,newTransaction);
            transactionSuccess();
            transactionClear();
        }catch(Exception e){
            transactionError(e.getMessage());
        }
    }

    /**
     * On Action event for the deposit button
     */
    public void openDepositWindow(){
        try{
            Stage parent = (Stage) name.getScene().getWindow();
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(DepositController.class.getResource("deposit-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            DepositController controller = fxmlLoader.getController();
            controller.init(this);
            stage.setTitle("BMS - Deposit Window");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(parent);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    /**
     * On Action event for the withdraw button
     */
    public void openWithdrawWindow(){
        try{
            Stage parent = (Stage) name.getScene().getWindow();
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(DepositController.class.getResource("withdraw-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            WithdrawController controller = fxmlLoader.getController();
            controller.init(this);
            stage.setTitle("BMS - Withdraw Window");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(parent);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    /**
     * On Action event for the loan button
     */
    public void openLoanWindow(){
        try{
            Stage parent = (Stage) name.getScene().getWindow();
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(DepositController.class.getResource("loan-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            LoanController controller = fxmlLoader.getController();
            controller.init(this);
            stage.setTitle("BMS - Loan Window");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(parent);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * On Action event for the deposit history button
     */
    public void openDepositHistory(){
        try{
            Stage parent = (Stage) name.getScene().getWindow();
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(DepositHistoryController.class.getResource("deposit-history-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            DepositHistoryController controller = fxmlLoader.getController();
            controller.init(this.user.getId());
            stage.setTitle("BMS - Deposit History");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(parent);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * On Action event for the withdraw history button
     */
    public void openWithdrawHistory(){
        try{
            Stage parent = (Stage) name.getScene().getWindow();
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(DepositHistoryController.class.getResource("withdraw-history-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            WithdrawHistoryController controller = fxmlLoader.getController();
            controller.init(this.user.getId());
            stage.setTitle("BMS - Withdraw History");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(parent);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * On Action event for the transaction history button
     */
    public void openTransactionHistory(){
        try{
            Stage parent = (Stage) name.getScene().getWindow();
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(DepositHistoryController.class.getResource("transaction-history-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            TransactionHistoryController controller = fxmlLoader.getController();
            controller.init(this.user.getId());
            stage.setTitle("BMS - Transaction History");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(parent);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * On Action event for the loan history button
     */
    public void openLoanHistory(){
        try{
            Stage parent = (Stage) name.getScene().getWindow();
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(DepositHistoryController.class.getResource("loan-history-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            LoanHistoryController controller = fxmlLoader.getController();
            controller.init(this.user.getId());
            stage.setTitle("BMS - Loan History");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(parent);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
