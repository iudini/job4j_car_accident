package ru.job4j.accident.service;

import org.springframework.stereotype.Service;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.repository.AccidentMem;

import java.util.Map;

@Service
public class AccidentService {
    private AccidentMem accidentMem;

    public AccidentService(AccidentMem accidentMem) {
        this.accidentMem = accidentMem;
    }

    public Map<Integer, Accident> getAllAccidents() {
        return accidentMem.getAccidents();
    }
}
