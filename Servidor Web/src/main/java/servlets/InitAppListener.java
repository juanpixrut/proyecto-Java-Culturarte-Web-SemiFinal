/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Juanpi
 */

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.*;

import logica.*;
import persistencia.*;

@WebListener
public class InitAppListener implements ServletContextListener {
    
    ControladoraNueva Sistema = new ControladoraNueva(); //creada como hija de ictrl, (pasar todos los metodos luego a ictrl)

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // Acá corre cuando la app se inicia
        try {
            //Sistema.altaPerfilColaborador("juanpi", "juan", "fontes", "@gmail.com", null);
            Sistema.altaPerfilProponente("juanpiProp", "juan2", "fontes2", "juan2@gmail.com", null, "cuenca", "proxeneta", "misitio.com");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Acá podés limpiar recursos si querés
    }
}
