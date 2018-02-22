package AJSServer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class MysqlPredmetDao implements PredmetDao{
	
	private JdbcTemplate jdbcTemplate;
	
	public MysqlPredmetDao(JdbcTemplate jdbcTemplate) {
		super();
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public String pridatPredmet(String nazov) throws Ajs_Exception {
		if(getPredmetUUID(nazov) != null) {
			throw new Ajs_Exception("Predmet uz existuje!");
		}
		String uuid = UUID.randomUUID().toString();
		jdbcTemplate.update("INSERT INTO predmet(UUID, NazovPredmetu)" + "VALUES(?, ?)", new Object[] {uuid, nazov});
        return uuid;
		
	}

	@Override
	public List<Predmet> getPredmety() {
		String sql = "SELECT * FROM predmet";
        return jdbcTemplate.query(sql, new PredmetRowMapper());
	}

	@Override
	public void vymazPredmet(String nazov) throws Ajs_Exception {
		if(getPredmetUUID(nazov) == null) {
			throw new Ajs_Exception("Predmet s nazvom " + nazov + " neexistuje!");
		}
		String sql = "DELETE FROM predmet WHERE NazovPredmetu = ?";
        jdbcTemplate.update(sql,new Object[] {nazov});		
	}
	
	public String getPredmetUUID(String nazovPredmetu) {
		String sql = "SELECT UUID FROM predmet WHERE NazovPredmetu = ?;";
		try {
			return (String) jdbcTemplate.queryForObject(sql, new Object[] { nazovPredmetu }, String.class);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	private class PredmetRowMapper implements RowMapper {

        public Predmet mapRow(ResultSet rs, int i) throws SQLException {
            Predmet predmet = new Predmet();
            predmet.setUuid(rs.getString("UUID"));
            predmet.setNazov(rs.getString("NazovPredmetu"));
            return predmet;             
        }
        
    }

}
