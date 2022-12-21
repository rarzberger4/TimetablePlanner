package TTP;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Person {
    private String name;
    List<LocalDate> notAvailable = new ArrayList<>();

    public Person(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setNotAvailable(LocalDate date) {
        notAvailable.add(date);
    }

    public boolean checkAvailability(LocalDate date) {
        return  !notAvailable.contains(date);
    }

}
