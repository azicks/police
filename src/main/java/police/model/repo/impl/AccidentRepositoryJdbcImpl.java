package police.model.repo.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import police.model.Accident;
import police.model.repo.AccidentRepository;

import java.util.List;

@Repository
public class AccidentRepositoryJdbcImpl implements AccidentRepository {
    private final JdbcTemplate jdbc;

    @Autowired
    public AccidentRepositoryJdbcImpl(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public boolean addAccident(Accident accident) {
        jdbc.update("insert into accident (name, address, text) values (?, ?, ?)",
                accident.getName(), accident.getAddress(), accident.getText());
        return true;
    }

    @Override
    public boolean delete(long id) {
        jdbc.update("delete from accident where id = ?", id);
        return true;
    }

    @Override
    public boolean update(Accident accident) {
        jdbc.update("update accident  set name = ?, address = ?, text = ? where id = ?",
                accident.getName(), accident.getAddress(), accident.getText(), accident.getId());
        return true;
    }

    @Override
    public List<Accident> getAllAccidents() {
        return jdbc.query("select id, name, address, text from accident",
                (rs, row) -> {
                    Accident accident = new Accident(rs.getString("name"),
                            rs.getString("text"),
                            rs.getString("address"));
                    accident.setId(rs.getInt("id"));
                    return accident;
                });
    }

    @Override
    public Accident getById(long id) {
        return jdbc.queryForObject("select id, name, address, text from accident where id = ?",
                (rs, row) -> {
                    Accident accident = new Accident(rs.getString("name"), rs.getString("text"), rs.getString("address"));
                    accident.setId(id);
                    return accident;
                }, id);
    }
}
