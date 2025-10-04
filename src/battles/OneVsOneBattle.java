package battles;

import droids.Droid;
import droids.Support;

public class OneVsOneBattle extends Battle {
    private Droid droid1;
    private Droid droid2;

    public OneVsOneBattle(Droid droid1, Droid droid2) {
        super();
        this.droid1 = droid1;
        this.droid2 = droid2;
    }

    @Override
    public void startBattle() {
        logger.logAction("\nБій 1 на 1");
        logger.logAction(String.format("\n%s VS %s\n", droid1.getName(), droid2.getName()));

        int round = 1;
        while (droid1.isAlive() && droid2.isAlive()) {
            logger.logAction(String.format("\nРаунд %d", round));
            performAttack(droid1, droid2);
            if (droid1 instanceof Support && random.nextDouble() < 0.3) {
                performHeal((Support) droid1, droid1);
            }

            if (!droid2.isAlive()) {
                break;
            }
            performAttack(droid2, droid1);
            if (droid2 instanceof Support && random.nextDouble() < 0.3) {
                performHeal((Support) droid2, droid2);
            }

            round++;

            if (round > 100) {
                logger.logAction("\nБій затягнувся, нічия!");
                return;
            }
        }

        logger.logAction("\n" + "=".repeat(50));
        if (droid1.isAlive()) {
            logger.logAction(String.format("Переможець: %s (HP: %d/%d)",
                    droid1.getName(), droid1.getCurrentHealth(), droid1.getMaxHealth()));
        } else {
            logger.logAction(String.format("Переможець: %s (HP: %d/%d)",
                    droid2.getName(), droid2.getCurrentHealth(), droid2.getMaxHealth()));
        }
        logger.logAction("=".repeat(50));
    }
}