package sk.kosickaakademia.lenart;

public enum Genre {
    SHOOTING(0), MOBA(1), RTS(2), OTHER(3);

    private final int value;
    Genre(int value){
        this.value = value;
    }
    public int getValue(){
        return value;
    }
}
