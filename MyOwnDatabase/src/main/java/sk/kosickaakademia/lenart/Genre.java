package sk.kosickaakademia.lenart;

public enum Genre {
    SHOOTING(0), MOBA(1), RTS(2), SPORT(3), OTHER(4);

    private final int value;
    Genre(int value){
        this.value = value;
    }
    public int getValue(){
        return value;
    }
}
