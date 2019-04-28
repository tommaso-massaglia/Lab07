package it.polito.tdp.poweroutages.model;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import it.polito.tdp.poweroutages.db.PowerOutageDAO;

public class Model {

	private Map<Integer, ArrayList<PowerOutage>> nercOutages;
	private PowerOutageDAO podao;
	private int worst_score;
	private List<PowerOutage> WCS_List;
	private long maxOre;
	private int rangeAnniMax;

	public Model() {
		podao = new PowerOutageDAO();
		this.nercOutages = new HashMap<Integer, ArrayList<PowerOutage>>();
	}

	public List<Nerc> getNercList() {
		return podao.getNercList();
	}

	public ArrayList<PowerOutage> getPoList(int nercId) {
		if (!this.nercOutages.containsKey(nercId)) {
			ArrayList<PowerOutage> PowerOutages = new ArrayList<PowerOutage>(podao.getOutageList(nercId));
			this.nercOutages.put(nercId, PowerOutages);
			return PowerOutages;
		}
		return this.nercOutages.get(nercId);
	}

	/**
	 * Richiama l'algoritmo ricorsivo
	 * 
	 * @param maxOre       Numero massimo di ore di disservizio da considerare
	 * @param rangeAnniMax Range massimo di anni considerabile
	 * @param nercId       ID del NERC da considerare
	 * @return Stringa contenente informazioni sul WCScenario
	 */
	public String WCScenario(long maxOre, int rangeAnniMax, int nercId) {

		// Resetto i valori modificati dall'algoritmo
		this.worst_score = -1;
		this.WCS_List = new ArrayList<PowerOutage>();

		// Setto i parametri impostati dall'utente
		this.maxOre = maxOre;
		this.rangeAnniMax = rangeAnniMax;
		this.getPoList(nercId);

		// Richiamo l'algoritmo ricorsivo
		this.cerca(0, new HashSet<PowerOutage>(), nercId);

		// Scrivo la soluzione su una stringa
		String soluzione = new String();
		soluzione += "Numero di utenti interessati: " + this.worst_score + "\n" + "Elenco dei guasti considerati:\n";
		for (PowerOutage PO : this.WCS_List)
			soluzione += PO.toString() + "\n";
		return soluzione;
	}

	// Algoritmo ricorsivo
	public void cerca(int L, Set<PowerOutage> parziale, int nercId) {

		// Controlla la validità della soluzione e altrimenti interrompe
		if (!this.controllaSoluzione(parziale) && L > 0) {
			return;
		}

		// Controlla se ho ottenuto un nuovo punteggio ottimo
		if (this.calcolaPunteggio(parziale) > this.worst_score) {
			this.worst_score = this.calcolaPunteggio(parziale);
			this.WCS_List = new ArrayList<PowerOutage>(parziale);
			System.err.println("TROVATO PUNTEGGIO OTTIMO " + this.worst_score);
		}

		// Itera l'algoritmo
		for (PowerOutage PO : this.nercOutages.get(nercId)) {
			if (!parziale.contains(PO)) {
				parziale.add(PO);
				this.cerca(L + 1, parziale, nercId);
				parziale.remove(PO);
			}
		}

	}

	public boolean controllaSoluzione(Set<PowerOutage> parziale) {
		if (this.yearRangeCheck(parziale))
			if (this.calcolaTotOre(parziale) <= this.maxOre)
				return true;
			return false;
	}

	public int calcolaPunteggio(Set<PowerOutage> parziale) {
		int score = 0;
		for (PowerOutage PO : parziale) {
			score += PO.getCustomers_afflicted();
		}
		return score;
	}

	public int calcolaTotOre(Set<PowerOutage> parziale) {
		int tot = 0;
		for (PowerOutage PO : parziale) {
			tot += (int) PO.getStart().until(PO.getFinish(), ChronoUnit.HOURS);
		}
		return tot;
	}

	public boolean yearRangeCheck(Set<PowerOutage> parziale) {
		int minyear = 99999;
		int maxyear = 0;
		for (PowerOutage PO : parziale) {
			if (PO.getStart().getYear() < minyear)
				minyear = PO.getStart().getYear();
			if (PO.getStart().getYear() > maxyear)
				maxyear = PO.getStart().getYear();
		}
		if ((maxyear - minyear) <= this.rangeAnniMax) {
			return true;
		}
		return false;
	}

}
