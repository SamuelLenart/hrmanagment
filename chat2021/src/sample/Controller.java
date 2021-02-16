package sample;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;

import javafx.stage.Stage;
import sk.kosickaakademia.lenart.chat.Database;
import sk.kosickaakademia.lenart.entity.User;

public class Controller {

    @FXML
    private TextField txt_login;
    public PasswordField txt_password;
    public Label lbl_error;
    public Button btn_login;

    public void btn_click(ActionEvent actionEvent){
        System.out.println("It works!");
        String login = txt_login.getText().trim();
        String password = txt_password.getText().trim();
        if(login.length()>0 && password.length()>0){
            Database database = new Database();
            User user = database.loginUser(login,password);
            if(user==null){
                lbl_error.setVisible(true);
            }else {
                System.out.println("You're logged!");
                openMainForm(user);
            }
        }
    }

    private void openMainForm(User user){
        try {
            FXMLLoader root = new FXMLLoader();
            root.setLocation(getClass().getResource("main.fxml"));
            Stage stage = new Stage();
            stage.setTitle("My chat 2021 application");
            stage.setScene(new Scene(root.load(), 800, 600));
            stage.show();
            MainController mc= root.getController();
            mc.setUser(user);
            mc.initLoginName();
            btn_login.getScene().getWindow().hide();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
