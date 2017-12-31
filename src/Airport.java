import java.awt.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

import org.graphstream.graph.Graph;

public class Airport {
	private String nombreAeropuerto;
	private String IATA;
	private Lector lector;
	private HashMap<String,Airport> mapa;
	private Airport a;

	public Airport(String nombre, String iata){
		this.nombreAeropuerto = nombre;
		this.IATA = iata;

	}

	public Airport(){}



	public HashMap<String,Airport> CrearAeropuertos(HashMap<String,Airport> lista) throws IOException{
		int contador=0;
		lector=new Lector();

		String[] lineas = lector.Leer("C:/Users/Jaime/Documents/airports.dat");
		for (int i=0; i<lineas.length; i++){
			String info = lineas[i];





			String[] split = lineas[i].split("(?<=,)");




			String id = split[0];
			String airport = split[1];
			String city = split[2];
			String country = split[3];
			String iata = split[4];
			String icao = split[5];
			String regex = "(\")|(,)";
			String aa = airport.replaceAll(regex, "");
			String newId = id.replaceAll(regex,"");
			String newiata = iata.replaceAll(regex,"");
			String newicao = icao.replaceAll(regex,"");




			//System.out.println(newId + " " + newiata);
			//int identificacion = Integer.parseInt(newiata);



			if (newiata.length()>3){
				Airport aeropuerto = new Airport(aa,newicao);	
				lista.put(newicao, aeropuerto);

			}

			else{
				Airport aeropuerto = new Airport(aa,newiata);	
				lista.put(newiata,aeropuerto);
			}




		}

		//System.out.println(lista.size());
		//System.out.println("Contador " + contador);
		mapa=lista;
		return lista;
	}


	public void CrearNodosAeropuertos(HashMap<String, Airport> aeropuertos, Graph grafo) throws IOException{
		Airport a = new Airport();
		a.CrearAeropuertos(aeropuertos);
		Properties property = new Properties();
		Iterator<Map.Entry<String, Airport>> entries = aeropuertos.entrySet().iterator();
		while (entries.hasNext()) {
			Map.Entry<String, Airport> entry = entries.next();
			property.put(entry.getKey(), entry.getValue());
			grafo.addNode(entry.getKey());
		}
		
		mapa = aeropuertos;
		
	}

	public String[] VerificarAeropuertos() throws IOException{
		boolean cambios=false;
		String token1 = "";

		Scanner inFile1 = new Scanner(new File("C:/Users/Jaime/Documents/aeropuertos.txt"));
		ArrayList<String> temps = new ArrayList<String>();

		while (inFile1.hasNext()) {
			// find next line
			token1 = inFile1.next();
			temps.add(token1);
		}
		inFile1.close();

		String[] iata = temps.toArray(new String[0]);
		//System.out.println(iata.length);


		return iata;
	}

	public void CrearFicheroAeropuertos() throws IOException{
		Airport c = new Airport();
		String[] comparador = c.VerificarAeropuertos();
		int size = comparador.length;
		Iterator<Map.Entry<String, Airport>> entries = mapa.entrySet().iterator();
		if (comparador.length>size){
			while (entries.hasNext()) {
				Map.Entry<String, Airport> entry = entries.next();
				try{
					FileWriter fw = new FileWriter("C:/Users/Jaime/Documents/aeropuertos.txt",true);
					fw.write(entry.getKey()+System.lineSeparator());
					fw.close();
				}catch(IOException e){
					e.printStackTrace();
				}

			}
		}
	}


	public String getNombreAeropuerto() {
		return nombreAeropuerto;
	}

	public void setNombreAeropuerto(String nombreAeropuerto) {
		this.nombreAeropuerto = nombreAeropuerto;
	}

	public String getIATA() {
		return IATA;
	}

	public void setIATA(String iATA) {
		IATA = iATA;
	}

	public Lector getLector() {
		return lector;
	}

	public void setLector(Lector lector) {
		this.lector = lector;
	}
}
