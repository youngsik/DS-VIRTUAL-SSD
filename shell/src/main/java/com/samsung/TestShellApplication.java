package com.samsung;

import com.samsung.command.CommandInvoker;

import java.util.HashMap;
import java.util.Scanner;

public class TestShellApplication {
    public static void main(String[] args) {
        CommandInvoker commandInvoker = new CommandInvoker(new HashMap<>());
        commandInvoker.initAllCommands();

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
