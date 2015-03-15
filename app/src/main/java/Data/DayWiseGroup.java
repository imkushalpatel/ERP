package Data;

import java.util.ArrayList;

/**
 * Created by Kushal on 12-03-2015.
 */
public class DayWiseGroup {

    private String MonthName, MonthTotal;
    private ArrayList<DayWiseList> Children;

    public DayWiseGroup(String monthName, String monthTotal, ArrayList<DayWiseList> child) {
        MonthName = monthName;
        MonthTotal = monthTotal;
        this.Children = child;
    }


    public DayWiseGroup(String MonthName, String MonthTotal) {
        this.MonthName = MonthName;
        this.MonthTotal = MonthTotal;
    }

    public String getMonthName() {
        return MonthName;
    }

    public void setMonthName(String monthName) {
        MonthName = monthName;
    }

    public String getMonthTotal() {
        return MonthTotal;
    }

    public void setMonthTotal(String monthTotal) {
        MonthTotal = monthTotal;
    }

    public ArrayList<DayWiseList> getChildren() {
        return Children;
    }

    public void setChild(ArrayList<DayWiseList> child) {
        this.Children = child;
    }
}
