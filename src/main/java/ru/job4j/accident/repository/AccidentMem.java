package ru.job4j.accident.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentMem {
    private Map<Integer, Accident> accidents = new HashMap<>();
    AtomicInteger index = new AtomicInteger(1);

    public AccidentMem() {
        accidents.put(1, Accident.of(index.getAndIncrement(), "Name1", "Text1", "Moscow"));
        accidents.put(2, Accident.of(index.getAndIncrement(), "Name2", "Text2", "Kazan"));
        accidents.put(3, Accident.of(index.getAndIncrement(), "Name3", "Text3", "Tver"));
        accidents.put(4, Accident.of(index.getAndIncrement(), "Name4", "Text4", "Klin"));
    }

    public Collection<Accident> getAccidents() {
        return accidents.values();
    }

    public void create(Accident accident) {
        if (accident.getId() == 0) {
            accident.setId(index.getAndIncrement());
        }
        accidents.put(accident.getId(), accident);
    }

    public Accident find(int id) {
        return accidents.get(id);
    }

    public void update(Accident accident) {
        accidents.put(accident.getId(), accident);
    }
}
