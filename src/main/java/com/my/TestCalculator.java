package com.my;

import com.my.controller.CreateFormula;
import com.my.controller.FracCalculator;
import com.my.controller.FractionNum;

public class TestCalculator {
    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 10; i++) {
            String str = CreateFormula.createProblem();
            System.out.println(str);
            FractionNum num = FracCalculator.calculator.calculate(str);
            System.out.println(num);
            System.out.println("--------------------");
        }
    }
}
