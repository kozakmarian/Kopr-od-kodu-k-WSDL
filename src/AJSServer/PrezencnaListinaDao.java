package AJSServer;

import java.util.Date;
import java.util.List;

public interface PrezencnaListinaDao {
	
	public String pridajPrezencnuListinu(String uuidPredmetu, String datumACas, List<Ucastnik> ucastnici)  throws Ajs_Exception ;
	public List<String> getZoznamUcastnikov(String uuidListiny)  throws Ajs_Exception;
	public List<PrezencnaListina> getPrezencneListiny();
	public void vymazPrezencnuListinu(String uuidListiny)  throws Ajs_Exception;
}
