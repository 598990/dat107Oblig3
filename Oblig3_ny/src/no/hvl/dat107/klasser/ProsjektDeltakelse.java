package no.hvl.dat107.klasser;

import java.util.List;
import java.util.Objects;

import javax.persistence.*;

import no.hvl.dat107.klasser.ProsjektDeltakelse;

@Entity
@Table (name = "prosjektdeltakelse", schema = "oblig3")
public class ProsjektDeltakelse {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private int prosjektdeltakelseid;
	
	@ManyToOne
	@JoinColumn(name = "prosjektid")
	private Prosjekt prosjekt;
	
	@ManyToOne
	@JoinColumn(name = "ansattid")
	private Ansatt ansatt;
	
	private String rolle;
	private int arbeidstimer;
	
	public ProsjektDeltakelse() {
		
	}

	public ProsjektDeltakelse(Prosjekt prosjekt, Ansatt ansatt, String rolle, int arbeidstimer) {
		this.prosjekt = prosjekt;
		this.ansatt = ansatt;
		this.rolle = rolle;
		this.arbeidstimer = arbeidstimer;
	}

	public Prosjekt getProsjekt() {
		return prosjekt;
	}

	public void setProsjektid(Prosjekt prosjekt) {
		this.prosjekt = prosjekt;
	}

	public Ansatt getAnsatt() {
		return ansatt;
	}

	public void setAnsattid(Ansatt ansatt) {
		this.ansatt = ansatt;
	}

	public String getRolle() {
		return rolle;
	}

	public void setRolle(String rolle) {
		this.rolle = rolle;
	}

	public int getArbeidstimer() {
		return arbeidstimer;
	}

	public void setArbeidstimer(int arbeidstimer) {
		this.arbeidstimer = arbeidstimer;
	}

	@Override
	public int hashCode() {
		return Objects.hash(ansatt, prosjekt);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProsjektDeltakelse other = (ProsjektDeltakelse) obj;
		return ansatt == other.ansatt && prosjekt == other.prosjekt;
	}

	@Override
	public String toString() {
		return "ProsjektDeltakelse [prosjekt=" + prosjekt.getNavn() + ", ansatt=" + ansatt.toString() + ", rolle=" + rolle
				+ ", arbeidstimer=" + arbeidstimer + "]";
	}
	
}
