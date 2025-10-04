package battles;

import droids.Droid;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;

public class BattleStrategy {
    public static final int RANDOM = 1;
    public static final int WEAKEST = 2;
    public static final int SEQUENTIAL = 3;

    private int strategyType;
    private int currentIndex = 0;
    private Random random;

    public BattleStrategy(int strategyType) {
        this.strategyType = strategyType;
        this.random = new Random();
    }

    public Droid selectTarget(List<Droid> enemies) {
        List<Droid> alive = new ArrayList<>();
        for (Droid enemy : enemies) {
            if (enemy.isAlive()) {
                alive.add(enemy);
            }
        }

        if (alive.isEmpty()) return null;

        return switch (strategyType) {
            case RANDOM -> alive.get(random.nextInt(alive.size()));

            case WEAKEST -> {
                Droid weakest = alive.get(0);
                for (Droid enemy : alive) {
                    if (enemy.getCurrentHealth() < weakest.getCurrentHealth()) {
                        weakest = enemy;
                    }
                }
                yield weakest;
            }

            case SEQUENTIAL -> {
                currentIndex = currentIndex % alive.size();
                yield alive.get(currentIndex++);
            }

            default -> alive.get(0);
        };
    }

    public String getName() {
        return switch (strategyType) {
            case RANDOM -> "RANDOM";
            case WEAKEST -> "WEAKEST";
            case SEQUENTIAL -> "SEQUENTIAL";
            default -> "UNKNOWN";
        };
    }
}