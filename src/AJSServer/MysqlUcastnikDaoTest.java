package AJSServer;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

class MysqlUcastnikDaoTest {

	@Test
	void pridajUcastnikaTest(){
		JdbcTemplate jdbc = ObjectFactory.INSTANCE.getJdbcTemplate();
		UcastnikDao ucastnikDao = new MysqlUcastnikDao(jdbc);
		int pocetPred = jdbc.queryForInt("SELECT count(*) FROM ucastnik;");
		String uuid = ucastnikDao.pridajUcastnika("Majo", "Kozak");
		int pocetPo = jdbc.queryForInt("SELECT count(*) FROM ucastnik;");
		Assert.assertTrue(pocetPred + 1 == pocetPo);
		jdbc.update("DELETE FROM ucastnik WHERE UUID = ?", new Object[] {uuid});
	}

	@Test
	void getUcastnikovTest(){
		JdbcTemplate jdbc = ObjectFactory.INSTANCE.getJdbcTemplate();
		UcastnikDao ucastnikDao = new MysqlUcastnikDao(jdbc);
		int pocetPred = jdbc.queryForInt("SELECT count(*) FROM ucastnik;");
		String uuid = UUID.randomUUID().toString();
		String uuid2 = UUID.randomUUID().toString();
		String uuid3 = UUID.randomUUID().toString();
		jdbc.update("INSERT INTO ucastnik(UUID, Meno, Priezvisko)" + "VALUES(?, ?, ?)", new Object[] {uuid, "Majo", "Kozak"});
		jdbc.update("INSERT INTO ucastnik(UUID, Meno, Priezvisko)" + "VALUES(?, ?, ?)", new Object[] {uuid2, "Jozko", "Mrkvicka"});
		jdbc.update("INSERT INTO ucastnik(UUID, Meno, Priezvisko)" + "VALUES(?, ?, ?)", new Object[] {uuid3, "Fero", "Dlhan"});
    	int pocetPo = ucastnikDao.getUcastnikov().size();
		Assert.assertTrue(pocetPred + 3 == pocetPo);
		jdbc.update("DELETE FROM ucastnik WHERE UUID = ?", new Object[] {uuid});
		jdbc.update("DELETE FROM ucastnik WHERE UUID = ?", new Object[] {uuid2});
		jdbc.update("DELETE FROM ucastnik WHERE UUID = ?", new Object[] {uuid3});
	}
	
	@Test
	void vymazUcastnikaTest() throws Ajs_Exception{
		JdbcTemplate jdbc = ObjectFactory.INSTANCE.getJdbcTemplate();
		UcastnikDao ucastnikDao = new MysqlUcastnikDao(jdbc);
		String meno = "Majo";
		String priezvisko = "Kozak";
		String uuid = UUID.randomUUID().toString();
		jdbc.update("INSERT INTO ucastnik(UUID, Meno, Priezvisko)" + "VALUES(?, ?, ?)", new Object[] {uuid, meno, priezvisko});
		int pocetPred = jdbc.queryForInt("SELECT count(*) FROM ucastnik;");
		ucastnikDao.vymazUcastnika(uuid, meno, priezvisko);
		int pocetPo = jdbc.queryForInt("SELECT count(*) FROM ucastnik;");
		Assert.assertTrue(pocetPred == pocetPo + 1);
		jdbc.update("DELETE FROM ucastnik WHERE UUID = ?", new Object[] {uuid});
	}
	
