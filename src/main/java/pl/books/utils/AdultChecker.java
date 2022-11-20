package pl.books.utils;

import java.time.LocalDate;
import java.util.regex.Pattern;

public class AdultChecker {

    private final String pesel;
    private final LocalDate now;

    public AdultChecker(String pesel) {
        this.pesel = pesel;
        this.now = LocalDate.now();
    }

    /**
     * Second constructor meant to be used in tests- LocalDate.now() will vary each day.
     *
     * @param pesel
     * @param now   mocking today.
     */

    public AdultChecker(String pesel, LocalDate now) {
        this.pesel = pesel;
        this.now = now;
    }

    public boolean check() {
        try {
            String yearString = pesel.substring(0, 2);
            int year = 0;
            String monthString = pesel.substring(2, 4);
            int month = 0;
            int day = Integer.parseInt(pesel.substring(4, 6));
            boolean pattern1800 = Pattern.matches("^\\d{2}[8-9].*", pesel);
            boolean pattern1900 = Pattern.matches("^\\d{2}[0-1].*", pesel);
            boolean pattern2000 = Pattern.matches("^\\d{2}[2-3].*", pesel);
            LocalDate birthDate;

            if (pattern1800)
                return true;
            else if (pattern1900)
                return true;
            else if (pattern2000) {
                year = Integer.parseInt(yearString) + 2000;
                month = Integer.parseInt(monthString) - 20;
            }

            birthDate = LocalDate.of(year, month, day);
            return birthDate.plusYears(18).isBefore(now.plusDays(1));
        } catch (Exception e) {
            return false;
        }
    }
}
