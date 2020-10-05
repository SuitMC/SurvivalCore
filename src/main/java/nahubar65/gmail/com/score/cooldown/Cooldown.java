package nahubar65.gmail.com.score.cooldown;

public class Cooldown {

    private double cooldown;

    public Cooldown(double cooldown) {
        this.cooldown = System.currentTimeMillis() + (cooldown * 1000);
    }

    public int getCooldown() {
        return Math.toIntExact(Math.round((cooldown - System.currentTimeMillis()) / 1000));
    }

    public boolean end() {
        return getCooldown() <= 0;
    }
}