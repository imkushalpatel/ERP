package Data;

/**
 * Created by kushal on 16/3/15.
 */
public class DelayList {
    private String Name;
    private String Value;

    public DelayList(String name, String value) {
        Name = name;
        Value = value;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
