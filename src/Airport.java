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
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Scanner;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

public class Airport {
	private String ID;
	private String ciudad;
	private String pais;
	private String nombreAeropuerto;
	private String ICAO;
	private Lector lector;
	private HashMap<String,Airport> mapa;
	private Airport a;
	private HashMap<String, String> latia = new HashMap<String,String>();
	private HashMap<String, String> longa = new HashMap<String,String>();

	

	public Airport(String id,String nombre, String ciudad, String pais,String icao){
		this.nombreAeropuerto = nombre;
		this.ICAO = icao;
		this.ID = id;
		this.ciudad = ciudad;
		this.pais = pais;

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
			String lat = split[6];
			String lon = split[7];
			String alt = split[8];
			String regex = "(\")|(,)";
			
			
			
			String nombre = airport.replaceAll(regex, "");
			String newId = id.replaceAll(regex,"");
			String newCity = city.replaceAll(regex,"");
			String newCountry = country.replaceAll(regex,"");
			String newiata = iata.replaceAll(regex,"");
			String newicao = icao.replaceAll(regex,"");
			String latitude = lat.replaceAll(regex, "");
			String longitude = lon.replaceAll(regex, "");
			String altitude = alt.replaceAll(regex, "");
			



			//System.out.println(newId + " " + newiata);
			//int identificacion = Integer.parseInt(newiata);



			if (newiata.length()>3){
				Airport aeropuerto = new Airport(newId, nombre, newCity+newCountry, newiata, latitude);	
				lista.put(newicao, aeropuerto);
				latia.put(newicao, longitude);
				longa.put(newicao, altitude);

			}

			else{
				Airport aeropuerto = new Airport(newId, nombre, newCity, newCountry,newicao);	
				lista.put(newiata,aeropuerto);
				latia.put(newiata, latitude);
				longa.put(newiata, longitude);
			}



		}

		Iterator it = lista.entrySet().iterator();
		while (it.hasNext()){
			Map.Entry<String, Airport> entry = (Map.Entry<String, Airport>) it.next();
			String key = entry.getKey();
			if (key.matches("[^0-9]")){
				System.out.println("ERRRORRRRR " + key);
			}
		}
		System.out.println(lista.size());
		mapa=lista;
		return lista;
	}



	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getICAO() {
		return ICAO;
	}

	public void setICAO(String iCAO) {
		ICAO = iCAO;
	}

	public HashMap<String, Airport> getMapa() {
		return mapa;
	}

	public void setMapa(HashMap<String, Airport> mapa) {
		this.mapa = mapa;
	}

	public Airport getA() {
		return a;
	}

	public void setA(Airport a) {
		this.a = a;
	}

	public HashMap<String, String> getLatia() {
		return latia;
	}

	public void setLatia(HashMap<String, String> latia) {
		this.latia = latia;
	}

	public HashMap<String, String> getLonga() {
		return longa;
	}

	public void setLonga(HashMap<String, String> longa) {
		this.longa = longa;
	}

	public void CrearNodosAeropuertos(Graph grafo) throws IOException{
		String regex = "\\d+.";
		Iterator it = mapa.entrySet().iterator();
		while (it.hasNext()){
			Map.Entry<String, Airport> entry = (Map.Entry<String, Airport>) it.next();
			String iata = entry.getKey();
			grafo.addNode(iata);
			Node n = grafo.getNode(iata);
			n.setAttribute("x", longa.get(iata));
			n.setAttribute("y", latia.get(iata));
			n.addAttribute("ui.label", n.getId());
			
			
		}

	}
	
	
	public String[] CrearArrayAeropuertos(){
		String[] iata =  mapa.keySet().toArray(new String[0]);
		
		
		for (int i=0; i<iata.length;i++){
			//System.out.println("Info del array " + iata[i]);
		}
		return iata;
	}



	/*
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

		Iterator<Map.Entry<String, Airport>> entries = mapa.entrySet().iterator();

		for (String a : mapa.keySet()){
			try{
				String path = "C:/Users/Jaime/Documents/aeropuertos.txt";
				File file = new File(path);

				if (!file.exists()){
					file.createNewFile();
				}

				FileWriter fw = new FileWriter(file.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(a);
				bw.close();

			} catch (IOException e){
				e.printStackTrace();
			}
		}

	}
	 */

	public String getNombreAeropuerto() {
		return nombreAeropuerto;
	}

	public void setNombreAeropuerto(String nombreAeropuerto) {
		this.nombreAeropuerto = nombreAeropuerto;
	}

	public String getIATA() {
		return ICAO;
	}

	public void setIATA(String iATA) {
		ICAO = iATA;
	}

	public Lector getLector() {
		return lector;
	}

	public void setLector(Lector lector) {
		this.lector = lector;
	}
}
