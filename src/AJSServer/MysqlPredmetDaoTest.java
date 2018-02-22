package AJSServer;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

class MysqlPredmetDaoTest {

	@Test
	void pridatPredmetTest() throws Ajs_Exception {
		JdbcTemplate jdbc = ObjectFactory.INSTANCE.getJdbcTemplate();
		PredmetDao predmetDao = new MysqlPredmetDao(jdbc);
		int pocetPred = jdbc.queryForInt("SELECT count(*) FROM predmet;");
		String uuid = predmetDao.pridatPredmet("Statistika");
		int pocetPo = jdbc.queryForInt("SELECT count(*) FROM predmet;");
		Assert.assertTrue(pocetPred + 1 == pocetPo);
		jdbc.update("DELETE FROM predmet WHERE UUID = ?", new Object[] {uuid});
	}

	@Test
	void getPredmetyTest() throws Ajs_Exception{
		JdbcTemplate jdbc = ObjectFactory.INSTANCE.getJdbcTemplate();
		PredmetDao predmetDao = new MysqlPredmetDao(jdbc);
		int pocetPred = jdbc.queryForInt("SELECT count(*) FROM predmet;");
		String uuid= UUID.randomUUID().toString();
		String uuid2 = UUID.randomUUID().toString();
		String uuid3 = UUID.randomUUID().toString();
		jdbc.update("INSERT INTO predmet(UUID, NazovPredmetu)" + "VALUES(?, ?)", new Object[] {uuid, "Statistika"});
		jdbc.update("INSERT INTO predmet(UUID, NazovPredmetu)" + "VALUES(?, ?)", new Object[] {uuid2, "Analyza"});
		jdbc.update("INSERT INTO predmet(UUID, NazovPredmetu)" + "VALUES(?, ?)", new Object[] {uuid3, "Algebra"});
		int pocetPo = predmetDao.getPredmety().size();
		Assert.assertTrue(pocetPred + 3 == pocetPo);
		jdbc.update("DELETE FROM predmet WHERE UUID = ?", new Object[] {uuid});
		jdbc.update("DELETE FROM predmet WHERE UUID = ?", new Object[] {uuid2});
		jdbc.update("DELETE FROM predmet WHERE UUID = ?", new Object[] {uuid3});
	}
	
	@Test
	void vymazPredmetTest() throws Ajs_Exception {
		JdbcTemplate jdbc = ObjectFactory.INSTANCE.getJdbcTemplate();
		PredmetDao predmetDao = new MysqlPredmetDao(jdbc);
		String nazovPredmetu = "Statistika";
		String uuid = UUID.randomUUID().toString();
		jdbc.update("INSERT INTO predmet(UUID, NazovPredmetu)" + "VALUES(?, ?)", new Object[] {uuid, nazovPredmetu});
		int pocetPred = jdbc.queryForInt("SELECT count(*) FROM predmet;");
		predmetDao.vymazPredmet(nazovPredmetu);
		int pocetPo = jdbc.queryForInt("SELECT count(*) FROM predmet;");
		Assert.assertTrue(pocetPred == pocetPo + 1);
		jdbc.update("DELETE FROM predmet WHERE UUID = ?", new Object[] {uuid});
	}
	
}
