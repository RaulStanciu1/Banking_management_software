package com.bms.history;

import com.bms.data.Banking;
import com.bms.data.BankingType;
import com.bms.data.Transaction;
import com.bms.history.models.BankingModel;
import com.bms.history.models.TransactionModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class TransactionHistoryController {
    @FXML private TableView<TransactionModel> transactionTable;
    @FXML private TableColumn<TransactionModel,String> receiverColumn;
    @FXML private TableColumn<TransactionModel,String> amountColumn;
    @FXML private TableColumn<TransactionModel,String> dateColumn;
    @FXML private TableColumn<TransactionModel,String> currencyColumn;
    private void error(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("Something went wrong");
        alert.showAndWait();
    }
    public void init(int userId){
        try{
            List<Transaction> tmpList = DBHistory.getTransactionList(userId);
            List<TransactionModel> models = TransactionModel.getModelList(tmpList);
            ObservableList<TransactionModel> transactionList = FXCollections.observableList(models);
            receiverColumn.setCellValueFactory(new PropertyValueFactory<>("Receiver"));
            amountColumn.setCellValueFactory(new PropertyValueFactory<>("Amount"));
            dateColumn.setCellValueFactory(new PropertyValueFactory<>("Date"));
            currencyColumn.setCellValueFactory(new PropertyValueFactory<>("ReceiverCurrency"));
            transactionTable.setItems(transactionList);
        }catch(Exception e){
            error();
        }
    }
}
