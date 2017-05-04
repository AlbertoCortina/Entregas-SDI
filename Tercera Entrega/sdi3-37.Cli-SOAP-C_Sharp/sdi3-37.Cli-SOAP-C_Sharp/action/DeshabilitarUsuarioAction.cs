using System;
using sdi3_37.Cli_SOAP_C_Sharp.SOAP;

namespace sdi3_37.Cli_C_Sharp
{
    class DeshabilitarUsuarioAction : Action
    {
        public DeshabilitarUsuarioAction()
        {

        }

        public void execute()
        {
            Console.WriteLine("---------------Deshabilitar usuario---------------");           

            //Pedir datos
            Console.Write("Id del usuario que desea deshabilitar: ");
            var userId = Console.ReadLine();
            
            try
            {
                AdminService aService = new AdminServiceClient();

                user u = aService.findUserById(new findUserById(Convert.ToInt64(userId))).@return;
                
                if (u.status.Equals(userStatus.DISABLED))
                {
                    Console.WriteLine("\tEl usuario ya estaba deshabilitado");
                }
                else
                {
                    aService.disableUser(new disableUser(Convert.ToInt64(userId)));
                    Console.WriteLine("\tUsuario deshabilitado correctamente");
                }

            } catch(NullReferenceException e)
            {
                Console.WriteLine("\tNo existe el usuario");
            } catch (Exception e)
            {
                Console.WriteLine("\tError grave");
            }

            Console.WriteLine("--------------------------------------------------");
        }
    }
}
