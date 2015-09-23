package hollowsoft.cangamemaker.model;

import com.orm.SugarRecord;

import java.util.HashMap;

public final class Setting extends SugarRecord<Setting> {

    public static final int TIME = 5;

    private HashMap<Step, Integer> timeMap = new HashMap<>();

    public Setting() {

    }

    public Setting(final int time) {

        timeMap.put(Step.TEXT, time);
        timeMap.put(Step.AUDIO, time);
        timeMap.put(Step.VIDEO, time);
    }

    public int getSet(final Step step) {
        return timeMap.get(step);
    }

    public void putSet(final Step step, final int time) {
        timeMap.put(step, time);
    }

    public void removeSet(final Step step) {
        timeMap.remove(step);
    }

    public HashMap<Step, Integer> getTimeMap() {
        return timeMap;
    }

    public void setTimeMap(HashMap<Step, Integer> timeMap) {
        this.timeMap = timeMap;
    }
}
