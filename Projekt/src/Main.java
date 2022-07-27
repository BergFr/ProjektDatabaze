import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Scanner sc = new Scanner(System.in);

		Databaze data = new Databaze();

		System.out.println("P�ihl�en� do datab�ze...");
		String heslo = "";

		try (BufferedReader br = new BufferedReader(new FileReader("heslo.txt"))) {
			String s;
			while ((s = br.readLine()) != null) {
				heslo = s;
			}
		} catch (Exception e) {
			System.err.println("Chyba p�i �ten� ze souboru.");
		}

		if (heslo.equals("Databaze")) {
			System.out.print("P�ihl�en� bylo �sp�n�\n");

			while (true) {
				data.vypisDatabaze();
				data.naklady();
				System.out.println("1. Pridat ucitele");
				System.out.println("2. Pridat studenta:");
				System.out.println("3. V�pis datab�ze");
				System.out.println("4. Zadat zn�mky studentovi");
				System.out.println("5. Vymazat studenta u�iteli");
				System.out.println("6. Vymazat z datab�ze");
				System.out.println("7. V�pis u�itel� studenta");
				System.out.println("8. Vyhledat ID");
				System.out.println("9. V�pis dle ID");
				System.out.println("10. Ulo�it do SQL");
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
					System.out.println("P�ipojen� k datab�zi..." + data.connect());
					System.out.println("Vytvo�n� tabulek...:" + data.createTable());
					data.ulozitDataDoSQL();
					System.out.println("Data ulo�ena");
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
			System.out.print("Nespr�vn� heslo");
		}

	}

}
