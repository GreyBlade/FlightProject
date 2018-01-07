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
		String[] lineas = lector.Leer("C:/Users/Jaime/Documents/routes.dat");		


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

			//System.out.println("key -> " + key + " value -> " + value);

			//System.out.println("pair with a key: " + key + " with a value of: " + value);
		}

		//System.out.println("RUTAAAS " + rutas.size());
		//System.out.println("hm " + hm.size());
		return rutas;
	}

	public String[] CrearArrayAeropuertosOrigen(){
		String[] iataOrigen = hm.keySet().toArray(new String[0]);
		//System.out.println("Array origen " + iataOrigen.length);
		for (int i=0; i<iataOrigen.length; i++){
			//System.out.println("Aeropuertos de origen " + iataOrigen[i]);
		}
		//System.out.println(iataOrigen.length);
		return iataOrigen;
	}

	public String[] CrearArrayAeropuertosDestino(){
		String[] iataDestino = hm.values().toArray(new String[0]);
		//System.out.println("Array destino " + iataDestino.length);

		for (int i=0; i<iataDestino.length; i++){
			//System.out.println("Aeropuertos de destino " + iataDestino[i]);
		}
		//System.out.println(iataDestino.length);
		return iataDestino;
	}


	public ArrayList ComprobarAerosOrigen() throws IOException{
		String[] comprobacion = CrearArrayAeropuertosOrigen();
		airnormal.CrearAeropuertos(ia);
		String[] aeros = airnormal.CrearArrayAeropuertos();
		String regex = "\\d+.";
		String[] Origenes = null;
		for (int i=0;i<comprobacion.length; i++){

			//System.out.println("Full match: " + matcher.group(0));
			comprobacion[i] = comprobacion[i].replaceAll(regex,"");
			//  System.out.println(comprobacion[i]);

		}

		for (int i=0; i<comprobacion.length; i++){
			for (int j=0; j<aeros.length; j++){
				if (comprobacion[i].equals(aeros[j])){
					//System.out.println("Se encontro aeropuerto origen existente -> " + comprobacion[i]);
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
					//System.out.println("Se encontro aeropuerto destino existente -> " + destino[i]);
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
				
				grafo.addEdge(newKeys + " " + iataDestino, newKeys, iataDestino);
			}

		}
		System.out.println(aas.size());

		return aas;

		/*	Iterator a = hm.entrySet().iterator();
		while (a.hasNext()){
			Map.Entry<String, String> entry = (Map.Entry<String, String>) a.next();
			String keys = entry.getKey();
			String newKeys = entry.getKey().replaceAll(regex, "");
			String values = entry.getValue();
			String iataDestino = values.replaceAll(regex, "");
			String idAerolinea = values.replaceAll(regex2,"");	

			grafo.setStrict(false);
			int contador =0;
			if (A.contains(newKeys)){
				String dest = hm.get(keys);

				if (B.contains(dest)){
					contador++;
					String c = Integer.toString(contador);
					System.out.println("RUTA ENCONTRADA " + newKeys + " -> " + dest);
					//grafo.setStrict(false);

				}
			}
		}*/


		/*Iterator entries = rutasActualizadas.entrySet().iterator();
		int a =0;
		while (entries.hasNext()){
			Map.Entry<String, String> entry =( Map.Entry<String, String>) entries.next();
			a++;
			String keys = entry.getKey();
			String newKeys = entry.getKey().replaceAll(regex, "");
			String values = entry.getValue();

			grafo.addEdge(a + " , " + keys + " , " + values, newKeys, values);
		}*/
	}

	/*public void crearRutasAristas(HashMap<String, String> rutas,HashMap<String,Airport> aeropuertos,Graph grafo) throws IOException{
		Rutas normal = new Rutas();
		airnormal = new Airport();
		airnormal.CrearAeropuertos(aeropuertos);
		normal.crearRutas(rutas);
		mapa = rutas;


		System.out.println("tama de rutas: " + rutas.size());
		String[] aero = airnormal.VerificarAeropuertos();
		//String[] origen = normal.ComprobarAeroOrigen();
		//String[] destino = normal.ComprobarAeroDestino();



		Iterator<Map.Entry<String, String>> entries = rutas.entrySet().iterator();
		while(entries.hasNext()){
			Map.Entry<String, String> entry = entries.next();

		}


		for (int i=0;i<aero.length; i++){
			for (int j=0;j<origen.length;j++){

				if (origen[j].equals(aero[i])){
					//System.out.println("Aeropuerto de origen encontrado " + origen[j]);

					//PORQUE .get devuelve el value en este caso el aeropuerto destino
					String airdestino="";
					if (rutas.containsValue(rutas.get(origen[j]))){
						airdestino = rutas.get(origen[j]);

						//System.out.println("Aeropuerto de destino " + airdestino + " para el origen " + origen[j]);


					}

					origenRutas.add(airdestino);

				}


			}
		}

		for (int k=0;k<aero.length; k++){
			for (int l = 0 ; l<destino.length; l++){
				if (destino[l].equals(aero[k])){
					//System.out.println("Aeropuerto de destino encontrado: " + destino[l] + " asd");
					destinoRutas.add(destino[l]);
				}
			}
		}

		String[] keys = origenRutas.toArray(new String[0]);
		String[] values = destinoRutas.toArray(new String[0]);
		System.out.println(keys.length);
		System.out.println(values.length);

		String origenaero;
		String destinoaero;

		for (Entry<String, String> entry : rutas.entrySet()) {
			String ori = entry.getKey();
			String desti = entry.getValue();
			//System.out.println("Conexion entre: " + ori + " con: " + desti);
		}


		ArrayList<String> LlavesOriginales = new ArrayList<String>(rutas.keySet());
		ArrayList<String> LlavesActualizadas = new ArrayList<String>(rutasActualizadas.keySet());



	}



	//	Iterator<Map.Entry<String, String>> entries = rutas.entrySet().iterator();



	public void CrearFicheroAeroOrigen() throws FileNotFoundException{

		Iterator<Map.Entry<String, String>> entries = mapa.entrySet().iterator();
		while (entries.hasNext()) {
			Map.Entry<String, String> entry = entries.next();
			try{
				String path = "C:/Users/Jaime/Documents/rutaorigen.txt";
				File file = new File(path);

				if (!file.exists()){
					file.createNewFile();
				}

				FileWriter fw = new FileWriter(file.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);

				bw.write(entry.getKey()+System.lineSeparator());
				bw.close();

			}catch(IOException e){
				e.printStackTrace();
			}

		}

	}

	public String[] ComprobarAeroOrigen() throws FileNotFoundException{


		boolean cambios=false;
		String token = "";

		Scanner inFile = new Scanner(new File("C:/Users/Jaime/Documents/rutaorigen.txt"));
		ArrayList<String> temps = new ArrayList<String>();

		while (inFile.hasNext()) {
			// find next line
			token = inFile.next();
			//System.out.println("origen " + token);
			temps.add(token);
		}
		inFile.close();

		String[] rutasorigen = temps.toArray(new String[0]);

		return rutasorigen;
	}


	public void CrearFicheroAeroDestino() throws FileNotFoundException{

		Iterator<Map.Entry<String, String>> entries = mapa.entrySet().iterator();
		while (entries.hasNext()) {
			Map.Entry<String, String> entry = entries.next();
			try{
				String path = "C:/Users/Jaime/Documents/rutadestino.txt";
				File file = new File(path);

				if (!file.exists()){
					file.createNewFile();
				}


				FileWriter fw = new FileWriter(file.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(entry.getValue()+System.lineSeparator());
				bw.close();
			}catch(IOException e){
				e.printStackTrace();
			}

		}

	}

	public String[] ComprobarAeroDestino() throws FileNotFoundException{


		boolean cambios=false;
		String token1 = "";

		Scanner inFile1 = new Scanner(new File("C:/Users/Jaime/Documents/rutadestino.txt"));
		ArrayList<String> temps1 = new ArrayList<String>();

		while (inFile1.hasNext()) {
			// find next line
			token1 = inFile1.next();
			//System.out.println("destino "  + token1);
			temps1.add(token1);
		}
		inFile1.close();

		String[] rutasdestino= temps1.toArray(new String[0]);

		return rutasdestino;
	}
	 */

}
