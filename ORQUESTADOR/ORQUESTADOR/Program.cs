using System;
using System.IO;
using System.Diagnostics;
using System.IO.Compression;
using System.Text;
using System.Net.Mail;
using System.Net;


namespace Orquestador
{
    public class Programa
    {
        string? rutaProyecto; //DECLARANDO VARIABLE DE LA RUTA DEL PROYECTO
        string? RutaEvidencia;
        static string? rutaComprimido; //DECLARANDO LAS VARIABLES PUBLIC STATIC PARA QUE PUEDAN SER LLAMADAS DESDE CUALQUIER METODO
        string? estadoCaso;// DECLARANDO VARIABLE QUE CONTIENE EL ESTADO DEL CASO
        bool estado;// DECLARANDO LA VARIABLE QUE CONTIENE EL ESTADO DEL CASO EN TIPO BOOLEANO

        public static void Main(string[] args)
        {
            // ACTUALMENTE, ESTE MÉTODO ESTÁ VACÍO PORQUE LA LÓGICA DE LA APLICACIÓN AÚN NO SE HA DEFINIDO.
            // PUEDE AGREGAR CÓDIGO AQUÍ PARA INICIALIZAR LA APLICACIÓN, PROCESAR ARGUMENTOS O LLAMAR A OTROS MÉTODOS.

        }


        //INICIALIZA EL PROCESO DE EJECUCION
        public static bool Ejecutar(string Test, string rutas, string casoID)
        {
            Programa myProcess = new Programa();
            bool estadoEjecucion = myProcess.OpenWithStartInfo(Test, rutas, casoID);
            return estadoEjecucion;
        }

        //ESTE METODO SE ENCARGA DE EJECUTAR EL COMANDO MVN EN LA RAIZ DEL PROGRAMA EN JAVA
        public bool OpenWithStartInfo(string Test, string rutas, string casoID)
        {
            string? resultConsole;
            try
            {
                rutaProyecto = @rutas;

                // Configuración del proceso
                Process process = new Process();
                ProcessStartInfo startInfo = ObtenerInformacionProceso(rutaProyecto);
                startInfo.WindowStyle = ProcessWindowStyle.Maximized;
                startInfo.RedirectStandardInput = true;
                startInfo.RedirectStandardOutput = true;
                startInfo.UseShellExecute = false;
                startInfo.CreateNoWindow = true;

                process.StartInfo = startInfo;

                // Configuración de la lectura asíncrona de la salida estándar
                StringBuilder outputData = new StringBuilder();
                process.OutputDataReceived += (sender, e) =>
                {
                    if (!String.IsNullOrEmpty(e.Data))
                    {
                        outputData.AppendLine(e.Data);
                    }
                };

                // Iniciar el proceso
                process.Start();

                // Iniciar la lectura asíncrona de la salida estándar
                process.BeginOutputReadLine();

                // Enviar datos de entrada (si es necesario)
                process.StandardInput.WriteLine(Test);
                process.StandardInput.Flush();
                process.StandardInput.Close();

                // Esperar a que el proceso termine
                process.WaitForExit();

                // Esperar a que se complete la lectura asíncrona de la salida estándar
                process.WaitForExit();

                // Obtener la salida completa
                resultConsole = outputData.ToString();
                extraerResultado("Estado del caso:", resultConsole);//SE LLAMA EL METODO DE EXTRACCION DEL REDULTADO DE EJECUCION, ENVIANDO EL STRING DESDE DONDE SE QUIERE INCIAR ESTA EXTRACCION
                ExtraerRutaCarpeta("Ruta de la carpeta:", resultConsole);//SE LLAMA EL METODO DE EXTRACCION DEL RUTA DONDE SE ENCUENTRA LA EVIDENCIA, ENVIANDO EL STRING DESDE DONDE SE QUIERE INCIAR ESTA EXTRACCION
                if (estadoCaso!.Contains("Exitoso"))// CONDICIONAL PARA ASIGNAR EL ESTADO DEL CASO
                {
                    estado = true;//SE ASIGNA EL ESTADO DEL CASO A LA VARIABLE ESTADO DE TIPO BOOLEANO
                    rutaComprimido = Comprimir(RutaEvidencia!, casoID);//SE ESTABLECE LA RUTA Y NOMBRE DEL ARCHIVO PARA LAS EVIDENCIAS
                }
                else
                {
                    estado = false;//SE ASIGNA EL ESTADO DEL CASO A LA VARIABLE ESTADO DE TIPO BOOLEANO
                    rutaComprimido = Comprimir(RutaEvidencia!, casoID);//SE ESTABLECE LA RUTA Y NOMBRE DEL ARCHIVO PARA LAS EVIDENCIAS
                }
            }
            catch (Exception i)
            {
                //EN CASO DE NO HABER UN MENSAJE EN CONSOLA QUE NOS DE EL ESTADO DEL CASO, SE EMITE COMO FALLIDO
                Console.WriteLine("IMPRIMIR EL ESTADO " + estado); //SE IMPRIME POR CONSOLA EL ESTADO DEL CASO
                Console.Write(i);//SE ESCRIBE POR CONSOLA LA EXCEPCION
                estado = false;////SE ASIGNA EL ESTADO DEL CASO A LA VARIABLE ESTADO DE TIPO BOOLEANO
            }

            return estado;//SE RETORNA EL ESTADO DEL CASO DE PRUEBA
        }

