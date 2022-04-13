package by.epam.kpp.logic;

import by.epam.kpp.entity.SomthMoving;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component                                                                  // для spring bean
public class HashCalculation {

    private final Map<SomthMoving, Double> hashMap = new HashMap<>();

    public boolean isContain(SomthMoving key) {
        return hashMap.containsKey(key);
    }

    public void addToMap(SomthMoving key, Double  obj) {
        hashMap.put(key, obj);
    }

    public Double getParameters(SomthMoving key) {
        return hashMap.get(key);
    }

}
