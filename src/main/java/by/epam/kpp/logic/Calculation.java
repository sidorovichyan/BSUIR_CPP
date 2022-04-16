package by.epam.kpp.logic;


import by.epam.kpp.exeption.IncorrectDataExeption;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class Calculation {

    private Calculation() {}

    private static final Logger logger = LogManager.getLogger(Calculation.class);

    public static double findAverageSpeed(double path, double speed) throws IncorrectDataExeption {
        if(speed < 0 || path < 0 )
        {
            logger.warn("Path or speed not be less than 0!" + IncorrectDataExeption.class.getName() +" at "+Calculation.class.getName());
            throw new IncorrectDataExeption("Path or speed not be less than 0! "+ IncorrectDataExeption.class.getName());
        }
        if(speed == 0)
        {
            logger.warn("Divide-by-zero error! " + IncorrectDataExeption.class.getName() +" at "+Calculation.class.getName());
            throw new IncorrectDataExeption("Divide-by-zero error! "+ IncorrectDataExeption.class.getName());
        }
        double scale = Math.pow(10, 2);
        double rezult = path/speed;
        rezult = Math.ceil(rezult * scale) / scale;
        return rezult;
    }

}
