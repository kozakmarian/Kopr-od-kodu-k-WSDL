package AJSServer;

import java.util.Date;
import java.util.List;

public class PrezencnaListina {
	
	private String uuid;
	private String uuidPredmetu;
	private String datumACas;
	private List<Ucastnik> ucastnici;
	
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public String getUuidPredmetu() {
		return uuidPredmetu;
	}
	
	public void setUuidPredmetu(String uuidPredmetu) {
		this.uuidPredmetu = uuidPredmetu;
	}
	
	public String getDatumACas() {
		return datumACas;
	}
	
	public void setDatumACas(String datumACas) {
		this.datumACas = datumACas;
	}
	
	public List<Ucastnik> getUcastnici() {
		return ucastnici;
	}
	
	public void setUcastnici(List<Ucastnik> ucastnici) {
		this.ucastnici = ucastnici;
	}
}
