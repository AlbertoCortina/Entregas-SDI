using System;
using sdi3_37.Cli_C_Sharp.SOAP;

namespace sdi3_37.Cli_C_Sharp
{
    class EliminarUsuarioAction : Action
    {
        public EliminarUsuarioAction()
        {

        }

        public void execute()
        {
            Console.WriteLine("---------------Eliminar usuario---------------");            

            //Pedir datos
            Console.Write("Id del usuario que desea eliminar: ");
            var userId = Console.ReadLine();

            try
            {
                AdminService aService = new AdminServiceClient();

                user u = aService.findUserById(new findUserById(Convert.ToInt64(userId))).@return;
                
                if(u == null)
                {
                    Console.WriteLine("\tNo existe el usuario");
                }
                else
                {
                    aService.deepDeleteUser(new deepDeleteUser(Convert.ToInt64(userId)));
                    Console.WriteLine("\tUsuario borrado correctamente");
                }               

            } catch(NullReferenceException e)
            {
                Console.WriteLine("\tNo existe el usuario");
            } catch(Exception e)
            {
                Console.WriteLine("\tError grave");
            }

            Console.WriteLine("----------------------------------------------");
        }
    }
}
