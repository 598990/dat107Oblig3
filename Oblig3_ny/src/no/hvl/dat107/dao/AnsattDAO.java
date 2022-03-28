package no.hvl.dat107.dao;

import java.time.LocalDate;


import java.util.List;
import java.util.Map;

import javax.persistence.*;

import no.hvl.dat107.exception.HasProjectsException;
import no.hvl.dat107.exception.IsBossException;
import no.hvl.dat107.extra.Password;
import no.hvl.dat107.klasser.Ansatt;

public class AnsattDAO {
	
	private EntityManagerFactory emf;
	
	public AnsattDAO() {
		
		emf = Persistence.createEntityManagerFactory("MPU", 
 			  Map.of("javax.persistence.jdbc.password", Password.PASS));
		
	}
	
	public Ansatt finnAnsattMedId(int id) {
		
		EntityManager em = emf.createEntityManager();
		Ansatt a = null;
		
		try {
			a = em.find(Ansatt.class, id);
		}
		finally {
			em.close();
		}
		
		return a;
		
	}
	
	public Ansatt finnAnsattMedBrukernavn(String brukernavn) {
		
		EntityManager em = emf.createEntityManager();
		
		String sql = "SELECT a FROM Ansatt a WHERE a.brukernavn = '" + brukernavn + "'";
		Ansatt a = null;
		
		try {
			TypedQuery<Ansatt> query = em.createQuery(sql, Ansatt.class);
			List<Ansatt> ansatte = query.getResultList();
			
			if(!ansatte.isEmpty()) {
				a = ansatte.get(0);
			}
		}
		finally {
			em.close();
		}
		
		return a;
		
	}
	
	private List<Ansatt> hentAlleAnsatte() {
		
		EntityManager em = emf.createEntityManager();
		
		String sql = "SELECT a FROM Ansatt a";
		
		List<Ansatt> ansatte = null;
		
		try {
			
			TypedQuery<Ansatt> query = em.createQuery(sql, Ansatt.class);
			ansatte = query.getResultList();
			
		}
		finally {
			em.close();
		}
		
		return ansatte;
		
	}
	
	public void skrivUtAlle() {
		
		List<Ansatt> ansatte = hentAlleAnsatte();
		
		if(ansatte != null) {
			for(Ansatt a : ansatte) {
				System.out.println(a.toString());
			}
		}
		
	}
	
	public void oppdaterLonn(Ansatt a, int nyLonn) {
		
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		try {
			tx.begin();
			
			Ansatt q = em.merge(a);
			
			a.setMaanedslonn(nyLonn); 
			q.setMaanedslonn(nyLonn);
			
			tx.commit();
		}
		catch(Throwable e) {
			e.printStackTrace();
			tx.rollback();
		}
		finally {
			em.close();
		}
	}
	
	public void oppdaterStilling(int id, String nyStilling) {
		
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		try {
			Ansatt a = em.find(Ansatt.class, id);
			tx.begin();
			
			Ansatt q = em.merge(a);
			
			a.setStilling(nyStilling);
			q.setStilling(nyStilling);
			
			tx.commit();
		}
		finally {
			em.close();
		}
	}
	
	public int leggTilNyAnsatt(Ansatt a) {
		
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		try {
			tx.begin();
			
			if(a.getAvdeling().equals(null)) {
				throw new NullPointerException();
			}
			
			em.persist(a);
			
			tx.commit();
		}
		catch(Throwable e) {
			e.printStackTrace();
			tx.rollback();
		}
		finally {
			em.close();
		}
		
		return a.getAnsattID();
	}
	
	public boolean slettAnsatt(Ansatt a) {
		
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		boolean slettet = false;
		
		try {
			tx.begin();
			
			if(a.getDeltakelser().isEmpty() || a.getAvdeling().getAvdelingsjef().equals(a)) {
				em.remove(em.find(Ansatt.class, a.getAnsattID()));
				slettet = true;
			}
			else if (a.getDeltakelser().isEmpty()) {
				throw new IsBossException("Cant delete " + a.toString() + " because they are a boss");
			}
			else {
				throw new HasProjectsException("Cant delete " + a.toString() + " because it has projects stored");
			}
			
			tx.commit();
		}
		catch(IsBossException e) {
			System.out.println("Ansatte kan ikke slettes siden ansatt er sjef i en avdeling");
		}
		catch(HasProjectsException e) {
			System.out.println("Ansatte kan ikke slettes siden den ansatte deltar i prosjekter");
		}
		catch(NullPointerException e) {
			System.out.println("Den ansatte eksisterer ikke");
		}
		catch (javax.persistence.RollbackException e) {
			System.out.println("Kan ikke slettes");
		}
		catch (Throwable e) {
			e.printStackTrace();
			tx.rollback();
		}
		finally {
			em.close();
		}
		
		return slettet;
	}

}
