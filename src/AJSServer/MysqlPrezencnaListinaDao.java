package AJSServer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class MysqlPrezencnaListinaDao implements PrezencnaListinaDao{
	
	private JdbcTemplate jdbcTemplate;
	
	public MysqlPrezencnaListinaDao(JdbcTemplate jdbcTemplate) {
		super();
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public String pridajPrezencnuListinu(String uuidPredmetu, String datumACas,
			List<Ucastnik> ucastnici)  throws Ajs_Exception {
		if(existujePredmet(uuidPredmetu) == false) {
			throw new Ajs_Exception("Predmet neexistuje!");
		}
		String uuid = UUID.randomUUID().toString();
		jdbcTemplate.update("INSERT INTO prezencna_listina(UUID, UUIDPredmetu, DatumACas)" + "VALUES(?, ?, ?)", new Object[] {uuid, uuidPredmetu, datumACas});
        for(Ucastnik ucastnik : ucastnici) {
        	if(existujeUcastnik(ucastnik.getUuid()) == false) {
    			throw new Ajs_Exception("Ucastnik neexistuje!");
    		}
        	jdbcTemplate.update("INSERT INTO ucast(UUIDUcastnik, UUIDPrezencnaListina)" + "VALUES(?, ?)", new Object[] {ucastnik.getUuid(), uuid});
        }
		return uuid;
		
	}

	@Override
	public List<String> getZoznamUcastnikov(String uuidListiny)  throws Ajs_Exception {
		if(existujeListina(uuidListiny) == false) {
			throw new Ajs_Exception("Prezencna listina neexistuje!");
		}
		String sql = "SELECT UC.* FROM ucastnik UC JOIN ucast U JOIN prezencna_listina PL ON UC.UUID = U.UUIDUcastnik AND PL.UUID = U.UUIDPrezencnaListina WHERE PL.UUID = ?";
		return jdbcTemplate.query(sql, new Object[] {uuidListiny}, new UcastnikRowMapper());
	}

	@Override
	public List<PrezencnaListina> getPrezencneListiny() {
		String sql = "SELECT * FROM prezencna_listina";
        
        return jdbcTemplate.query(sql, new PrezencnaListinaRowMapper());
	}

	@Override
	public void vymazPrezencnuListinu(String uuidListiny)  throws Ajs_Exception {
		if(existujeListina(uuidListiny) == false) {
			throw new Ajs_Exception("Prezencna listina neexistuje!");
		}
		String sql = "DELETE FROM prezencna_listina WHERE UUID = ?";
        jdbcTemplate.update(sql,new Object[] {uuidListiny});
        String sql2 = "DELETE FROM ucast WHERE UUIDPrezencnaListina = ?";
        jdbcTemplate.update(sql2,new Object[] {uuidListiny});
	}
	
	private class PrezencnaListinaRowMapper implements RowMapper {

        public PrezencnaListina mapRow(ResultSet rs, int i) throws SQLException {
            PrezencnaListina prezencnaListina = new PrezencnaListina();
            prezencnaListina.setUuid(rs.getString("UUID"));
            prezencnaListina.setUuidPredmetu(rs.getString("UUIDPredmetu"));
            prezencnaListina.setDatumACas(rs.getString("DatumACas"));
            return prezencnaListina;             
        }
        
    }
	
	private class UcastnikRowMapper implements RowMapper {

        public String mapRow(ResultSet rs, int i) throws SQLException {
            String meno = rs.getString("Meno");
            String priezvisko = rs.getString("Priezvisko");
            String menoAPriezvisko = meno + " " + priezvisko;
            return menoAPriezvisko;             
        }
        
    }
	
	public boolean existujeUcastnik(String uuid) {
		String sql = "SELECT UUID FROM ucastnik WHERE UUID = ?;";
		try {
			if( (String) jdbcTemplate.queryForObject(sql, new Object[] {uuid}, String.class) != null) {
				return true;
			} else {
				return false;
			}
		} catch (EmptyResultDataAccessException e) {
			return false;
		}
	}
	
	public boolean existujePredmet(String uuid) {
		String sql = "SELECT UUID FROM predmet WHERE UUID = ?;";
		try {
			if( (String) jdbcTemplate.queryForObject(sql, new Object[] {uuid}, String.class) != null) {
				return true;
			} else {
				return false;
			}
		} catch (EmptyResultDataAccessException e) {
			return false;
		}
	}

	public boolean existujeListina(String uuid) {
		String sql = "SELECT UUID FROM prezencna_listina WHERE UUID = ?;";
		try {
			if( (String) jdbcTemplate.queryForObject(sql, new Object[] {uuid}, String.class) != null) {
				return true;
			} else {
				return false;
			}
		} catch (EmptyResultDataAccessException e) {
			return false;
		}
	}
}
