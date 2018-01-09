import java.awt.Component;
import java.awt.EventQueue;
import java.awt.List;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.Caret;

import org.graphstream.algorithm.Dijkstra;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.Path;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.ui.swingViewer.View;
import org.graphstream.ui.swingViewer.Viewer;

import javax.swing.JPanel;
import javax.swing.JInternalFrame;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Ventana {

	JFrame frame;
	private JTextField textField;
	private Graph grafo;
	JComboBox aeroOrigen = new JComboBox();
	JComboBox aeroDestino = new JComboBox();
	private HashMap<String,Airport> hm = new HashMap<String, Airport>();
	private ArrayList<String> aeropuertos = new ArrayList<String>();
	private HashMap<String, ArrayList> paises = new HashMap<String, ArrayList>();
	private String aa="";
	private String b="";
	private Dijkstra dij = new Dijkstra(Dijkstra.Element.EDGE, null,null);
	private ArrayList<Node> list1 = new ArrayList<Node>();
	private String ax = "";
	private String bx = "";
	


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ventana window = new Ventana();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws IOException 
	 */
	public Ventana() throws IOException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws IOException 
	 */
	private void initialize() throws IOException {
		frame = new JFrame();
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		Graph graph = new MultiGraph("Prueba1");
		graph.setStrict(false);
		graph.setAutoCreate(true);
		//graph.addAttribute("ui.stylesheet", "graph { fill-color: blue; }");


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
		hm = airports;
		//rutas.Test(graph);
		ru = rutas.CrearArrayAeropuertosOrigen();
		rudes = rutas.CrearArrayAeropuertosDestino();
		rutas.CrearAristas(graph);

		Viewer viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
		View view = viewer.addDefaultView(false);   // false indicates "no JFrame".





		aeroOrigen.setBounds(12, 32, 129, 28);
		frame.getContentPane().add(aeroOrigen);

		aeroDestino.setBounds(159, 32, 129, 28);
		frame.getContentPane().add(aeroDestino);

		AgregarPaises();

		JLabel lblAeropuertoDeOrigen = new JLabel("Aeropuerto de Origen");
		lblAeropuertoDeOrigen.setBounds(12, 10, 119, 14);
		frame.getContentPane().add(lblAeropuertoDeOrigen);

		JLabel lblAeropuertoDeDestino = new JLabel("Aeropuerto de Destino");
		lblAeropuertoDeDestino.setBounds(159, 10, 119, 14);
		frame.getContentPane().add(lblAeropuertoDeDestino);

		textField = new JTextField();
		textField.setBounds(436, 32, 346, 25);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		JInternalFrame internalFrame = new JInternalFrame("Mapa Mundial");
		internalFrame.setBounds(10, 133, 1350, 626);
		frame.getContentPane().add(internalFrame);
		internalFrame.getContentPane().add(view);

		JButton btnEncontrarRutas = new JButton("Encuentra ruta mas corta");
		btnEncontrarRutas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				if (list1.size()>0){
					Node a;
					for (int i=0;i<list1.size();i++){
						a = list1.get(i);
						String id = a.getId();
						graph.getNode(id).addAttribute("ui.style", "fill-color: black;");
					}
					
					list1.clear();
				}
			
				
				
				
				String regex = "\\s\\w+";
				String origen = String.valueOf(aeroOrigen.getSelectedItem()).replaceAll(regex,"");
				String destino = String.valueOf(aeroDestino.getSelectedItem()).replaceAll(regex,"");
				
				
				
				
		

				
				b=destino;
				Dijkstra dijkstra = new Dijkstra(Dijkstra.Element.EDGE, null,null);
				
				dijkstra.init(graph);
				dijkstra.setSource(graph.getNode(origen));
				dijkstra.compute();

				for (Node node : dijkstra.getPathNodes(graph.getNode(destino)))
					node.addAttribute("ui.style", "fill-color: blue;");
					
				for (Node node : dijkstra.getPathNodes(graph.getNode(destino)))
					list1.add(0, node);
				
					



				System.out.println(dijkstra.getPath(graph.getNode(destino)));
				Path a = dijkstra.getPath(graph.getNode(destino));
				String b = a.toString();
				dij = dijkstra;

				if (b.equals("[]")){
					textField.setText("No se encontro una ruta");
				}
				else{
					textField.setText(b);
				}
			}
		});
		btnEncontrarRutas.setBounds(66, 84, 200, 28);
		frame.getContentPane().add(btnEncontrarRutas);
		internalFrame.setVisible(true);


	}


	public void AgregarPaises(){

		Iterator it = hm.entrySet().iterator();
		Airport air = new Airport();
		while (it.hasNext()){
			Map.Entry<String, Airport> entry = (Map.Entry<String, Airport>) it.next();
			String key = entry.getKey();
			air = entry.getValue();
			String pais = air.getPais();
			aeroOrigen.addItem(key + " " + pais);
			aeroDestino.addItem(key + " " + pais);


			//paises.put(pais, null);
		}
	}
}
