package sk.kosickaakademia.lenart.company.entity;

import org.graalvm.compiler.replacements.StandardGraphBuilderPlugins;
import sk.kosickaakademia.lenart.company.enumerator.Gender;

public class User {
    private int id;
    private String fName;
    private String lName;
    private int age;
    private Gender gender;

    public User(int id, String fName, String lName, int age, int gender) {
        this(fName, lName, age, gender);
        this.id = id;
    }

    public User( String fName, String lName, int age, int gender) {
        this.fName = fName;
        this.lName = lName;
        this.age = age;
        this.gender = gender==0?Gender.MALE:gender==1?Gender.FEMALE:Gender.OTHER;
    }

    public int getId(){
        return id;
    }

    public String getfName(){
        return fName;
    }

    public String getlName(){
        return lName;
    }

    public int getAge(){
        return age;
    }

    public Gender getGender(){
        return gender;
    }
}
