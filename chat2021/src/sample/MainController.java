package sample;

import sk.kosickaakademia.lenart.entity.User;

import java.awt.*;
import java.awt.event.MouseEvent;

public class MainController {
    public Label lbl_login;
    private User user;

    public MainController(){
        System.out.println("Constructor");
    }

    public void logout(MouseEvent mouseEvent){
    }
    public void setUser(User user){
        this.user = user;
    }
}
