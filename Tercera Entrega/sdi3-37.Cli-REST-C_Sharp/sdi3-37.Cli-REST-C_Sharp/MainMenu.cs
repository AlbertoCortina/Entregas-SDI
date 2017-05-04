using sdi3_37.Cli_REST_C_Sharp.action;
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
    class MainMenu
    {
        static void Main(string[] args)
        {
            //Pedir datos
            Console.Write("Usuario: ");
            var username = Console.ReadLine();

            Console.Write("Contraseña: ");
            var password = Console.ReadLine();

            login(username, password).Wait();
            Console.WriteLine();

            if(Sesion.Instance.User != null)
            {
                //Selecionar opción
                while (true)
                {
                    Console.WriteLine("-----------------------------------------------------------------------");
                    Console.WriteLine("USUARIO "+ Sesion.Instance.User.login);

                    //Opción 1: ListarCategoriasAction            
                    Console.Write("\t1- Listar categorias\n");

                    //Opción 2: TareasCategoriaAction
                    Console.Write("\t2- Ver tareas de una categoria\n");

                    //Opción 3: MarcarTareaFinalizadaAction
                    Console.Write("\t3- Marcar tarea como finalizada\n");

                    //Opción 4: CrearTareaAction
                    Console.Write("\t4- Crear tarea\n");

                    //Opción 0: Salir
                    Console.WriteLine();
                    Console.Write("\t0 - Salir\n");

                    Console.Write("Opción: ");
                    switch (Console.ReadLine())
                    {
                        case "0":
                            return;
                        case "1":
                            Console.WriteLine();
                            new ListarCategoriasAction().execute();
                            break;
                        case "2":
                            Console.WriteLine();
                            new TareasCategoriaAction().execute();
                            break;
                        case "3":
                            Console.WriteLine();
                            new MarcarTareaFinalizadaAction().execute();
                            break;
                        case "4":
                            Console.WriteLine();
                            new CrearTareaAction().execute();
                            break;
                        default:
                            Console.WriteLine("\tOpción errónea");
                            break;
                    }
                    Console.WriteLine();
                }
            }
            else
            {
                Console.Write("\tNo existe el usuario");
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

                HttpResponseMessage response = await cliente.GetAsync("login/username=" + username + "&&password=" + password);
                if (response.IsSuccessStatusCode)
                {
                    User user = await response.Content.ReadAsAsync<User>();
                    Sesion.Instance.User = user;                 
                }
            }
        }
    }
}
