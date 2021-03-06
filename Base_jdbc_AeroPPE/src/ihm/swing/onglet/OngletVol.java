package ihm.swing.onglet;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controleur.swing.CreateVol;
import controleur.swing.DeleteVol;
import controleur.swing.ReadVol;
import controleur.swing.UpdateVol;
import dao.Connexion;
import ihm.swing.JTableRenderer;

public class OngletVol extends JPanel {

	private static final long serialVersionUID = 1L;

	private static OngletVol instance = null;
	private JTable table = null; 

	public static OngletVol getInstance() {
		if (instance==null) {
			instance=new OngletVol();
		}
		return instance;
	}
	
	private OngletVol() {
		super();
		this.setLayout(new BorderLayout());
		this.creerOngletVol();
		this.creerBoutonsVol();
	}
	
	public void afficherSelect() {
		String sql3 = "SELECT COUNT(*) as nbLignes FROM VOL";
		ResultSet rs3 = Connexion.executeQuery(sql3);
		int nbLignes = 0;
		try {
			rs3.next();
			nbLignes = rs3.getInt("nbLignes");
		} catch (SQLException e3) {
			e3.printStackTrace();
		}
		String reqVol = "SELECT * FROM VOL;";
		ResultSet rsVol = Connexion.executeQuery(reqVol);
		String colonnes[] = {"Num�ro Vol", "Num�ro Pilote", "Num�ro Avion", "Ville D�part", "Ville Arriv�e", "Heure D�part", "Heure Arriv�e"};
		String dicoVol[][] = new String[nbLignes][7];
		int i = 0;
		try {
			while (rsVol.next()) {
				int numVol = rsVol.getInt("numVol");
				int numPil = rsVol.getInt("numPil");
				int numAv = rsVol.getInt("numAv");
				String villeDep = rsVol.getString("villeDep");
				String villeArr = rsVol.getString("villeArr");
				String hDep = rsVol.getString("hDep");
				String hArr = rsVol.getString("hArr");
				dicoVol[i][0] = String.valueOf(numVol);
				dicoVol[i][1] = String.valueOf(numPil);
				dicoVol[i][2] = String.valueOf(numAv);
				dicoVol[i][3] = villeDep;
				dicoVol[i][4] = villeArr;
				dicoVol[i][5] = hDep;
				dicoVol[i][6] = hArr;
				i++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DefaultTableModel model = new DefaultTableModel(dicoVol, colonnes);
		table.setModel(model);
	}
	
	public void creerOngletVol() {
		JPanel jpanelTableVol = new JPanel(new BorderLayout());
		this.creerPanelOngletVol(jpanelTableVol);
		this.add(jpanelTableVol);
	}
	
	private void creerPanelOngletVol(JPanel jpanelTableVol) {
		table = new JTable();
		table.setShowGrid(true);
		table.setShowVerticalLines(true);
		table.setDefaultRenderer(Object.class, new JTableRenderer());
		table.setAutoCreateRowSorter(true);
		this.afficherSelect();
		JScrollPane pane = new JScrollPane(table);
		jpanelTableVol.add(pane);
	}
	
	//boutons pied de page :
	public void creerBoutonsVol() {
		JButton rechercher = new JButton("Rechercher");
		JButton ajouter = new JButton("Ajouter");
		JButton supprimer = new JButton("Supprimer");
		JButton modifier = new JButton("Modifier");
		rechercher.setPreferredSize(new Dimension(150, 50));
		rechercher.setBorder(BorderFactory.createTitledBorder("Vol"));
		ajouter.setPreferredSize(new Dimension(150, 50));
		ajouter.setBorder(BorderFactory.createTitledBorder("Vol"));
		supprimer.setPreferredSize(new Dimension(150, 50));
		supprimer.setBorder(BorderFactory.createTitledBorder("Vol"));
		modifier.setPreferredSize(new Dimension(150, 50));
		modifier.setBorder(BorderFactory.createTitledBorder("Vol"));
		JPanel southVol = new JPanel();
		southVol.add(rechercher);
		southVol.add(ajouter);
		southVol.add(supprimer);
		southVol.add(modifier);
		ActionListener alRecherche = new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {	
				new ReadVol();
			}
		};
		rechercher.addActionListener(alRecherche);
		ActionListener alAjouter = new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {				
				new CreateVol();
			}
		};
		ajouter.addActionListener(alAjouter);
		ActionListener alSuppr = new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {				
				new DeleteVol();
			}
		};
		supprimer.addActionListener(alSuppr);
		ActionListener alModif = new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {				
				new UpdateVol();
			}
		};
		modifier.addActionListener(alModif);
		this.add(southVol, BorderLayout.SOUTH);
	}
	
}
