import java.util.ArrayList;
import java.util.List;

public class Student implements Zamestnanec {

	private String jmenoStudenta;
	private int rokNarozeni;
	List<Double> listZnamky;
	private boolean stipendium;
	final double STP = 1.5;
	private double prumer = 0;

	private int IDstudenta;

	Student(int IDstudenta, String jmeno, int rokNarozeni) {
		this.jmenoStudenta = jmeno;
		this.IDstudenta = IDstudenta;
		this.rokNarozeni = rokNarozeni;
		stipendium = false;

		listZnamky = new ArrayList<Double>();

	}

	@Override
	public String toString() {
		return jmenoStudenta;
	}

	public void stipendium() {
		float soucetZnamek = 0;
		for (int i = 0; i < listZnamky.size(); i++) {
			soucetZnamek = (float) (soucetZnamek + listZnamky.get(i));
		}

		prumer = soucetZnamek / listZnamky.size();

		if (listZnamky.size() == 0) {
			stipendium = false;
			System.out.printf(" Prùmìr %.1f", prumer);

		}

		if (listZnamky.size() != 0) {

			if (prumer < STP) {
				stipendium = true;

			} else {
				stipendium = false;

			}
			System.out.printf(" Prùmìr %.1f", prumer);
		}
	}

	public double getPrumer() {
		return prumer;
	}

	public void setPrumer(double prumer) {
		this.prumer = prumer;
	}

	public boolean getStipendium() {
		return stipendium;
	}

	public void setStipendium(boolean stipendium) {
		this.stipendium = stipendium;
	}

///IMPLEMENTS///////////////////////////////////////
	
	
	
	
	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return IDstudenta;
	}

	@Override
	public void setID(int ID) {
		this.IDstudenta = ID;
		
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
		// TODO Auto-generated method stub
		return jmenoStudenta;
	}

	@Override
	public void setJmeno(String jmenoStudenta) {
		this.jmenoStudenta = jmenoStudenta;
		
	}

	
	
	
	
	
	
	

}
