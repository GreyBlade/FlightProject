import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;

import org.graphstream.graph.*;
import org.graphstream.graph.implementations.SingleGraph;
public class FlightProject {

	public static void main(String[]args) throws IOException{

		Graph graph = new SingleGraph("Prueba1");
		Airport aeropuertos = new Airport();
		Rutas rutas = new Rutas();
		HashMap<String,String> routes = new HashMap<String,String>();
		HashMap<String, Airport> airports = new HashMap<String,Airport>();
		HashMap<String, Airport> airportsnew = new HashMap<String,Airport>();
		String[] a=null;
		aeropuertos.CrearNodosAeropuertos(airports, graph);
		aeropuertos.CrearFicheroAeropuertos();
		aeropuertos.VerificarAeropuertos();
		
		rutas.crearRutasAristas(routes, airportsnew, graph);
		rutas.CrearFicheroAeroOrigen();
		rutas.ComprobarAeroOrigen();
		rutas.CrearFicheroAeroDestino();
		rutas.CrearFicheroAeroDestino();
		graph.display();
		
		
		/*
		graph.addNode("A");
		graph.addNode("B");
		graph.addNode("C");
		graph.addEdge("AB", "A", "B");
		graph.addEdge("BC", "B", "C");
		graph.addEdge("CA", "C", "A");

		graph.display();
		 */

	}
}