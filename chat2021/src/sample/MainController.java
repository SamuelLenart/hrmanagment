package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import sk.kosickaakademia.lenart.chat.Database;
import sk.kosickaakademia.lenart.entity.Message;
import sk.kosickaakademia.lenart.entity.User;
import java.util.Date;
import java.util.List;

public class MainController {
    public Label lbl_login;
    private User user;
    public TextField txt_sender;
    public TextField txt_subject;
    public ListView lbx_messages;

    public MainController(){
        System.out.println("Constructor");
    }

    public void logout(MouseEvent mouseEvent) {
    }

    public void setUser(User user){
        this.user=user;
    }

    public void initLoginName() {
        lbl_login.setText(user.getLogin());
    }


    public void click_refresh(ActionEvent actionEvent) {

        List<Message> list = new Database().getMyMessages(user.getLogin());
        if(list.isEmpty()){
            return;
        }
        for(Message m:list){
            String sender = m.getFrom();
            Date dt = m.getDt();
            String text = m.getText();
            lbx_messages.getItems().add(0, sender+" "+dt);
            lbx_messages.getItems().add(1," > "+text);
        }

    }
    public void click_send(ActionEvent actionEvent) {
        String sender = txt_sender.getText().trim();
        String subject = txt_subject.getText().trim();
        if (sender.length() < 1 || subject.length() < 1)
            return;
        boolean result = new Database().sendMessage(user.getId(), sender, subject);
        txt_sender.setText("");
        txt_subject.setText("");
    }
}
