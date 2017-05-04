using sdi3_37.Cli_REST_C_Sharp.dto;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Text;
using System.Threading.Tasks;

namespace sdi3_37.Cli_REST_C_Sharp
{
    class Login
    {
        static void Main(string[] args)
        {
            //Pedir datos
            Console.Write("Usuario: ");
            var username = Console.ReadLine();

            Console.Write("Contraseña: ");
            var password = Console.ReadLine();

            login(username, password).Wait();           

            if (Sesion.Instance.User != null)
            {
                Console.WriteLine();
                new MainMenu().execute();
            }
            else
            {
                Console.WriteLine("\tUsuario o contraseña incorrecto");
                Console.ReadKey();
            }                      
        }

        static async Task login(String username, String password)
        {
            using (var cliente = new HttpClient())
            {
                cliente.BaseAddress = new Uri(Sesion.Instance.URL);
                cliente.DefaultRequestHeaders.Accept.Clear();
                cliente.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));                
                String auth = Convert.ToBase64String(System.Text.Encoding.ASCII.GetBytes(username + ":" + password));               
                cliente.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Basic", auth);
               

                HttpResponseMessage response = await cliente.GetAsync("login/username=" + username + "&&password=" + password);
                if (response.IsSuccessStatusCode)
                {
                    User user = await response.Content.ReadAsAsync<User>();
                    Sesion.Instance.User = user;
                }
                else if (response.StatusCode == System.Net.HttpStatusCode.NotFound)
                {
                    Console.WriteLine("\tUsuario o contraseña incorrecto");
                }
                else
                {
                    Console.WriteLine("\tError peticion login");
                }
            }
        }
    }
}
