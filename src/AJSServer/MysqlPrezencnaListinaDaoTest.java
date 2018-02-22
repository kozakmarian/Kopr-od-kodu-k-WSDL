package AJSServer;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

class MysqlPrezencnaListinaDaoTest {

	@Test
	void pridajPrezencnuListinuTest()  throws Ajs_Exception {
		JdbcTemplate jdbc = ObjectFactory.INSTANCE.getJdbcTemplate();
		PrezencnaListinaDao plDao = new MysqlPrezencnaListinaDao(jdbc);
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
	    
	    String uuid1 = UUID.randomUUID().toString();
		String uuid2 = UUID.randomUUID().toString();
		String uuid3 = UUID.randomUUID().toString();
		jdbc.update("INSERT INTO predmet(UUID, NazovPredmetu)" + "VALUES(?, ?)", new Object[] {uuid1, "Statistika"});
		jdbc.update("INSERT INTO predmet(UUID, NazovPredmetu)" + "VALUES(?, ?)", new Object[] {uuid2, "Analyza"});
		jdbc.update("INSERT INTO predmet(UUID, NazovPredmetu)" + "VALUES(?, ?)", new Object[] {uuid3, "Algebra"});
		int pocetPred = jdbc.queryForInt("SELECT count(*) FROM prezencna_listina;");
				
		String uuidPl1 = plDao.pridajPrezencnuListinu(uuid1, "1991-05-11 03:42:33", ucastnici);
		String uuidPl2 = plDao.pridajPrezencnuListinu(uuid2, "1945-07-21 03:59:49", ucastnici);
		String uuidPl3 = plDao.pridajPrezencnuListinu(uuid3, "2003-09-03 15:04:12", ucastnici);
		
		int pocetPo = jdbc.queryForInt("SELECT count(*) FROM prezencna_listina;");
		Assert.assertTrue(pocetPred + 3 == pocetPo);
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

	@Test
	void getZoznamUcastnikovTest()  throws Ajs_Exception{
		JdbcTemplate jdbc = ObjectFactory.INSTANCE.getJdbcTemplate();
		PrezencnaListinaDao plDao = new MysqlPrezencnaListinaDao(jdbc);
		String uuidU1 = UUID.randomUUID().toString();
		String uuidU2 = UUID.randomUUID().toString();
		String uuidU3 = UUID.randomUUID().toString();
		jdbc.update("INSERT INTO ucastnik(UUID, Meno, Priezvisko)" + "VALUES(?, ?, ?)", new Object[] {uuidU1, "Majo", "Kozak"});
		jdbc.update("INSERT INTO ucastnik(UUID, Meno, Priezvisko)" + "VALUES(?, ?, ?)", new Object[] {uuidU2, "Jozko", "Mrkvicka"});
		jdbc.update("INSERT INTO ucastnik(UUID, Meno, Priezvisko)" + "VALUES(?, ?, ?)", new Object[] {uuidU3, "Fero", "Dlhan"});
	    	    
	    String uuid1 = UUID.randomUUID().toString();
		jdbc.update("INSERT INTO predmet(UUID, NazovPredmetu)" + "VALUES(?, ?)", new Object[] {uuid1, "Statistika"});
		
		String uuidPl1 = UUID.randomUUID().toString();
		jdbc.update("INSERT INTO prezencna_listina(UUID, UUIDPredmetu, DatumACas)" + "VALUES(?, ?, ?)", new Object[] {uuidPl1, uuid1, "1991-05-11 03:42:33"});
		jdbc.update("INSERT INTO ucast(UUIDUcastnik, UUIDPrezencnaListina)" + "VALUES(?, ?)", new Object[] {uuidU1, uuidPl1});
		int pocetPred = plDao.getZoznamUcastnikov(uuidPl1).size();
		jdbc.update("INSERT INTO ucast(UUIDUcastnik, UUIDPrezencnaListina)" + "VALUES(?, ?)", new Object[] {uuidU2, uuidPl1});
		jdbc.update("INSERT INTO ucast(UUIDUcastnik, UUIDPrezencnaListina)" + "VALUES(?, ?)", new Object[] {uuidU3, uuidPl1});
		
		int pocetPo = plDao.getZoznamUcastnikov(uuidPl1).size();
		Assert.assertTrue(pocetPred + 2 == pocetPo);
		jdbc.update("DELETE FROM ucast WHERE UUIDUcastnik = ?", new Object[] {uuidU1});
		jdbc.update("DELETE FROM ucast WHERE UUIDUcastnik = ?", new Object[] {uuidU2});
		jdbc.update("DELETE FROM ucast WHERE UUIDUcastnik = ?", new Object[] {uuidU3});
		jdbc.update("DELETE FROM prezencna_listina WHERE UUID = ?", new Object[] {uuidPl1});
		jdbc.update("DELETE FROM predmet WHERE UUID = ?", new Object[] {uuid1});
		jdbc.update("DELETE FROM ucastnik WHERE UUID = ?", new Object[] {uuidU1});
		jdbc.update("DELETE FROM ucastnik WHERE UUID = ?", new Object[] {uuidU2});
		jdbc.update("DELETE FROM ucastnik WHERE UUID = ?", new Object[] {uuidU3});
	}
	
	@Test
	void vymazPrezencnuListinuTest()  throws Ajs_Exception{
		JdbcTemplate jdbc = ObjectFactory.INSTANCE.getJdbcTemplate();
		PrezencnaListinaDao plDao = new MysqlPrezencnaListinaDao(jdbc);
		String meno = "Majo";
		String priezvisko = "Kozak";
		String uuid = UUID.randomUUID().toString();
		jdbc.update("INSERT INTO ucastnik(UUID, Meno, Priezvisko)" + "VALUES(?, ?, ?)", new Object[] {uuid, meno, priezvisko});
	    
	    
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
		jdbc.update("INSERT INTO ucast(UUIDUcastnik, UUIDPrezencnaListina)" + "VALUES(?, ?)", new Object[] {uuid, uuidPl1});
		jdbc.update("INSERT INTO prezencna_listina(UUID, UUIDPredmetu, DatumACas)" + "VALUES(?, ?, ?)", new Object[] {uuidPl2, uuid2, "1945-07-21 03:59:49"});
		jdbc.update("INSERT INTO ucast(UUIDUcastnik, UUIDPrezencnaListina)" + "VALUES(?, ?)", new Object[] {uuid, uuidPl2});
		jdbc.update("INSERT INTO prezencna_listina(UUID, UUIDPredmetu, DatumACas)" + "VALUES(?, ?, ?)", new Object[] {uuidPl3, uuid3, "2003-09-03 15:04:12"});
		jdbc.update("INSERT INTO ucast(UUIDUcastnik, UUIDPrezencnaListina)" + "VALUES(?, ?)", new Object[] {uuid, uuidPl3});
		int pocetPred = jdbc.queryForInt("SELECT count(*) FROM prezencna_listina;");
		
		plDao.vymazPrezencnuListinu(uuidPl1);
		plDao.vymazPrezencnuListinu(uuidPl2);
		
		int pocetPo = plDao.getPrezencneListiny().size();
		Assert.assertTrue(pocetPred == pocetPo + 2);
		jdbc.update("DELETE FROM ucast WHERE UUIDUcastnik = ?", new Object[] {uuid});
		jdbc.update("DELETE FROM prezencna_listina WHERE UUID = ?", new Object[] {uuidPl3});
		jdbc.update("DELETE FROM predmet WHERE UUID = ?", new Object[] {uuid1});
		jdbc.update("DELETE FROM predmet WHERE UUID = ?", new Object[] {uuid2});
		jdbc.update("DELETE FROM predmet WHERE UUID = ?", new Object[] {uuid3});
		jdbc.update("DELETE FROM ucastnik WHERE UUID = ?", new Object[] {uuid});
	}
	
	@Test
	void getPrezencneListinyTest(){
		JdbcTemplate jdbc = ObjectFactory.INSTANCE.getJdbcTemplate();
		PrezencnaListinaDao plDao = new MysqlPrezencnaListinaDao(jdbc);
		String meno = "Majo";
		String priezvisko = "Kozak";
		String uuid = UUID.randomUUID().toString();
		jdbc.update("INSERT INTO ucastnik(UUID, Meno, Priezvisko)" + "VALUES(?, ?, ?)", new Object[] {uuid, meno, priezvisko});
	    
	    
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
		jdbc.update("INSERT INTO ucast(UUIDUcastnik, UUIDPrezencnaListina)" + "VALUES(?, ?)", new Object[] {uuid, uuidPl1});
		int pocetPred = plDao.getPrezencneListiny().size();
		
		jdbc.update("INSERT INTO prezencna_listina(UUID, UUIDPredmetu, DatumACas)" + "VALUES(?, ?, ?)", new Object[] {uuidPl2, uuid2, "1945-07-21 03:59:49"});
		jdbc.update("INSERT INTO ucast(UUIDUcastnik, UUIDPrezencnaListina)" + "VALUES(?, ?)", new Object[] {uuid, uuidPl2});
		jdbc.update("INSERT INTO prezencna_listina(UUID, UUIDPredmetu, DatumACas)" + "VALUES(?, ?, ?)", new Object[] {uuidPl3, uuid3, "2003-09-03 15:04:12"});
		jdbc.update("INSERT INTO ucast(UUIDUcastnik, UUIDPrezencnaListina)" + "VALUES(?, ?)", new Object[] {uuid, uuidPl3});
		int pocetPo = plDao.getPrezencneListiny().size();
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
