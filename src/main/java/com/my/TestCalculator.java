package com.my;

import com.my.controller.CalculatePlatform;


import java.util.Scanner;


public class TestCalculator {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入生成式子的条数：" );
        int num = scanner.nextInt();
        System.out.println("请输入生成式子的值的最大值：" );
        int maxNum = scanner.nextInt();
        CalculatePlatform.runPlatform(num,maxNum);
    }
}
