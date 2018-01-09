import java.io.File;

import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;

import org.graphstream.algorithm.APSP;
import org.graphstream.algorithm.APSP.APSPInfo;
import org.graphstream.algorithm.Dijkstra;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.implementations.SingleGraph;

public class FlightProject {

	public static void main(String[]args) throws IOException{

		/*Graph graph = new MultiGraph("Prueba1");
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
		//rutas.Test(graph);
		ru = rutas.CrearArrayAeropuertosOrigen();
		rudes = rutas.CrearArrayAeropuertosDestino();
		rutas.CrearAristas(graph);
		
		Dijkstra dijkstra = new Dijkstra(Dijkstra.Element.EDGE, null,null);
		
		dijkstra.init(graph);
		dijkstra.setSource(graph.getNode("PKC"));
		dijkstra.compute();
		
		for (Node node : dijkstra.getPathNodes(graph.getNode("SAL")))
			node.addAttribute("ui.style", "fill-color: blue;");
		
		for (Edge edge : dijkstra.getTreeEdges())
			edge.addAttribute("ui.style", "fill-color: red;");
		
		System.out.println(dijkstra.getPath(graph.getNode("SAL")));*/

		Ventana window = new Ventana();
		window.frame.setVisible(true);
		

		


		//graph.display().disableAutoLayout();


	}
}