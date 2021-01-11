var ctx, mealAjaxUrl = "profile/meals/";

function updateFilteredTable() {
    $.ajax({
        type: "GET",
        url: mealAjaxUrl + "filter",
        data: $("#filter").serialize()
    }).done(updateTableByData);
}

function clearFilter() {
    $("#filter")[0].reset();
    $.get(mealAjaxUrl, updateTableByData);
}

$.ajaxSetup({
    converters: {
        "text json": function (text) {
            var json = JSON.parse(text);
            $(json).each(function () {
                this.dateTime = this.dateTime.replace('T', ' ').substr(0,16);
            });
            return json;
        }
    }
});


$(function () {
    ctx = {
        ajaxUrl: mealAjaxUrl,
        datatableApi: $("#datatable").DataTable({
            "ajax": {
                "url": mealAjaxUrl,
                "dataSrc": ""
            },
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime",

                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
                {
                    "defaultContent": "",
                    "orderable": false,
                    "render": renderEditBtn
                },
                {
                    "defaultContent": "",
                    "orderable": false,
                    "render": renderDeleteBtn
                }
            ],
            "order": [
                [
                    0,
                    "desc"
                ]
            ],
            "createdRow": function (row, data, dataIndex) {
                $(row).attr("data-mealExcess", data.excess);
            }
        }),
        updateTable: updateFilteredTable

    };
    makeEditable();

    $.datetimepicker.setLocale('en');

    $(function () {
        $('#startDate').datetimepicker({
            format: 'Y/m/d',
            onShow: function (ct) {
                this.setOptions({
                    maxDate: $('#endDate').val() ? $('#endDate').val() : false
                })
            },
            timepicker: false
        });
        $('#endDate').datetimepicker({
            format: 'Y/m/d',
            onShow: function (ct) {
                this.setOptions({
                    minDate: jQuery('#startDate').val() ? jQuery('#startDate').val() : false
                })
            },
            timepicker: false
        });
        $('#startTime').datetimepicker({
            datepicker: false,
            format: 'H:i'
        });
        $('#endTime').datetimepicker({
            datepicker: false,
            format: 'H:i'
        });
        $('#dateTime').datetimepicker({
            format: 'Y-m-d\\TH:i',
        });
    });
});