package com.bms.history;

import com.bms.data.Loan;
import com.bms.history.models.BankingModel;
import com.bms.history.models.LoanModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class LoanHistoryController {
    @FXML private TableView<LoanModel> loanTable;
    @FXML private TableColumn<LoanModel,String> requestedColumn;
    @FXML private TableColumn<LoanModel,String> paymentLeftColumn;
    @FXML private TableColumn<LoanModel,String> loanDateColumn;
    @FXML private TableColumn<LoanModel,String> nextPaymentColumn;
    private void error(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("Something went wrong");
        alert.showAndWait();
    }
    public void init(int userId){
        try{
            error();
            List<Loan> tmpList = DBHistory.getLoanList(userId);
            List<LoanModel> models = LoanModel.getModelList(tmpList);
            ObservableList<LoanModel> loanList = FXCollections.observableList(models);
            requestedColumn.setCellValueFactory(new PropertyValueFactory<>("AmountRequested"));
            paymentLeftColumn.setCellValueFactory(new PropertyValueFactory<>("PaymentLeft"));
            loanDateColumn.setCellValueFactory(new PropertyValueFactory<>("LoanDate"));
            nextPaymentColumn.setCellValueFactory(new PropertyValueFactory<>("NextPaymentDate"));
            loanTable.setItems(loanList);
        }catch(Exception e){
            error();
        }
    }
}
