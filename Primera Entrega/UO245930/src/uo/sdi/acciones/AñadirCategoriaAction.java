package uo.sdi.acciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import alb.util.log.Log;
import uo.sdi.business.CategoryService;
import uo.sdi.business.Services;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.model.Association;
import uo.sdi.model.Category;
import uo.sdi.model.User;

public class AñadirCategoriaAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {

		String resultado = "EXITO";
		String name = request.getParameter("nombreCategoria");
		HttpSession session = request.getSession();
		User user = ((User) session.getAttribute("user"));		
		CategoryService categoryService = Services.getCategoryService();
		try {
			Category category = new Category(name);
			Association.Organize.link(user, category);
			categoryService.createCategory(category);
			request.setAttribute("listaCategorias", categoryService.findCategoriesByUserId(user.getId()));
			Log.debug("Categoria insertada correctamente");
		} catch (BusinessException e) {
			Log.debug("Algo ha ocurrido añadiendo la categoria");
			resultado = "FRACASO";
		}		
		return resultado;
	}
}