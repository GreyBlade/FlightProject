import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.graphstream.graph.Graph;

public class Rutas {

	private int Origen;
	private int Destino;
	private Lector lector;
	private Airport airnormal = new Airport();
	private Airport origen;
	private Airport destino;
	private HashMap<String,Airport> ia = new HashMap<String,Airport>();
	private HashMap<String,String> hm = new HashMap<String,String>();
	private ArrayList<String> ao = new ArrayList<String>();
	private ArrayList<String> ad = new ArrayList<String>();
	private HashMap<String,String> rutasActualizadas = new HashMap<String,String>();
	private HashMap<String,String> rutasAerolineas = new HashMap<String,String>();
	private HashMap<String,String> aas = new HashMap<String,String>();

	public Rutas(Airport origen, Airport destino){
		this.origen = origen;
		this.destino = destino;
	}

	public Rutas(){}


	public HashMap<String,String> crearRutas(HashMap rutas) throws IOException{
		lector = new Lector();
		airnormal = new Airport();
		String[] lineas = lector.Leer("assets/routes.dat");		


		for (int i=0; i<lineas.length; i++){
			String info = lineas[i];
			String[] split = lineas[i].split("(?<=,)");

			String airline = split[0];
			String airlineid = split[1];
			String source = split[2];
			String destination = split[4];
			String regex = "(\")|(,)";
			String id = airlineid.replaceAll(regex,"");
			String origen = source.replaceAll(regex,"");
			String destino = destination.replaceAll(regex, "");
			rutas.put(i + " " + origen, destino);

			rutasAerolineas.put(i + " " + origen, destino + " " + id);
		}
		hm = rutas;

		Iterator it = rutasAerolineas.entrySet().iterator();
		while (it.hasNext()){
			Map.Entry<String, String> entry = (Map.Entry<String, String>) it.next();
			String key = entry.getKey();
			String value = entry.getValue();

		}


		return rutas;
	}

	public String[] CrearArrayAeropuertosOrigen(){
		String[] iataOrigen = hm.keySet().toArray(new String[0]);
		for (int i=0; i<iataOrigen.length; i++){
		}
		return iataOrigen;
	}

	public String[] CrearArrayAeropuertosDestino(){
		String[] iataDestino = hm.values().toArray(new String[0]);

		for (int i=0; i<iataDestino.length; i++){
		}
		return iataDestino;
	}


	public ArrayList ComprobarAerosOrigen() throws IOException{
		String[] comprobacion = CrearArrayAeropuertosOrigen();
		airnormal.CrearAeropuertos(ia);
		String[] aeros = airnormal.CrearArrayAeropuertos();
		String regex = "\\d+.";
		String[] Origenes = null;
		for (int i=0;i<comprobacion.length; i++){

			comprobacion[i] = comprobacion[i].replaceAll(regex,"");

		}

		for (int i=0; i<comprobacion.length; i++){
			for (int j=0; j<aeros.length; j++){
				if (comprobacion[i].equals(aeros[j])){
					ao.add(comprobacion[i]);
				}
			}
		}

		System.out.println("Size aero origen " + ao.size());
		return ao;
	}


	public ArrayList ComprobarAerosDestino() throws IOException{
		String[] destino = CrearArrayAeropuertosDestino();

		airnormal.CrearAeropuertos(ia);
		String[] aeros = airnormal.CrearArrayAeropuertos();
		String[] Destinos=null;
		for (int i=0; i<destino.length; i++){
			for (int j=0; j<aeros.length; j++){
				if (destino[i].equals(aeros[j])){
					ad.add(destino[i]);
				}
			}
		}
		System.out.println("Size aero destino : " + ad.size());
		return  ad;
	}
	public HashMap<String,String> CrearAristas(Graph grafo) throws IOException{
		ArrayList<String> AeropuertosOrigen = new ArrayList<String>(ComprobarAerosOrigen());
		ArrayList<String> AeropuertosDestino = new ArrayList<String> (ComprobarAerosDestino());
		ArrayList<String> A = new ArrayList<String>();
		ArrayList<String> B = new ArrayList<String>();
		String regex = "\\d+.";
		String regex2 = "\\D";
		airnormal.CrearAeropuertos(ia);
		String[] aeros = airnormal.CrearArrayAeropuertos();


		Iterator it = hm.entrySet().iterator();
		while (it.hasNext()){
			Map.Entry<String, String> entry = (Map.Entry<String, String>) it.next();
			String keys = entry.getKey();
			String newKeys = entry.getKey().replaceAll(regex, "");
			String values = entry.getValue();
			String iataDestino = values.replaceAll(regex, "");
			String idAerolinea = values.replaceAll(regex2,"");	
			//	System.out.println("keys -> " + newKeys);

			for (int i=0; i<aeros.length;i++){
				if (newKeys.equals(aeros[i])){
					System.out.println("Se encuentra el aeropuerto -> " + aeros[i] + " conectado a -> " + iataDestino);
					A.add(aeros[i]);
				}
			}

			for (int i=0; i< aeros.length;i++){
				if (iataDestino.equals(aeros[i])){
					System.out.println("AERO DESTINO : " + iataDestino);
					B.add(iataDestino);
				}
			}



		}
		int contador = 0;
		Iterator a = hm.entrySet().iterator();
		while (a.hasNext()){
			Map.Entry<String, String> entry = (Map.Entry<String, String>) a.next();
			String keys = entry.getKey();
			String newKeys = entry.getKey().replaceAll(regex, "");
			String values = entry.getValue();
			String iataDestino = values.replaceAll(regex, "");
			String idAerolinea = values.replaceAll(regex2,"");	

			if (A.contains(newKeys)&&(B.contains(iataDestino))){
				String icao = ia.get(newKeys).getIATA();
				
				grafo.addEdge(newKeys + " " + iataDestino, newKeys, iataDestino).addAttribute("ui.style", "fill-color: yellow;");;
			}

		}
		System.out.println(aas.size());

		return aas;	
	}
}
