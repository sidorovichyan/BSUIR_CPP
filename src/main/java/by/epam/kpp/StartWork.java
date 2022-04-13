package by.epam.kpp;

import by.epam.kpp.logic.Calculation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StartWork {

    private static final Logger logger = LogManager.getLogger(Calculation.class);

    public static void main(String[] args) {
        logger.info("Start application");
        System.out.println("hello");
        SpringApplication.run(StartWork.class, args);
    }
}
