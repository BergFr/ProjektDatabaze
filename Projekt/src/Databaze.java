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
		System.out.printf("Zadejte ID u�itele:");
		int ID = Integer.parseInt(sc.nextLine());
		int podminka = 0;
		int podminka2 = 0;

		for (Ucitel ucitel : listUcitelu) {

			for (Student student : ucitel.listStudentu) {
				// podminka2 aby se hl�ky nevypisovala v�cekr�t, pokud bude jeden student u
				// v�ce u�itel�
				if (podminka2 == 0) {
					if (student.getID() == ID) {
						System.out.println("\nZadan� ID ji� je v datab�zi student�\n");
						podminka = 1;
						podminka2 = 1;
					}
				}
			}

			if (ucitel.getID() == ID) {
				System.out.println("\nZadan� ID ji� je v datab�zi u�itel�\n");
				podminka = 1;
			}
		}

		if (podminka == 0) {
			System.out.printf("Zadej jm�no a p�ij�m�n� u�itele:");
			String jmenoU = sc.nextLine();
			System.out.printf("Zadej rok narozen� u�itele:");
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

		System.out.print("Zadejte ID u�itele, pod kter�m bude student:");
		IDucitele = Integer.parseInt(sc.nextLine());

		System.out.print("Zadejte ID studenta, kter�bo hudete p�i�azovat:");
		IDstudent = Integer.parseInt(sc.nextLine());

		// Existuje v cel� datab�z� stejn� ID studenta?
		for (Ucitel ucitel : listUcitelu) {
			for (Student student : ucitel.listStudentu) {

				if (student.getID() == IDstudent) {
					if (podminkaVypis == 1) {
						System.out.print("\nZadan� ID studenta ji� v datab�zi existuje\n\n");
					}
					IDstudent = student.getID();
					jmenoStudenta = student.getJmeno();
					rokNarozeni = student.getRokNarozeni();

					podminka = 2;

					// Je zadan� ID u konkr�tn�ho u�itele?
					for (Ucitel ucitelX : listUcitelu) {
						if (ucitelX.getID() == IDucitele) {
							for (Student studentX : ucitelX.listStudentu) {
								if (studentX.getID() == IDstudent) {
									// Pokud je v datab�zi u u�itel� v�ce stejn�ch id, tak by se zpr�vy vypisovali
									// pro ka�dou osobu, proto if podminkaVypis
									if (podminkaVypis == 1) {
										System.out.print("Zadan� ID je ji� p�i�azeno u zadan�ho u�itele\n\n");
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

		// M� zadan� ID u� n�kter� u�itel?
		for (Ucitel ucitel : listUcitelu) {
			if (ucitel.getID() == IDstudent) {
				System.out.print("\nZadan� ID m� ji� u�itel\n\n");
				podminka = 0;
			}
		}

		// pokud v datab�zi ji� existuje student, nebude se zakl�dat nov�, ale p�i�ad�
		// se existuj�c�
		if (podminka == 2) {
			for (Ucitel ucitel : listUcitelu) {
				if (ucitel.getID() == IDucitele) {
					ucitel.listStudentu.add(new Student(IDstudent, jmenoStudenta, rokNarozeni));
				}
			}
		}

		if (podminka == 1) {

			System.out.print("Zadejte jm�no studenta, kter�bo hudete p�i�azovat:");
			jmenoStudenta = sc.nextLine();
			System.out.print("Zadejte rok narozen� studenta, kter�bo hudete p�i�azovat:");
			rokNarozeni = Integer.parseInt(sc.nextLine());

			for (Ucitel ucitel : listUcitelu) {
				if (ucitel.getID() == IDucitele) {
					ucitel.listStudentu.add(new Student(IDstudent, jmenoStudenta, rokNarozeni));
				}
			}
		}

	}

	void pridatZnamky() {
		System.out.print("Zadejte ID u�itele, pod kter�m je student:");
		int IDucitele = Integer.parseInt(sc.nextLine());
		System.out.print("Zadejte ID studenta:");
		int IDstudenta = Integer.parseInt(sc.nextLine());

		for (Ucitel ucitel : listUcitelu) {
			if (ucitel.getID() == IDucitele) {

				for (Student student : ucitel.listStudentu) {
					if (student.getID() == IDstudenta) {
						System.out.print("\nVybran� student " + student.getJmeno() + " u u�itele "
								+ ucitel.getJmeno() + "\n");
						System.out.print("Zadejte po�et zn�mek:");
						int pocet = Integer.parseInt(sc.nextLine());
						for (int i = 0; i < pocet; i++) {
							System.out.print("Zadejte zn�mku:");
							double znamka = Double.parseDouble(sc.nextLine());
							student.listZnamky.add(znamka);
						}
					}
				}
			}
		}
	}

////////////////////MAZ�N�///////////////////////////////
	void vymazat() {

		System.out.print("Zadejte ID:");
		int ID = Integer.parseInt(sc.nextLine());
		listUcitelu.removeIf(Ucitel -> (Ucitel.getID() == ID));

		for (Ucitel listU : listUcitelu) {
			listU.listStudentu.removeIf(Student -> (Student.getID() == ID));
		}
		System.out.print("�lov�k s ID " + ID + " byl vymaz�n\n");
	}

	void vymazatStudentaUciteli() {
		System.out.print("Zadejte ID u�itele:");
		int IDucitele = Integer.parseInt(sc.nextLine());
		System.out.print("Zadejte ID studenta:");
		int IDstudenta = Integer.parseInt(sc.nextLine());

		for (Ucitel ucitel : listUcitelu) {
			if (ucitel.getID() == IDucitele) {
				for (Student student : ucitel.listStudentu) {
					if (student.getID() == IDstudenta) {
						System.out.print("\nStudent " + student.getJmeno() + " byl vymaz�n u u�itele "
								+ ucitel.getJmeno() + "\n\n");
					}
				}

				ucitel.listStudentu.removeIf(Student -> (Student.getID() == IDstudenta));

			}
		}
	}

////////V�PISY//////////////////////////////////////////////
	void vypisDatabaze() {

		List<Student> sortedList;

		System.out.print("                               __    ___     __     __  __ \n");
		System.out.print(
				"_____________________________ |  \\ /\\ |  /\\ |__) /\\  _/|__ ______________________________________\n");
		System.out.print("                              |__//--\\| /--\\|__)/--\\/__|__\n");

		System.out.print("||U�itel�:\n||\n");
		for (Ucitel ucitel : listUcitelu) {

			System.out.print("||ID " + ucitel.getID() + "|" + ucitel.getJmeno() + "|"
					+ ucitel.getRokNarozeni() + "|");
			ucitel.getPlat();
			System.out.println();
			System.out.print("||Studenti dle pr�m�ru:\n");

			// U u�itele se se�adili studenti
			sortedList = ucitel.listStudentu.stream().sorted(Comparator.comparing(Student::getPrumer))
					.collect(Collectors.toList());

			for (Student student : sortedList) {
				System.out.print("||   ID " + student.getID() + "|" + student.getJmeno() + "|"
						+ student.getRokNarozeni() + "|Zn�mky: ");
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
				"______________N�klady____________________________________________________________________________\n");

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

		// se�azen� u�itel� podle po�tu studentu sestupn�
		System.out.print("||U�itel� dle po�tu student�:\n");
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
			System.out.print("-(da� " + (naklady * 0.21) + ")" + "=" + nakladyPoZdaneni + "\n");

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
		System.out.println("||Platy u�itel�=" + nakladyPoZdaneniSecteno + "+(da� " + (nakladySecteno * 0.21) + ")="
				+ nakladySecteno);
		System.out.println("||Stipendia=" + stipendiaCelkem);
		System.out.println("||Celkem=" + (nakladySecteno + stipendiaCelkem));
		System.out.print(
				"_________________________________________________________________________________________________\n");
	}

	void vypisUciteluStudenta() {

		System.out.println("Zadejte ID studenta:");
		int IDstudenta = Integer.parseInt(sc.nextLine());

		// Test, jestli ID nepat�� n�hodou u�iteli
		for (Ucitel ucitel : listUcitelu) {
			if (ucitel.getID() == IDstudenta) {
				System.out.print("Zadan� ID nepat�� studentovi\n");
			}
		}

		// mus�m vypsat studenta, jinak by ho to furt vypisovalo, p�i kontrole dal��ho
		// u�itele

		int podminka = 0;

		for (Ucitel ucitel : listUcitelu) {
			for (Student student : ucitel.listStudentu)
				if (podminka == 0) {

					if (student.getID() == IDstudenta) {
						System.out.print(
								"Student: ID " + student.getID() + "| " + student.getJmeno() + "\n");
						System.out.print("U�itl�:\n");
						podminka = 1;
					}
				}
		}

		for (Ucitel ucitel : listUcitelu) {
			if (ucitel.getID() == IDstudenta) {
			}
			// Pokud zadan� ID nepat�� u�iteli->
			else {
				// -> tak projedu studenty
				for (Student student : ucitel.listStudentu) {
					// Pokud zadan� ID pat�� studentovi
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

		// nejd��ve nejdu osobu
		// za�nu v u�itel�ch - pat�� ID u�iteli?
		for (Ucitel ucitel : listUcitelu) {

			if (ucitel.getID() == ID) {
				System.out.print("U�itel: ID " + ucitel.getID() + "|" + ucitel.getJmeno() + "|"
						+ ucitel.getRokNarozeni());

				// je pot�eba vypsat je�t� plat u�itele v �ist�m
				// plat u�itele se odvij� od po�tu student� +500 za ka�d�ho studenta, co m�
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
			// pokud ID nen� u�itele, bude studenta
			else {

				// cyklus je ve foru u�itele, tak�e by to vypsalo studenta v�cekr�t, proto�e 1
				// studen m��e b�t u jednoho u�itele. Proto p�id�v�m podm�nku, d�ky kter� se
				// student p�i nalezen� vyp�e pouze jednou
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

		// nejd��ve se do se�azen�ho seznamu ulo�� dle ID u�itel�
		List<Ucitel> sortedListU = listUcitelu.stream().sorted(Comparator.comparingInt(Ucitel::getID))
				.collect(Collectors.toList());

		// v�pis se�azen�ch u�itel�
		System.out.print("\nList u�itel� dle ID:\n");
		for (Ucitel ucitel : sortedListU) {
			System.out.print("   ID " + ucitel.getID() + "|" + ucitel.getJmeno() + "\n");
		}
		System.out.println();

		// d�le je pot�eba vypsat se�azen� studenty dle ID
		// zkus�m studenty zat�m ulo�it do nov�ho seznamu
		System.out.println("List student� dle ID:");
		List<Student> listS = new ArrayList<Student>();

		// studenti se ve v�pisu ukazuj� 2x, proto�e je to ukl�d� z ka�d�ho u�itele
		// je pot�eba o�et�it, kdy� student se zadan�m ID u� v nov�m listu ji� je, tak
		// aby se u� do listu nevytvo�il
		int ID;
		String jmenoStudenta;
		int rokNarozeni;

		for (Ucitel ucitel : listUcitelu) {
			for (Student student : ucitel.listStudentu) {
				ID = student.getID();
				jmenoStudenta = student.getJmeno();
				rokNarozeni = student.getRokNarozeni();

				// pokud je list pr�zdn�, p��d�m tam prvek
				if (listS.size() == 0) {
					listS.add(new Student(ID, jmenoStudenta, rokNarozeni));
				}

				// je pot�eba nep�id�vat do listu studenty s ID, kter� tam ji� je

				int podminka = 0;

				for (Student studentX : listS) {
					if (studentX.getID() == ID) {
						podminka = 1;
					}
				}

				if (podminka == 0) {
					listS.add(new Student(ID, jmenoStudenta, rokNarozeni));
				}

				// KONE�N�

			}
		}

		// te� list se�ad�m
		List<Student> listSt = listS.stream().sorted(Comparator.comparingInt(Student::getID))
				.collect(Collectors.toList());

		// d�le sta�� list jen vypsat
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
		// Je�t� je pot�eba ulo�it zn�mky

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

		/////// vlo�en� u�itel� do SQL//////
		String ucitele = "INSERT INTO ucitele(IDu,jmenoUcitele,rokNarozeni) VALUES(?,?,?)";

		int IDu = 0;
		String jmenoUcitele = "";
		int rokNarozeniU = 0;

		// iterace ka�d�ho u�itele a ulo�en� ho do SQL
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

		////// vlo�en� student� do datab�ze/////
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
			// v SQL je pot�eba ud�lat speci�ln� z�znam, na kter� kdy� naraz�m, tak mi to
			// ��ky za�ne p�i�azovat dal��mu u�iteli

		}

		// nyn� je ulo��m do datab�ze - k tomu je pot�eba projet listS, kam se ulo�ili
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

		///// Ulo�en� zn�mek do SQL///////////////

		// je pot�eba proiterovat ka�d�ho u�itele a v n�m ka�d�ho studenta, aby jsem se
		// dostal do zn�mek
		List<Double> znamkyNove = new ArrayList<>();

		// v ka�d�m u�iteli proiteruji ka�d�ho studenta a v n�m v�echny zn�mky -> ty
		// ulo��m do nov�ho listu znamkyNove

		int podminka = -1;

		for (Ucitel ucitel : listUcitelu) {
			// ka�d� u�itel bude m�t zn�mky odd�len� -10

			podminka = -1;
			znamkyNove.add((double) -10);

			for (Student student : ucitel.listStudentu) {

				// ka�d� student bude m�t zn�mky odd�len� -5

				if (podminka == 0) {
					znamkyNove.add((double) -5);
				}

				for (Double znamky : student.listZnamky) {

					znamkyNove.add(znamky);

				}
				podminka++;

			}

		}

		// ulo�en� zn�mek do SQL

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

		System.out.print("Zadejte ID u�itele");
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

		// v�echny u�itel�
		System.out.print("U�itel�\n");
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

		// v�echny studenty

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

		// v�echny zn�mky

		System.out.print("Zn�mky\n");
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

		// load u�itel�

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

		// load student�

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

		// load zn�mek

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

				// ulo�en� zn�mek ke studentovi

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
		
		System.out.print("[1] Smazat u�itele , [2] Smazat studenta?");
		
		int volba = Integer.parseInt(sc.nextLine());
		
		
		switch(volba) {
		case 1:
			System.out.print("Pozice u�itele v SQL:");
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
			
			//Je pot�eba p�i smaz�n� u�itele smazat jeho studenty a i jejich zn�mky
			
			
			
			
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
