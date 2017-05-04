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
    class Sesion
    {
        private static readonly Lazy<Sesion> INSTANCE = new Lazy<Sesion>(() => new Sesion());

        private String REST_SERVICE_URL = "http://localhost:8280/sdi3-37.Web/rest/UsersServiceRS/";
        public String URL
        {
            get
            {
                return REST_SERVICE_URL;
            }
        }

        private User user;
        public User User
        {
            get
            {
                return user;
            }
            set
            {
                user = value;
            }
        }

        private Sesion() { }

        public static Sesion Instance
        {
            get
            {
                return INSTANCE.Value;
            }
        }
    }
}