        //METODO PARA INICIAR LA CONSOLA
        public static ProcessStartInfo ObtenerInformacionProceso(string directorioTrabajo)
        {
            ProcessStartInfo startInfo = new ProcessStartInfo
            {
                WorkingDirectory = directorioTrabajo,
                FileName = "cmd.exe"
            };
            return startInfo;
        }

        //ESTE METODO SE ENCARGA DE EXTRAER UN SEGMENTO DEL TEXTO DEL RESULTADO DE LA EJECUCION POR CONSOLA
        public void extraerResultado(string palabraInicio, string texto)
        {
            string fraseCompleta = "";//INICIALIZANDO LA VARIABLE QUE GUARDA LA FRASE COMPLETA
            int indexPrimera = texto.IndexOf(palabraInicio);
            fraseCompleta = texto.Substring(indexPrimera);
            int startIndex = 17; //SE SELECCIONA LA POSICION INICIAL DEL TEXTO EXTRAIDO
            int length = 7;  //SE SELECCIONA EL TAMAÑO EN CARACTERES DE LA PALABRA QUE SE DESEA CAPTURAR
            estadoCaso = fraseCompleta.Substring(startIndex, length);
        }

        //ESTE METODO SE ENCARGA DE EXTRAER EL SEGMENTO DEL RESULTADO DE LA EJECUCION POR CONSOLA
        public void ExtraerRutaCarpeta(string palabraInicio, string texto)
        {
            string fraseCompleta = "";//INICIALIZANDO LA VARIABLE QUE GUARDA LA FRASE COMPLETA
            int indexPrimera = texto.IndexOf(palabraInicio);
            fraseCompleta = texto.Substring(indexPrimera);
            int startIndex = 20; //SE SELECCIONA LA POSICION INICIAL DEL TEXTO EXTRAIDO
            int length = 11;  //SE SELECCIONA EL TAMAÑO EN CARACTERES DE LA PALABRA QUE SE DESEA CAPTURAR
            RutaEvidencia = fraseCompleta.Substring(startIndex, length);
            Console.WriteLine(RutaEvidencia);

        }




        //METODO ENCARGADO DE ENVIAR LA RUTA DE LAS EVIDENCIAS EL 
        public static string ruta()
        {
            return rutaComprimido!;
        }

        public string Comprimir(string ruta, string casoId)
        {
            if (!ruta.Equals("\\evidencias"))
            {
                DateTime fechaActual = DateTime.Now;
                string fecha = fechaActual.ToString("-yyyyMMdd-HHmmss");
                RutaEvidencia = rutaProyecto + ruta;
                string startPath = @RutaEvidencia;//RUTA DE LA CARPETA DONDE ESTAN LAS EVIDENCIAS
                string rutaC = RutaEvidencia+ @"orquestador\CasoDePrueba" +  casoId + fecha + ".zip";//RUTA DONDE SE VA A GUARDAR EL ARCHIVO .ZIP CON LAS EVIDENCIAS
                //string startPath1  = rutaProyecto+ @"\outPutDataOrquestador\";
                string zipPath = @rutaC;//RUTA DEL .ZIP
                Console.WriteLine(zipPath);
                System.Threading.Thread.Sleep(2000);//TIEMPO DE ESPERA 
                ZipFile.CreateFromDirectory(startPath, zipPath);//CREAMOS LA CARPETA .ZIP DONDE SE ENCUENTRAN LAS EVIDENCIAS
                return zipPath; //RETORNA LA RUTA DEL .ZIP CREADO
            }
            else
            {
                return "SinEvidencia";
            }
        }

        public void EnviarCorreo(string destinatario, string asunto, string cuerpo)
        {
            // Configuración del servidor SMTP
            string servidorSMTP = "smtp.tu-servidor.com"; // Cambia esto por tu servidor SMTP
            int puertoSMTP = 587; // Cambia esto según la configuración de tu servidor
            string usuario = "tu-correo@ejemplo.com"; // Cambia esto por tu correo
            string contraseña = "tu-contraseña"; // Cambia esto por tu contraseña

            try
            {
                // Crear el mensaje
                MailMessage mensaje = new MailMessage();
                mensaje.From = new MailAddress(usuario);
                mensaje.To.Add(destinatario);
                mensaje.Subject = asunto;
                mensaje.Body = cuerpo;
                mensaje.IsBodyHtml = true; // Si deseas enviar HTML, cámbialo a true

                // Configurar el cliente SMTP
                using (SmtpClient cliente = new SmtpClient(servidorSMTP, puertoSMTP))
                {
                    cliente.Credentials = new NetworkCredential(usuario, contraseña);
                    cliente.EnableSsl = true; // Cambia esto según tu servidor SMTP

                    // Enviar el correo
                    cliente.Send(mensaje);
                }
            }
            catch (Exception ex)
            {
                // Manejar la excepción
                Console.WriteLine($"Error al enviar el correo: {ex.Message}");
            }
        }


    }
}