	@Test
	void getPrezencneListinyTest()  throws Ajs_Exception{
		JdbcTemplate jdbc = ObjectFactory.INSTANCE.getJdbcTemplate();
		UcastnikDao ucastnikDao = new MysqlUcastnikDao(jdbc);
		String meno = "Majo";
		String priezvisko = "Kozak";
		String uuid = UUID.randomUUID().toString();
		jdbc.update("INSERT INTO ucastnik(UUID, Meno, Priezvisko)" + "VALUES(?, ?, ?)", new Object[] {uuid, meno, priezvisko});
		Ucastnik ucastnik = new Ucastnik();
        ucastnik.setUuid(uuid);
        ucastnik.setMeno(meno);
        ucastnik.setPriezvisko(priezvisko);
		List<Ucastnik> ucastnici = new ArrayList<>();
	    ucastnici.add(ucastnik);
	    
	    //PredmetDao predmetDao = new MysqlPredmetDao(jdbc);
	    String uuid1 = UUID.randomUUID().toString();
		String uuid2 = UUID.randomUUID().toString();
		String uuid3 = UUID.randomUUID().toString();
		jdbc.update("INSERT INTO predmet(UUID, NazovPredmetu)" + "VALUES(?, ?)", new Object[] {uuid1, "Statistika"});
		jdbc.update("INSERT INTO predmet(UUID, NazovPredmetu)" + "VALUES(?, ?)", new Object[] {uuid2, "Analyza"});
		jdbc.update("INSERT INTO predmet(UUID, NazovPredmetu)" + "VALUES(?, ?)", new Object[] {uuid3, "Algebra"});
		
		String uuidPl1 = UUID.randomUUID().toString();
		String uuidPl2 = UUID.randomUUID().toString();
		String uuidPl3 = UUID.randomUUID().toString();
		jdbc.update("INSERT INTO prezencna_listina(UUID, UUIDPredmetu, DatumACas)" + "VALUES(?, ?, ?)", new Object[] {uuidPl1, uuid1, "1991-05-11 03:42:33"});
		jdbc.update("INSERT INTO ucast(UUIDUcastnik, UUIDPrezencnaListina)" + "VALUES(?, ?)", new Object[] {ucastnik.getUuid(), uuidPl1});
		int pocetPred = ucastnikDao.getPrezencneListiny(uuid).size();
		
		jdbc.update("INSERT INTO prezencna_listina(UUID, UUIDPredmetu, DatumACas)" + "VALUES(?, ?, ?)", new Object[] {uuidPl2, uuid2, "1945-07-21 03:59:49"});
		jdbc.update("INSERT INTO ucast(UUIDUcastnik, UUIDPrezencnaListina)" + "VALUES(?, ?)", new Object[] {ucastnik.getUuid(), uuidPl2});
		jdbc.update("INSERT INTO prezencna_listina(UUID, UUIDPredmetu, DatumACas)" + "VALUES(?, ?, ?)", new Object[] {uuidPl3, uuid3, "2003-09-03 15:04:12"});
		jdbc.update("INSERT INTO ucast(UUIDUcastnik, UUIDPrezencnaListina)" + "VALUES(?, ?)", new Object[] {ucastnik.getUuid(), uuidPl3});
		int pocetPo = ucastnikDao.getPrezencneListiny(uuid).size();
		Assert.assertTrue(pocetPred + 2 == pocetPo);
		jdbc.update("DELETE FROM ucast WHERE UUIDUcastnik = ?", new Object[] {uuid});
		jdbc.update("DELETE FROM ucast WHERE UUIDUcastnik = ?", new Object[] {uuid});
		jdbc.update("DELETE FROM ucast WHERE UUIDUcastnik = ?", new Object[] {uuid});
		jdbc.update("DELETE FROM prezencna_listina WHERE UUID = ?", new Object[] {uuidPl1});
		jdbc.update("DELETE FROM prezencna_listina WHERE UUID = ?", new Object[] {uuidPl2});
		jdbc.update("DELETE FROM prezencna_listina WHERE UUID = ?", new Object[] {uuidPl3});
		jdbc.update("DELETE FROM predmet WHERE UUID = ?", new Object[] {uuid1});
		jdbc.update("DELETE FROM predmet WHERE UUID = ?", new Object[] {uuid2});
		jdbc.update("DELETE FROM predmet WHERE UUID = ?", new Object[] {uuid3});
		jdbc.update("DELETE FROM ucastnik WHERE UUID = ?", new Object[] {uuid});
	}

}
