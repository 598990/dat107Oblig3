package no.hvl.dat107.klasser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.*;

@Entity
@Table (name = "avdeling", schema = "oblig3")
public class Avdeling {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private int avdelingid;
	
	private String avdelingnavn;
	
	@OneToOne 
	@JoinColumn (name = "avdelingsjef")
	private Ansatt avdelingsjef;
	
	@OneToMany (mappedBy = "avdeling", fetch = FetchType.EAGER)
	private List<Ansatt> ansatte = new ArrayList<Ansatt>();
	
	public Avdeling() {
		
	}

	public Avdeling(int avdelingid, String avdelingnavn, Ansatt avdelingsjef) {
		this.avdelingid = avdelingid;
		this.avdelingnavn = avdelingnavn;
		this.avdelingsjef = avdelingsjef;
	}

	public int getAvdelingid() {
		return avdelingid;
	}

	public void setAvdelingid(int avdelingid) {
		this.avdelingid = avdelingid;
	}

	public String getAvdelingnavn() {
		return avdelingnavn;
	}

	public void setAvdelingnavn(String avdelingnavn) {
		this.avdelingnavn = avdelingnavn;
	}

	public Ansatt getAvdelingsjef() {
		return avdelingsjef;
	}

	public void setAvdelingsjef(Ansatt avdelingsjef) {
		this.avdelingsjef = avdelingsjef;
	}
	
	public List<Ansatt> getAnsatte() {
		return ansatte;
	}
	
	public void setAnsatte(List<Ansatt> ansatte) {
		this.ansatte = ansatte;
	}

	@Override
	public String toString() {
		return "Avdeling [avdelingid=" + avdelingid + ", avdelingnavn=" + avdelingnavn + ", avdelingsjef="
				+ avdelingsjef + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(avdelingid);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Avdeling other = (Avdeling) obj;
		return Objects.equals(avdelingid, other.avdelingid);
	}	
	
	
}
