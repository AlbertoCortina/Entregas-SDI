using sdi3_37.Cli_REST_C_Sharp.dto;
using sdi3_37.Cli_REST_C_Sharp.util;
using System;
using System.Collections.Generic;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Threading.Tasks;

namespace sdi3_37.Cli_REST_C_Sharp.action
{
    class TareasCategoriaAction : Action
    {
        private static Category category;
        private static String json;       

        public TareasCategoriaAction()
        {

        }

        public void execute()
        {
            Console.WriteLine("------TAREAS PENDIENTES Y RETRASADAS DE UNA CATEGORIA------");

            //Pedir datos
            Console.Write("Nombre de la categoria: ");
            var categoryName = Console.ReadLine();
            Console.WriteLine();

            //Comprobamos que exista la categoria para nuestro usuario
            comprobarCategoria(categoryName).Wait();

            if(category != null)
            {              
                listarTareas(category.name).Wait();
                print(Util.parsearTareas(json));                
            }
            else
            {
                Console.WriteLine("\tNo existe la categoría introducida");
            }
            Console.WriteLine("-----------------------------------------------------------");
        }

        static async Task comprobarCategoria (String categoryName)
        {
            using (var cliente = new HttpClient())
            {
                cliente.BaseAddress = new Uri(Sesion.Instance.URL);
                cliente.DefaultRequestHeaders.Accept.Clear();
                cliente.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));

                HttpResponseMessage response = await cliente.GetAsync("buscarCategoria/id="+Sesion.Instance.User.id+"&&categoria="+categoryName);
                if (response.IsSuccessStatusCode)
                {
                    Category categoria = await response.Content.ReadAsAsync<Category>();
                    category = categoria;
                }
            }
        }

        static async Task listarTareas (String categoryName)
        {
            using (var cliente = new HttpClient())
            {
                cliente.BaseAddress = new Uri(Sesion.Instance.URL);
                cliente.DefaultRequestHeaders.Accept.Clear();
                cliente.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));

                HttpResponseMessage response = await cliente.GetAsync("tareas/id=" + Sesion.Instance.User.id + "&&categoria=" + categoryName);
                if (response.IsSuccessStatusCode)
                {
                    json = await response.Content.ReadAsStringAsync();                 
                }
            }
        }

        private static void print (List<Tarea> tareas)
        {
            Console.WriteLine(String.Format("{0,-20}{1,-20}{2,-20}{3,-25}{4,-20}", "ID", "TITULO", "FECHA_CREACION", "FECHA_FINALIZACION", "FECHA_PLANEADA"));

            foreach(Tarea t in tareas) {
                Console.Write(String.Format("{0,-20}", t.id));
                Console.Write(String.Format("{0,-20}", t.title));
                Console.Write(String.Format("{0,-20}", t.created.ToString("d")));
                Console.Write(String.Format("{0,-25}", ""));
                Console.WriteLine(String.Format("{0,-20}", t.planned.ToString("d")));
            }
        }
    }
}
