package sk.kosickaakademia.rodnecislo;

import java.awt.*;

public class Task {

    public int suc(int a, int b) {
        return a + b;
    }

    public boolean isPrimeNumber(int m) {
        if (m <= 1)
            return false;
        for (int i = 2; i < Math.sqrt(m); i++) {
            if (m % i == 0)
                return false;
        }
        return true;
    }

    public boolean isRectangularTriangle(int a, int b, int c) {
        if (a <= 0 || b <= 0 || c <= 0)
            return false;
        if (a * a + b * b == c * c || a * a + c * c == b * b || b * b + c * c == a * a)
            return true;
        else
            return false;
    }

    public String revers(String text) {
        String chain = "";
        for (int i = text.length() - 1; i >= 0; i--) {
            chain = chain + text.charAt(i);
        }
        return chain;
    }

    public int calculateRectangle(int a, int b) {
        return a * b;
    }

    public int divide(int a, int b) {
        return a / b;
    }

    public int howLongisRectangle(int a, int b) {
        return 2 * (a + b);
    }

    public boolean isThisTrue() {
        return true;
    }

    public int max(int[] array) {
        int max = 0;
        if (array.length == 0) {
            return 0;
        }
        for (int i = 0; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }

        return max;
    }
    public double VolumeofCone(int radius, int height){
        if(radius <= 0 || height <= 0){
            return 0;
        }

        double result = 0;
        result = 1/3.00*((Math.PI)*(Math.pow(radius,2))*height);
        result *= 100;
        result = Math.round(result);
        result /= 100;
        return result;
    }
}

