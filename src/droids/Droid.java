package droids;

public abstract class Droid {
    protected String name;
    protected int maxHealth;
    protected int currentHealth;
    protected int damage;
    protected int accuracy; // 0-100%
    protected String type;

    public Droid(String name, int health, int damage, int accuracy, String type) {
        this.name = name;
        this.maxHealth = health;
        this.currentHealth = health;
        this.damage = damage;
        this.accuracy = accuracy;
        this.type = type;
    }

    public int attack(Droid target) {
        if (Math.random() * 100 <= accuracy) {
            int actualDamage = calculateDamage();
            target.takeDamage(actualDamage);
            return actualDamage;
        }
        return 0;
    }

    protected int calculateDamage() {
        return damage + (int)(Math.random() * (damage * 0.2));
    }

    public void takeDamage(int amount) {
        currentHealth = Math.max(0, currentHealth - amount);
    }

    public void heal(int amount) {
        currentHealth = Math.min(maxHealth, currentHealth + amount);
    }

    public boolean isAlive() {
        return currentHealth > 0;
    }

    public String getName() { return name; }
    public int getCurrentHealth() { return currentHealth; }
    public int getMaxHealth() { return maxHealth; }
    public String getType() { return type; }

    public int getHealthPercent() {
        return (int)((double)currentHealth / maxHealth * 100);
    }

    @Override
    public String toString() {
        return String.format("%s (%s) - HP: %d/%d (%d%%)",
                name, type, currentHealth, maxHealth, getHealthPercent());
    }

    public String getFullInfo() {
        return String.format("%s [%s]\n  HP: %d | Damage: %d | Accuracy: %d%%",
                name, type, maxHealth, damage, accuracy);
    }
}
