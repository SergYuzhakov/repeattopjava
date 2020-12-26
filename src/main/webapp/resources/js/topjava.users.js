var ctx;

// $(document).ready(function () {
$(function () {
    // https://stackoverflow.com/a/5064235/548473
    ctx = {
        ajaxUrl: "admin/users/",
        datatableApi: $("#datatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "name"
                },
                {
                    "data": "email"
                },
                {
                    "data": "roles"
                },
                {
                    "data": "enabled"
                },
                {
                    "data": "registered"
                },
                {
                    "defaultContent": "Edit",
                    "orderable": false
                },
                {
                    "defaultContent": "Delete",
                    "orderable": false
                }
            ],
            "order": [
                [
                    0,
                    "asc"
                ]
            ]
        }),
        update: updateUserTable

    };
    makeEditable();
});

function updateUserTable() {
    $.get(ctx.ajaxUrl, updateTableByData);
}

function enableUser(checkbox) {
    var id = checkbox.closest('tr').attr("data-id")
    var enabled = checkbox.is(":checked");
    $.ajax({
        url: ctx.ajaxUrl + id,
        type: "POST",
        data: "enabled=" + enabled
    }).done(function () {
        checkbox.closest('tr').attr("data-userEnabled", enabled);
        successNoty(enabled ? "User Activated" : "User Deactivated");
    }).fail(function () {
        checkbox.prop("checked", !enabled);
    });
}