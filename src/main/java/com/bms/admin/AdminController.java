package com.bms.admin;

import com.bms.Main;
import com.bms.MainController;
import com.bms.data.Admin;
import com.bms.data.User;
import com.bms.history.models.BankingModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;

public class AdminController {
    @FXML private Text deposits;
    @FXML private Text withdraws;
    @FXML private Text loans;
    @FXML private Text transactions;
    @FXML private Text euro;
    @FXML private Text ron;
    @FXML private Text usd;
    @FXML private Text users;
    @FXML private PieChart dailyStatsChart;
    @FXML private TableView<UserModel> userTable;
    @FXML private TableColumn<UserModel,String> userIdColumn;
    @FXML private TableColumn<UserModel,String> usernameColumn;
    @FXML private TableColumn<UserModel,String> currencyColumn;
    @FXML private TableColumn<UserModel,String> balanceColumn;
    @FXML private TableColumn<UserModel,String> codeColumn;
    private Admin admin;
    private void error(String msg){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(msg);
        alert.showAndWait();
    }
    private void updateDailyStats(){
        try{
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                    new PieChart.Data("Deposits/Withdraws", this.admin.getDailyStats().bankingStats()),
                    new PieChart.Data("Transactions", this.admin.getDailyStats().transactionStats()),
                    new PieChart.Data("Loans", this.admin.getDailyStats().loanStats()));
            dailyStatsChart.setData(pieChartData);
        }catch(Exception e){
            error(e.getMessage());
        }

    }
    private void updateTotalStats(){
        try{
            deposits.setText("Deposits: "+this.admin.getTotalStats().deposits());
            withdraws.setText("Withdraws: "+this.admin.getTotalStats().withdraws());
            loans.setText("Loans: "+this.admin.getTotalStats().loans());
            transactions.setText("Transactions: "+this.admin.getTotalStats().transactions());
            euro.setText("EURO Stored: "+this.admin.getTotalStats().euro());
            ron.setText("RON Stored: "+this.admin.getTotalStats().ron());
            usd.setText("USD Stored: "+this.admin.getTotalStats().usd());
            users.setText("Total Nr. Of Users: "+this.admin.getTotalStats().users());
        }catch(Exception e){
            error(e.getMessage());
        }
    }
    private void updateCurrentUsers(){
        try{
            List<User> tmpList = Admin.getAllUsers();
            List<UserModel> modelList = UserModel.getModelList(tmpList);
            ObservableList<UserModel> usersList = FXCollections.observableList(modelList);
            userIdColumn.setCellValueFactory(new PropertyValueFactory<>("UserId"));
            usernameColumn.setCellValueFactory(new PropertyValueFactory<>("Username"));
            currencyColumn.setCellValueFactory(new PropertyValueFactory<>("Currency"));
            balanceColumn.setCellValueFactory(new PropertyValueFactory<>("Balance"));
            codeColumn.setCellValueFactory(new PropertyValueFactory<>("Code"));
            userTable.setItems(usersList);
        }catch(Exception e){
            error(e.getMessage());
        }
    }
    public void updateUI(){
        updateDailyStats();
        updateTotalStats();
        updateCurrentUsers();
    }

    public void init(Admin admin){
        this.admin=admin;
        try{
            this.admin.getDailyStats();
            this.admin.getTotalStats();
            updateUI();
        }catch(Exception e){
            error(e.getMessage());
        }

    }
    public void logout(){
        try {
            this.admin = null;
            Stage stage = (Stage) users.getScene().getWindow();
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
}
