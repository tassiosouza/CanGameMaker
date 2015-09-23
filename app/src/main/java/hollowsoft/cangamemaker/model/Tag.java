package hollowsoft.cangamemaker.model;

import com.orm.SugarRecord;

public class Tag extends SugarRecord<Tag> {

    private String name;
    private Patient patient;

    public Tag() {

    }

    public Tag(final String name, final Patient patient) {
        this.name = name;
        this.patient = patient;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}
