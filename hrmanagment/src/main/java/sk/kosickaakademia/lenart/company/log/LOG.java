package sk.kosickaakademia.lenart.company.log;

public class LOG {
    public void error(String msg){
        System.out.println("[ERROR] : "+msg);
    }
    public void print(String msg){
        System.out.println("[OK] : "+msg);
    }
    public void info(String msg){
        System.out.println("[INFO] : "+msg);
    }
}
