package droids;

public class Support extends Droid {
    private static final int BASE_HEALTH = 100;
    private static final int BASE_DAMAGE = 20;
    private static final int BASE_ACCURACY = 70;
    private int healAmount = 15;

    public Support(String name) {
        super(name, BASE_HEALTH, BASE_DAMAGE, BASE_ACCURACY, "Медик");
    }

    public int healAlly(Droid ally) {
        if (ally.getCurrentHealth() < ally.getMaxHealth()) {
            int oldHealth = ally.getCurrentHealth();
            ally.heal(healAmount);
            return ally.getCurrentHealth() - oldHealth;
        }
        return 0;
    }
}