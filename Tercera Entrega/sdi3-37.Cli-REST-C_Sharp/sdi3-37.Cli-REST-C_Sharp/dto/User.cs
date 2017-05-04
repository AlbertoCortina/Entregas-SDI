using sdi3_37.Cli_REST_C_Sharp.dto.types;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace sdi3_37.Cli_REST_C_Sharp.dto
{
    class User
    {
        private long Id;        
        public long id
        {
            get
            {
                return Id;
            }
            set
            {
                Id = value;
            }
        }

        private String Login;
        public String login
        {
            get
            {
                return Login;
            }
            set
            {
                Login = value;
            }
        }

        private String Password;
        public String password
        {
            get
            {
                return Password;
            }
            set
            {
                Password = value;
            }
        }

        private String Email;
        public String email
        {
            get
            {
                return Email;
            }
            set
            {
                Email = value;
            }

        }

        private Boolean IsAdmin = false;
        public Boolean isAdmin
        {
            get
            {
                return IsAdmin;
            }
            set
            {
                IsAdmin = value;
            }
        }

        private UserStatus Status = UserStatus.ENABLED;
        public UserStatus status
        {
            get
            {
                return Status;
            }
            set
            {
                Status = value;
            }
        }
    }
}
