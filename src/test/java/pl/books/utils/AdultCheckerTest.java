package pl.books.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;

class AdultCheckerTest {

    @ParameterizedTest
    @ValueSource(strings = {"88051099999", "88851099999", "04251099999", "04311099999", "04311199999"})
    void should_return_true_for_adult(String pesel) {
        //when
        boolean result = new AdultChecker(pesel, LocalDate.of(2022, 11, 11)).check();

        //then
        Assertions.assertTrue(result);
    }

    @ParameterizedTest
    @ValueSource(strings = {"04311299999", "04321099999"})
    void should_return_false_for_non_adult(String pesel) {
        //when
        boolean result = new AdultChecker(pesel, LocalDate.of(2022, 11, 11)).check();

        //then
        Assertions.assertFalse(result);
    }

}