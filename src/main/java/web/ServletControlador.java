package web;

import datos.ClienteDaoJDBC;
import dominio.Cliente;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/ServletControlador")
public class ServletControlador extends HttpServlet {
    
    private void accionDefault(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Cliente> clientes = new ClienteDaoJDBC().listar();
        System.out.println("clientes = " + clientes);
        HttpSession sesion = request.getSession();
        sesion.setAttribute("clientes", clientes);
        sesion.setAttribute("totalClientes", clientes.size());
        sesion.setAttribute("saldoTotal", this.calcularSaldoTotal(clientes));
        //El siguiente codigo hace que el URL mantenga la ultima acccion por lo que al actualiza
        //si se agrego un item o se elimino, el navegador tratara de volver a hacerlo.
        //request.getRequestDispatcher("clientes.jsp").forward(request, response);*/
        response.sendRedirect("clientes.jsp"); //Abre cliente al igual que el anterior pero notifica el URL del navegador.
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Entre en el metodo doGet, antes de editar");
        String accion = request.getParameter("accion");
        if(accion != null){
            switch(accion){
                case "editar": 
                    this.editarCliente(request, response);
                    break;
                default:
                    this.accionDefault(request, response);
            }
        }
        else{
            this.accionDefault(request, response);
        }
    }
    
    private double calcularSaldoTotal(List<Cliente> clientes){
        double saldoTotal = 0;
        for(Cliente cliente: clientes){
            saldoTotal += cliente.getSaldo();
        }
        return saldoTotal;
    }
    
    private void editarCliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        //recuperamos el idCliente
        int idCliente = Integer.parseInt(request.getParameter("idCliente"));
        Cliente cliente = new ClienteDaoJDBC().encontrar(new Cliente(idCliente));
        //vamos a usar el metodo Get para enviar la informacion al JSP, 
        //por eso usa el atributo cliente para enviar el objeto Cliente recien recuperado.
        request.setAttribute("cliente", cliente);
        //Ubicacion del jsp a llamar para realizar la edición.
        String jspEditar = "/WEB-INF/paginas/cliente/editarCliente.jsp";
        request.getRequestDispatcher(jspEditar).forward(request, response);
        
        
        
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String accion = request.getParameter("accion");
        System.out.println("Entre en el metodo doPost, antes de insertar");
        if(accion != null){
            switch(accion){
                case "insertar": 
                    this.insertarCliente(request, response);
                    break;
                case "modificar":
                    this.modificarCliente(request, response);
                    break;
                default:
                    this.accionDefault(request, response);
            }
        }
        else{
            this.accionDefault(request, response);
        }
    }
    
    private void modificarCliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        //Recuperamos los valores del formulario editarCliente
        int idCliente = Integer.parseInt(request.getParameter("idCliente"));
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String email = request.getParameter("email");
        String telefono = request.getParameter("telefono");
        double saldo = 0;
        String saldoString = request.getParameter("saldo");
        if (saldoString != null && !"".equals(saldoString)){
            saldo = Double.parseDouble(saldoString);
        }
        
        //Creamos el objeto de cliente (modelo)
        Cliente cliente = new Cliente(idCliente, nombre, apellido, email, telefono, saldo);
        
        //Modificar el objeto en la base de datos
        int registrosModificados = new ClienteDaoJDBC().actualizar(cliente);
        System.out.println("Registros modificados = " + registrosModificados);
        
        //Redirigimos hacia la accion por default
        this.accionDefault(request, response);
    }
    
    private void insertarCliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        //Recuperamos los valores del formulario agregarCliente
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String email = request.getParameter("email");
        String telefono = request.getParameter("telefono");
        double saldo = 0;
        String saldoString = request.getParameter("saldo");
        if (saldoString != null && !"".equals(saldoString)){
            saldo = Double.parseDouble(saldoString);
        }
        
        //Creamos el objeto de cliente (modelo)
        Cliente cliente = new Cliente(nombre, apellido, email, telefono, saldo);
        
        //Insertamos el nuevo objeto en la base de datos
        int registrosModificados = new ClienteDaoJDBC().insertar(cliente);
        System.out.println("Registros modificados = " + registrosModificados);
        
        //Redirigimos hacia la accion por default
        this.accionDefault(request, response);
    }
}
