package AJSServer;

import org.springframework.jdbc.core.JdbcTemplate;

import com.mysql.cj.jdbc.MysqlDataSource;

public enum ObjectFactory {

	INSTANCE;
	
	private JdbcTemplate jdbcTemplate;
	private PredmetDao predmetDao;
	private PrezencnaListinaDao prezencnaListinaDao;
	private UcastnikDao ucastnikDao;
	
	public JdbcTemplate getJdbcTemplate() {
        if (jdbcTemplate == null) {
            MysqlDataSource dataSource = new MysqlDataSource();
            dataSource.setUrl("jdbc:mysql://localhost/ajskod?serverTimezone=UTC");
            dataSource.setUser("kopr");
            dataSource.setPassword("koprkopr");
            jdbcTemplate = new JdbcTemplate(dataSource);
        }
        return jdbcTemplate;        
    }
	
	public PredmetDao getPredmetDao() {
        if (predmetDao == null) {
            predmetDao = new MysqlPredmetDao(getJdbcTemplate());            
        }
        return predmetDao;
    }
	
	public PrezencnaListinaDao getPrezencnaListinaDao() {
        if (prezencnaListinaDao == null) {
        	prezencnaListinaDao = new MysqlPrezencnaListinaDao(getJdbcTemplate());            
        }
        return prezencnaListinaDao;
    }
	
	public UcastnikDao getUcastnikDao() {
        if (ucastnikDao == null) {
        	ucastnikDao = new MysqlUcastnikDao(getJdbcTemplate());            
        }
        return ucastnikDao;
    }
}
