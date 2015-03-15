package Data;

/**
 * Created by Kushal on 12-03-2015.
 */
public class DayWiseList {
    private String MonthDay, DayWork;

    public DayWiseList(String monthDay, String dayWork) {
        MonthDay = monthDay;
        DayWork = dayWork;
    }

    public String getMonthDay() {
        return MonthDay;
    }

    public void setMonthDay(String monthDay) {
        MonthDay = monthDay;
    }

    public String getDayWork() {
        return DayWork;
    }

    public void setDayWork(String dayWork) {
        DayWork = dayWork;
    }
}
