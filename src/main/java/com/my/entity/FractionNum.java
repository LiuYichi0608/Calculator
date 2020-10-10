package com.my.entity;


public class FractionNum {
    boolean reduced = false;
    //分子
    int numerator;

    //分母
    int denominator;

    public FractionNum(int n, int d) {
        numerator = n;
        denominator = d;
        reduction();
    }

    public FractionNum(int n) {
        numerator = n;
        denominator = 1;
    }

    public static FractionNum with(FractionNum a, FractionNum b) {
        return new FractionNum(b.denominator * a.getNum() + b.numerator, b.denominator);
    }

    public int getNum() {
        if (denominator != 1) {
            System.out.println(new Throwable().getStackTrace()[0].toString());
        }
        return numerator;
    }

    /**
     * 约分处理
     */
    public void reduction() {
        int times = gcd(numerator, denominator);
        numerator /= times;
        denominator /= times;
        if (times > 1) {
            this.reduced = true;
        }
    }

    public boolean isFraction() {
        if (denominator == 0) {
            return false;
        }
        return !(numerator % denominator == 0);
    }

    @Override
    public String toString() {

        if (isFraction()) {//有余数
            if (numerator > denominator) {
                return (numerator / denominator) + "’" + numerator % denominator + "/" + denominator;
            }
            return numerator + "/" + denominator;
        } else {//没有余数
            if (denominator == 0) {
                return "" + numerator;
            } else {
                return "" + numerator / denominator;
            }
        }
    }

    /**
     * 辗转相除法（欧几里得算法）求最大公因数
     *
     * @param a 第一个数
     * @param b 第二个数
     * @return 最大公因数
     */
    private static int gcd(int a, int b) {
        return (b == 0) ? a : gcd(b, a % b);
    }


    public static FractionNum add(FractionNum a, FractionNum b) {
        return new FractionNum(a.numerator * b.denominator + b.numerator * a.denominator, b.denominator * a.denominator);
    }

    public static FractionNum sub(FractionNum a, FractionNum b) {
        return new FractionNum(a.numerator * b.denominator - b.numerator * a.denominator, b.denominator * a.denominator);
    }

    public static FractionNum mul(FractionNum a, FractionNum b) {
        return new FractionNum(a.numerator * b.numerator, b.denominator * a.denominator);
    }

    public static FractionNum div(FractionNum a, FractionNum b) {
        return new FractionNum(a.numerator * b.denominator, b.numerator * a.denominator);
    }


}
