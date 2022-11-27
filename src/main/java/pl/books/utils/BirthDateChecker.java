package pl.books.utils;

import java.time.LocalDate;
import java.util.regex.Pattern;

public class BirthDateChecker {

    private final String pesel;

    public BirthDateChecker(String pesel) {
        this.pesel = pesel;
    }

    public LocalDate getBirthDateFromPesel() {
        String yearString = pesel.substring(0, 2);
        int year = 0;
        String monthString = pesel.substring(2, 4);
        int month = 0;
        int day = Integer.parseInt(pesel.substring(4, 6));
        boolean pattern1800 = Pattern.matches("^\\d{2}[8-9].*", pesel);
        boolean pattern1900 = Pattern.matches("^\\d{2}[0-1].*", pesel);
        boolean pattern2000 = Pattern.matches("^\\d{2}[2-3].*", pesel);
        LocalDate birthDate;

        if (pattern1800) {
            year = Integer.parseInt(yearString) + 1800;
            month = Integer.parseInt(monthString) -80;
        } else if (pattern1900) {
            year = Integer.parseInt(yearString) + 1900;
            month = Integer.parseInt(monthString);
        } else if (pattern2000) {
            year = Integer.parseInt(yearString) + 2000;
            month = Integer.parseInt(monthString) - 20;
        }

        return LocalDate.of(year, month, day);
    }
}
