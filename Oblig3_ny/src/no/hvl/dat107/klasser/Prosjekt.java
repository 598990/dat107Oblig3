package no.hvl.dat107.klasser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.*;

@Entity 
@Table (name = "prosjekt", schema = "oblig3")
public class Prosjekt {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private int prosjektid;
	
	private String navn;
	private String beskrivelse;
	
	@OneToMany(mappedBy="prosjekt")
	private List<ProsjektDeltakelse> deltakelser = new ArrayList<ProsjektDeltakelse>();
	
	public Prosjekt() {
		
	}

	public Prosjekt(String navn, String beskrivelse) {
		this.navn = navn;
		this.beskrivelse = beskrivelse;
	}

	public int getProsjektid() {
		return prosjektid;
	}

	public void setProsjektid(int prosjektid) {
		this.prosjektid = prosjektid;
	}

	public String getNavn() {
		return navn;
	}

	public void setNavn(String navn) {
		this.navn = navn;
	}

	public String getBeskrivelse() {
		return beskrivelse;
	}

	public void setBeskrivelse(String beskrivelse) {
		this.beskrivelse = beskrivelse;
	}
	
	public List<ProsjektDeltakelse> getDeltakelser() {
		return deltakelser;
	}
	
	public void setDeltakelser(List<ProsjektDeltakelse> deltakelser) {
		this.deltakelser = deltakelser;
	}

	@Override
	public String toString() {
		return "Prosjekt [prosjektid=" + prosjektid + ", navn=" + navn + ", beskrivelse=" + beskrivelse + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(prosjektid);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Prosjekt other = (Prosjekt) obj;
		return prosjektid == other.prosjektid;
	}
}
