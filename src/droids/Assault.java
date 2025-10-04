package droids;

public class Assault extends Droid {
    private static final int BASE_HEALTH = 120;
    private static final int BASE_DAMAGE = 30;
    private static final int BASE_ACCURACY = 75;

    public Assault(String name) {
        super(name, BASE_HEALTH, BASE_DAMAGE, BASE_ACCURACY, "Штурмовик");
    }

    @Override
    public int attack(Droid target) {
        int firstHit = super.attack(target);
        if (target.isAlive() && Math.random() < 0.2) {
            return firstHit + super.attack(target);
        }
        return firstHit;
    }
}