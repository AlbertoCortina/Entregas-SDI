using sdi3_37.Cli_REST_C_Sharp.dto;
using System;
using System.Collections.Generic;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Threading.Tasks;

namespace sdi3_37.Cli_REST_C_Sharp.action
{
    class ListarCategoriasAction : Action
    {

        public ListarCategoriasAction()
        {

        }

        public void execute()
        {
            Console.WriteLine("------LISTA DE CATEGORIAS PARA EL USUARIO " + Sesion.Instance.User.login + "------");
            listar().Wait();
            Console.WriteLine("-----------------------------------------------------");
        }

        static async Task listar ()
        {
            using (var cliente = new HttpClient())
            {
                cliente.BaseAddress = new Uri(Sesion.Instance.URL);
                cliente.DefaultRequestHeaders.Accept.Clear();
                cliente.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));

                HttpResponseMessage response = await cliente.GetAsync("categorias/"+Sesion.Instance.User.id);
                if (response.IsSuccessStatusCode)
                {
                    List<Category> categorias = await response.Content.ReadAsAsync<List<Category>>();
                    print(categorias);                
                }
            }
        }

        private static void print (List<Category> categorias)
        {
            foreach(var c in categorias)
            {
                Console.WriteLine(c.name);
            }
        }
    }
}
