package sk.kosickaakademia.lenart.company.enumerator;


import sk.kosickaakademia.lenart.company.entity.User;

import java.util.List;

public enum Gender {
    MALE(0), FEMALE(1), OTHER(2);

    private final int value;
    Gender (int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }
}
