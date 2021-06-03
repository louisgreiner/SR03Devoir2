package projet2;

import java.sql.Connection; 
import java.sql.DriverManager; 
import java.util.Properties;
import java.io.IOException;
import java.sql.SQLException;
import java.net.URL;
import java.io.BufferedInputStream;
import java.io.FileInputStream;

public class ConfigConnection {

	public static Connection getConnection(String nomFichierProp) throws IOException, ClassNotFoundException, SQLException {
		FileInputStream fis=new FileInputStream(nomFichierProp); 
		Properties props = new Properties();
		System.out.println("url : " + nomFichierProp);

		try {
			props.load(fis);
			String driver = props.getProperty("driver");
			String url = props.getProperty("url");
			String utilisateur = props.getProperty("utilisateur");
			String mdp = props.getProperty("mdp");
			Class.forName(driver);
			return DriverManager.getConnection(url, utilisateur, mdp);
		} finally {
			if (fis != null) {
				fis.close();
			}
		}
	}
}