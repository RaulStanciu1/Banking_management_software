package com.bms.history;

import com.bms.data.Banking;
import com.bms.data.BankingType;
import com.bms.history.models.BankingModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class DepositHistoryController {
    @FXML private TableView<BankingModel> depositTable;
    @FXML private TableColumn<BankingModel,String> amountColumn;
    @FXML private TableColumn<BankingModel,String> dateColumn;
    private void error(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("Something went wrong");
        alert.showAndWait();
    }
    public void init(int userId){
        try{
            List<Banking> tmpList = DBHistory.getBankingList(userId, BankingType.DEPOSIT);
            List<BankingModel> models = BankingModel.getModelList(tmpList);
            ObservableList<BankingModel> bankingList = FXCollections.observableList(models);
            amountColumn.setCellValueFactory(new PropertyValueFactory<>("Amount"));
            dateColumn.setCellValueFactory(new PropertyValueFactory<>("Date"));
            depositTable.setItems(bankingList);
        }catch(Exception e){
            error();
        }
    }
}
