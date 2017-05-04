using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace sdi3_37.Cli_REST_C_Sharp.dto
{
    class Category
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

        private String Name;
        public String name
        {
            get
            {
                return Name;
            }
            set
            {
                Name = value;
            }
        }

        private long UserId;
        public long userId
        {
            get
            {
                return UserId;
            }
            set
            {
                UserId = value;
            }
        }
    }
}
