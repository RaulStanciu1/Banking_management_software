package com.bms.banking;

import com.bms.Main;
import com.bms.MainController;
import com.bms.data.BankingType;
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

public class BankingController {
    @FXML private Text name;
    @FXML private ImageView currencyFlag;
    @FXML private Text currBalance;
    @FXML private Text bankingCode;
    @FXML private Text infoMsg;
    @FXML private TextField amountToSend;
    @FXML private TextField receiverBankingCode;
    private User user;
    private void transactionError(String msg){
        infoMsg.setStyle("-fx-fill: RED");
        infoMsg.setText(msg);
    }
    private void transactionSuccess(){
        infoMsg.setStyle("-fx-fill: GREEN");
        infoMsg.setText("Transaction was made successfully");
    }
    private void transactionClear(){
        amountToSend.setText("");
        receiverBankingCode.setText("");
    }
    public User getUser(){
        return user;
    }
    public void updateBalance(){
        currBalance.setText(this.user.getAmount()+" "+this.user.getCurrency().getSymbol());
    }
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
    public void copyToClipboard(){
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent content = new ClipboardContent();
        content.putString(bankingCode.getText());
        clipboard.setContent(content);
    }

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

    public void openLoanWindow(){

    }

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
