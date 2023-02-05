package com.bms.banking;

import com.bms.data.Banking;
import com.bms.data.BankingType;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.util.Objects;

public class WithdrawController {
    private BankingController parent;
    @FXML
    private ImageView currencyFlag;
    @FXML private Text currBalance;
    @FXML private Text infoMsg;
    @FXML private TextField amountToWithdraw;
    private void withdrawError(String msg){
        infoMsg.setStyle("-fx-fill: RED");
        infoMsg.setText(msg);
    }
    private void withdrawSuccess(){
        infoMsg.setStyle("-fx-fill: GREEN");
        infoMsg.setText("Amount successfully withdrawn");
    }
    public void init(BankingController parent){
        this.parent=parent;
        String currPath = "";
        switch(this.parent.getUser().getCurrency()){
            case RON -> currPath+="ro.png";
            case USD -> currPath+="us.png";
            case EURO -> currPath+="eu.png";
        }
        currencyFlag.setImage(new Image(Objects.requireNonNull(BankingController.class.getResource(currPath)).toString()));
        currBalance.setText(this.parent.getUser().getAmount()+" "+this.parent.getUser().getCurrency().getSymbol());
    }

    public void commitWithdraw(){
        try {
            double amount;
            try{
                amount = Double.parseDouble(amountToWithdraw.getText());
            }catch(Exception e){
                throw new Exception("Amount is invalid");
            }
            Banking withdrawMade = this.parent.getUser().createNewBanking(BankingType.WITHDRAW,amount);
            DBBanking.insertBanking(this.parent.getUser(),withdrawMade);
            amountToWithdraw.setText("");
            updateBalance();
            this.parent.updateBalance();
            withdrawSuccess();
        }catch(Exception e){
            withdrawError(e.getMessage());
        }
    }
    public void updateBalance(){
        currBalance.setText(this.parent.getUser().getAmount()+" "+this.parent.getUser().getCurrency().getSymbol());
    }
}
