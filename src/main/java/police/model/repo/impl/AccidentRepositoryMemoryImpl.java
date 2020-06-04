package police.model.repo.impl;

import police.model.Accident;
import police.model.repo.AccidentRepository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccidentRepositoryMemoryImpl implements AccidentRepository {
    private final Map<Integer, Accident> accidents = new HashMap<>();

    @PostConstruct
    public void init() {
        for (int n = 1; n < 5; n++) {
            Accident acc = new Accident("acc" + n, "text" + n, "Улица Пушкина, село Кукушкино");
            acc.setId(n);
            accidents.put(n, acc);
        }
    }

    @Override
    public boolean addAccident(Accident accident) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        boolean result = false;
        if (accidents.containsKey(id)) {
            accidents.remove(id);
            result = true;
        }
        return result;
    }

    @Override
    public boolean update(Accident accident) {
        boolean result = false;
        Integer id = accident.getId();
        if (accidents.containsKey(id)) {
            accidents.put(id, accident);
            result = true;
        }
        return result;
    }

    @Override
    public List<Accident> getAllAccidents() {
        return new ArrayList<>(accidents.values());
    }

    @Override
    public Accident getById(int id) {
        return accidents.get(id);
    }
}
