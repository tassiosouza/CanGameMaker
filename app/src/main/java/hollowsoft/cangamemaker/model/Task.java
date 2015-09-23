package hollowsoft.cangamemaker.model;

import com.orm.SugarRecord;

import java.util.HashMap;
import java.util.Map;

public class Task extends SugarRecord<Task> {

    private String name;
    private Map<Step, String> stepMap = new HashMap<>();
    private Patient patient;

    // Gambiarra por causa do belo orm que eu escolhir usar

    private String text;
    private String pathImage;
    private String pathSound;
    private String pathVideo;

    public void init() {
        stepMap.put(Step.TEXT, text);
        stepMap.put(Step.AUDIO, pathSound);
        stepMap.put(Step.IMAGE, pathImage);
        stepMap.put(Step.VIDEO, pathVideo);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStep(final Step step) {
        return stepMap.get(step);
    }

    public void putStep(final Step step, final String assetPath) {

        if (step == Step.TEXT) {
            text = assetPath;

        } else if (step == Step.AUDIO) {
            pathSound = assetPath;

        } else if (step == Step.VIDEO) {
            pathVideo = assetPath;

        } else if (step == Step.IMAGE) {
            pathImage = assetPath;
        }

        stepMap.put(step, assetPath);
    }

    public void removeStep(final Step step) {
        stepMap.remove(step);
    }

    public Map<Step, String> getStepMap() {
        return stepMap;
    }

    public void setStepMap(final Map<Step, String> stepMap) {
        this.stepMap = stepMap;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}
