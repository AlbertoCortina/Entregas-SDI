package com.sdi.rest.filter;

import java.io.IOException;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;

import com.sdi.business.exception.BusinessException;
import com.sdi.dto.User;
import com.sdi.infrastructure.Factories;

@WebFilter(dispatcherTypes = { DispatcherType.REQUEST }, urlPatterns = { "/rest/*" })
public class FilterRest implements Filter {

    /**
     * Default constructor.
     */
    public FilterRest() {
    }

    /**
     * @see Filter#destroy()
     */
    public void destroy() {
    }

    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    public void doFilter(ServletRequest request, ServletResponse response,
	    FilterChain chain) throws IOException, ServletException {

		if (!(request instanceof HttpServletRequest)) {
		    chain.doFilter(request, response);
		    return;
		}
	
		HttpServletRequest req = (HttpServletRequest) request;
		
	
		String cabecera = req.getHeader("Authorization");
		cabecera = cabecera.split(" ")[1];
		byte[] decoded = DatatypeConverter.parseBase64Binary(cabecera);
		cabecera = new String(decoded);
		String[] aux = cabecera.split(":");
		String username = aux[0];
		String password = aux[1];
	
		try {
		    User user = Factories.services.getUserService().findLoggableUser(username, password);
		    if (user == null)
		    	return;
		} catch (BusinessException e) {
			return;
		}
	
		chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {

    }
}