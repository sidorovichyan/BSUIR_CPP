package by.epam.kpp.logic;

import by.epam.kpp.entity.SomthMoving;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component                                                                  // для spring bean
public class HashCalculation {

    private final HashMap<Double, SomthMoving> hashMap = new HashMap<>();

    public boolean isContain(Double key) {
        return hashMap.containsKey(key);
    }

    public void addToMap(Double key, SomthMoving obj) {
        hashMap.put(key, obj);
    }

    public SomthMoving getParameters(Double key) {
        return hashMap.get(key);
    }

}
