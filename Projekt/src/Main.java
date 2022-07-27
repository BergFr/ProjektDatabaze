import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Scanner sc = new Scanner(System.in);

		Databaze data = new Databaze();

		System.out.println("Pøihlášení do databáze...");
		String heslo = "";

		try (BufferedReader br = new BufferedReader(new FileReader("heslo.txt"))) {
			String s;
			while ((s = br.readLine()) != null) {
				heslo = s;
			}
		} catch (Exception e) {
			System.err.println("Chyba pøi ètení ze souboru.");
		}

		if (heslo.equals("Databaze")) {
			System.out.print("Pøihlášení bylo úspìšné\n");

			while (true) {
				data.vypisDatabaze();
				data.naklady();
				System.out.println("1. Pridat ucitele");
				System.out.println("2. Pridat studenta:");
				System.out.println("3. Výpis databáze");
				System.out.println("4. Zadat známky studentovi");
				System.out.println("5. Vymazat studenta uèiteli");
				System.out.println("6. Vymazat z databáze");
				System.out.println("7. Výpis uèitelù studenta");
				System.out.println("8. Vyhledat ID");
				System.out.println("9. Výpis dle ID");
				System.out.println("10. Uložit do SQL");
				System.out.println("11. Nacista data ze SQL");
				System.out.println("12. Najit v SQL");
				System.out.println("13. Vypsat data ze SQL");
				System.out.println("14. Vymazat ze SQL");

				int volba = Integer.parseInt(sc.nextLine());

				switch (volba) {

				case 1:
					data.pridatUcitele();
					break;
				case 2:
					data.pridatStudenta();
					break;
				case 3:
					data.naklady();
					data.vypisDatabaze();
					break;
				case 4:
					data.pridatZnamky();
					break;
				case 5:
					data.vymazatStudentaUciteli();
					break;
				case 6:
					data.vymazat();
					break;
				case 7:
					data.vypisUciteluStudenta();
					break;
				case 8:
					data.vyhledatID();
					break;
				case 9:
					data.vypisDleID();
					break;

				////////////////////
				case 10:
					System.out.println("Pøipojení k databázi..." + data.connect());
					System.out.println("Vytvoøní tabulek...:" + data.createTable());
					data.ulozitDataDoSQL();
					System.out.println("Data uložena");
					data.disconnect();
					break;
				case 11:
					System.out.println(data.connect());
					data.nacistData();
					data.disconnect();
					break;
				case 12:
					System.out.println(data.connect());
					data.najitVsql();
					data.disconnect();
					break;
				case 13:
					System.out.println(data.connect());
					data.vypsatVsechnyZeSQL();
					data.disconnect();
					break;
				case 14:
					System.out.println(data.connect());
					data.vymazatZeSQL();
					data.disconnect();
					break;

				}
			}

		} else {
			System.out.print("Nesprávné heslo");
		}

	}

}
