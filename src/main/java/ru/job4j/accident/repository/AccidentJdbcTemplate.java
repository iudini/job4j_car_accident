package ru.job4j.accident.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;

import java.sql.PreparedStatement;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

//@Repository
public class AccidentJdbcTemplate {
    private final JdbcTemplate jdbc;

    private final RowMapper<Accident> accidentRowMapper = (rs, row) -> {
        Accident accident = new Accident();
        accident.setId(rs.getInt("a_id"));
        accident.setName(rs.getString("a_name"));
        accident.setAddress(rs.getString("address"));
        accident.setText(rs.getString("text"));
        AccidentType accidentType = new AccidentType();
        accidentType.setId(rs.getInt("at_id"));
        accidentType.setName(rs.getString("at_name"));
        accident.setType(accidentType);
        return accident;
    };
    private final RowMapper<Rule> ruleRowMapper = (rs, row) -> {
        Rule rule = new Rule();
        rule.setId(rs.getInt("id"));
        rule.setName(rs.getString("name"));
        return rule;
    };
    private final RowMapper<AccidentType> accidentTypeRowMapper = (rs, row) -> {
        AccidentType accidentType = new AccidentType();
        accidentType.setId(rs.getInt("id"));
        accidentType.setName(rs.getString("name"));
        return accidentType;
    };

    public AccidentJdbcTemplate(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public Collection<Accident> getAccidents() {
        List<Accident> accidents = jdbc.query(
                "select a.id as a_id, a.name as a_name, a.address, a.text, at.id as at_id, at.name as at_name " +
                        "from accident a left join accident_type at on at.id=a.accident_type_id order by a.id",
                accidentRowMapper);
        for (Accident accident : accidents) {
            List<Rule> rules = jdbc.query(
                    "select r.id, r.name from rule r " +
                            "join accidents_rules ar on r.id = ar.rule_id where ar.accident_id=?",
                    ruleRowMapper, accident.getId());
            rules.forEach(accident::addRule);
        }
        return accidents;
    }

    public Collection<AccidentType> getTypes() {
        return jdbc.query("select id, name from accident_type", accidentTypeRowMapper);
    }

    public Collection<Rule> getRules() {
        return jdbc.query("select id, name from rule", ruleRowMapper);
    }

    public void create(Accident accident, String[] ids) {
        final String INSERT_SQL = "INSERT INTO accident (name, text, address, accident_type_id) VALUES (?, ?, ?, ?)";
                KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[] {"id"});
            ps.setString(1, accident.getName());
            ps.setString(2, accident.getText());
            ps.setString(3, accident.getAddress());
            ps.setInt(4, accident.getType().getId());
            return ps;
    }, keyHolder);
        accident.setId(keyHolder.getKey().intValue());
        for (String id : ids) {
            jdbc.update("INSERT INTO accidents_rules (accident_id, rule_id) VALUES (?, ?)",
                    accident.getId(), Integer.parseInt(id));
        }
    }

    public void update(Accident accident, String[] ids) {
        jdbc.update("UPDATE accident SET name = ?, text = ?, address = ?, accident_type_id = ? WHERE id = ?",
            accident.getName(),
            accident.getText(),
            accident.getAddress(),
            accident.getType().getId(),
            accident.getId());
        jdbc.update("DELETE FROM accidents_rules WHERE accident_id = ?", accident.getId());
        for (String id : ids) {
            jdbc.update("INSERT INTO accidents_rules (accident_id, rule_id) VALUES (?, ?)",
                    accident.getId(), Integer.parseInt(id));
        }
    }

    public Optional<Accident> findById(int id) {
        Accident accident = jdbc.queryForObject(
                "select a.id as a_id, a.name as a_name, a.address, a.text, at.id as at_id, at.name as at_name " +
                        "from accident a left join accident_type at on at.id=a.accident_type_id where a.id = ?",
                accidentRowMapper, id);
        if (accident == null) {
            return Optional.empty();
        }
        List<Rule> rules = jdbc.query(
                "select r.id, r.name from rule r " +
                        "join accidents_rules ar on r.id = ar.rule_id where ar.accident_id=?",
                ruleRowMapper, accident.getId());
        rules.forEach(accident::addRule);
        return Optional.of(accident);
    }
}
