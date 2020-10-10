package com.my.controller;

import com.my.util.ReadTxt;

import java.util.Scanner;

public class CalculatePlatform {
    /**
     * 运行四则计算器平台
     * @param num       生成四则运算式子的个数
     * @param maxNum    数值允许范围的最大值
     * @throws Exception 抛出异常
     */
    public static void runPlatform(int num,int maxNum) throws Exception {
        Scanner scanner = new Scanner(System.in);
        StringBuilder exercisesBuffer = new StringBuilder();    // 练习题目buffer
        StringBuilder answerBuffer = new StringBuilder();       // 练习答案buff
        StringBuilder correctBuffer = new StringBuilder();      // 正确答案buff
        StringBuilder wrongBuffer = new StringBuilder();        // 错误答案buff
        String[] officialAnswer = new String[num];              // 练习答案, 用于逐个与用户输入答案做比较
        int[] userAnswerIndex = new int[num];                   // int数组, 0表示答案有误, 1表示答案正确

        String userAnswer;                                      // 暂存用户输入答案
        int correctNum = 0;                                     // 回答正确题目个数
        int wrongNum = 0;                                       // 回答错误题目个数

        for (int i = 0; i < num; i++) {
            // 生成式子
            String str = CreateFormula.createProblem(maxNum);
            System.out.println(str);
            // 把每一次生成的式子存入exercisesBuffer
            exercisesBuffer.append("第").append(i + 1).append("题：").append(str).append("\r\n--------------------\r\n");
            // 获取式子运算结果
            FractionNum answerNum = FracCalculator.calculator.calculate(str);
            // 保存结果答案
            officialAnswer[i] = answerNum.toString();

            /* 用户逐题输入式子的结果 */
            userAnswer = scanner.next();
            if(officialAnswer[i].equals(userAnswer)){
                userAnswerIndex[i] = 1;
                correctNum++;
            } else {
                userAnswerIndex[i] = 0;
                wrongNum++;
            }

            // 把每一次生成的答案存入answerBuffer
            answerBuffer.append("第").append(i + 1).append("题答案：").append(answerNum.toString()).append("\n").append("--------------------\n");
            System.out.println("--------------------");

            // 最后一个式子生成完毕, 把exercisesBuffer和answerBuffer内容存入相应文档
            if(i == num -1){
                ReadTxt.writeTxt("C:\\Users\\10973\\Desktop\\test\\Exercises.txt",exercisesBuffer.toString());
                ReadTxt.writeTxt("C:\\Users\\10973\\Desktop\\test\\Answers.txt",answerBuffer.toString());
            }
        }

        // 统计用户的答题情况
        correctBuffer.append("Correct:").append(correctNum).append("(");
        wrongBuffer.append("Wrong:").append(wrongNum).append("(");
        for (int i = 0; i < num; i++){
            if(userAnswerIndex[i] == 1){
                correctBuffer.append((i+1)).append(", ");
            } else {
                wrongBuffer.append((i+1)).append(", ");
            }
            if(i == (num - 1)){
                correctBuffer.append(")");
                wrongBuffer.append(")");
            }
        }
        // 把用户答题情况保存到文档中
        ReadTxt.writeTxt("C:\\Users\\10973\\Desktop\\test\\Grade.txt",
                correctBuffer.toString() + "\r\n" + wrongBuffer.toString());
    }
}
