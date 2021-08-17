package ru.job4j.accident.service;

import org.springframework.stereotype.Service;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;
import ru.job4j.accident.repository.AccidentRepository;
import ru.job4j.accident.repository.AccidentTypeRepository;
import ru.job4j.accident.repository.RuleRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class AccidentService {
    private final AccidentRepository accidents;
    private final AccidentTypeRepository accidentTypes;
    private final RuleRepository rules;

    public AccidentService(AccidentRepository accidents, AccidentTypeRepository accidentTypes, RuleRepository rules) {
        this.accidents = accidents;
        this.accidentTypes = accidentTypes;
        this.rules = rules;
    }

    public Collection<Accident> getAccidents() {
        List<Accident> res = new ArrayList<>();
        accidents.findAll().forEach(res::add);
        return res;
    }

    public Collection<AccidentType> getTypes() {
        List<AccidentType> res = new ArrayList<>();
        accidentTypes.findAll().forEach(res::add);
        return res;
    }

    public Collection<Rule> getRules() {
        List<Rule> res = new ArrayList<>();
        rules.findAll().forEach(res::add);
        return res;
    }

    public void create(Accident accident, String[] ids) {
        for (String id : ids) {
            rules.findById(Integer.parseInt(id))
                    .ifPresent(accident::addRule);
        }
        accidents.save(accident);
    }

    public void update(Accident accident, String[] ids) {
        create(accident, ids);
    }

    public Optional<Accident> findById(int id) {
        return accidents.findById(id);
    }
}
