package battles;

import droids.Droid;
import droids.Support;
import java.util.List;

public class TeamBattle extends Battle {
    private List<Droid> team1;
    private List<Droid> team2;
    private BattleStrategy strategy;

    public TeamBattle(List<Droid> team1, List<Droid> team2, BattleStrategy strategy) {
        super();
        this.team1 = team1;
        this.team2 = team2;
        this.strategy = strategy;
    }

    @Override
    public void startBattle() {
        logger.logAction("\nБій команд розпочато!");
        String team1Names = "";
        for (Droid droid : team1) {
            if (!team1Names.isEmpty()) {
                team1Names += ", ";
            }
            team1Names += droid.getName();
        }
        logger.logAction("\nКоманда 1: " + team1Names);

        // Команда 2
        String team2Names = "";
        for (Droid droid : team2) {
            if (!team2Names.isEmpty()) {
                team2Names += ", ";
            }
            team2Names += droid.getName();
        }
        logger.logAction("Команда 2: " + team2Names);

        logger.logAction("Стратегія: " + strategy.getName() + "\n");

        int round = 1;
        while (hasAliveDroids(team1) && hasAliveDroids(team2)) {
            logger.logAction(String.format("\nРаунд %d", round));
            for (Droid attacker : team1) {
                if (!attacker.isAlive()) continue;
                if (!hasAliveDroids(team2)) break;

                if (attacker instanceof Support && random.nextDouble() < 0.4) {
                    Droid ally = findWeakestAlly(team1);
                    if (ally != null && ally != attacker) {
                        performHeal((Support) attacker, ally);
                        continue;
                    }
                }

                Droid target = strategy.selectTarget(team2);
                if (target != null) {
                    performAttack(attacker, target);
                    if (!target.isAlive()) {
                        logger.logAction(String.format("  %s знищено!", target.getName()));
                    }
                }
            }

            if (!hasAliveDroids(team2)) break;

            for (Droid attacker : team2) {
                if (!attacker.isAlive()) continue;
                if (!hasAliveDroids(team1)) break;

                if (attacker instanceof Support && random.nextDouble() < 0.4) {
                    Droid ally = findWeakestAlly(team2);
                    if (ally != null && ally != attacker) {
                        performHeal((Support) attacker, ally);
                        continue;
                    }
                }

                Droid target = strategy.selectTarget(team1);
                if (target != null) {
                    performAttack(attacker, target);
                    if (!target.isAlive()) {
                        logger.logAction(String.format("  %s знищено!", target.getName()));
                    }
                }
            }

            round++;

            if (round > 50) {
                logger.logAction("\nБій затягнувся! Нічия!");
                return;
            }
        }

        logger.logAction("\n" + "=".repeat(50));
        if (hasAliveDroids(team1)) {
            logger.logAction("Перемогла команда 1!");
            logger.logAction("Живі бійці: " + getAliveCount(team1));
        } else {
            logger.logAction("Перемогла команда 2!");
            logger.logAction("Живі бійці: " + getAliveCount(team2));
        }
        logger.logAction("=".repeat(50));
    }

    private boolean hasAliveDroids(List<Droid> team) {
        for (Droid droid : team) {
            if (droid.isAlive()) {
                return true;
            }
        }
        return false;
    }

    private int getAliveCount(List<Droid> team) {
        int count = 0;
        for (Droid droid : team) {
            if (droid.isAlive()) {
                count++;
            }
        }
        return count;
    }

    private Droid findWeakestAlly(List<Droid> team) {
        Droid weakest = null;

        for (Droid droid : team) {
            if (!droid.isAlive() || droid.getCurrentHealth() >= droid.getMaxHealth()) {
                continue;
            }
            if (weakest == null || droid.getCurrentHealth() < weakest.getCurrentHealth()) {
                weakest = droid;
            }
        }

        return weakest;
    }
}