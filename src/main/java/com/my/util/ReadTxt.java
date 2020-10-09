package com.my.util;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import java.io.InputStreamReader;

public class ReadTxt {
	/**传入txt路径读取txt文件
     * @param txtPath 文件路径
     * @return 返回读取到的内容
     */
    public static StringBuffer readTxt(String txtPath) {
        File file = new File(txtPath);
        
        if(file.isFile() && file.exists()){
            try (FileInputStream fileInputStream = new FileInputStream(file);
                 InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                 BufferedReader bufferedReader = new BufferedReader(inputStreamReader)){
                StringBuffer sb = new StringBuffer();
                String text;
                while((text = bufferedReader.readLine()) != null){
                    sb.append(text);
                }
                return sb;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    

    /**使用FileOutputStream来写入txt文件
     * @param txtPath txt文件路径
     * @param content 需要写入的文本
     */
    public static void writeTxt(String txtPath,String content){    
       File file = new File(txtPath);
       try (FileOutputStream fileOutputStream = new FileOutputStream(file)){
           if(!file.exists()){
               //判断文件是否存在，如果不存在就新建一个txt
               file.createNewFile();
           }        
           fileOutputStream.write(content.getBytes());
           fileOutputStream.flush();
       } catch (Exception e) {
           e.printStackTrace();
       }
    }
    public static void main(String[] args) {
		StringBuffer sb = ReadTxt.readTxt("C:\\Users\\10973\\Desktop\\test\\orig.txt");
		System.out.println(sb.toString());
	}
}
