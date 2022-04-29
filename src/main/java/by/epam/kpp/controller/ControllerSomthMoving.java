package by.epam.kpp.controller;

import by.epam.kpp.count.CountAtomic;
import by.epam.kpp.entity.SomthMoving;
import by.epam.kpp.exeption.IncorrectDataExeption;
import by.epam.kpp.logic.HashCalculation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

import static by.epam.kpp.logic.Calculation.findAverageSpeed;

@RestController()
@RequestMapping("/TimeMoving")
public class ControllerSomthMoving {

    Logger logger = LogManager.getLogger(ControllerSomthMoving.class);

    private ModelAndView modelAndView;

    private HashCalculation hashCalculation;


    private CountAtomic countAtomic;

    @Autowired
    public void setCountAtomic(){countAtomic = new CountAtomic();}
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
        System.out.println(Thread.currentThread());
        modelAndView.addObject("SomthMoving", new SomthMoving());
        modelAndView.addObject("timeMoving", 0);
        modelAndView.setStatus(HttpStatus.OK);
        countAtomic.increment();
        //countThread.startThread();
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

    }

    @PostMapping("/bulk")
    public ResponseEntity<?> doBulk(@RequestBody List<SomthMoving> params) {

        List<Double> rezults = new ArrayList<>();

        for (SomthMoving smth:params) {
            try {
                rezults.add(findAverageSpeed(smth.getPath(),smth.getSpeed()));
            } catch (IncorrectDataExeption e) {
                e.printStackTrace();
            }
        }


        Map<String, Object> mapa = new HashMap<>();

        mapa.put("avergeRezult", rezults
                .stream()
                .mapToDouble(d -> d)
                .average()
                .orElseThrow(NoSuchElementException::new));

        mapa.put("maxRezult", rezults
                .stream()
                .mapToDouble(d -> d)
                .max()
                .orElseThrow(NoSuchElementException::new));

        mapa.put("minRezult", rezults
                .stream()
                .mapToDouble(d -> d)
                .min()
                .orElseThrow(NoSuchElementException::new));

        mapa.put("minPath", params
                .stream()
                .min(Comparator.comparing(SomthMoving::getPath))
                .orElseThrow(NoSuchElementException::new));

        mapa.put("minSpeed", params
                .stream()
                .min(Comparator.comparing(SomthMoving::getSpeed))
                .orElseThrow(NoSuchElementException::new));

        mapa.put("maxPath", params
                .stream()
                .max(Comparator.comparing(SomthMoving::getPath))
                .orElseThrow(NoSuchElementException::new));

        mapa.put("maxSpeed", params
                .stream()
                .max(Comparator.comparing(SomthMoving::getSpeed))
                .orElseThrow(NoSuchElementException::new));

        return new ResponseEntity<>(mapa,HttpStatus.OK);
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
