using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace sdi3_37.Cli_REST_C_Sharp.dto
{
    class Tarea
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

        private String Title;
        public String title
        {
            get
            {
                return Title;
            }
            set
            {
                Title = value;
            }
        }

        private String Comments;
        public String comments
        {
            get
            {
                return Comments;
            }
            set
            {
                Comments = value;
            }
        }

        private DateTime Created = DateTime.Today;
        public DateTime created
        {
            get
            {
                return Created;
            }
            set
            {
                Created = value;
            }
        }

        private DateTime Planned;
        public DateTime planned
        {
            get
            {
                return Planned;
            }
            set
            {
                Planned = value;
            }
        }

        private DateTime Finished;
        public DateTime finished
        {
            get
            {
                return Finished;
            }
            set
            {
                Finished = value;
            }
        }

        private long CategoryId;
        public long categoryId
        {
            get
            {
                return CategoryId;
            }
            set
            {
                CategoryId = value;
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
