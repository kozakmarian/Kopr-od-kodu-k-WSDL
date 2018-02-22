package AJSServer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class MysqlUcastnikDao implements UcastnikDao {
	
	private JdbcTemplate jdbcTemplate;
	
	public MysqlUcastnikDao(JdbcTemplate jdbcTemplate) {
		super();
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public String pridajUcastnika(String meno, String priezvisko) {
		String uuid = UUID.randomUUID().toString();
		jdbcTemplate.update("INSERT INTO ucastnik(UUID, Meno, Priezvisko)" + "VALUES(?, ?, ?)", new Object[] {uuid, meno, priezvisko});
        return uuid;
		
	}

	@Override
	public List<Ucastnik> getUcastnikov() {
		String sql = "SELECT * FROM ucastnik";
        
        return jdbcTemplate.query(sql, new UcastnikRowMapper());
	}

	@Override
	public void vymazUcastnika(String uuid, String meno, String priezvisko)  throws Ajs_Exception{
		if(existujeUcastnik(uuid) == false) {
			throw new Ajs_Exception("Ucastnik " + meno + " " + priezvisko + " neexistuje!");
		}
		String sql = "DELETE FROM ucastnik WHERE UUID = ? AND Meno = ? AND Priezvisko = ?";
        jdbcTemplate.update(sql,new Object[] {uuid, meno, priezvisko});
        String sql2 = "DELETE FROM ucast WHERE UUIDUcastnik = ?";
        jdbcTemplate.update(sql2,new Object[] {uuid});
	}

	@Override
	public List<PrezencnaListina> getPrezencneListiny(String uuid)  throws Ajs_Exception {
		if(existujeUcastnik(uuid) == false) {
			throw new Ajs_Exception("Ucastnik neexistuje!");
		}
		String sql = "SELECT PL.* FROM prezencna_listina PL JOIN ucast U JOIN ucastnik UC ON PL.UUID = U.UUIDPrezencnaListina AND UC.UUID = U.UUIDUcastnik WHERE UC.UUID = ?";
		return jdbcTemplate.query(sql, new Object[] {uuid}, new PrezencnaListinaRowMapper());
	}
	
	private class UcastnikRowMapper implements RowMapper {

        public Ucastnik mapRow(ResultSet rs, int i) throws SQLException {
            Ucastnik ucastnik = new Ucastnik();
            ucastnik.setUuid(rs.getString("UUID"));
            ucastnik.setMeno(rs.getString("Meno"));
            ucastnik.setPriezvisko(rs.getString("Priezvisko"));
            return ucastnik;             
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
	
	private class PrezencnaListinaRowMapper implements RowMapper {

        public PrezencnaListina mapRow(ResultSet rs, int i) throws SQLException {
            PrezencnaListina prezencnaListina = new PrezencnaListina();
            prezencnaListina.setUuid(rs.getString("UUID"));
            prezencnaListina.setUuidPredmetu(rs.getString("UUIDPredmetu"));
            prezencnaListina.setDatumACas(rs.getString("DatumACas"));
            return prezencnaListina;             
        }
        
    }


}
