import droids.*;
import battles.*;
import utils.FileManager;
import java.util.*;

public class Main {
    private static List<Droid> droids = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static List<String> lastBattleLog = null;

    public static void main(String[] args) {
        System.out.println("БОЇ ДРОЇДІВ\n");

        while (true) {
            showMenu();
            int choice = getIntInput("Оберіть опцію: ");

            switch (choice) {
                case 1 -> createDroid();
                case 2 -> showDroids();
                case 3 -> startOneVsOne();
                case 4 -> startTeamBattle();
                case 5 -> saveBattle();
                case 6 -> replayBattle();
                case 0 -> {
                    System.out.println("\nРоботу завершено");
                    return;
                }
                default -> System.out.println("Невірний вибір!");
            }
        }
    }

    private static void showMenu() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("ГОЛОВНЕ МЕНЮ");
        System.out.println("=".repeat(40));
        System.out.println("1. Створити дроїда");
        System.out.println("2. Показати список дроїдів");
        System.out.println("3. Запустити бій 1 на 1");
        System.out.println("4. Запустити командний бій");
        System.out.println("5. Зберегти останній бій");
        System.out.println("6. Відтворити бій з файлу");
        System.out.println("0. Вийти");
        System.out.println("=".repeat(40));
    }

    private static void createDroid() {
        System.out.println("\nТипи дроїдів:");
        System.out.println("1. Снайпер");
        System.out.println("2. Штурмовик");
        System.out.println("3. Танк");
        System.out.println("4. Медик");

        int type = getIntInput("Оберіть тип (1-4): ");
        System.out.print("Введіть ім'я дроїда: ");
        String name = scanner.nextLine();

        Droid droid = switch (type) {
            case 1 -> new Sniper(name);
            case 2 -> new Assault(name);
            case 3 -> new Tank(name);
            case 4 -> new Support(name);
            default -> null;
        };

        if (droid != null) {
            droids.add(droid);
            System.out.println("\nДроїда створено!");
            System.out.println(droid.getFullInfo());
        } else {
            System.out.println("Невірний тип!");
        }
    }

    private static void showDroids() {
        if (droids.isEmpty()) {
            System.out.println("\nСписок дроїдів порожній!");
            return;
        }

        System.out.println("\nСПИСОК ДРОЇДІВ:");
        for (int i = 0; i < droids.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, droids.get(i).getFullInfo());
        }
    }

    private static void startOneVsOne() {
        if (droids.size() < 2) {
            System.out.println("\nПотрібно мінімум 2 дроїди!");
            return;
        }

        showDroids();
        int idx1 = getIntInput("Оберіть першого дроїда (номер): ") - 1;
        int idx2 = getIntInput("Оберіть другого дроїда (номер): ") - 1;

        if (idx1 < 0 || idx1 >= droids.size() || idx2 < 0 || idx2 >= droids.size()) {
            System.out.println("Невірний вибір!");
            return;
        }
        Droid d1 = cloneDroid(droids.get(idx1));
        Droid d2 = cloneDroid(droids.get(idx2));

        Battle battle = new OneVsOneBattle(d1, d2);
        battle.startBattle();
        lastBattleLog = battle.getBattleLog();
    }

    private static void startTeamBattle() {
        if (droids.size() < 2) {
            System.out.println("\nПотрібно мінімум 2 дроїди!");
            return;
        }

        System.out.println("\nСтратегії бою:");
        System.out.println("1. Випадкова цiль");
        System.out.println("2. Найслабша цiль");
        System.out.println("3. По черзі");

        int stratChoice = getIntInput("Оберіть стратегію (1-3): ");
        BattleStrategy strategy = new BattleStrategy(stratChoice);

        List<Droid> team1 = selectTeam("Команда 1");
        List<Droid> team2 = selectTeam("Команда 2");

        if (team1.isEmpty() || team2.isEmpty()) {
            System.out.println("Команди не можуть бути порожніми!");
            return;
        }

        Battle battle = new TeamBattle(team1, team2, strategy);
        battle.startBattle();
        lastBattleLog = battle.getBattleLog();
    }

    private static List<Droid> selectTeam(String teamName) {
        List<Droid> team = new ArrayList<>();
        System.out.println("\nФормування " + teamName);

        while (true) {
            showDroids();
            System.out.println("Поточна команда: " +
                    (team.isEmpty() ? "порожня" :
                            team.stream().map(Droid::getName).reduce((a, b) -> a + ", " + b).orElse("")));

            int idx = getIntInput("Оберіть дроїда (0 - завершити): ") - 1;

            if (idx == -1) break;

            if (idx >= 0 && idx < droids.size()) {
                team.add(cloneDroid(droids.get(idx)));
                System.out.println("Дроїда додано до команди!");
            }
        }

        return team;
    }

    private static void saveBattle() {
        if (lastBattleLog == null) {
            System.out.println("\nНемає бою для збереження!");
            return;
        }

        System.out.print("Введіть назву файлу (без розширення): ");
        String filename = scanner.nextLine() + ".txt";
        FileManager.saveBattleLog(lastBattleLog, filename);
    }

    private static void replayBattle() {
        List<String> logs = FileManager.listBattleLogs();

        if (logs.isEmpty()) {
            System.out.println("\nНемає збережених боїв!");
            return;
        }

        System.out.println("\nЗбережені бої:");
        for (int i = 0; i < logs.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, logs.get(i));
        }

        int idx = getIntInput("Оберіть бій для відтворення (номер): ") - 1;

        if (idx >= 0 && idx < logs.size()) {
            FileManager.replayBattle(logs.get(idx));
        } else {
            System.out.println("Невірний вибір!");
        }
    }

    private static Droid cloneDroid(Droid original) {
        return switch (original.getType()) {
            case "Снайпер" -> new Sniper(original.getName());
            case "Штурмовик" -> new Assault(original.getName());
            case "Танк" -> new Tank(original.getName());
            case "Медик" -> new Support(original.getName());
            default -> null;
        };
    }

    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Невірний вибір!");
            }
        }
    }
}