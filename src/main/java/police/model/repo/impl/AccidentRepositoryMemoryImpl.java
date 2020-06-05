package police.model.repo.impl;

import org.springframework.stereotype.Component;
import police.model.Accident;
import police.model.repo.AccidentRepository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AccidentRepositoryMemoryImpl implements AccidentRepository {
    private final Map<Long, Accident> accidents = new HashMap<>();
    private int id;

    @PostConstruct
    public void init() {
        for (int n = 1; n < 5; n++) {
            Accident acc = new Accident("acc" + n, "text" + n, "Улица Пушкина, село Кукушкино");
            addAccident(acc);
        }
    }

    @Override
    public boolean addAccident(Accident accident) {
        accident.setId(id++);
        accidents.put(accident.getId(), accident);
        return true;
    }

    @Override
    public boolean delete(long id) {
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
        long id = accident.getId();
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
    public Accident getById(long id) {
        return accidents.get(id);
    }
}
