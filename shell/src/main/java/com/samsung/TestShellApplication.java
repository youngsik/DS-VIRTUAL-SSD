package com.samsung;

import com.samsung.command.CommandInvoker;
import com.samsung.command.testscript.ScriptManager;
import com.samsung.command.testscript.TestScript1Command;
import com.samsung.command.testscript.TestScript2Command;
import com.samsung.command.testscript.TestScript3Command;
import com.samsung.command.testshell.*;
import com.samsung.file.FileManager;
import com.samsung.file.JarExecutor;

import java.util.HashMap;
import java.util.Scanner;

public class TestShellApplication {
    public static void main(String[] args) {
        CommandInvoker commandInvoker = new CommandInvoker(new HashMap<>());
        commandInvoker.initAllCommands();

        // 사용자 입력 받기
        System.out.println("testshell 명령어: write, read, erase, erase_range, exit, help, fullwrite, fullread");
        System.out.println("testscript 명령어: 1_FullWriteAndReadCompare, 2_PartialLBAWrite, 3_WriteReadAging");

        while (true) {
            try {
                String[] cmdArgs = split(getInput());
                commandInvoker.execute(cmdArgs);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

    }

    private static String getInput() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine().trim();
    }

    private static String[] split(String input) {
        return input.split(" ");
    }
}
