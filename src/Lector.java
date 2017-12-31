import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Lector {
	private String path;
	
	public Lector(){
		
	}
	
	
	public String[] Leer(String path) throws IOException{
		String[] lines = Files.readAllLines(new File(path).toPath()).toArray(new String[0]);

		
		return lines;
	}
}
