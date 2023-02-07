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
 * DepositController: A class used for event handling in the deposit window
 */
public class DepositController {
    private BankingController parent;
    @FXML private ImageView currencyFlag;
    @FXML private Text currBalance;
    @FXML private Text infoMsg;
    @FXML private TextField amountToDeposit;

    /**
     * Helper method for displaying error message
     * @param msg error message
     */
    private void depositError(String msg){
        infoMsg.setStyle("-fx-fill: RED");
        infoMsg.setText(msg);
    }

    /**
     * Helper method for displaying a success message
     */
    private void depositSuccess(){
        infoMsg.setStyle("-fx-fill: GREEN");
        infoMsg.setText("Amount successfully deposited");
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
     * On Action event for the Make a deposit button
     */
    public void commitDeposit(){
        try {
            double amount;
            try{
                amount = Double.parseDouble(amountToDeposit.getText());
            }catch(Exception e){
                throw new Exception("Amount is invalid");
            }
            Banking depositMade = this.parent.getUser().createNewBanking(BankingType.DEPOSIT,amount);
            DBBanking.insertBanking(this.parent.getUser(),depositMade);
            amountToDeposit.setText("");
            updateBalance();
            this.parent.updateBalance();
            depositSuccess();
        }catch(Exception e){
            depositError(e.getMessage());
        }
    }

    /**
     * A method used for updating the ui upon modification to the balance
     */
    public void updateBalance(){
        currBalance.setText(this.parent.getUser().getAmount()+" "+this.parent.getUser().getCurrency().getSymbol());
    }
}
