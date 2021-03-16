package sk.kosickaakademia.lenart.company.entity;

public class Statistic {
    private int count;
    private int countMale;
    private int countFemale;
    private int minAge;
    private int maxAge;
    private double avgAge;

    public Statistic(int count, int countMale, int countFemale, int minAge, int maxAge, double avgAge) {
        this.count = count;
        this.countMale = countMale;
        this.countFemale = countFemale;
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.avgAge = avgAge;
    }

    public int getCount() {
        return count;
    }

    public int getCountMale() {
        return countMale;
    }

    public int getCountFemale() {
        return countFemale;
    }

    public int getMinAge() {
        return minAge;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public double getAvgAge() {
        return avgAge;
    }
}
