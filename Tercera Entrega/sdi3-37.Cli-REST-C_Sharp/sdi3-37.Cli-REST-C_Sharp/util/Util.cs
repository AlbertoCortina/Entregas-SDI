using sdi3_37.Cli_REST_C_Sharp.dto;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace sdi3_37.Cli_REST_C_Sharp.util
{
    class Util
    {
        public static List<Tarea> parsearTareas (String json)
        {
            //Lista de tareas que vamos a devolver
            List<Tarea> tareas = new List<Tarea>();

            //Tarea auxiliar
            Tarea tareaAux = null;

            //Diccionario con todos los campos de una tarea
            Dictionary<String, Object> diccionario = new Dictionary<String, Object>();
            diccionario["id"] = null;
            diccionario["title"] = null;
            diccionario["comments"] = null;
            diccionario["created"] = null;
            diccionario["planned"] = null;
            diccionario["finished"] = null;
            diccionario["categoryId"] = null;
            diccionario["userId"] = null;

            //Quitamos los caracteres que nos queremos
            String aux = json.Replace("[", "").Replace("]", "").Replace("{", "").Replace("}", "").Replace("\"", "");

            //Dividimos por ,
            String[] aux2 = aux.Split(',');

            //Metemos los valores en el diccionario
            for(int i = 0; i < aux2.Length; i++)
            {
                String[] aux3 = aux2[i].Split(':');

                diccionario[aux3[0]] = aux3[1];

                if (aux3[0].Equals("id"))
                {
                    tareaAux = new Tarea();
                }
                if(aux3[0].Equals("userId"))
                {
                    tareaAux.id = Convert.ToInt64(diccionario["id"]);
                    tareaAux.title = (String)diccionario["title"];
                    tareaAux.comments = (String)diccionario["comments"];                                  
                    tareaAux.created = (new DateTime(1970, 1, 1)).AddMilliseconds(Convert.ToDouble(diccionario["created"]));
                    tareaAux.planned = (new DateTime(1970, 1, 1)).AddMilliseconds(Convert.ToDouble(diccionario["planned"]));
                    tareaAux.finished = (new DateTime(1970, 1, 1)).AddMilliseconds(Convert.ToDouble(diccionario["finished"]));
                    tareaAux.categoryId = Convert.ToInt64(diccionario["categoryId"]);
                    tareaAux.userId = Convert.ToInt64(diccionario["userId"]);

                    tareas.Add(tareaAux);
                }
            }

            return tareas;
        }

        public static String parsearJson(Tarea tarea)
        {            
            String json = "{" +
                    "\"id\":" + tarea.id + "," +
                    "\"title\": \"" + tarea.title + "\"," +
                    "\"comments\": \"" + tarea.comments + "\"," +
                    "\"created\": " + (long)(tarea.created - new DateTime(1970, 1, 1)).TotalMilliseconds + "," +
                    "\"planned\": " + (long)(tarea.created - new DateTime(1970, 1, 1)).TotalMilliseconds + ",";
           
            if (tarea.finished != new DateTime(1970, 1, 1))
            {
                json = json + "\"finished\": " + (long)(tarea.finished - new DateTime(1970, 1, 1)).TotalMilliseconds + ",";
            }

            if (tarea.categoryId != 0)
            {
                json = json + "\"categoryId\": " + tarea.categoryId + ",";
            }

            json = json + "\"userId\": " + tarea.userId + "}";

            return json;              
       }
    }
}
