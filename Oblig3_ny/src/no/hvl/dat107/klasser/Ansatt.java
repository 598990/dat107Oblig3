package no.hvl.dat107.klasser;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.*;

@Entity
@Table (name = "ansatt", schema = "oblig3")
public class Ansatt {

	@Id 
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private int ansattid;
	
	private String brukernavn;
	private String fornavn;
	private String etternavn;
	private LocalDate ansattdato;
	private String stilling;
	private int maanedslonn;
	
	@ManyToOne 
	@JoinColumn(name = "avdeling", referencedColumnName = "avdelingid")
	private Avdeling avdeling;
	
	@OneToMany(mappedBy = "ansatt")
	List<ProsjektDeltakelse> deltakelser = new ArrayList<ProsjektDeltakelse>();
	
	public Ansatt() {
		
	}
	
	
	public Ansatt(String brukernavn, String fornavn, String etternavn, LocalDate ansattdato,
			String stilling, int maanedslonn, Avdeling avdeling) {
		this.brukernavn = brukernavn;
		this.fornavn = fornavn;
		this.etternavn = etternavn;
		this.ansattdato = ansattdato;
		this.stilling = stilling;
		this.maanedslonn = maanedslonn;
		this.avdeling = avdeling;
	}


	public int getAnsattID() {
		return ansattid;
	}


	public void setAnsattID(int ansattID) {
		this.ansattid = ansattID;
	}


	public String getBrukernavn() {
		return brukernavn;
	}


	public void setBrukernavn(String brukernavn) {
		this.brukernavn = brukernavn;
	}


	public String getFornavn() {
		return fornavn;
	}


	public void setFornavn(String fornavn) {
		this.fornavn = fornavn;
	}


	public String getEtternavn() {
		return etternavn;
	}


	public void setEtternavn(String etternavn) {
		this.etternavn = etternavn;
	}


	public LocalDate getAnsattdato() {
		return ansattdato;
	}


	public void setAnsattdato(LocalDate ansattdato) {
		this.ansattdato = ansattdato;
	}


	public String getStilling() {
		return stilling;
	}


	public void setStilling(String stilling) {
		this.stilling = stilling;
	}


	public int getMaanedslonn() {
		return maanedslonn;
	}


	public void setMaanedslonn(int maanedslonn) {
		this.maanedslonn = maanedslonn;
	}
	
	public Avdeling getAvdeling() {
		return avdeling;
	}
	
	public void setAvdeling(Avdeling avdeling) {
		this.avdeling = avdeling;
	}
	
	public List<ProsjektDeltakelse> getDeltakelser() {
		return deltakelser;
	}
	
	public void setDeltakelser(List<ProsjektDeltakelse> deltakelser) {
		this.deltakelser = deltakelser;
	}


	@Override
	public String toString() {
		return "Ansatt [ansattid=" + ansattid + ", brukernavn=" + brukernavn + ", fornavn=" + fornavn + ", etternavn="
				+ etternavn + ", ansattdato=" + ansattdato + ", stilling=" + stilling + ", maanedslonn=" + maanedslonn
				+ ", avdeling=" + avdeling.getAvdelingnavn() + "]";
	}


	@Override
	public int hashCode() {
		return Objects.hash(ansattid);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ansatt other = (Ansatt) obj;
		return ansattid == other.ansattid;
	}
	
}
