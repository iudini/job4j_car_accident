package ru.job4j.accident.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

//@Repository
public class AccidentMem {
    private final Map<Integer, Accident> accidents = new HashMap<>();
    private final Map<Integer, AccidentType> types = new HashMap<>();
    private final Map<Integer, Rule> rules = new HashMap<>();
    private final AtomicInteger index = new AtomicInteger(1);

    public AccidentMem() {
        rules.put(1, Rule.of(1, "Статья. 1"));
        rules.put(2, Rule.of(2, "Статья. 2"));
        rules.put(3, Rule.of(3, "Статья. 3"));
        Set<Rule> set1 = new HashSet<>();
        set1.add(rules.get(1));
        Set<Rule> set2 = new HashSet<>();
        set1.add(rules.get(2));
        set1.add(rules.get(3));
        Set<Rule> set3 = new HashSet<>();
        set1.add(rules.get(1));
        set1.add(rules.get(3));
        types.put(1, AccidentType.of(1, "Две машины"));
        types.put(2, AccidentType.of(2, "Машина и человек"));
        types.put(3, AccidentType.of(3, "Машина и велосипед"));
        accidents.put(1, Accident.of(index.getAndIncrement(), "Name1", "Text1", "Moscow", types.get(1), set1));
        accidents.put(2, Accident.of(index.getAndIncrement(), "Name2", "Text2", "Kazan", types.get(2), set2));
        accidents.put(3, Accident.of(index.getAndIncrement(), "Name3", "Text3", "Tver", types.get(3), set3));
        accidents.put(4, Accident.of(index.getAndIncrement(), "Name4", "Text4", "Klin", types.get(2), set1));
    }

    public Collection<Accident> getAccidents() {
        return accidents.values();
    }

    public Collection<AccidentType> getTypes() {
        return types.values();
    }

    public Collection<Rule> getRules() {
        return rules.values();
    }

    public void create(Accident accident, String[] ids) {
        if (accident.getId() == 0) {
            accident.setId(index.getAndIncrement());
        }
        Set<Rule> rules = Arrays.stream(ids)
                .map(id -> this.rules.get(Integer.parseInt(id)))
                .collect(Collectors.toSet());
        accident.setRules(rules);
        accident.setType(types.get(accident.getType().getId()));
        accidents.put(accident.getId(), accident);
    }

    public Optional<Accident> findById(int id) {
        return Optional.of(accidents.get(id));
    }

    public AccidentType findTypeById(int id) {
        return types.get(id);
    }

    public void update(Accident accident) {
        accidents.put(accident.getId(), accident);
    }
}
