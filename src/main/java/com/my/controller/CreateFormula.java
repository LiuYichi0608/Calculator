package com.my.controller;

import java.util.Random;

public class CreateFormula {
    public static final String True = "true";
    public static final String False = "false";
    public static final String Error = "error";

    public static String createProblem() throws Exception {  //产生整数式子
        Random r = new Random();
        String[] operator = {"+", "-", "*", "÷"};
        //操作符的个数
        int operatorNum = 1 + r.nextInt(2);
        //新建数组来保存操作数
        FractionNum[] number = new FractionNum[operatorNum + 1];
//        String[] number = new String[operatorNum+1];
        //操作符的下标
        int[] arr = index(operatorNum);
        String s = "";

        for (int j = 0; j < operatorNum + 1; j++) {
            int num1 = r.nextInt(10);
//            int num2 = 10 - num1;
            int num2 = 1 + r.nextInt(9);
            int flag = r.nextInt(2);//0为整数，1为分数
            FractionNum num;
            if (flag == 0) {
                num = new FractionNum(num1);
            } else {
                num = new FractionNum(num1, num2);
            }
            number[j] = num;
        }

        //如果flag=0，则该式子加左括号，如果flag=1，则该式子加右括号，2为无括号
        int flag = r.nextInt(3);
//        int flag = 1;
        switch (operatorNum) {
            case 1: {
                s = number[0] + operator[arr[0]] + number[1];
                break;
            }
            case 2: {
                if (flag == 0) {
                    s = "(" + number[0] + operator[arr[0]] + number[1] + ")" + operator[arr[1]] + number[2];
                } else if (flag == 1) {
                    s = number[0] + operator[arr[0]] + "(" + number[1] + operator[arr[1]] + number[2] + ")";
                } else {
                    s = number[0] + operator[arr[0]] + number[1] + operator[arr[1]] + number[2];
                }
                break;
            }
            default:
                break;
        }
        s = CreateFormula.check(s, operatorNum, arr, number, operator, flag);
        if ("The formula is error".equals(s)) {
            return createProblem();
        }
        FractionNum num = FracCalculator.calculator.calculate(s);
        int answer = num.numerator;
        if (answer >= 0) {
            s = s + "=";
        } else {
            return createProblem();
        }
        return s;

    }

    /**
     * 产生操作符的下标数组
     *
     * @param n 下标数组的位数
     * @return 返回下标数组
     */
    public static int[] index(int n) { //产生操作符的下标数组
        Random random = new Random();
        int[] a = new int[n];
        for (int j = 0; j < n; j++) {
            a[j] = random.nextInt(4);
        }
        return a;
    }

