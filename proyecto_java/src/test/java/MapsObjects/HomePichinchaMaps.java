package MapsObjects;
import org.openqa.selenium.By;
import com.epam.healenium.SelfHealingDriver;

public class HomePichinchaMaps {

	public HomePichinchaMaps(SelfHealingDriver driver) {
		super();
	}   
	
	// LOCALIZADORES PAGINA PICHINCHA
	protected static By btnContinuar = By.xpath("//*[@class='btn btn-01']");
	protected static By btnCuentapibank = By.xpath("//*[@class='type-account-001']");
	protected static By btnUntitular = By.xpath("//*[@class='account-owner-only']");
	protected static By btnCheck1 = By.xpath("(//*[@class='check-label'])[1]");
	protected static By btnEntendido = By.xpath("(//*[@class='btn btn-01'])[2]");
	protected static By btnCheck2 = By.xpath("(//*[@class='check-label'])[2]");
	protected static By txtNombres = By.xpath("//*[@id='input-name']");
	protected static By txtEmail = By.xpath("//*[@id='input-email']");
	protected static By txtmEmail1= By.xpath("//*[@id='input-repeat-email']");
	protected static By txtCelular= By.xpath("//*[@id='input-phone']");
	protected static By txtCelular1= By.xpath("//*[@id='input-repeatphone']");
	
	protected static By txtOTP= By.xpath("//*[@id='input-otp']");
	protected static By btnAceptar= By.xpath("/html/body/app-root/main/app-pichincha-container/app-pich-contact-data/app-otp-modal/p-dialog/div/div/div[2]/section/div/form/div[2]/button");
	
	
}
