package AJSServer;

import java.util.List;

public interface PredmetDao {
	
	public String pridatPredmet(String nazov) throws Ajs_Exception;
	public List<Predmet> getPredmety();
	public void vymazPredmet(String nazov) throws Ajs_Exception;
}
