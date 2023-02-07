package com.bms.banking;

import com.bms.data.Banking;
import com.bms.data.BankingType;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.util.Objects;

/**
 * WithdrawController: A class used for event handling for the withdraw window
 */
public class WithdrawController {
    private BankingController parent;
    @FXML
    private ImageView currencyFlag;
    @FXML private Text currBalance;
    @FXML private Text infoMsg;
    @FXML private TextField amountToWithdraw;

    /**
     * Helper method used for displaying an error message
     * @param msg the error message
     */
    private void withdrawError(String msg){
        infoMsg.setStyle("-fx-fill: RED");
        infoMsg.setText(msg);
    }

    /**
     * Helper method used for displaying a success message
     */
    private void withdrawSuccess(){
        infoMsg.setStyle("-fx-fill: GREEN");
        infoMsg.setText("Amount successfully withdrawn");
    }

    /**
     * Function that runs upon opening the window
     * @param parent the parent window
     */
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

    /**
     * On Action for the withdraw button
     */
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

    /**
     * Function that updates ui upon balance changes
     */
    public void updateBalance(){
        currBalance.setText(this.parent.getUser().getAmount()+" "+this.parent.getUser().getCurrency().getSymbol());
    }
}
