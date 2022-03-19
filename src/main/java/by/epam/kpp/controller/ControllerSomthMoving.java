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

import static by.epam.kpp.logic.Calculation.findAverageSpeed;

@RestController()
@RequestMapping("/AverageSpeed")
public class ControllerSomthMoving {

    Logger logger = LogManager.getLogger(ControllerSomthMoving.class);

    private final CountThread countThread = new CountThread();
    private ModelAndView modelAndView;
    private HashCalculation hashCalculation;

    @Autowired
    public void setModelAndView()
    {
        modelAndView = new ModelAndView();
    }

    @Autowired                                                                            // spring иньекция
    public void sethashCalculation(HashCalculation hashCalculation) {
        this.hashCalculation = hashCalculation;
    }

    @GetMapping()
    public ModelAndView doGet(){
        modelAndView.setViewName("index");
        modelAndView.addObject("SomthMoving", new SomthMoving());
        modelAndView.setStatus(HttpStatus.OK);
        countThread.startThread();
        logger.info("Successfully getMapping");
        return modelAndView;
    }

    @PostMapping()
    public ModelAndView doPost(@RequestParam("path")Double path, @RequestParam("speed")Double speed)
    {
        SomthMoving somthMoving;
        double averangeSpeed = 0;
        double hashkey = 0;
        try {
            averangeSpeed = findAverageSpeed(path,speed);
        } catch (IncorrectDataExeption e) {
            modelAndView.addObject("Error","Некорректные данные. Измените путь или скорость.");
            modelAndView.setStatus(HttpStatus.BAD_REQUEST);
            return modelAndView;
        }
        hashkey = (path*speed*averangeSpeed)*31;
        if(hashCalculation.isContain(hashkey))
        {
            somthMoving = hashCalculation.getParameters(hashkey);
            logger.info("get from HashMap by key: "+hashkey);
        }else{
            somthMoving = new SomthMoving(path,speed,averangeSpeed);
            hashCalculation.addToMap(hashkey,somthMoving);
            logger.info("add to HashMap key: "+hashkey);
        }
        System.out.println("hash "+somthMoving.hashCode());
        modelAndView.setViewName("index");
        modelAndView.addObject("Error","");
        modelAndView.addObject("SomthMoving", somthMoving);
        modelAndView.setStatus(HttpStatus.OK);
        logger.info("Successfully postMapping");

        return modelAndView;
    }

    @GetMapping("/count")
    public ModelAndView doGetCounter()
    {
        logger.info("get numb connection by /count");
        modelAndView.addObject("count", CountAtomic.value());
        modelAndView.setViewName("count");
        logger.info("Successfully getMapping(/count)");
        return modelAndView;
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)                                         // обработчик unchecked ошибок
    public ResponseEntity<String> handleUncheced(RuntimeException e) {
        logger.warn("error 500");
        return new ResponseEntity<>( "<h1>Error 500<br></h1>"+RuntimeException.class+ e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }





}
