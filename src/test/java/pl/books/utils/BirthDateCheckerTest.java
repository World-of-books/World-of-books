package pl.books.utils;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BirthDateCheckerTest {

    @ParameterizedTest
    @CsvSource(value = {"88851099999 ;1888-05-10", "10921099999; 1810-12-10"}, delimiter = ';')
    void should_return_correct_value_for_1800_pesels(String pesel, String expectedOutcome) {
        //given
        LocalDate expected = LocalDate.parse(expectedOutcome);

        //when
        LocalDate result = new BirthDateChecker(pesel).getBirthDateFromPesel();

        //then
        assertEquals(expected, result);
    }

    @ParameterizedTest
    @CsvSource(value = {"88051099999 ;1988-05-10", "10121099999; 1910-12-10"}, delimiter = ';')
    void should_return_correct_value_for_1900_pesels(String pesel, String expectedOutcome) {
        //given
        LocalDate expected = LocalDate.parse(expectedOutcome);

        //when
        LocalDate result = new BirthDateChecker(pesel).getBirthDateFromPesel();

        //then
        assertEquals(expected, result);
    }

    @ParameterizedTest
    @CsvSource(value = {"88251099999 ;2088-05-10", "10321099999; 2010-12-10"}, delimiter = ';')
    void should_return_correct_value_for_2000_pesels(String pesel, String expectedOutcome) {
        //given
        LocalDate expected = LocalDate.parse(expectedOutcome);

        //when
        LocalDate result = new BirthDateChecker(pesel).getBirthDateFromPesel();

        //then
        assertEquals(expected, result);
    }


}