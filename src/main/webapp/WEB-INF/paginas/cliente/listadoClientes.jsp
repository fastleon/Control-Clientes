<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="es_CO"/>

<!-- Forma antigua para ver el listado de forma basica
<ul>
c:forEach var="cliente" items="SignoPesos{clientes}">
    <li>SignoPesos{cliente.idCliente} SignoPesos{cliente.nombre} SignoPesos{cliente.apellido} SignoPesos{cliente.saldo}</li>
/c:forEach>
</ul>
-->

<section id="clientes">
    <div class="container">
        <div class="row">
            <!-- Listado de Clientes -->
            <div class="col-md-9">
                <div class="card">
                    <div class="card-header">
                        <h4>Listado de Clientes</h4>
                    </div>
                    <table class="table table-striped">
                        <thead class="thead-dark">
                            <tr>
                                <th>#</th>
                                <th>Nombre</th>
                                <th>Saldo</th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody>
                            <!-- Iteramos para ver los registros -->
                            <c:forEach var="cliente" items="${clientes}" varStatus="status">
                                <tr>
                                    <td>${cliente.idCliente}</td>
                                    <!-- Si se desea que sea un autoincrementable indiferente del idCliente se puede usar lo siguiente:
                                    <td>SignoPesos{status.count}</td> -->
                                    <td>${cliente.nombre} ${cliente.apellido}</td>
                                    <td> <fmt:formatNumber value="${cliente.saldo}" type="currency"/> </td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/ServletControlador?accion=editar&idCliente=${cliente.idCliente}"
                                           class="btn btn-secundary">
                                            <i class="fas fa-angle-double-right"></i> Editar
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
            <!-- Fin Listado de Clientes -->

            <!--Inicio Tarjetas para los totales-->
            <div class="col-md-3">
                <div class="card text-center bg-info mb-3">
                    <div class="card-body">
                        <h3>Saldo Total</h3>
                        <h4 class="display-4">
                            <fmt:formatNumber value="${saldoTotal}" type="currency" />

                        </h4>
                    </div>
                </div>

                <div class="card text-center bg-success text-white mb-3">
                    <div class="card-body">
                        <h3>Total Clientes </h3>
                        <h4 class="display-4">
                            <i class="fas fa-users"></i> ${totalClientes}
                        </h4>
                    </div>
                </div>
            </div>
            <!--Fin tarjetas de totales-->
        </div>
    </div>
</section>

<!-- Agregar cliente modal -->
<jsp:include page="/WEB-INF/paginas/cliente/agregarCliente.jsp"/>
