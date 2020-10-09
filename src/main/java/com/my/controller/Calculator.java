package com.my.controller;

import java.util.Stack;

public class Calculator {
    private static final Stack<Character> stack;//后缀表达式
    private static final Stack<Character> stack_1;//符号栈
    private static final Stack<Character> stack_2;//临时栈

    static {
        stack = new Stack<Character>();
        stack_1 = new Stack<Character>();
        stack_2 = new Stack<Character>();
    }


    public static void main(String[] args) {
        String str = "1/2";
        try {
            Double b = calculate(str);
            System.out.println("运算结果为：" + b);
        } catch (Exception e) {
            System.out.println("error");
        }
    }

    //运算
    public static Double calculate(String str) throws Exception {
        char[] arr = str.toCharArray();
        //转化为后缀表达式
        for (char c : arr) {
            if (Character.isDigit(c)) {//判断是否为数字
                stack.push(c);
            } else if (c == '*' || c == '/') {
                while (!stack_1.empty()) {
                    char ch = stack_1.pop();
                    if (ch == '(') {
                        stack_1.push(ch);
                        break;
                    } else if (ch == '*' || ch == '/') {
                        stack.push(ch);
                    } else {
                        stack_2.push(ch);
                    }
                }
                while (!stack_2.empty()) {
                    stack_1.push(stack_2.pop());
                }
                stack_1.push(c);
            } else if (c == '+' || c == '-') {
                while (!stack_1.empty()) {
                    char ch = stack_1.pop();
                    if (ch == '(') {
                        stack_1.push(ch);
                        break;
                    } else if (ch == '*' || ch == '/' || ch == '+' || ch == '-') {
                        stack.push(ch);
                    } else {
                        stack_2.push(ch);
                    }
                }
                while (!stack_2.empty()) {
                    stack_1.push(stack_2.pop());
                }
                stack_1.push(c);
            } else if (c == '(') {
                stack_1.push(c);
            } else if (c == ')') {
                char ch = stack_1.peek();
                while (ch != '(') {
                    ch = stack_1.pop();
                    stack.push(ch);
                }
                stack.pop();
            } else {
                throw new Exception();
            }
        }
        while (!stack_1.empty()) {
            stack.push(stack_1.pop());
        }
        //进行运算
        int index = 0;
        while (!stack.empty()) {
            index++;
            stack_2.push(stack.pop());
        }
        Stack<Double> s = new Stack<Double>();//用于最后计算的栈
        while (!stack_2.empty()) {
            char ch = stack_2.pop();
            if (index > 1) {
//                if(index == 3){
//                    return s.pop();
//                }
                if (ch == '*' || ch == '/' || ch == '+' || ch == '-') {
                    double sum = 0;
                    double num1 = s.pop();
                    double num2 = s.pop();
                    switch (ch) {
                        case '*':
                            sum = num2 * num1;
                            break;
                        case '/':
                            sum = num2 / num1;
                            break;
                        case '+':
                            sum = num2 + num1;
                            break;
                        case '-':
                            sum = num2 - num1;
                            break;
                    }
                    s.push(sum);
                } else if (Character.isDigit(ch)) {
                    s.push((double) Character.getNumericValue(ch));
                } else {
                    throw new Exception();
                }
            } else {
                //TODO
                s.push((double) Character.getNumericValue(ch));
                break;
            }
        }
        return s.pop();
    }

    /**
     * 带分数比较
     *
     * @param num1 带分数1
     * @param num2 带分数2
     * @return false表示前一个值比另一个值大，true表示后者比前者大
     */
    public static boolean FractionNumCalculate(FractionNum num1, FractionNum num2) throws Exception {
        //false表示前一个值比另一个值大，true表示后者比前者大
        int num1Index1 = num1.toString().indexOf('’');
        int num2Index1 = num2.toString().indexOf('’');
        if (num1Index1 != -1 || num2Index1 != -1) {
            if (num1Index1 == -1) {//3和2’1/3
                Double answer1 = Calculator.calculate(num1.toString());
                System.out.println(num2.toString().substring(0, num2Index1));
                Double answer2 = Calculator.calculate(num2.toString().substring(0, num2Index1));
                return answer1 <= answer2;
            } else if (num2Index1 == -1) {//2’1/3和3
                Double answer1 = Calculator.calculate(num1.toString().substring(0, num1Index1));
                Double answer2 = Calculator.calculate(num2.toString());
                return answer1 <= answer2;
            } else {//2’1/3和3‘1/3
                if (num1Index1 >= num2Index1) {
                    Double answer1 = Calculator.calculate(num1.toString().substring(0, num1Index1));
                    Double answer2 = Calculator.calculate(num2.toString().substring(0, num2Index1));
                    if (answer1 > answer2) {//分数前的数值比较，前者大返回false
                        return false;
                    }
                    if (answer1 < answer2) {//分数前的数值比较，前者大返回true
                        return true;
                    } else {
                        answer1 = Calculator.calculate(num1.toString().substring(num1Index1 + 1));
                        answer2 = Calculator.calculate(num2.toString().substring(num2Index1 + 1));
                        return answer1 <= answer2;
                    }
                } else {
                    //后一个分数的数值更大
                    return true;
                }
            }

        } else {
            try {

                Double answer1 = calculate(num1.toString());
                Double answer2 = calculate(num2.toString());
                return answer1 <= answer2;
            } catch (Exception e) {
                System.out.println(num1.toString() + "---");
                System.out.println(num2.toString() + "---");
                e.printStackTrace();
            }
            return false;
        }
    }


}
