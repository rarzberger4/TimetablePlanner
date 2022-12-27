package TTP;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Lecturer extends Person {
    private List<LocalDate> notAvailable = new ArrayList<>();

    public Lecturer(String name) {
        super(name);
    }

    public void setNotAvailable(LocalDate date) {
        notAvailable.add(date);
    }

    public void resetNotAvailable() {
        notAvailable.clear();
    }

    public boolean checkAvailability(LocalDate date) {
        return !notAvailable.contains(date);
    }

}
