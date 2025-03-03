package PageObjects;

import com.epam.healenium.SelfHealingDriver;
import ClasesBase.Periferia;
import MapsObjects.HomePichinchaMaps;

public class HomePichinchaPage extends HomePichinchaMaps {

	public HomePichinchaPage(SelfHealingDriver driver) {
		super(driver);
	}

	// METODO INICIAL PARA PONER LA URL
	public static HomePichinchaPage urlAcceso(String url, SelfHealingDriver driver) {
		driver.get(url);
		return new HomePichinchaPage(driver);
	}

	// METODO PARA DILIGENCIAR EL LOGIN
	public static void inicioSolicitud( String nombre)
			throws Exception {
		Periferia.tiempoEspera(5);
		try {
			try {
				Periferia.click(btnContinuar,  "click en boton siguiente", 2);
				Periferia.click(btnCuentapibank,  "click en boton cuenta pibank", 2);
				Periferia.click(btnContinuar,  "click en boton continuar", 2);
				Periferia.click(btnUntitular,  "click en boton un titular", 2);
				Periferia.click(btnContinuar,  "click en boton continuar", 2);
				Periferia.click(btnCheck1,  "click en boton check", 2);
				Periferia.scrollElement(btnEntendido,  "realiza scroll", 2);
				Periferia.click(btnEntendido,  "click en boton Entendido", 2);
				Periferia.click(btnCheck2,  "click en boton check", 2);
				Periferia.click(btnEntendido,  "click en boton Entendido", 2);
				Periferia.click(btnContinuar,  "click en boton Continuar", 2);
				Periferia.sendkey(nombre, txtNombres,  "se ingresa nombre del usuario", 2);
				
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (Exception e) {
			//Periferia.printConsole("Estado del caso: Fallido");
			throw new InterruptedException();
		}

	}

}
