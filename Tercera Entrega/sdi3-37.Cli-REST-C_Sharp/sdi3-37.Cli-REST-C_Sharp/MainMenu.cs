using sdi3_37.Cli_REST_C_Sharp.action;
using System;

namespace sdi3_37.Cli_REST_C_Sharp
{
    class MainMenu : action.Action
    {
        public MainMenu ()
        {

        }

        public void execute()
        {
            //Selecionar opción
            while (true)
            {
                Console.WriteLine("-----------------------------------------------------------------------");
                Console.WriteLine("USUARIO " + Sesion.Instance.User.login);

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
    }
}
