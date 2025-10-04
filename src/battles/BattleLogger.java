package battles;

import java.util.ArrayList;
import java.util.List;

public class BattleLogger {
    private List<String> log;

    public BattleLogger() {
        this.log = new ArrayList<>();
    }

    public void logAction(String action) {
        log.add(action);
        System.out.println(action);
    }

    public List<String> getLog() {
        return new ArrayList<>(log);
    }

    public void clear() {
        log.clear();
    }
}
