package no.hvl.dat107.dao;

import java.util.ArrayList;
import java.util.List;

import java.util.Map;
import java.util.Scanner;

import javax.persistence.*;

import org.postgresql.jdbc.PgArray;

import no.hvl.dat107.exception.HasWorkersException;
import no.hvl.dat107.extra.Password;
import no.hvl.dat107.klasser.Ansatt;
import no.hvl.dat107.klasser.Prosjekt;
import no.hvl.dat107.klasser.ProsjektDeltakelse;

public class ProsjektDAO {

	private EntityManagerFactory emf;
	
	public ProsjektDAO() {
		emf = Persistence.createEntityManagerFactory("MPU", 
	 			  Map.of("javax.persistence.jdbc.password", Password.PASS)); 
	}
	
	public List<Prosjekt> hentAlleProsjekter() {
		
		EntityManager em = emf.createEntityManager();
		
		String sql = "SELECT p FROM Prosjekt p";
		
		List<Prosjekt> prosjekter = null;
		
		try {
			TypedQuery<Prosjekt> query = em.createQuery(sql, Prosjekt.class);
			prosjekter = query.getResultList();
		}
		finally {
			em.close();
		}
		
		return prosjekter;
		
	}
	
	public void skrivUtAlleProsjekter() {
		
		List<Prosjekt> prosjekter = hentAlleProsjekter();
		
		for(Prosjekt p : prosjekter) {
			System.out.println(p.toString());
		}
	}
	
	public String leggTilNyttProsjekt(Prosjekt p) {
		
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		try {
			tx.begin();
			
			em.persist(p);
			
			tx.commit();
		}
		catch(Throwable e) {
			e.printStackTrace();
			tx.rollback();
		}
		finally {
			em.close();
		}
		
		return p.getNavn();
	}
	
	public Prosjekt finnProsjektMedId(int id) {
		
		EntityManager em = emf.createEntityManager();
		
		Prosjekt p = null;
		
		try {
			p = em.find(Prosjekt.class, id);
		}
		finally {
			em.close();
		}
		
		return p;
		
	}
	
	public List<ProsjektDeltakelse> hentAlleDeltakelser() {
		
		EntityManager em = emf.createEntityManager();
		
		List<ProsjektDeltakelse> deltakelser = null;
		
		String sql = "SELECT p FROM ProsjektDeltakelse p";
		
		try {
			TypedQuery<ProsjektDeltakelse> query = em.createQuery(sql, ProsjektDeltakelse.class);
			deltakelser = query.getResultList();
		}
		finally {
			em.close();
		}
		
		return deltakelser;
		
	}
	
	public void skrivUtAlleDeltakelser() {
		
		List<ProsjektDeltakelse> deltakelser = hentAlleDeltakelser();
		
		for(ProsjektDeltakelse pd : deltakelser) {
			System.out.println(pd.toString());
		}
		
	}
	
	public void registrerDeltakelse(ProsjektDeltakelse pd) {
		
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		try {
			tx.begin();
			
			em.persist(pd);
			
			tx.commit();
		}
		finally {
			em.close();
		}
		
	}
	
	public ProsjektDeltakelse foerTimer(Ansatt a, Prosjekt p, int timer) {
		
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		List<ProsjektDeltakelse> deltakelser = hentAlleDeltakelser();
		
		ProsjektDeltakelse deltakelse = null;
		
		try {
			tx.begin();
			
			for(ProsjektDeltakelse pd : deltakelser) {
				if(pd.getAnsatt().equals(a) && pd.getProsjekt().equals(p)) {
					ProsjektDeltakelse e = em.merge(pd);
					
					e.setArbeidstimer(timer);
					pd.setArbeidstimer(timer);
					
					tx.commit();
					deltakelse = pd;
				}
			}
		}
		finally {
			em.close();
		}
		
		return deltakelse;
	}
	
	public void skrivUtProsjekt(Prosjekt p) {
		
		List<ProsjektDeltakelse> deltakelser = p.getDeltakelser();
		
		List<Ansatt> ansatte = new ArrayList<Ansatt>();
		
		for(ProsjektDeltakelse pd : deltakelser) {
			ansatte.add(pd.getAnsatt());
		}
		
		System.out.println(p.toString() + "\n" + "Ansatte på prosjekt");
		
		for(Ansatt a : ansatte) {
			System.out.println("\t" + a.toString());
		}
		
	}
	
	public boolean slettProsjekt(Prosjekt p) {
		boolean slettet = false;
		
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		try {
			if(p.getDeltakelser().isEmpty()) {
				em.remove(em.find(Prosjekt.class, p.getProsjektid()));
				slettet = true;
			}
			else {
				throw new HasWorkersException("Cant delete " + p.toString() + " because it contains workers");
			}
		}
		catch(HasWorkersException e) {
			System.out.println("Prosjektet har deltakere");
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
