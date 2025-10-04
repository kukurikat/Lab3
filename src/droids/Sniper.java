package droids;

public class Sniper extends Droid {
    private static final int BASE_HEALTH = 80;
    private static final int BASE_DAMAGE = 45;
    private static final int BASE_ACCURACY = 90;

    public Sniper(String name) {
        super(name, BASE_HEALTH, BASE_DAMAGE, BASE_ACCURACY, "Снайпер");
    }

    @Override
    protected int calculateDamage() {
        if (Math.random() < 0.25) {
            return damage * 2;
        }
        return super.calculateDamage();
    }
}
