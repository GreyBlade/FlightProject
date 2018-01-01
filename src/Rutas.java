import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

import org.graphstream.graph.Graph;

public class Rutas {

	private int Origen;
	private int Destino;
	private Lector lector;
	private Airport airnormal;
	private Airport origen;
	private Airport destino;
	private HashMap<String,String> mapa;

	public Rutas(Airport origen, Airport destino){
		this.origen = origen;
		this.destino = destino;
	}

	public Rutas(){}


	public HashMap<String,String> crearRutas(HashMap rutas) throws IOException{
		lector = new Lector();
		airnormal = new Airport();
		String[] lineas = lector.Leer("C:/Users/Jaime/Documents/routes.dat");		


		for (int i=0; i<lineas.length; i++){
			String info = lineas[i];
			String[] split = lineas[i].split("(?<=,)");

			String airline = split[0];
			String airlineid = split[1];
			String source = split[2];
			String destination = split[4];
			String regex = "(\")|(,)";
			String origen = source.replaceAll(regex,"");
			String destino = destination.replaceAll(regex, "");
			rutas.put(origen, destino);
		}
		return rutas;
	}

	public void crearRutasAristas(HashMap<String, String> rutas,HashMap<String,Airport> aeropuertos,Graph grafo) throws IOException{
		Rutas normal = new Rutas();
		airnormal = new Airport();
		airnormal.CrearAeropuertos(aeropuertos);
		normal.crearRutas(rutas);
		mapa = rutas;


		String[] aero = airnormal.VerificarAeropuertos();
		String[] origen = normal.ComprobarAeroOrigen();
		String[] destino = normal.ComprobarAeroDestino();


		Iterator<Map.Entry<String, String>> entries = rutas.entrySet().iterator();

		for (int i=0;i<aero.length; i++){
			for (int j=0;j<origen.length;j++){
				
				if (aero[i].equals(origen[j])){
					System.out.println(" Aeropuerto origen : " + origen[j] + " Se encuentra");
					if (aero[i].equals(destino[j])){
						System.out.println("Aeropuerto destino: " + destino[j] + " Se encuentra ");
						
						grafo.addEdge(origen[j] + " , " + destino[j], origen[j], destino[j]);
					}
				}
				
				
			}
		}
	}



	//	Iterator<Map.Entry<String, String>> entries = rutas.entrySet().iterator();



	public void CrearFicheroAeroOrigen() throws FileNotFoundException{

		Rutas c = new Rutas();
		String[] comparador = c.ComprobarAeroOrigen();
		int size = comparador.length;
		Iterator<Map.Entry<String, String>> entries = mapa.entrySet().iterator();
		if (comparador.length>size){
			while (entries.hasNext()) {
				Map.Entry<String, String> entry = entries.next();
				try{
					FileWriter fw = new FileWriter("C:/Users/Jaime/Documents/rutaorigen.txt",true);
					fw.write(entry.getKey()+System.lineSeparator());
					fw.close();
				}catch(IOException e){
					e.printStackTrace();
				}

			}
		}
	}

	public String[] ComprobarAeroOrigen() throws FileNotFoundException{


		boolean cambios=false;
		String token1 = "";

		Scanner inFile1 = new Scanner(new File("C:/Users/Jaime/Documents/rutaorigen.txt"));
		ArrayList<String> temps = new ArrayList<String>();

		while (inFile1.hasNext()) {
			// find next line
			token1 = inFile1.next();
			temps.add(token1);
		}
		inFile1.close();

		String[] rutasorigen = temps.toArray(new String[0]);

		return rutasorigen;
	}


	public void CrearFicheroAeroDestino() throws FileNotFoundException{

		Rutas c = new Rutas();
		String[] comparador = c.ComprobarAeroOrigen();
		int size = comparador.length;
		Iterator<Map.Entry<String, String>> entries = mapa.entrySet().iterator();
		if (comparador.length>size){
			while (entries.hasNext()) {
				Map.Entry<String, String> entry = entries.next();
				try{
					FileWriter fw = new FileWriter("C:/Users/Jaime/Documents/rutadestino.txt",true);
					fw.write(entry.getValue()+System.lineSeparator());
					fw.close();
				}catch(IOException e){
					e.printStackTrace();
				}

			}
		}
	}

	public String[] ComprobarAeroDestino() throws FileNotFoundException{


		boolean cambios=false;
		String token1 = "";

		Scanner inFile1 = new Scanner(new File("C:/Users/Jaime/Documents/rutaorigen.txt"));
		ArrayList<String> temps = new ArrayList<String>();

		while (inFile1.hasNext()) {
			// find next line
			token1 = inFile1.next();
			temps.add(token1);
		}
		inFile1.close();

		String[] rutasorigen = temps.toArray(new String[0]);

		return rutasorigen;
	}

}
