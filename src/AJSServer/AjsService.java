package AJSServer;
import java.util.Date;
import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;


@WebService
public class AjsService {
	
	public String pridajPredmet(@WebParam(name = "nazov")String nazov) throws Ajs_Exception{
		return ObjectFactory.INSTANCE.getPredmetDao().pridatPredmet(nazov);
	}
	
	public List<Predmet> getPredmety(){
		return ObjectFactory.INSTANCE.getPredmetDao().getPredmety();
	}
	
	public void vymazPredmet(@WebParam(name = "nazov")String nazov) throws Ajs_Exception{
		ObjectFactory.INSTANCE.getPredmetDao().vymazPredmet(nazov);
	}
	
	public String pridajUcastnika(@WebParam(name = "meno")String meno, @WebParam(name = "priezvisko")String priezvisko){
		return ObjectFactory.INSTANCE.getUcastnikDao().pridajUcastnika(meno, priezvisko);
	}
	
	public List<Ucastnik> getUcastnikov(){
		return ObjectFactory.INSTANCE.getUcastnikDao().getUcastnikov();
	}
	
	public void vymazUcastnika(@WebParam(name = "uuid")String uuid, @WebParam(name = "meno")String meno, @WebParam(name = "priezvisko")String priezvisko)  throws Ajs_Exception{
		ObjectFactory.INSTANCE.getUcastnikDao().vymazUcastnika(uuid, meno, priezvisko);
	}
	
	public List<PrezencnaListina> getPrezencneListinyUcastnika(@WebParam(name = "uuid")String uuid)  throws Ajs_Exception{
		return ObjectFactory.INSTANCE.getUcastnikDao().getPrezencneListiny(uuid);
	}
	
	public String pridajPrezencnuListinu(String uuidPredmetu, String datumACas, List<Ucastnik> ucastnici)  throws Ajs_Exception {
		return ObjectFactory.INSTANCE.getPrezencnaListinaDao().pridajPrezencnuListinu(uuidPredmetu, datumACas, ucastnici);
	}
	
	public List<String> getZoznamUcastnikov(String uuidPrezencnejListiny)  throws Ajs_Exception{
		return ObjectFactory.INSTANCE.getPrezencnaListinaDao().getZoznamUcastnikov(uuidPrezencnejListiny);
	}
	
	public List<PrezencnaListina> getPrezencneListiny(){
		return ObjectFactory.INSTANCE.getPrezencnaListinaDao().getPrezencneListiny();
	}
	
	public void vymazPrezencnuListinu(String uuidListiny)  throws Ajs_Exception{
		ObjectFactory.INSTANCE.getPrezencnaListinaDao().vymazPrezencnuListinu(uuidListiny);
	}
}
