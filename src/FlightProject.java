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

		Ventana window = new Ventana();
		window.frame.setVisible(true);
	
	}
}