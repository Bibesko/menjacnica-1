package menjacnica.gui;

import java.awt.EventQueue;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import menjacnica.Menjacnica;
import menjacnica.Valuta;
import menjacnica.interfejs.MenjacnicaInterface;

public class GUIKontroler {
	
	private static MenjacnicaGUI glavniprozor;
	private static MenjacnicaInterface menjacnica;

	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				
				try {
					menjacnica = new Menjacnica();
					glavniprozor = new MenjacnicaGUI();
					glavniprozor.setVisible(true);
					glavniprozor.setLocationRelativeTo(null);
				} catch (Exception e) {			
					e.printStackTrace();
				}				
			}
		});		
	}
	
	public static void ugasiAplikaciju() {
		int opcija = JOptionPane.showConfirmDialog(glavniprozor.getContentPane(),
				"Da li ZAISTA zelite da izadjete iz apliacije", "Izlazak",
				JOptionPane.YES_NO_OPTION);

		if (opcija == JOptionPane.YES_OPTION)
			System.exit(0);
	}

	public static void prikaziDodajKursGUI() {
		DodajKursGUI prozor = new DodajKursGUI();
		prozor.setLocationRelativeTo(glavniprozor.getContentPane());
		prozor.setVisible(true);
	}
	
	public static void prikaziAboutProzor(){
		JOptionPane.showMessageDialog(glavniprozor.getContentPane(),
				"Autor: Bojan Tomic, Verzija 1.0", "O programu Menjacnica",
				JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static void sacuvajUFajl() {
		try {
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showSaveDialog(glavniprozor.getContentPane());

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();

				menjacnica.sacuvajUFajl(file.getAbsolutePath());
			}
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(glavniprozor.getContentPane(), e1.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public static void ucitajIzFajla() {
		try {
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showOpenDialog(glavniprozor.getContentPane());

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				menjacnica.ucitajIzFajla(file.getAbsolutePath());
				glavniprozor.prikaziSveValute(menjacnica.vratiKursnuListu());
			}	
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(glavniprozor.getContentPane(), e1.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public static void prikaziObrisiKursGUI() {
		if (glavniprozor.list.getSelectedValue() != null) {
			ObrisiKursGUI prozor = new ObrisiKursGUI(null,
					(Valuta) (glavniprozor.list.getSelectedValue()));
			prozor.setLocationRelativeTo(glavniprozor.getContentPane());
			prozor.setVisible(true);
		}
	}
	
	public static void prikaziIzvrsiZamenuGUI() {
		if (glavniprozor.list.getSelectedValue() != null) {
			IzvrsiZamenuGUI prozor = new IzvrsiZamenuGUI(null,
					(Valuta) (glavniprozor.list.getSelectedValue()));
			prozor.setLocationRelativeTo(glavniprozor.getContentPane());
			prozor.setVisible(true);
		}
	}
	
	public static void unesiKurs(String naziv,String skraceniNaziv,Integer sifra,double prodajniKurs
			, double kupovniKurs, double srednjiKurs) {
		try {
			Valuta valuta = new Valuta();

			// Punjenje podataka o valuti
			valuta.setNaziv(naziv);
			valuta.setSkraceniNaziv(skraceniNaziv);
			valuta.setSifra(sifra);
			valuta.setProdajni(prodajniKurs);
			valuta.setKupovni(kupovniKurs);
			valuta.setSrednji(srednjiKurs);
			
			// Dodavanje valute u kursnu listu
			menjacnica.dodajValutu(valuta);

			// Osvezavanje glavnog prozora
			glavniprozor.prikaziSveValute(menjacnica.vratiKursnuListu());
			
			//Zatvaranje DodajValutuGUI prozora
			//dispose();
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(glavniprozor.getContentPane(), e1.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public static void izvrsiZamenu(Valuta valuta,boolean selected, double iznos){
		try{
			double konacniIznos = 
					menjacnica.izvrsiTransakciju(valuta,
							selected, 
							iznos);
		
			IzvrsiZamenuGUI.textFieldKonacniIznos.setText(""+konacniIznos);
		} catch (Exception e1) {
		JOptionPane.showMessageDialog(glavniprozor.getContentPane(), e1.getMessage(),
				"Greska", JOptionPane.ERROR_MESSAGE);
	}
	}
	
	public static void obrisiValutu(Valuta valuta) {
		try{
			menjacnica.obrisiValutu(valuta);
			
			glavniprozor.prikaziSveValute(menjacnica.vratiKursnuListu());
			//dispose();
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(glavniprozor.getContentPane(), e1.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	
}
