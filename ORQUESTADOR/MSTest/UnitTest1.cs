//15/10/2024
//VERSION 4.0
//AJUSTES CARGUE DE EVIDENCIAS

using Microsoft.VisualStudio.TestTools.UnitTesting;
using System;

namespace Orquestador
{
    [TestClass]
    public class UnitTest1
    {
        private TestContext? testContextInstance;
        // 1-LOCAL    2-REMOTO
        int? inicializar = 1;
        string? casoIDs = "1215564"; // Valor por defecto

        public TestContext TestContext
        {
            get
            {
                return testContextInstance!;
            }
            set
            {
                testContextInstance = value;
            }
        }

        [TestInitialize()]
        public void TestInitialize()
        {
            if (inicializar == 2)
            {
                // Obtener los casoIDs desde las propiedades de TestContext (__Tfs_TestCaseId__)
                casoIDs = this.TestContext.Properties["__Tfs_TestCaseId__"]?.ToString() ?? "ValorPorDefecto";
            }
        }

        [TestMethod]
        public void inicioDeSesion()
        {
            string ruta = @"D:\AUTOMATIZACION_PIBANK\proyecto_java";

            // Dividir los casoIDs en un array si hay múltiples casos
            string[] ids = casoIDs!.Split(',');

            // Ejecutar cada casoID uno por uno
            foreach (string casoID in ids)
            {
               bool estado = Programa.Ejecutar($"mvn clean test -Dtest=RunPrincipalTest#CasoDePrueba{casoID}", ruta, casoID);
                //Thread.Sleep(5000);
                // Si la prueba falla, intentar nuevamente
                if (!estado)
                {
                    estado = Programa.Ejecutar($"mvn clean test -Dtest=RunPrincipal#CasoDePrueba{casoID}", ruta, casoID);
                }

                // Extraer la ruta del archivo .ZIP de evidencia
                string rutaEvidencia = Programa.ruta();
               
                // Verificar si se generó evidencia y agregarla al contexto del test
                if (!rutaEvidencia.Equals("SinEvidencia"))
                {
                    TestContext.AddResultFile(rutaEvidencia);
                }
                // Validar si la prueba fue exitosa o fallida
                Assert.AreEqual(true, estado, $"El caso de prueba con ID {casoID} falló.");
            }

        }
    }
}
