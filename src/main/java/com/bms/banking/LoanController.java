package com.bms.banking;

import com.bms.data.Loan;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.util.Objects;

public class LoanController {
    private BankingController parent;
    @FXML private ImageView currencyFlag;
    @FXML private Text currBalance;
    @FXML private Text infoMsg;
    @FXML private TextField amountToLoan;
    private void loanError(String msg){
        infoMsg.setStyle("-fx-fill: RED");
        infoMsg.setText(msg);
    }
    private void loanSuccess(){
        infoMsg.setStyle("-fx-fill: GREEN");
        infoMsg.setText("Loan successfully sent to you");
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

    public void commitLoan(){
        try {
            double amount;
            try{
                amount = Double.parseDouble(amountToLoan.getText());
            }catch(Exception e){
                throw new Exception("Amount is invalid");
            }
            Loan loanMade = this.parent.getUser().createNewLoan(amount);
            DBBanking.insertLoan(this.parent.getUser(),loanMade);
            amountToLoan.setText("");
            updateBalance();
            this.parent.updateBalance();
            loanSuccess();
        }catch(Exception e){
            loanError(e.getMessage());
        }
    }
    public void updateBalance(){
        currBalance.setText(this.parent.getUser().getAmount()+" "+this.parent.getUser().getCurrency().getSymbol());
    }
}
