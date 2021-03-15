package sk.kosickaakademia.rodnecislo;

public class Bank {
    public double termnovanyVklad(int value, int years, double interest, boolean VAT){
        if(value<=0 || years<=0 || interest<=0)
        return 0;
        if(interest==0 || years==0)
            return value;

        int i;
        for(i=0; i<years; i++){
            double profit = (interest/100.0)*value;
            if(VAT){
                profit = 0.8 * profit;
            }
            double roundProfit = Math.round(profit*100)/100.0;
            value = (int) (value + profit);
        }

        return 0;
    }
}
