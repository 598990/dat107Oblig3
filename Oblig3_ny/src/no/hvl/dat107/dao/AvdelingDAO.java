package no.hvl.dat107.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.*;

import no.hvl.dat107.exception.HasWorkersException;
import no.hvl.dat107.extra.Password;
import no.hvl.dat107.klasser.Ansatt;
import no.hvl.dat107.klasser.Avdeling;

public class AvdelingDAO {

	private EntityManagerFactory emf;
	private AnsattDAO adao;

	public AvdelingDAO() {
		emf = Persistence.createEntityManagerFactory("MPU", Map.of("javax.persistence.jdbc.password", Password.PASS));
		adao = new AnsattDAO();
	}

	public Avdeling finnAvdelingMedId(int id) {

		EntityManager em = emf.createEntityManager();

		Avdeling avd = null;

		try {
			avd = em.find(Avdeling.class, id);
		} finally {
			em.close();
		}

		return avd;

	}

	public void skrivUtAlleAnsattePaaAvdeling(int id) {

		Avdeling avd = finnAvdelingMedId(id);
		
		List<Ansatt> ansatte = avd.getAnsatte();

		for (Ansatt a : ansatte) {
			if (a.getAvdeling().equals(avd)) {
				if (avd.getAvdelingsjef().equals(a)) {
					System.out.print("Avdelingssjef, ");
				}
				System.out.println(a.toString());
			}

		}
	}

	public Ansatt endreAvdeling(int ansattid, int nyAvdelingid) {

		Ansatt a = adao.finnAnsattMedId(ansattid);

		Avdeling avd = finnAvdelingMedId(nyAvdelingid);

		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();

			Ansatt q = em.merge(a);

			if (!erSjef(a.getAnsattID())) {
				a.setAvdeling(avd);
				q.setAvdeling(avd);
			}

			tx.commit();
		} catch (Throwable e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
		
		return a;

	}
	
	private boolean erSjef(int ansattid) {
		Ansatt a = adao.finnAnsattMedId(ansattid);
		
		List<Avdeling> avdelinger = hentAlleAvdelinger();
		
		for(Avdeling avd : avdelinger) {
			if(avd.getAvdelingsjef() != null && avd.getAvdelingsjef().equals(a)) {
				return true;
			}
		}
		return false;
	}

	public Avdeling opprettNyAvdeling(String navn, Ansatt sjef) {

		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		Avdeling ny = null;
		Avdeling avd = null;

		try {
			tx.begin();
			
			ny = new Avdeling();
			
			ny.setAvdelingnavn(navn);
			
			if(erSjef(sjef.getAnsattID())) {
				return null;
			}
			
			em.persist(ny);
			
			tx.commit();
			
			tx.begin();
			
			List<Avdeling> avdelinger = hentAlleAvdelinger();
			
			for(Avdeling a : avdelinger) {
				avd = a;
			}
			
			endreAvdeling(sjef.getAnsattID(), avd.getAvdelingid());
			
			ny.setAvdelingsjef(sjef);
			
			
			tx.commit();
		}
		catch(Throwable e) {
			e.printStackTrace();
			tx.rollback();
		}
		finally {
			em.close();
		}

		return ny;

	}
	
	public void skrivUtAlleAvdelinger() {
		
		List<Avdeling> avdelinger = hentAlleAvdelinger();
		
		for(Avdeling a : avdelinger) {
			System.out.println(a.toString());
		}
		
	}
	
	private List<Avdeling> hentAlleAvdelinger() {
		EntityManager em = emf.createEntityManager();
		
		String sql = "SELECT a FROM Avdeling a";
		
		List<Avdeling> avdelinger = null;
		
		try {
			TypedQuery<Avdeling> query = em.createQuery(sql, Avdeling.class);
			avdelinger = query.getResultList();
		}
		finally {
			em.close();
		}
		
		return avdelinger;
	}
	
	public boolean slettAvdeling(Avdeling a) {
		boolean slettet = false;
		
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		try {
			tx.begin();
			
			if(a.getAnsatte().isEmpty()) {
				em.remove(em.find(Avdeling.class, a.getAvdelingid()));
				slettet = true;
			}
			else {
				throw new HasWorkersException("Cant delete " + a.toString() + " because it contains workers");
			}
		}
		catch(HasWorkersException e) {
			System.out.println("Avdelingen har ansatte");
			tx.rollback();
		}
		catch(Throwable e) {
			e.printStackTrace();
			tx.rollback();
		}
		finally {
			em.close();
		}
		
		return slettet;
	}
}
