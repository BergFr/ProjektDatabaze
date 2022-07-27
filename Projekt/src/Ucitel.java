
import java.util.ArrayList;

import java.util.List;

public class Ucitel implements Zamestnanec {

	private String jmenoUcitele;
	private int IDucitele;
	private int rokNarozeni;
	private int pocetStudentu; // promìnná kvùli metodì vypisUciteluDleStudentu, jelikož v pøíkazu, kde je ::
								// nelze zadat listStudentu.size. Setter není, protože není potøeba.
	List<Student> listStudentu;

	Ucitel(int IDucitele, String jmeno, int rokNarozeni) {
		this.jmenoUcitele = jmeno;
		this.IDucitele = IDucitele;
		this.rokNarozeni = rokNarozeni;
		listStudentu = new ArrayList<Student>();

	}

	public void getPlat() {

		int pocetStudentu = listStudentu.size();
		System.out.print("PSc " + pocetStudentu + "|");

		int pocetStudentuSeS = 0;

		for (Student student : listStudentu) {
			if (student.getStipendium() == true) {
				pocetStudentuSeS++;
			}
		}

		System.out.print("PSst " + pocetStudentuSeS);

	}

	public int getPocetStudentu() {
		pocetStudentu = listStudentu.size();
		return pocetStudentu;
	}
	
	@Override
	public String toString() {
		return "ID " + IDucitele + "| " + jmenoUcitele + "|PS " + listStudentu.size();
	}
	
	
	///IMPLEMENTS///////////////////////////////////////
	
	

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return IDucitele;
	}

	@Override
	public void setID(int IDucitele) {

		this.IDucitele = IDucitele;
		
	}

	@Override
	public int getRokNarozeni() {
		// TODO Auto-generated method stub
		return rokNarozeni;
	}

	@Override
	public void setRokNarozeni(int rokNarozeni) {
		// TODO Auto-generated method stub
		this.rokNarozeni = rokNarozeni;
	}

	@Override
	public String getJmeno() {
		return jmenoUcitele;
		
	}

	@Override
	public void setJmeno(String jmenoUcitele) {
		// TODO Auto-generated method stub
		this.jmenoUcitele = jmenoUcitele;
	}

}
