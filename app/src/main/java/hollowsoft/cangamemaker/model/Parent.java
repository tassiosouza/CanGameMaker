package hollowsoft.cangamemaker.model;

import com.orm.SugarRecord;

public final class Parent extends SugarRecord<Parent> {

    private String name;
    private String mobile;

    public Parent() {

    }

    public Parent(final String name, final String mobile) {
        this.name = name;
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
