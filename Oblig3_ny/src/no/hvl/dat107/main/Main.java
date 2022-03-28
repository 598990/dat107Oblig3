package no.hvl.dat107.main;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import no.hvl.dat107.dao.AnsattDAO;
import no.hvl.dat107.dao.AvdelingDAO;
import no.hvl.dat107.dao.ProsjektDAO;
import no.hvl.dat107.klasser.Ansatt;
import no.hvl.dat107.klasser.Avdeling;
import no.hvl.dat107.klasser.Prosjekt;
import no.hvl.dat107.klasser.ProsjektDeltakelse;

public class Main {

	public static void main(String[] args) {
		
		AnsattDAO adao = new AnsattDAO();
		AvdelingDAO avdao = new AvdelingDAO();
		ProsjektDAO pdao = new ProsjektDAO();
		
		Scanner scanner = new Scanner(System.in);
		String linje = "";
		
		while (!linje.equalsIgnoreCase("avslutt")) {
			Ansatt a = null;
			Avdeling avd = null;
			Prosjekt p = null;
			int id = 0;
			int extraid = 0;
			System.out.println();
			System.out.println("Velkommen til Ansattoversikten");
			System.out.println("Skriv hva du ønsker å gjøre, eller skriv avslutt om du er ferdig");
			
			linje = scanner.nextLine();
			
			switch(linje.toLowerCase()) {
			case "avslutt":
				break;
			case "finn ansatt med id":
				System.out.println("Skriv id til den ansatte du ønsker");
				id = scanner.nextInt();
				linje = scanner.nextLine();
				a = adao.finnAnsattMedId(id);
				System.out.println(a.toString());
				break;
			case "finn ansatt med brukernavn":
				System.out.println("Skriv inn brukernavn til ansatt");
				linje = scanner.nextLine();
				a = adao.finnAnsattMedBrukernavn(linje);
				System.out.println(a.toString());
				break;
			case "skriv ut alle ansatte":
				adao.skrivUtAlle();
				break;
			case "oppdater ansatt lønn":
				System.out.println("Skriv id til den ansatte du ønsker å endre lønn til");
				id = scanner.nextInt();
				linje = scanner.nextLine();
				a = adao.finnAnsattMedId(id);
				System.out.println("Hva skal den nye lønnen være?");
				int nyLonn = scanner.nextInt();
				linje = scanner.nextLine();
				adao.oppdaterLonn(a, nyLonn);
				System.out.println(a.toString());
				break;
			case "oppdater ansatt stilling":
				System.out.println("Skriv id til den ansatte du ønsker å endre stilling på");
				id = scanner.nextInt();
				linje = scanner.nextLine();
				System.out.println("Hva er den nye stillingen");
				linje = scanner.nextLine();
				adao.oppdaterStilling(id, linje);
				System.out.println(adao.finnAnsattMedId(id).toString());
				break;
			case "legg til ny ansatt":
				System.out.println("Brukernavn?");
				String brukernavn = scanner.nextLine();
				System.out.println("Fornavn?");
				String fornavn = scanner.nextLine();
				System.out.println("Etternavn?");
				String etternavn = scanner.nextLine();
				System.out.println("Stilling?");
				String stilling = scanner.nextLine();
				System.out.println("Lønn?");
				int lonn = scanner.nextInt();
				linje = scanner.nextLine();
				System.out.println("Avdelingsid?");
				int ansattAvdeling = scanner.nextInt();
				linje = scanner.nextLine();
				avd = avdao.finnAvdelingMedId(ansattAvdeling);
				a = new Ansatt(brukernavn, fornavn, etternavn, LocalDate.now(), stilling, lonn, avd);
				adao.leggTilNyAnsatt(a);
				System.out.println(a.toString());
				break;
			case "slett ansatt":
				System.out.println("Skriv id til ansatt som skal slettes");
				id = scanner.nextInt();
				linje = scanner.nextLine();
				a = adao.finnAnsattMedId(id);
				if(adao.slettAnsatt(a)) {
					System.out.println("Den ansatte ble slettet");
				}
				else {
					System.out.println("Den ansatte kunne ikke bli slettet");
				}
				break;
			case "finn avdeling med id":
				System.out.println("Skriv id til avdeling");
				id = scanner.nextInt();
				linje = scanner.nextLine();
				avd = avdao.finnAvdelingMedId(id);
				System.out.println(avd.toString());
				break;
			case "skriv ut alle ansatte på avdeling":
				System.out.println("Skriv id til avdeling");
				id = scanner.nextInt();
				linje = scanner.nextLine();
				avdao.skrivUtAlleAnsattePaaAvdeling(id);
				break;
			case "endre ansatt avdeling":
				System.out.println("Skriv id til ansatt");
				id = scanner.nextInt();
				linje = scanner.nextLine();
				System.out.println("Skriv id til ny avdeling");
				extraid = scanner.nextInt();
				linje = scanner.nextLine();
				System.out.println(avdao.endreAvdeling(id, extraid).toString());
				break;
			case "ny avdeling":
				System.out.println("Navn på avdeling");
				String avdelingNavn = scanner.nextLine();
				System.out.println("Id til ansatt som skal være sjef");
				id = scanner.nextInt();
				linje = scanner.nextLine();
				a = adao.finnAnsattMedId(id);
				avd = avdao.opprettNyAvdeling(avdelingNavn, a);
				if(avd == null) {
					System.out.println("Kunne ikke legge til avdeling");
				}
				else {
					System.out.println("Avdeling ble lagt til");
				}
				break;
			case "skriv ut alle avdelinger":
				avdao.skrivUtAlleAvdelinger();
				break;
			case "slett avdeling":
				System.out.println("Skriv inn id på avdeling du vil slette");
				id = scanner.nextInt();
				linje = scanner.nextLine();
				avd = avdao.finnAvdelingMedId(id);
				if(avdao.slettAvdeling(avd)) {
					System.out.println("Avdeling ble slettet");
				}
				else {
					System.out.println("Avdeling kunne ikke slettes");
				}
				break;
			case "skriv ut alle prosjekter":
				pdao.skrivUtAlleProsjekter();
				break;
			case "legg til nytt prosjekt":
				System.out.println("Navn?");
				String prosjektNavn = scanner.nextLine();
				System.out.println("Beskrivelse?");
				String beskrivelse = scanner.nextLine();
				String ut = pdao.leggTilNyttProsjekt(new Prosjekt(prosjektNavn, beskrivelse));
				if(ut != null) {
					System.out.println(ut + " ble lagt til");
				}
				else {
					System.out.println("Kunne ikke legge til");
				}
				break;
			case "finn prosjekt med id":
				System.out.println("Skriv id til prosjekt");
				id = scanner.nextInt();
				linje = scanner.nextLine();
				System.out.println(pdao.finnProsjektMedId(id).toString());
				break;
			case "skriv ut alle deltakelser":
				pdao.skrivUtAlleDeltakelser();
				break;
			case "registrer deltakelse":
				System.out.println("Skriv id på ønsket prosjekt");
				id = scanner.nextInt();
				linje = scanner.nextLine();
				p = pdao.finnProsjektMedId(id);
				System.out.println("Skriv id til ansatt som skal delta");
				extraid = scanner.nextInt();
				linje = scanner.nextLine();
				a = adao.finnAnsattMedId(extraid);
				System.out.println("Rolle?");
				String rolle = scanner.nextLine();
				System.out.println("Antall arbeidstimer");
				int timer = scanner.nextInt();
				linje = scanner.nextLine();
				pdao.registrerDeltakelse(new ProsjektDeltakelse(p, a, rolle, timer));
				System.out.println("Deltakelse registrert");
				break;
			case "før timer":
				System.out.println("Skriv id på ansatt");
				id = scanner.nextInt();
				linje = scanner.nextLine();
				a = adao.finnAnsattMedId(id);
				System.out.println("Skriv id til prosjekt");
				extraid = scanner.nextInt();
				linje = scanner.nextLine();
				p = pdao.finnProsjektMedId(extraid);
				System.out.println("Timer?");
				int t = scanner.nextInt();
				linje = scanner.nextLine();
				System.out.println(pdao.foerTimer(a, p, t).toString());
				break;
			case "skriv ut prosjekt":
				System.out.println("Skriv id på ønsket prosjekt");
				id = scanner.nextInt();
				linje = scanner.nextLine();
				pdao.skrivUtProsjekt(pdao.finnProsjektMedId(id));
				break;
			case "slett prosjekt":
				System.out.println("Skriv id på prosjekt som skal slettes");
				id = scanner.nextInt();
				linje = scanner.nextLine();
				if(pdao.slettProsjekt(pdao.finnProsjektMedId(id))) {
					System.out.println("Prosjektet ble slettet");
				}
				else {
					System.out.println("Prosjektet kunne ikke bli slettet");
				}
				break;
			default:
				System.out.println("ugyldig input");
				break;
			}
			
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
