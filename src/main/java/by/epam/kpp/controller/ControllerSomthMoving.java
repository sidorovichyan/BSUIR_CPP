package by.epam.kpp.controller;

import by.epam.kpp.count.CountAtomic;
import by.epam.kpp.count.CountThread;
import by.epam.kpp.entity.SomthMoving;
import by.epam.kpp.exeption.IncorrectDataExeption;
import by.epam.kpp.logic.HashCalculation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.OptionalDouble;

import static by.epam.kpp.logic.Calculation.findAverageSpeed;

@RestController()
@RequestMapping("/TimeMoving")
public class ControllerSomthMoving {

    Logger logger = LogManager.getLogger(ControllerSomthMoving.class);

    private final CountThread countThread = new CountThread();
    private ModelAndView modelAndView;

    private HashCalculation hashCalculation;

    @Autowired
    public void setModelAndView() {
        modelAndView = new ModelAndView();
    }

    @Autowired                                                                            // spring иньекция
    public void sethashCalculation(HashCalculation hashCalculation) {
        this.hashCalculation = hashCalculation;
    }

    @GetMapping()
    public ModelAndView doGet() {
        modelAndView.setViewName("index");
        modelAndView.addObject("SomthMoving", new SomthMoving());
        modelAndView.addObject("timeMoving", 0);
        modelAndView.setStatus(HttpStatus.OK);
        countThread.startThread();
        logger.info("Successfully getMapping");
        return modelAndView;
    }

    @PostMapping()
    public ModelAndView doPost(@ModelAttribute SomthMoving somthMoving) {
        double timeMoving = 0;
        if(hashCalculation.isContain(somthMoving)){
            logger.info("get from HashMap by key: "+somthMoving.hashCode());
            timeMoving = hashCalculation.getParameters(somthMoving);
        }else{
            try {
                timeMoving = findAverageSpeed(somthMoving.getPath(),somthMoving.getSpeed());
                hashCalculation.addToMap(somthMoving,timeMoving);
                logger.info("add to HashMap key: "+somthMoving.hashCode());
            } catch (IncorrectDataExeption e) {
                modelAndView.addObject("Error","Некорректные данные. Измените путь или скорость.");
                modelAndView.setStatus(HttpStatus.BAD_REQUEST);
                return modelAndView;
            }
        }
        modelAndView.setViewName("index");
        modelAndView.addObject("Error","");
        modelAndView.addObject("SomthMoving", somthMoving);
        modelAndView.setStatus(HttpStatus.OK);
        modelAndView.addObject("timeMoving", timeMoving);
        logger.info("Successfully postMapping");
        return modelAndView;

//

//    //    hashkey = (path*speed*averangeSpeed)*31;
//
//
//        if(hashCalculation.isContain(hashkey))
//        {
//            somthMoving = hashCalculation.getParameters(hashkey);
//            logger.info("get from HashMap by key: "+hashkey);
//        }else{
//            somthMoving = new SomthMoving(path,speed,averangeSpeed);
//            hashCalculation.addToMap(hashkey,somthMoving);
//            logger.info("add to HashMap key: "+hashkey);
//        }
//        System.out.println("hash "+somthMoving.hashCode());
//
    }


    @PostMapping("/bulk")
    public ResponseEntity<?> doBulk(@RequestBody List<SomthMoving> params) {
//        SomthMoving min = params
//                .stream()
//                .min(Comparator.comparing(SomthMoving::getAverangeSpeed))
//                .orElseThrow(NoSuchElementException::new);
//        SomthMoving max = params
//                .stream()
//                .max(Comparator.comparing(SomthMoving::getAverangeSpeed))
//                .orElseThrow(NoSuchElementException::new);
//
//        OptionalDouble average = params.stream().mapToDouble(SomthMoving::getAverangeSpeed).average();
//
//        logger.info("Successfully postMapping bulk operations");
//        return new ResponseEntity<>(
//                "\nMax result: p = " + max.getPath()+" s = "+max.getSpeed() +" -> "+max.getAverangeSpeed() +
//                        "\nMin result: p = " + min.getPath()+" s = "+min.getSpeed() +" -> "+min.getAverangeSpeed()+
//                        "\nAverange result: "+average.getAsDouble(), HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("/count")
    public ModelAndView doGetCounter() {
        logger.info("get numb connection by /count");
        modelAndView.addObject("count", CountAtomic.value());
        modelAndView.setViewName("count");
        logger.info("Successfully getMapping(/count)");
        return modelAndView;
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    // обработчик unchecked ошибок
    public ResponseEntity<String> handleUncheced(RuntimeException e) {
        logger.warn("error 500");
        return new ResponseEntity<>("<h1>Error 500<br></h1>" + RuntimeException.class + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
