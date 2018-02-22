package AJSServer;

import java.util.List;

public interface UcastnikDao {

	public String pridajUcastnika(String meno, String priezvisko);
	public List<Ucastnik> getUcastnikov();
	public void vymazUcastnika(String uuid, String meno, String priezvisko) throws Ajs_Exception;
	public List<PrezencnaListina> getPrezencneListiny(String uuid)  throws Ajs_Exception;
}
