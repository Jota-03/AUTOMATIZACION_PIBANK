package MapsObjects;
import org.openqa.selenium.By;
import com.epam.healenium.SelfHealingDriver;

public class HomeYopmailMaps {

	public HomeYopmailMaps(SelfHealingDriver driver) {
		super();
	}   
	
	// LOCALIZADORES PAGINA YOPMAIL
	protected static By btnNuevo = By.xpath("//*[@onclick=\'newgen();\'][1]");
	protected static By btnRevisarCorreo = By.xpath("//*[@class=\'md but text f24 egenbut\'][2]");
	protected static By btnMensajeNuevo = By.xpath("//*[@id=\'newmail\']");
	protected static By btnCorreo = By.xpath("//*[@id='msgbody']");
	protected static By txtCorreo = By.xpath("//*[@id='msgbody']");
		
}
