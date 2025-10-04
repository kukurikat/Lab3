package utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    private static final String BATTLE_LOGS_DIR = "battle_logs";

    public static void saveBattleLog(List<String> log, String filename) {
        try {
            File dir = new File(BATTLE_LOGS_DIR);
            if (!dir.exists()) {
                dir.mkdir();
            }

            try (PrintWriter writer = new PrintWriter(
                    new FileWriter(BATTLE_LOGS_DIR + "/" + filename))) {
                for (String line : log) {
                    writer.println(line);
                }
            }
            System.out.println("\nБій успішно збережено у файл: " + filename);
        } catch (IOException e) {
            System.err.println("Помилка збереження файлу: " + e.getMessage());
        }
    }

    public static void replayBattle(String filename) {
        try (BufferedReader reader = new BufferedReader(
                new FileReader(BATTLE_LOGS_DIR + "/" + filename))) {
            System.out.println("\nВідтворення бою з файлу: " + filename + "\n");
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                try {
                    Thread.sleep(300); // Затримка для ефекту
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Файл не знайдено: " + filename);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public static List<String> listBattleLogs() {
        List<String> logs = new ArrayList<>();
        File dir = new File(BATTLE_LOGS_DIR);
        if (dir.exists() && dir.isDirectory()) {
            File[] files = dir.listFiles((d, name) -> name.endsWith(".txt"));
            if (files != null) {
                for (File file : files) {
                    logs.add(file.getName());
                }
            }
        }
        return logs;
    }
}