    /**
     * 检验式子是否正确
     *
     * @param str         第一次生成式子
     * @param operatorNum 获取相应的操作符个数1-2
     * @param arr         操作符下标，0表示+，1表示-，2表示*，3表示÷
     * @param number      数字数组
     * @param operator    +-*÷数组
     * @return 经过判断和处理后，如果式子内容没有问题，则返回更改后的式子，式子有问题则返回"The formula is error"
     */
    public static String check(String str, int operatorNum, int[] arr, FractionNum[] number, String[] operator, int flag) throws Exception {

        for (int i = 0; i < operatorNum; i++) {
            if (arr[i] == 1) {  // 检测出当前操作符为-号
                if (operatorNum != 1) {  // 操作符的个数不唯一
                    String str1;
                    String str2;
                    if (i == 0) {  // -号为第一个运算符
                        if (flag == 0) {  // -号为第一个运算符且为左括号的情况
                            if ("error".equals(compare(number[0].toString(), number[1].toString()))) {
                                str = "The formula is error";
                            }
                            if ("true".equals(compare(number[0].toString(), number[1].toString()))) {
                                FractionNum temp = number[0];
                                number[0] = number[1];
                                number[1] = temp;
                                str = "(" + number[0] + operator[arr[0]] + number[1] + ")" + operator[arr[1]] + number[2];
                            }
                        }
                        if (flag == 1) {
                            str1 = "" + number[0];
                            str2 = "(" + number[1] + operator[arr[1]] + number[2] + ")";
                            if ("error".equals(compare(str1, str2))) {
                                str = "The formula is error";
                            }
                            if ("true".equals(compare(str1, str2))) {
                                str = str2 + operator[arr[i]] + str1;
                            }
                        } else {
                            str1 = "" + number[0];
                            str2 = number[1] + operator[arr[1]] + number[2];
                            if ("error".equals(compare(str1, str2))) {
                                str = "The formula is error";
                            }
                            if ("true".equals(compare(str1, str2))) {
                                str = str2 + operator[arr[i]] + str1;
                            }
                        }
                    } else {//第二个运算符为-号
                        if (flag == 0) {
                            str1 = "(" + number[0] + operator[arr[0]] + number[1] + ")";
                            str2 = "" + number[2];
                            if ("error".equals(compare(str1, str2))) {
                                str = "The formula is error";
                            }
                            if ("true".equals(compare(str1, str2))) {
                                str = str2 + operator[arr[i]] + str1;
                            }
                        }
                        if (flag == 1) {
                            if ("error".equals(compare(number[1] + "", number[2] + ""))) {
                                str = "The formula is error";
                            }
                            if ("true".equals(compare(number[1] + "", number[2] + ""))) {
                                FractionNum temp = number[1];
                                number[1] = number[2];
                                number[2] = temp;
                                str = number[0] + operator[arr[0]] + "(" + number[1] + operator[arr[1]] + number[2] + ")";
                            }
                        } else {
                            str1 = number[0] + operator[arr[0]] + number[1];
                            str2 = "" + number[2];
                            if ("error".equals(compare(str1, str2))) {
                                str = "The formula is error";
                            }
                            if ("true".equals(compare(str1, str2))) {
                                str = str2 + operator[arr[i]] + str1;
                            }
                        }
                    }
                } else {
                    FractionNum temp;
                    /* 式子只有一个操作符且操作符为-号, 先比较-号前后的两个数字, 若后者比前者大则数字的顺序颠倒 */
                    if (Calculator.FractionNumCalculate(number[0], number[1])) {
                        temp = number[0];
                        number[0] = number[1];
                        number[1] = temp;
                        str = number[0] + "-" + number[1];
                    }
                }
            }
            if (arr[i] == 3) {  // 检测到操作符为÷号
                if (operatorNum == 1) {  // 操作符为÷号且只有一个操作符
                    /* 判断÷号后面的数字是否为0, 如果为0就把其改为1 */
                    number[1] = (number[1].numerator == 0) ? new FractionNum(1) : number[1];
                    str = number[0] + operator[arr[0]] + number[1];
                } else {  // 操作符为÷号
                    /* 判断÷号后面的数字是否为0, 如果为0就把其改为1 */
                    number[i + 1] = (number[i + 1].numerator == 0) ? new FractionNum(1) : number[i + 1];
                    if (flag == 0) {  // 判断式子是否为左括号情况
                        str = "(" + number[0] + operator[arr[0]] + number[1] + ")" + operator[arr[1]] + number[2];
                    } else {  // 式子为右括号或者无括号
                        str = number[0] + operator[arr[0]] + number[1] + operator[arr[1]] + number[2];
                    }
                }
            }
        }
//        System.out.println(str);
        return str;
    }

    /**
     * @param str1 -号式子的左半部
     * @param str2 -号式子的右半部
     * @return 返回一个boolean类型的值，false表示左半部值比右半部值大，true表示后者比前者大
     */
    public static String compare(String str1, String str2) {
        //false表示前一个值比另一个值大，true表示后者比前者大
        try {
            /*调用CalculatorT.calculator.calculate()方法实现带分数的运算器计算，返回一个FractionNum带分数类型*/
            FractionNum num1 = FracCalculator.calculator.calculate(str1);
            FractionNum num2 = FracCalculator.calculator.calculate(str2);
            if (isNormal(num1) && isNormal(num2)) {
                if (Calculator.FractionNumCalculate(num1, num2)) {
                    return True;
                }
            } else {
                return Error;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return False;
    }

    /**
     * 判断分数内容是否正常，即是否出现负数的分子分母
     *
     * @param num 带分数
     * @return 如果带分数的分子分母存在负数则返回false, 否则返回true
     */
    public static boolean isNormal(FractionNum num) {
        return num.denominator >= 0 && num.numerator >= 0;
    }


}
