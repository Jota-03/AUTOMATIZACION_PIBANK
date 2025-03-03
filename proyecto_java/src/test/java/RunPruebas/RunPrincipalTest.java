package RunPruebas;

import java.io.*;
import java.util.Properties;
import org.apache.logging.log4j.*;
import org.testng.annotations.*;
import com.epam.healenium.SelfHealingDriver;
import ClasesBase.Periferia;
import PageObjects.HomePichinchaPage;
import PageObjects.HomeYopmailPage;
import Utilidades.*;
import io.qameta.allure.*;

public class RunPrincipalTest {
	Periferia periferia;
	Logger log = LogManager.getLogger(RunPrincipalTest.class.getName());
	SelfHealingDriver driver;
	static MyScreenRecorder recorder;
	static HomePichinchaPage pichinchaPage;
	static GenerarReportePdf generarPDF;
	static String rutaEvidencia;
	static String urlpichincha,urlYopmail;	
	static String analyst,rutaImagen;
	static HomeYopmailPage YopmailPage;
	int scala;

	@BeforeMethod
	public void beforeClass() throws IOException {

		// PROPERTIES
		Properties propiedades = new Properties();
		InputStream entrada = null;
		entrada = new FileInputStream("./data.properties");
		propiedades.load(entrada);
		
		rutaImagen = propiedades.getProperty("LogoCliente");
		rutaEvidencia = propiedades.getProperty("rutaEvidencia");
		analyst = propiedades.getProperty("analystNL");
		urlpichincha = propiedades.getProperty("urlpichincha");
		urlYopmail = propiedades.getProperty("urlYopmail");
		scala=Integer.parseInt(propiedades.getProperty("scala"));
		
		
		// CLASE BASE
		periferia = new Periferia(rutaEvidencia);
		GenerarReportePdf.Setter(analyst, urlpichincha, scala);
		
		// INSTANCIAR LAS CLASES DE LAS PAGINAS
		pichinchaPage = new HomePichinchaPage(driver);
		YopmailPage = new HomeYopmailPage(driver);
				
		// INSTANCIAR LA CLASE DE GENERAR PDF
		generarPDF = new GenerarReportePdf(rutaImagen);
		
	}

	@DataProvider(name = "DataPichincha")
	public Object[][] data() throws Exception {
	    try {
	    	return ExcelUtilidades.getTableArray("./Data/Data.xlsx", "pichincha");
	    } catch (Exception e) {
	        System.out.println(e);
	        log.error(e.toString());
	    }
	    return null;
	}

	@Severity(SeverityLevel.NORMAL)
	@Story("Pibank")
	@Description("inicio formulario")
	@Test(dataProvider = "DataPichincha")
	public void CasoDePrueba1215564(String generarEvidencia, String nombre) throws Exception {

		// OBTENER EL NOMBRE DEL TEST - (ScreenShot's)
		String nomTest = Thread.currentThread().getStackTrace()[1].getMethodName();
		try {
			driver = Periferia.inicializarNavegadores("Chrome");
			Periferia.Setter(generarEvidencia);
			MyScreenRecorder.Setter(generarEvidencia,nomTest);
			GenerarReportePdf.Setter(generarEvidencia,nomTest);
			// ELIMINA LAS EVIDENCIAS DE CASOS ANTERIORES
			Periferia.eliminarCarpeta();
			// CREAR CARPETA PARA ALMACENAMIENTO DE IMAGENES
			File rutaCarpeta = Periferia.crearCarpeta(nomTest);
			// IMPRIMIR RUTA PARA TEMAS DE ORQUESTADOR
			Periferia.printConsole("Ruta de la carpeta:" + rutaCarpeta);
			// INICIAR LA CREACION DE EVIDENCIAS
			GenerarReportePdf.createTemplate(rutaCarpeta);
			MyScreenRecorder.startRecording(rutaCarpeta);
			// INICIO DE LOG
			log.info("Comienza el proceso de automatización de {}", nomTest);
			// LLAMADO DE METODOS ->>
			HomePichinchaPage.urlAcceso(urlpichincha, driver);
			HomePichinchaPage.inicioSolicitud(nombre);	

			// IMPRIMIR ESTADO FINAL DEL CASOS PARA TEMAS DE ORQUESTADOR
			Periferia.printConsole("Estado del caso: Exitoso");			
		} catch (Exception e) {
			Periferia.printConsole("Estado del caso: Fallido");
			GenerarReportePdf.closeTemplate(e.toString());			
		}
	}	
	
	@Severity(SeverityLevel.NORMAL)
	@Story("Pibank")
	@Description("inicio formulario")
	@Test(dataProvider = "DataPichincha")
	public void CasoDePrueba1215671(String generarEvidencia, String nombre) throws Exception {
		// OBTENER EL NOMBRE DEL TEST - (ScreenShot's)
		String nomTest = Thread.currentThread().getStackTrace()[1].getMethodName();		
		try {
			driver = Periferia.inicializarNavegadores("Chrome");
			Periferia.Setter(generarEvidencia);
			MyScreenRecorder.Setter(generarEvidencia,nomTest);
			GenerarReportePdf.Setter(generarEvidencia,nomTest);
			// ELIMINA LAS EVIDENCIAS DE CASOS ANTERIORES
			Periferia.eliminarCarpeta();
			// CREAR CARPETA PARA ALMACENAMIENTO DE IMAGENES
			File rutaCarpeta = Periferia.crearCarpeta(nomTest);
			// IMPRIMIR RUTA PARA TEMAS DE ORQUESTADOR
			Periferia.printConsole("Ruta de la carpeta:" + rutaCarpeta);
			// INICIAR LA CREACION DE EVIDENCIAS
			GenerarReportePdf.createTemplate(rutaCarpeta);
			MyScreenRecorder.startRecording(rutaCarpeta);
			// INICIO DE LOG
			log.info("Comienza el proceso de automatización de {}", nomTest);
			Periferia.printConsole("COMIENZA EL PROCESO DE AUTOMATIZACION " + nomTest);
			// LLAMADO DE METODOS ->>
			HomeYopmailPage.urlAcceso(urlYopmail, driver);
			HomeYopmailPage.inicioCorreoTemp(nombre);				
			//IMPRIMIR ESTADO FINAL DEL CASOS PARA TEMAS DE ORQUESTADOR
			Periferia.printConsole("Estado del caso: Exitoso");
		} catch (Exception e) {
			Periferia.printConsole("Estado del caso: Fallido");
			GenerarReportePdf.closeTemplate(e.toString());
		}
	}
		

	@AfterMethod
	public void afterClass() throws Exception
	{
		// FINALIZAR GENERACION DE EVIDENCIAS
		MyScreenRecorder.stopRecording();
		GenerarReportePdf.closeTemplate();
		if (driver != null) {
			driver.quit();
		}
	}
}
