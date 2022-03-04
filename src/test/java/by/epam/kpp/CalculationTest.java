package by.epam.kpp;

import by.epam.kpp.exeption.IncorrectDataExeption;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import by.epam.kpp.logic.Calculation;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculationTest {
    @Test
    void testFindAverangeSpeed1() throws IncorrectDataExeption {
        double rezult = Calculation.findAverageSpeed(30,6);
        int expected = 5;
        assertEquals(expected, rezult);
    }

    @Test
    void testFindAverangeSpeed2() throws IncorrectDataExeption {
        Throwable thrown = assertThrows(IncorrectDataExeption.class, () -> {
            Calculation.findAverageSpeed(30,0);
        });
        Assertions.assertNotNull(thrown.getMessage());
    }

    @Test
    void testFindAverangeSpeed3() throws IncorrectDataExeption {
        Throwable thrown = assertThrows(IncorrectDataExeption.class, () -> {
            Calculation.findAverageSpeed(-10,20);
        });
        Assertions.assertNotNull(thrown.getMessage());
    }

}
