package PageObjects;

import com.epam.healenium.SelfHealingDriver;
import ClasesBase.Periferia;
import MapsObjects.HomeYopmailMaps;

public class HomeYopmailPage extends HomeYopmailMaps {

	public HomeYopmailPage(SelfHealingDriver driver) {
		super(driver);
	}

	// METODO INICIAL PARA PONER LA URL
	public static HomeYopmailPage urlAcceso(String url, SelfHealingDriver driver) {
		driver.get(url);
		return new HomeYopmailPage(driver);
	}

	// METODO PARA UN NUEVO CORREO
	public static void inicioCorreoTemp(String nombre)
			throws Exception {
		Periferia.tiempoEspera(5);
		try {
			try {
				
				Periferia.click(btnNuevo,  "click en boton Nuevo", 2);
				Periferia.click(btnRevisarCorreo,  "click en boton revisar correo", 2);
				Periferia.click(btnMensajeNuevo,  "click en boton mensaje nuevo", 4);
				Periferia.tabulacion(5);
				Periferia.sendkey(nombre, txtCorreo, "se ingresa nombre del usuario", 2);
				
				
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
