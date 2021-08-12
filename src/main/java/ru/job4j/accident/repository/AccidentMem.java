package ru.job4j.accident.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;

import java.util.HashMap;
import java.util.Map;

@Repository
public class AccidentMem {
    private Map<Integer, Accident> accidents = new HashMap<>();

    public AccidentMem() {
        accidents.put(1, Accident.of(1, "Name1", "Text1", "Moscow"));
        accidents.put(2, Accident.of(2, "Name2", "Text2", "Kazan"));
        accidents.put(3, Accident.of(3, "Name3", "Text3", "Tver"));
        accidents.put(4, Accident.of(4, "Name4", "Text4", "Klin"));
    }

    public Map<Integer, Accident> getAccidents() {
        return accidents;
    }
}
