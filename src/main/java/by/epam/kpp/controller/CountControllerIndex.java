package by.epam.kpp.controller;

import by.epam.kpp.count.CountAtomic;
import by.epam.kpp.entity.SomthMoving;
import by.epam.kpp.exeption.IncorrectDataExeption;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import static by.epam.kpp.logic.Calculation.findAverageSpeed;

@Controller
public class CountControllerIndex {

    Logger logger = LogManager.getLogger(CountControllerIndex.class);

    @RequestMapping(value = { "/count",  }, method = RequestMethod.GET)
    public String getCount(Model model){
        logger.info("get numb connection by /count");
        model.addAttribute("count",CountAtomic.value());
        return "count";
    }


}
