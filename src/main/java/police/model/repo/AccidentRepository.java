package police.model.repo;

import police.model.Accident;

import java.util.List;

public interface AccidentRepository {
    public boolean addAccident(Accident accident);
    public boolean delete(long id);
    public boolean update(Accident accident);
    public List<Accident> getAllAccidents();
    public Accident getById(long id);
}
