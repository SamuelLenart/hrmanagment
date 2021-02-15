package sample;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import sk.kosickaakademia.lenart.chat.Database;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Chat 2021 app");
        primaryStage.setScene(new Scene(root, 500, 300));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
        //new Database().test();
        Database database = new Database();
        database.insertNewUser("Samuel","Visma2021");
        //database.loginUser("Samuel", "Visma2021");
    }
}
