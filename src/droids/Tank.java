package droids;

public class Tank extends Droid {
    private static final int BASE_HEALTH = 180;
    private static final int BASE_DAMAGE = 40;
    private static final int BASE_ACCURACY = 60;
    private int armor = 5;

    public Tank(String name) {
        super(name, BASE_HEALTH, BASE_DAMAGE, BASE_ACCURACY, "Танк");
    }

    @Override
    public void takeDamage(int amount) {
        int reducedDamage = Math.max(1, amount - armor);
        super.takeDamage(reducedDamage);
    }

    @Override
    public String getFullInfo() {
        return super.getFullInfo() + " | Armor: " + armor;
    }
}