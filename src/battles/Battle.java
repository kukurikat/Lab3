package battles;

import droids.Droid;
import droids.Support;
import java.util.List;
import java.util.Random;

public abstract class Battle {
    protected BattleLogger logger;
    protected Random random;

    public Battle() {
        this.logger = new BattleLogger();
        this.random = new Random();
    }

    public abstract void startBattle();

    public List<String> getBattleLog() {
        return logger.getLog();
    }

    protected void performAttack(Droid attacker, Droid target) {
        int damage = attacker.attack(target);
        if (damage > 0) {
            logger.logAction(String.format("  %s атакує %s і завдає %d урону! (HP: %d/%d)",
                    attacker.getName(), target.getName(), damage,
                    target.getCurrentHealth(), target.getMaxHealth()));
        } else {
            logger.logAction(String.format("  %s атакує %s, але промахується!",
                    attacker.getName(), target.getName()));
        }
    }

    protected void performHeal(Support healer, Droid ally) {
        int healed = healer.healAlly(ally);
        if (healed > 0) {
            logger.logAction(String.format("  %s лікує %s на %d HP! (HP: %d/%d)",
                    healer.getName(), ally.getName(), healed,
                    ally.getCurrentHealth(), ally.getMaxHealth()));
        }
    }
}