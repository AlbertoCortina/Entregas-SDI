using sdi3_37.Cli_REST_C_Sharp.dto;
using sdi3_37.Cli_REST_C_Sharp.util;
using System;
using System.Collections.Generic;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Text;
using System.Threading.Tasks;

namespace sdi3_37.Cli_REST_C_Sharp.action
{
    class MarcarTareaFinalizadaAction : Action
    {
        private static String json;

        public MarcarTareaFinalizadaAction ()
        {

        }

        public void execute()
        {
            Console.WriteLine("------MARCAR TAREA COMO FINALIZADA------");

            //Pedir datos
            Console.Write("Id de la tarea que desea finalizar: ");
            var tareaIdEntrada = Console.ReadLine();

            long tareaId;
            if(long.TryParse(tareaIdEntrada, out tareaId))
            {
                buscarTarea(tareaId).Wait();
                List<Tarea> tarea = Util.parsearTareas(json);

                if(tarea[0] != null && tarea[0].userId == Sesion.Instance.User.id)
                {
                    if(tarea[0].finished == new DateTime(1970, 1, 1))
                    {
                        finalizarTarea(tarea[0]).Wait();
                    }
                    else
                    {
                        Console.WriteLine("\tLa tarea con id " + tareaId + " ya estaba finalizada");
                    }
                }
                else
                {
                    Console.WriteLine("\tNo existe la tarea con id " + tareaId);
                }
            }
            else
            {
                Console.WriteLine("\tId introducido inválido");
            }

            Console.WriteLine("----------------------------------------");
        }

        static async Task buscarTarea (long tareaId)
        {
            using (var cliente = new HttpClient())
            {
                cliente.BaseAddress = new Uri(Sesion.Instance.URL);
                cliente.DefaultRequestHeaders.Accept.Clear();
                cliente.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));

                HttpResponseMessage response = await cliente.GetAsync("buscarTarea/id="+tareaId);
                if (response.IsSuccessStatusCode)
                {
                    json = await response.Content.ReadAsStringAsync();                    
                }
            }
        } 

        static async Task finalizarTarea (Tarea tarea)
        {
            using (var cliente = new HttpClient())
            {
                cliente.BaseAddress = new Uri(Sesion.Instance.URL);
                cliente.DefaultRequestHeaders.Accept.Clear();
                cliente.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));

                var json = Util.parsearJson(tarea); 
                var stringContent = new StringContent(json, Encoding.UTF8, "application/json");
                HttpResponseMessage response = await cliente.PutAsync("finalizarTarea", stringContent);
                if (response.IsSuccessStatusCode)
                {
                    Console.WriteLine("\tTarea con id " + tarea.id + " marcada como finalizada correctamente");
                }
                else
                {
                    Console.WriteLine("\tHubo algún problema al marcar como finalizada la tarea con id " + tarea.id);
                }
            }
        }
    }
}
