package com.bhole.shop.authn.domain;

import java.io.*;

/**
 * @program: bhole-shop
 * @description:
 * @author: joke
 * @date: 2023/6/1 16:18
 * @version: 1.0
 */
public class Test {

    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder("start\n");

        try {

            // 执行脚本文件
            String cmd = "ll /";
            //主要在这步写入后调用命令
            Process process = Runtime.getRuntime().exec(cmd);
            try (PrintWriter printWriter =
                         new PrintWriter(
                                 new BufferedWriter(new OutputStreamWriter(process.getOutputStream())), true);
                 BufferedReader read =
                         new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                printWriter.println(cmd);
                printWriter.println("exit");
                String line;
                while ((line = read.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                System.out.println(sb);
            }
            // Java父线程等待Shell子进程执行完毕
        } catch (Exception e) {

        } finally {
            System.out.println(sb);
        }
    }
}
