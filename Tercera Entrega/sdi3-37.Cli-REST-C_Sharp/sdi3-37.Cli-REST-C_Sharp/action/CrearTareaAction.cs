using sdi3_37.Cli_REST_C_Sharp.dto;
using sdi3_37.Cli_REST_C_Sharp.util;
using System;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Text;
using System.Threading.Tasks;

namespace sdi3_37.Cli_REST_C_Sharp.action
{
    class CrearTareaAction : Action
    {

        public CrearTareaAction ()
        {

        }

        public void execute()
        {
            Console.WriteLine("------CREAR TAREA------");

            //Pedir datos 
            Console.Write("Nombre de la tarea: ");
            var nombre = Console.ReadLine();

            Console.Write("Comentario de la tarea: ");
            var comentario = Console.ReadLine();
            comentario = comentario.Replace(":", "_");

            Console.Write("Fecha planeada(yyyy-mm-dd): ");
            var fecha = Console.ReadLine();           

            //Creamos la tarea con los datos
            Tarea t = new Tarea();
            t.userId = Sesion.Instance.User.id;
            t.title = nombre;
            t.comments = comentario;
            t.created = DateTime.Today;
            try
            {
                t.planned = Convert.ToDateTime(fecha);

                if(DateTime.Compare(t.planned,t.created) >= 0)
                {
                    insertarTarea(t).Wait();                   
                }
                else
                {
                    Console.WriteLine("\tFecha planeada menor que fecha actual, no se crea la tarea");
                }
            }
            catch (Exception e)
            {
                Console.WriteLine("\tFormato de fecha introducido incorrecto");
            }

            Console.WriteLine("-----------------------");
        }

        static async Task insertarTarea (Tarea tarea)
        {
            using (var cliente = new HttpClient())
            {
                cliente.BaseAddress = new Uri(Sesion.Instance.URL);
                cliente.DefaultRequestHeaders.Accept.Clear();
                cliente.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));

                var json = Util.parsearJson(tarea);
                var stringContent = new StringContent(json, Encoding.UTF8, "application/json");
                HttpResponseMessage response = await cliente.PostAsync("crearTarea", stringContent);
                if (response.IsSuccessStatusCode)
                {
                    Console.WriteLine("\tSe ha creada la tarea correctamente");
                }
                else
                {
                    Console.WriteLine("\tHubo algún problema creando la tarea");
                }
            }
        }
    }
}
