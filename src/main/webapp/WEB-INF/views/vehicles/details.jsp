<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<%@include file="/WEB-INF/views/common/head.jsp" %>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

    <%@ include file="/WEB-INF/views/common/header.jsp" %>
    <!-- Left side column. contains the logo and sidebar -->
    <%@ include file="/WEB-INF/views/common/sidebar.jsp" %>

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Main content -->
        <section class="content">

            <div class="row">
                <div class="col-md-3">

                    <!-- Profile Image -->
                    <div class="box box-primary">
                        <div class="box-body box-profile">
                            <h3 class="text-center">${vehicle.modele()} ${vehicle.constructeur()} #${vehicle.id()}</h3>

                            <ul class="list-group list-group-unbordered">
                                <li class="list-group-item">
                                    <b>Nombre de places</b> <a class="pull-right">${vehicle.nbPlaces()}</a>
                                </li>
                                <li class="list-group-item">
                                    <b>Nombre de client</b> <a class="pull-right">${nbClient}</a>
                                </li>
                                <li class="list-group-item">
                                    <b>Nombre de reservation</b> <a class="pull-right">${nbReservation}</a>
                                </li>
                            </ul>
                        </div>
                        <!-- /.box-body -->
                    </div>
                    <!-- /.box -->
                </div>
                <!-- /.col -->
                <div class="col-md-9">
                    <div class="nav-tabs-custom">
                        <ul class="nav nav-tabs">
                            <li class="active"><a href="#client" data-toggle="tab">Client</a></li>
                            <li><a href="#rent" data-toggle="tab">Reservation</a></li>
                        </ul>
                        <div class="tab-content">
                            <div class="active tab-pane" id="client">
                                <div class="box-body no-padding">
                                    <table class="table table-striped">
                                        <tr>
                                            <th style="width: 10px">#</th>
                                            <th>Nom</th>
                                            <th>Prenom</th>
                                            <th>Email</th>
                                        </tr>
                                        <c:forEach items="${clients}" var="client">
                                            <tr>
                                                <td>${client.id()}</td>
                                                <td>${client.nom()}</td>
                                                <td>${client.prenom()}</td>
                                                <td>${client.email()}</td>
                                            </tr>
                                        </c:forEach>
                                    </table>
                                </div>
                            </div>
                            <!-- /.tab-pane -->
                            <div class="tab-pane" id="rent">
                                <!-- /.box-header -->
                                <div class="box-body no-padding">
                                    <table class="table table-striped">
                                        <tr>
                                            <th style="width: 10px">#</th>
                                            <th>Date de debut</th>
                                            <th>Date de fin</th>
                                        </tr>
                                        <c:forEach items="${rents}" var="rent">
                                            <tr>
                                                <td>${rent.id()}</td>
                                                <td>${rent.debut()}</td>
                                                <td>${rent.fin()}</td>
                                            </tr>
                                        </c:forEach>
                                        </tr>
                                    </table>
                                </div>
                            </div>
                            <!-- /.tab-pane -->
                        </div>
                        <!-- /.tab-content -->
                    </div>
                    <!-- /.nav-tabs-custom -->
                </div>
                <!-- /.col -->
            </div>
            <!-- /.row -->

        </section>
        <!-- /.content -->
    </div>

    <%@ include file="/WEB-INF/views/common/footer.jsp" %>
</div>
<!-- ./wrapper -->

<%@ include file="/WEB-INF/views/common/js_imports.jsp" %>
</body>
</html>
