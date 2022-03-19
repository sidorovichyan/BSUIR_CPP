package by.epam.kpp.controller;

import by.epam.kpp.count.CountThread;
import by.epam.kpp.entity.SomthMoving;
import by.epam.kpp.exeption.IncorrectDataExeption;
import by.epam.kpp.logic.HashCalculation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static by.epam.kpp.logic.Calculation.*;

@Controller
public class ControllerIndex {

    private static final Logger logger = LogManager.getLogger(ControllerIndex.class);
    private final CountThread countThread = new CountThread();                             // для подсчета соединений

    private HashCalculation hashCalculation;

    @Autowired                                                                            // spring иньекция
    public void sethashCalculation(HashCalculation hashCalculation) {
        this.hashCalculation = hashCalculation;
    }

    @RequestMapping(value = { "/",  }, method = RequestMethod.GET)
    public String getIndex(@RequestParam(name = "path", required = false, defaultValue = "1") Double path,
                           @RequestParam(name = "speed", required = false, defaultValue = "1") Double speed,
                           Model model) throws IncorrectDataExeption {
        SomthMoving somthMoving = new SomthMoving();
        countThread.startThread();                                              // добавляем +1 к запросам
        double averangeSpeed = findAverageSpeed(path,speed);
        if(hashCalculation.isContain(averangeSpeed))
        {
            somthMoving = hashCalculation.getParameters(averangeSpeed);
            logger.info("get from HashMap by key: "+averangeSpeed);
        }else{
            somthMoving.setPath(path);
            somthMoving.setSpeed(speed);
            somthMoving.setAverangeSpeed(averangeSpeed);
            hashCalculation.addToMap(averangeSpeed,somthMoving);
            logger.info("add to HashMap key: "+averangeSpeed);
        }
        model.addAttribute("SomthMoving",somthMoving);
        logger.info("Successfully getMapping");
        return "index";
    }


    @RequestMapping(value = { "/",  }, method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> findAverange(@RequestBody List<SomthMoving> params)
    {
        SomthMoving min = params
                .stream()
                .min(Comparator.comparing(SomthMoving::getAverangeSpeed))
                .orElseThrow(NoSuchElementException::new);
        SomthMoving max = params
                .stream()
                .max(Comparator.comparing(SomthMoving::getAverangeSpeed))
                .orElseThrow(NoSuchElementException::new);

        OptionalDouble average = params.stream().mapToDouble(SomthMoving::getAverangeSpeed).average();

        logger.info("Successfully postMapping");
        return new ResponseEntity<>(
                "\nMax result: p = " + max.getPath()+" s = "+max.getSpeed() +" -> "+max.getAverangeSpeed() +
                        "\nMin result: p = " + min.getPath()+" s = "+min.getSpeed() +" -> "+min.getAverangeSpeed()+
                        "\nAverange result: "+average.getAsDouble(), HttpStatus.OK);
    }

    @ExceptionHandler(IncorrectDataExeption.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)                                                    // обработчик моих ошибок
    public ResponseEntity<String> handleMyErrors(IncorrectDataExeption e) {
        logger.warn("error 400");
        return new ResponseEntity<>( "<h1>Error 400<br></h1>"+ e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)                                         // обработчик unchecked ошибок
    public ResponseEntity<String> handleUncheced(RuntimeException e) {
        logger.warn("error 500");
        return new ResponseEntity<>( "<h1>Error 500<br></h1>"+RuntimeException.class+ e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
