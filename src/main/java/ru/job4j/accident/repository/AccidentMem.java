package ru.job4j.accident.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentMem {
    private Map<Integer, Accident> accidents = new HashMap<>();
    private Map<Integer, AccidentType> types = new HashMap<>();
    AtomicInteger index = new AtomicInteger(1);

    public AccidentMem() {
        types.put(1, AccidentType.of(1, "Две машины"));
        types.put(2, AccidentType.of(2, "Машина и человек"));
        types.put(3, AccidentType.of(3, "Машина и велосипед"));
        accidents.put(1, Accident.of(index.getAndIncrement(), "Name1", "Text1", "Moscow", types.get(1)));
        accidents.put(2, Accident.of(index.getAndIncrement(), "Name2", "Text2", "Kazan", types.get(2)));
        accidents.put(3, Accident.of(index.getAndIncrement(), "Name3", "Text3", "Tver", types.get(3)));
        accidents.put(4, Accident.of(index.getAndIncrement(), "Name4", "Text4", "Klin", types.get(2)));
    }

    public Collection<Accident> getAccidents() {
        return accidents.values();
    }

    public Collection<AccidentType> getTypes() {
        return types.values();
    }

    public void create(Accident accident) {
        if (accident.getId() == 0) {
            accident.setId(index.getAndIncrement());
        }
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
