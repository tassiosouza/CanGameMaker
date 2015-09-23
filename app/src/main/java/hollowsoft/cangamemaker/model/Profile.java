package hollowsoft.cangamemaker.model;

import com.orm.SugarRecord;

public final class Profile extends SugarRecord<Profile> {

    public Patient patient;
    public Setting setting;

    public Profile() {

    }

    public Profile(final Patient patient, final Setting setting) {
        this.setPatient(patient);
        this.setSetting(setting);
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Setting getSetting() {
        return setting;
    }

    public void setSetting(Setting setting) {
        this.setting = setting;
    }
}
