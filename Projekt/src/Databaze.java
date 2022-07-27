import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Databaze {
	Scanner sc = new Scanner(System.in);
	List<Ucitel> listUcitelu;

	Databaze() {
		listUcitelu = new ArrayList<Ucitel>();
	}

//////////////UCITEL//////////////////////////////////////////	
	void pridatUcitele() {
		System.out.printf("Zadejte ID uèitele:");
		int ID = Integer.parseInt(sc.nextLine());
		int podminka = 0;
		int podminka2 = 0;

		for (Ucitel ucitel : listUcitelu) {

			for (Student student : ucitel.listStudentu) {
				// podminka2 aby se hlášky nevypisovala vícekrát, pokud bude jeden student u
				// více uèitelù
				if (podminka2 == 0) {
					if (student.getID() == ID) {
						System.out.println("\nZadané ID již je v databázi studentù\n");
						podminka = 1;
						podminka2 = 1;
					}
				}
			}

			if (ucitel.getID() == ID) {
				System.out.println("\nZadané ID již je v databázi uèitelù\n");
				podminka = 1;
			}
		}

		if (podminka == 0) {
			System.out.printf("Zadej jméno a pøijímìní uèitele:");
			String jmenoU = sc.nextLine();
			System.out.printf("Zadej rok narození uèitele:");
			int rokNarozeniU = Integer.parseInt(sc.nextLine());
			listUcitelu.add(new Ucitel(ID, jmenoU, rokNarozeniU));
		}

	}

////////////STUDENT//////////////////////////////////////////
	void pridatStudenta() {

		int IDucitele = 0;
		int IDstudent = 0;
		String jmenoStudenta = "";
		int rokNarozeni = 0;
		int podminka = 1;
		int podminkaVypis = 1;

		System.out.print("Zadejte ID uèitele, pod kterým bude student:");
		IDucitele = Integer.parseInt(sc.nextLine());

		System.out.print("Zadejte ID studenta, kterébo hudete pøiøazovat:");
		IDstudent = Integer.parseInt(sc.nextLine());

		// Existuje v celé databází stejné ID studenta?
		for (Ucitel ucitel : listUcitelu) {
			for (Student student : ucitel.listStudentu) {

				if (student.getID() == IDstudent) {
					if (podminkaVypis == 1) {
						System.out.print("\nZadané ID studenta již v databázi existuje\n\n");
					}
					IDstudent = student.getID();
					jmenoStudenta = student.getJmeno();
					rokNarozeni = student.getRokNarozeni();

					podminka = 2;

					// Je zadané ID u konkrétního uèitele?
					for (Ucitel ucitelX : listUcitelu) {
						if (ucitelX.getID() == IDucitele) {
							for (Student studentX : ucitelX.listStudentu) {
								if (studentX.getID() == IDstudent) {
									// Pokud je v databázi u uèitelù více stejných id, tak by se zprávy vypisovali
									// pro každou osobu, proto if podminkaVypis
									if (podminkaVypis == 1) {
										System.out.print("Zadané ID je již pøiøazeno u zadaného uèitele\n\n");
										podminkaVypis++;
									}
									podminka = 0;

								}
							}
						}
					}
				}
			}
		}

		// Má zadané ID už nìkterý uèitel?
		for (Ucitel ucitel : listUcitelu) {
			if (ucitel.getID() == IDstudent) {
				System.out.print("\nZadané ID má již uèitel\n\n");
				podminka = 0;
			}
		}

		// pokud v databázi již existuje student, nebude se zakládat nový, ale pøiøadí
		// se existující
		if (podminka == 2) {
			for (Ucitel ucitel : listUcitelu) {
				if (ucitel.getID() == IDucitele) {
					ucitel.listStudentu.add(new Student(IDstudent, jmenoStudenta, rokNarozeni));
				}
			}
		}

		if (podminka == 1) {

			System.out.print("Zadejte jméno studenta, kterébo hudete pøiøazovat:");
			jmenoStudenta = sc.nextLine();
			System.out.print("Zadejte rok narození studenta, kterébo hudete pøiøazovat:");
			rokNarozeni = Integer.parseInt(sc.nextLine());

			for (Ucitel ucitel : listUcitelu) {
				if (ucitel.getID() == IDucitele) {
					ucitel.listStudentu.add(new Student(IDstudent, jmenoStudenta, rokNarozeni));
				}
			}
		}

	}

	void pridatZnamky() {
		System.out.print("Zadejte ID uèitele, pod kterým je student:");
		int IDucitele = Integer.parseInt(sc.nextLine());
		System.out.print("Zadejte ID studenta:");
		int IDstudenta = Integer.parseInt(sc.nextLine());

		for (Ucitel ucitel : listUcitelu) {
			if (ucitel.getID() == IDucitele) {

				for (Student student : ucitel.listStudentu) {
					if (student.getID() == IDstudenta) {
						System.out.print("\nVybraný student " + student.getJmeno() + " u uèitele "
								+ ucitel.getJmeno() + "\n");
						System.out.print("Zadejte poèet známek:");
						int pocet = Integer.parseInt(sc.nextLine());
						for (int i = 0; i < pocet; i++) {
							System.out.print("Zadejte známku:");
							double znamka = Double.parseDouble(sc.nextLine());
							student.listZnamky.add(znamka);
						}
					}
				}
			}
		}
	}

////////////////////MAZÁNÍ///////////////////////////////
	void vymazat() {

		System.out.print("Zadejte ID:");
		int ID = Integer.parseInt(sc.nextLine());
		listUcitelu.removeIf(Ucitel -> (Ucitel.getID() == ID));

		for (Ucitel listU : listUcitelu) {
			listU.listStudentu.removeIf(Student -> (Student.getID() == ID));
		}
		System.out.print("Èlovìk s ID " + ID + " byl vymazán\n");
	}

	void vymazatStudentaUciteli() {
		System.out.print("Zadejte ID uèitele:");
		int IDucitele = Integer.parseInt(sc.nextLine());
		System.out.print("Zadejte ID studenta:");
		int IDstudenta = Integer.parseInt(sc.nextLine());

		for (Ucitel ucitel : listUcitelu) {
			if (ucitel.getID() == IDucitele) {
				for (Student student : ucitel.listStudentu) {
					if (student.getID() == IDstudenta) {
						System.out.print("\nStudent " + student.getJmeno() + " byl vymazán u uèitele "
								+ ucitel.getJmeno() + "\n\n");
					}
				}

				ucitel.listStudentu.removeIf(Student -> (Student.getID() == IDstudenta));

			}
		}
	}

////////VÝPISY//////////////////////////////////////////////
	void vypisDatabaze() {

		List<Student> sortedList;

		System.out.print("                               __    ___     __     __  __ \n");
		System.out.print(
				"_____________________________ |  \\ /\\ |  /\\ |__) /\\  _/|__ ______________________________________\n");
		System.out.print("                              |__//--\\| /--\\|__)/--\\/__|__\n");

		System.out.print("||Uèitelé:\n||\n");
		for (Ucitel ucitel : listUcitelu) {

			System.out.print("||ID " + ucitel.getID() + "|" + ucitel.getJmeno() + "|"
					+ ucitel.getRokNarozeni() + "|");
			ucitel.getPlat();
			System.out.println();
			System.out.print("||Studenti dle prùmìru:\n");

			// U uèitele se seøadili studenti
			sortedList = ucitel.listStudentu.stream().sorted(Comparator.comparing(Student::getPrumer))
					.collect(Collectors.toList());

			for (Student student : sortedList) {
				System.out.print("||   ID " + student.getID() + "|" + student.getJmeno() + "|"
						+ student.getRokNarozeni() + "|Známky: ");
				for (Double znamky : student.listZnamky) {
					System.out.print(znamky + "|");

				}
				student.stipendium();
				System.out.print(" STP-" + student.getStipendium() + "\n");

			}
			System.out.println("||");
		}

	}

	void naklady() {

		System.out.print(
				"______________Náklady____________________________________________________________________________\n");

		List<Ucitel> sortedList = listUcitelu.stream()
				.sorted(Comparator.comparingInt(Ucitel::getPocetStudentu).reversed()).collect(Collectors.toList());

		int PSBS = 0;
		int PSST = 0;
		int PSBScelkem = 0;
		int PSSTcelkem = 0;
		int PUcelkem = 0;
		int PSc = 0;

		double naklady = 0;
		double nakladySecteno = 0;
		double nakladyPoZdaneni;
		double nakladyPoZdaneniSecteno = 0;
		double stipendiaCelkem = 0;

		// sežazení uèitelù podle poètu studentu sestupnì
		System.out.print("||Uèitelé dle poètu studentù:\n");
		for (Ucitel sort : sortedList) {

			System.out.print("||ID " + sort.getID() + "|" + sort.getJmeno() + "|" + sort.getRokNarozeni()
					+ "\n||     ");

			PSBS = 0;
			PSST = 0;

			for (Student student : sort.listStudentu) {

				if (student.getStipendium() == true) {
					PSST++;
				}
				if (student.getStipendium() == false) {
					PSBS++;
				}

			}

			PSc = sort.listStudentu.size();
			naklady = PSc * 1000 + PSST * 500;
			nakladySecteno = nakladySecteno + naklady;
			nakladyPoZdaneni = naklady - (naklady * 0.21);

			System.out.print("PSc " + PSc + "*1000" + "=" + (PSBS * 1000) + "|PSST " + PSST + "*500=" + (PSST * 500)
					+ " =>" + naklady);
			System.out.print("-(daò " + (naklady * 0.21) + ")" + "=" + nakladyPoZdaneni + "\n");

			PSBScelkem = PSBScelkem + PSBS;
			PSSTcelkem = PSSTcelkem + PSST;
			PUcelkem++;

			nakladyPoZdaneniSecteno = nakladyPoZdaneniSecteno + nakladyPoZdaneni;
		}

		stipendiaCelkem = PSSTcelkem * 300;

		System.out.println("||");
		System.out.println("||PSBSc..." + PSBScelkem);
		System.out.println("||PSSTc..." + PSSTcelkem + "*300" + "=" + stipendiaCelkem);
		System.out.println("||PSC....." + (PSSTcelkem + PSBScelkem));
		System.out.println("||PUC....." + PUcelkem);

		System.out.println("||");
		System.out.println(
				"________Celkem___________________________________________________________________________________");
		System.out.println("||Platy uèitelù=" + nakladyPoZdaneniSecteno + "+(daò " + (nakladySecteno * 0.21) + ")="
				+ nakladySecteno);
		System.out.println("||Stipendia=" + stipendiaCelkem);
		System.out.println("||Celkem=" + (nakladySecteno + stipendiaCelkem));
		System.out.print(
				"_________________________________________________________________________________________________\n");
	}

	void vypisUciteluStudenta() {

		System.out.println("Zadejte ID studenta:");
		int IDstudenta = Integer.parseInt(sc.nextLine());

		// Test, jestli ID nepatøí náhodou uèiteli
		for (Ucitel ucitel : listUcitelu) {
			if (ucitel.getID() == IDstudenta) {
				System.out.print("Zadané ID nepatøí studentovi\n");
			}
		}

		// musím vypsat studenta, jinak by ho to furt vypisovalo, pøi kontrole dalšího
		// uèitele

		int podminka = 0;

		for (Ucitel ucitel : listUcitelu) {
			for (Student student : ucitel.listStudentu)
				if (podminka == 0) {

					if (student.getID() == IDstudenta) {
						System.out.print(
								"Student: ID " + student.getID() + "| " + student.getJmeno() + "\n");
						System.out.print("Uèitlé:\n");
						podminka = 1;
					}
				}
		}

		for (Ucitel ucitel : listUcitelu) {
			if (ucitel.getID() == IDstudenta) {
			}
			// Pokud zadané ID nepatøí uèiteli->
			else {
				// -> tak projedu studenty
				for (Student student : ucitel.listStudentu) {
					// Pokud zadané ID patøí studentovi
					if (student.getID() == IDstudenta) {

						System.out.print("   ID " + ucitel.getID() + "| " + ucitel.getJmeno() + "\n");
					}
				}

			}

		}
	}

	void vyhledatID() {

		int podminka = 0;
		int PSst = 0;

		System.out.print("Zadejte ID osoby:");
		int ID = Integer.parseInt(sc.nextLine());

		// nejdøíve nejdu osobu
		// zaènu v uèitelích - patøí ID uèiteli?
		for (Ucitel ucitel : listUcitelu) {

			if (ucitel.getID() == ID) {
				System.out.print("Uèitel: ID " + ucitel.getID() + "|" + ucitel.getJmeno() + "|"
						+ ucitel.getRokNarozeni());

				// je potøeba vypsat ještì plat uèitele v èistým
				// plat uèitele se odvijí od poètu studentù +500 za každého studenta, co má
				// stipendium

				int PSc = ucitel.listStudentu.size();
				for (Student student : ucitel.listStudentu) {

					System.out.print("|PSc " + PSc);
					if (student.getStipendium() == true) {
						PSst++;
					}
				}

				double vyplataHruba = (PSc * 1000) + (PSst * 500);
				double vyplataCista = vyplataHruba - vyplataHruba * 0.21;
				System.out.print("|PSst " + PSst + " =>" + vyplataCista);
				System.out.println();

			}
			// pokud ID není uèitele, bude studenta
			else {

				// cyklus je ve foru uèitele, takže by to vypsalo studenta vícekrát, protože 1
				// studen mùže být u jednoho uèitele. Proto pøidávám podmínku, díky které se
				// student pøi nalezení vypíše pouze jednou
				if (podminka == 0) {

					for (Student student : ucitel.listStudentu) {
						if (student.getID() == ID) {
							System.out.print("Student: ID " + student.getID() + "|" + student.getJmeno()
									+ "|" + student.getRokNarozeni() + "|STP-" + student.getStipendium() + "\n");
							podminka++;
						}
					}
				}
			}

		}

	}

	void vypisDleID() {

		// nejdøíve se do seøazeného seznamu uloží dle ID uèitelé
		List<Ucitel> sortedListU = listUcitelu.stream().sorted(Comparator.comparingInt(Ucitel::getID))
				.collect(Collectors.toList());

		// výpis seøazených uèitelù
		System.out.print("\nList uèitelù dle ID:\n");
		for (Ucitel ucitel : sortedListU) {
			System.out.print("   ID " + ucitel.getID() + "|" + ucitel.getJmeno() + "\n");
		}
		System.out.println();

		// dále je potøeba vypsat seøazené studenty dle ID
		// zkusím studenty zatím uložit do nového seznamu
		System.out.println("List studentù dle ID:");
		List<Student> listS = new ArrayList<Student>();

		// studenti se ve výpisu ukazují 2x, protože je to ukládá z každého uèitele
		// je potøeba ošetøit, když student se zadaným ID už v novém listu již je, tak
		// aby se už do listu nevytvoøil
		int ID;
		String jmenoStudenta;
		int rokNarozeni;

		for (Ucitel ucitel : listUcitelu) {
			for (Student student : ucitel.listStudentu) {
				ID = student.getID();
				jmenoStudenta = student.getJmeno();
				rokNarozeni = student.getRokNarozeni();

				// pokud je list prázdný, pøídám tam prvek
				if (listS.size() == 0) {
					listS.add(new Student(ID, jmenoStudenta, rokNarozeni));
				}

				// je potøeba nepøidávat do listu studenty s ID, které tam již je

				int podminka = 0;

				for (Student studentX : listS) {
					if (studentX.getID() == ID) {
						podminka = 1;
					}
				}

				if (podminka == 0) {
					listS.add(new Student(ID, jmenoStudenta, rokNarozeni));
				}

				// KONEÈNÌ

			}
		}

		// teï list seøadím
		List<Student> listSt = listS.stream().sorted(Comparator.comparingInt(Student::getID))
				.collect(Collectors.toList());

		// dále staèí list jen vypsat
		for (Student student : listSt) {
			System.out.print("   ID " + student.getID() + "|" + student.getJmeno() + "\n");

		}

	}

////////////////////SQL//////////////////////////////////////
////////////////////SQL//////////////////////////////////////
////////////////////SQL//////////////////////////////////////
	private Connection conn;

	public boolean connect() {
		conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:myDB.db");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return false;
		}
		return true;
	}

	public void disconnect() {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException ex) {
				System.out.println(ex.getMessage());
			}
		}
	}

	public boolean createTable() {
		if (conn == null)
			return false;
		String ucitele = "CREATE TABLE IF NOT EXISTS ucitele(" + "id integer PRIMARY KEY," + "IDu int,"
				+ "jmenoUcitele varchar," + "rokNarozeni int" + ");";

		String studenti = "CREATE TABLE IF NOT EXISTS studenti(" + "id integer PRIMARY KEY," + "IDs int,"
				+ "jmenoStudenta varchar," + "rokNarozeni int" + ");";

		// -------------------------------------------------
		// Ještì je potøeba uložit známky

		String znamky = "CREATE TABLE IF NOT EXISTS znamky(" + "id integer PRIMARY KEY," + "ZN float" + ");";

		// ---------------------------------------------
		try {
			Statement stmt = conn.createStatement();
			stmt.execute(ucitele);
			stmt.execute(studenti);
			stmt.execute(znamky);
			return true;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

//////////////////////////////////////////////////////////////////////
	public void ulozitDataDoSQL() {

		/////// vložení uèitelù do SQL//////
		String ucitele = "INSERT INTO ucitele(IDu,jmenoUcitele,rokNarozeni) VALUES(?,?,?)";

		int IDu = 0;
		String jmenoUcitele = "";
		int rokNarozeniU = 0;

		// iterace každého uèitele a uloèení ho do SQL
		for (Ucitel ucitel : listUcitelu) {
			IDu = ucitel.getID();
			jmenoUcitele = ucitel.getJmeno();
			rokNarozeniU = ucitel.getRokNarozeni();

			try {
				PreparedStatement pstmt = conn.prepareStatement(ucitele);
				pstmt.setInt(1, IDu);
				pstmt.setString(2, jmenoUcitele);
				pstmt.setInt(3, rokNarozeniU);
				pstmt.executeUpdate();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}

		////// vložení studentù do databáze/////
		List<Student> listS = new ArrayList<Student>();
		int ID;
		String jmenoStudenta;
		int rokNarozeni;

		for (Ucitel ucitel : listUcitelu) {
			listS.add(new Student(-10, "----", -10));
			for (Student student : ucitel.listStudentu) {
				ID = student.getID();
				jmenoStudenta = student.getJmeno();
				rokNarozeni = student.getRokNarozeni();

				listS.add(new Student(ID, jmenoStudenta, rokNarozeni));

			}
			// v SQL je potøeba udìlat speciální záznam, na která když narazím, tak mi to
			// žáky zaène pøiøazovat dalšímu uèiteli

		}

		// nyní je uložím do databáze - k tomu je potøeba projet listS, kam se uložili
		for (Student student : listS) {

			int IDs = student.getID();
			String jmenoStudentaX = student.getJmeno();
			int rokNarozeniS = student.getRokNarozeni();

			String studenti = "INSERT INTO studenti(IDs,jmenoStudenta,rokNarozeni) VALUES(?,?,?)";

			try {
				PreparedStatement pstmt = conn.prepareStatement(studenti);
				pstmt.setInt(1, IDs);
				pstmt.setString(2, jmenoStudentaX);
				pstmt.setInt(3, rokNarozeniS);
				pstmt.executeUpdate();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}

		///// Uložení známek do SQL///////////////

		// je potøeba proiterovat každého uèitele a v nìm každého studenta, aby jsem se
		// dostal do známek
		List<Double> znamkyNove = new ArrayList<>();

		// v každém uèiteli proiteruji každého studenta a v nìm všechny známky -> ty
		// uložím do nového listu znamkyNove

		int podminka = -1;

		for (Ucitel ucitel : listUcitelu) {
			// každý uèitel bude mít známky oddìlené -10

			podminka = -1;
			znamkyNove.add((double) -10);

			for (Student student : ucitel.listStudentu) {

				// každý student bude mít známky oddìlené -5

				if (podminka == 0) {
					znamkyNove.add((double) -5);
				}

				for (Double znamky : student.listZnamky) {

					znamkyNove.add(znamky);

				}
				podminka++;

			}

		}

		// uložení známek do SQL

		for (Double znamky : znamkyNove) {

			String znamkyX = "INSERT INTO znamky(ZN) VALUES(?)";

			try {
				PreparedStatement pstmt = conn.prepareStatement(znamkyX);
				pstmt.setDouble(1, znamky);
				pstmt.executeUpdate();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}

		}

	}

//////////////////////////////////////////////////////////////////////
	public void najitVsql() {
		String sql = "SELECT IDu, jmenoUcitele, rokNarozeni FROM ucitele where IDu=?";

		System.out.print("Zadejte ID uèitele");
		int IDucitele = Integer.parseInt(sc.nextLine());

		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, IDucitele);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				System.out.println("\t" + rs.getInt("IDu") + "\t" + rs.getString("jmenoUcitele") + "\t"
						+ rs.getInt("rokNarozeni"));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

//////////////////////////////////////////////////////////////////////
	public void vypsatVsechnyZeSQL() {

		// všechny uèitelé
		System.out.print("Uèitelé\n");
		String ucitele = "SELECT id, IDu, jmenoUcitele,rokNarozeni FROM ucitele";
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(ucitele);
			while (rs.next()) {

				System.out.println(rs.getInt("id")+" "+
						rs.getInt("IDu") + "\t" + rs.getString("jmenoUcitele") + "\t" + rs.getLong("rokNarozeni"));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		// všechny studenty

		System.out.print("Studenti\n");
		String studenti = "SELECT id,IDs, jmenoStudenta,rokNarozeni FROM studenti";
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(studenti);
			while (rs.next()) {

				System.out.println(rs.getInt("id") +" "+
						rs.getInt("IDs") + "\t" + rs.getString("jmenoStudenta") + "\t" + rs.getLong("rokNarozeni"));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		// všechny známky

		System.out.print("Známky\n");
		String znamky = "SELECT id,ZN FROM znamky";
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(znamky);
			while (rs.next()) {

				System.out.println(rs.getInt("id")+" "+rs.getInt("ZN"));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

//////////////////////////////////////////////////////////////////////
	public void nacistData() {

		// load uèitelé

		String sql = "SELECT IDu, jmenoUcitele,rokNarozeni FROM ucitele";
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {

				int IDucitele = rs.getInt("IDu");
				String jmenoUcitele = rs.getString("jmenoUcitele");
				int rokNarozeni = rs.getInt("rokNarozeni");

				listUcitelu.add(new Ucitel(IDucitele, jmenoUcitele, rokNarozeni));

			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		// load studentù

		int IDstudenta = 0;
		String jmenoStudenta = "";
		int rokNarozeni = 0;

		String studenti = "SELECT IDs, jmenoStudenta,rokNarozeni FROM studenti";
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(studenti);
			int podminka = -1;

			while (rs.next()) {

				IDstudenta = rs.getInt("IDs");
				jmenoStudenta = rs.getString("jmenoStudenta");
				rokNarozeni = rs.getInt("rokNarozeni");

				if (IDstudenta == -10) {
					podminka++;
				} else {
					listUcitelu.get(podminka).listStudentu.add(new Student(IDstudenta, jmenoStudenta, rokNarozeni));
				}

			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		// load známek

		double znamky = 0;

		String znamkyX = "SELECT ZN FROM znamky";
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(znamkyX);

			int i = -1;
			int j = 0;
			int k = 0;

			while (rs.next()) {
				k = 0;
				znamky = rs.getDouble("ZN");

				// --------------------------------------------------

				// uložení známek ke studentovi

				if (znamky == -10) {
					i++;
					j = 0;

					k = 1;
				}

				if (znamky == -5) {
					j++;

					k = 1;
				}

				if (k == 0) {
					listUcitelu.get(i).listStudentu.get(j).listZnamky.add(znamky);
				}

			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}

	public void vymazatZeSQL() {
		
		System.out.print("[1] Smazat uèitele , [2] Smazat studenta?");
		
		int volba = Integer.parseInt(sc.nextLine());
		
		
		switch(volba) {
		case 1:
			System.out.print("Pozice uèitele v SQL:");
			int IDu =Integer.parseInt(sc.nextLine());
			
			String sql1 = "DELETE FROM ucitele WHERE IDu = ?";

			try {
				PreparedStatement pstmt = conn.prepareStatement(sql1);
				pstmt.setInt(1, IDu);
				// execute the delete statement
				pstmt.executeUpdate();

			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
			
			//Je potøeba pøi smazání uèitele smazat jeho studenty a i jejich známky
			
			
			
			
			break;
			
		case 2:
			System.out.print("ID studenta:");
			int IDs =Integer.parseInt(sc.nextLine());
			
			String sql2 = "DELETE FROM studenti WHERE IDs = ?";

			try {
				PreparedStatement pstmt = conn.prepareStatement(sql2);
				pstmt.setInt(1, IDs);
				// execute the delete statement
				pstmt.executeUpdate();

			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
			
			break;
		}
		
		
		
		
		
		
		
	}

}
