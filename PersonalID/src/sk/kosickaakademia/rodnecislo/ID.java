package sk.kosickaakademia.rodnecislo;

import java.time.LocalDate;
import java.util.Calendar;

public class ID {
    private String id;
    private int day;
    private int month;
    private int year;
    private LocalDate date;
    private int age;

    public boolean checkId(String Id){
        if(Id.length()<9||Id.length()>11)
        return false;
        int cDigits = 0;
        for(int i = 0;i<Id.length();i++){
            if(Character.isDigit(Id.charAt(i)))
                cDigits++;
        }
        if(cDigits<9 || cDigits>10)
            return false;
        if(Id.length()==11 && (cDigits!=10 || Id.charAt(6)=='/'))
            return false;
        if(Id.length()==10 && cDigits==9 && Id.charAt(6)!='/')
            return false;
        if(Id.charAt(2)!='0' && Id.charAt(2)!='1' && Id.charAt(2)!='5' && Id.charAt(2)!='6')

        if(cDigits==9)
            Id=Id+'0';

        if(Id.charAt(6)=='/')
            Id = Id.substring(0,6)+Id.substring(7,11);
        long num = Long.parseLong(Id);
        System.out.println(num);

        if(num%11!=0) {
            System.out.println("Error, personal id is not divisible by 11");
            return false;

        }
        int day, month, year;
        day = Integer.parseInt(Id.substring(4,6));
        month = Integer.parseInt(Id.substring(2,4));
        year = Integer.parseInt(Id.substring(0,2));
        if (Id.charAt(2) == '5' || Id.charAt(2) == '6')
            month=month-50;

        year = Integer.parseInt(Id.substring(0,2));
        year = 2000 + year;
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        if (year > currentYear) {
            year = 100;
        }
        System.out.println(day+"-"+month+"-"+year);

        return true;
    }

    public void Age(){
        LocalDate dateTime = LocalDate.now();
        System.out.println(dateTime);
        int dayNow = dateTime.getDayOfMonth();
        int monthNow = dateTime.getMonthValue();
        int yearNow = dateTime.getYear();

        if(monthNow < month){
            age = yearNow-year-1;
            System.out.println("Si stary: " + age);
        } else if(monthNow == month && dayNow < day){
            age = yearNow-year-1;
            System.out.println("Si stary: " + age);
        }else{
            age = yearNow-year;
            System.out.println("Si stary: " + age);
        }

    }
}
