import java.io.File;

import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;

import org.graphstream.algorithm.Dijkstra;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.implementations.SingleGraph;

public class FlightProject {

	public static void main(String[]args) throws IOException{

		Graph graph = new MultiGraph("Prueba1");
		graph.setStrict(false);
		graph.setAutoCreate(true);

		Airport aeropuertos = new Airport();
		Rutas rutas = new Rutas();
		HashMap<String,String> routes = new HashMap<String,String>();
		HashMap<String, Airport> airports = new HashMap<String,Airport>();
		HashMap<String, Airport> airportsnew = new HashMap<String,Airport>();
		String[] a=null;
		String[] ru =null;
		String[] rudes = null;
		
		
		aeropuertos.CrearAeropuertos(airports);
		a = aeropuertos.CrearArrayAeropuertos();
		aeropuertos.CrearNodosAeropuertos(graph);
		rutas.crearRutas(routes);
		ru = rutas.CrearArrayAeropuertosOrigen();
		rudes = rutas.CrearArrayAeropuertosDestino();
		rutas.CrearAristas(graph);
		graph.display().disableAutoLayout();
		Dijkstra dijkstra = new Dijkstra(Dijkstra.Element.EDGE, null, "length");
		dijkstra.init(graph);
		dijkstra.setSource(graph.getNode("SFO"));
		dijkstra.compute();


	}
}