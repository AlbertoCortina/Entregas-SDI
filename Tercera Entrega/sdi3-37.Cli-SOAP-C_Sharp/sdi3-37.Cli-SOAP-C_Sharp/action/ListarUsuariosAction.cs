using System;
using sdi3_37.Cli_SOAP_C_Sharp.SOAP;

namespace sdi3_37.Cli_C_Sharp
{
    class ListarUsuariosAction : Action
    {      
        public ListarUsuariosAction()
        {

        }

        public void execute()
        {
            Console.WriteLine("---------------Listar usuarios---------------");

            try
            {
                AdminService aService = new AdminServiceClient();

                userInfo[] usuarios = aService.listAllUsers(new listAllUsers()).@return;

                print(usuarios);

            } catch(Exception e)
            {
                Console.WriteLine("\tError grave");
            }
            
            Console.WriteLine("---------------------------------------------");
        }

        private void print (userInfo[] usuarios)
        {
            Console.WriteLine(String.Format("{0,-20}{1,-20}{2,-20}{3,-20}{4,-27}{5,-20}{6,-20}", "LOGIN", "EMAIL", "ESTADO", "COMPLETADAS", "COMPLETADAS_RETRASADAS", "PLANIFICADAS", "NO_PLANIFICADAS"));

            foreach (var u in usuarios)
            {
                if (!u.user.isAdmin)
                {
                    Console.Write(String.Format("{0,-20}", u.user.login));
                    Console.Write(String.Format("{0,-20}", u.user.email));
                    Console.Write(String.Format("{0,-25}", u.user.status));
                    Console.Write(String.Format("{0,-25}", u.tareasCompletadas));
                    Console.Write(String.Format("{0,-21}", u.tareasCompletadasRetrasadas));
                    Console.Write(String.Format("{0,-22}", u.tareasPlanificadas));
                    Console.WriteLine(String.Format("{0,-20}", u.tareasSinPlanificar));
                }
            }
        }
    }
}
