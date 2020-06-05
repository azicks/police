<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Accident list</title>
    <meta charset="UTF-8"/>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh"
          crossorigin="anonymous">
    <script
            src="https://code.jquery.com/jquery-3.5.1.js"
            integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc="
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
            crossorigin="anonymous"></script>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-2"></div>
        <div class="col-md-8">
            <div class="row p-3">
                <h3>Список извещений о ДТП</h3>
            </div>
            <table class="table table-sm table-bordered text-center">
                <thead>
                <tr>
                    <th>#</th>
                    <th>Заявка</th>
                    <th>Адрес ДТП</th>
                    <th>Описание</th>
                    <th>Действие</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${accidents}" var="acc">
                    <tr>
                        <td class="align-middle"><c:out value="${acc.id}"/>
                        </td>
                        <td class="align-middle"><c:out value="${acc.name}"/>
                        </td>
                        <td class="align-middle"><c:out value="${acc.address}"/>
                        </td>
                        <td class="align-middle"><c:out value="${acc.text}"/>
                        </td>
                        <td class="align-middle">
                            <div class="mt-3">
                                <form method="post">
                                    <div class="form-group">
                                        <button class="btn btn-outline-info" type="button"
                                                data-toggle="modal" data-target="#editModal"
                                                data-action="edit"
                                                data-id="${acc.id}">Редактировать
                                        </button>
                                        <button class="btn btn-outline-danger" type="button"
                                                data-toggle="modal" data-target="#deleteModal"
                                                data-id="${acc.id}">Удалить
                                        </button>
                                    </div>
                                </form>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <form method="post">
                <div class="form-group">
                    <button class="btn btn-outline-success" type="button"
                            data-toggle="modal" data-target="#editModal"
                            data-action="create">Создать заявку
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- Modal delete -->
<div class="modal fade" id="deleteModal" tabindex="-1" role="dialog" aria-labelledby="deleteModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="deleteModalLabel">Удалить?</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
            </div>
            <div class="modal-footer">
                <form method="post" action="accident/delete">
                    <input id="modal_delete_id" type="hidden" name="id">
                    <button type="button" class="btn btn-outline-secondary" data-dismiss="modal">Отмена</button>
                    <button type="submit" name="action" value="delete" class="btn btn-outline-danger">Удалить</button>
                </form>
            </div>
        </div>
    </div>
</div>


<!-- Modal edit -->
<div class="modal fade" id="editModal" tabindex="-1" role="dialog" aria-labelledby="editModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="editModalLabel"></h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form method="post" id="form_accident" enctype="application/x-www-form-urlencoded">
                    <div class="form-group">
                        <label for="name">Извещение</label>
                        <input class="form-control" id="name" name="name" required="required">
                    </div>
                    <div class="form-group">
                        <label for="address">Адрес ДТП</label>
                        <input class="form-control" id="address" name="address" required="required">
                    </div>
                    <div class="form-group">
                        <label for="text">Описание</label>
                        <textarea class="form-control rounded-0" id="text" rows="4" name="text"></textarea>
                    </div>
                    <input type="hidden" id="id" name="id">
                    <input type="submit" id="form_accident_submit" hidden>
                </form>
            </div>
            <div class="modal-footer">
                <form method="post">
                    <button type="button" class="btn btn-outline-secondary" data-dismiss="modal">Отмена</button>
                    <button type="button" id="btn_ok" class="btn btn-outline-success" onclick="editOk()">Создать
                    </button>
                </form>
            </div>
        </div>
    </div>
</div>

<script>
    $('#deleteModal').on('show.bs.modal', function (event) {
        let button = $(event.relatedTarget);
        let id = button.data('id');
        let modal = $(this);
        modal.find('.modal-body').text('Удалить заявку с id = ' + id);
        $("#modal_delete_id").val(id);
    });

    $('#editModal').on('show.bs.modal', function (event) {
        let button = $(event.relatedTarget);
        let id = button.data('id');
        let action = button.data('action');
        let modal = $(this);
        let title;
        if ('create' === action) {
            title = 'Создать заявку';
            prepareNewModal(modal);
        }
        if ('edit' === action) {
            title = 'Изменить заявку с id = ' + id;
            $.ajax({
                type: 'GET',
                url: 'accident/' + id,
                dataType: 'json',
                success: function (data) {
                    let accident = data['accident'];
                    prepareEditModal(modal, accident)
                },
                error: function (xhr, error) {
                    alert(error);
                }
            });
        }
        modal.find('.modal-title').text(title);

    });

    function prepareEditModal(modal, accident) {
        modal.find('#name').val(accident.name);
        modal.find('#address').val(accident.address);
        modal.find('#text').val(accident.text);
        modal.find('#id').val(accident.id);
        modal.find('#btn_ok').text('Изменить');
        modal.find('#form_accident').attr("action", "accident/edit");
    }

    function prepareNewModal(modal) {
        modal.find('#name').empty();
        modal.find('#address').val('');
        modal.find('#text').val('');
        modal.find('#btn_ok').text('Создать');
        modal.find('#form_accident').attr("action", "accident/new");
    }

    function editOk() {
        $('#form_accident_submit').click();
    }

</script>
</body>
</html>
