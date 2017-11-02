$(document).ready(function () {
  $(function () {
    var dateFormat = 'dd-mm-yy';
    $("#datepicker").datepicker({
      minDate: 0,
      dateFormat: dateFormat
    });

    $("#task-datepicker").datepicker({
      dateFormat: dateFormat
    });

    $('#select-category').selectize({
      sortField: {
        field: 'text',
        direction: 'asc'
      }
    });

    $('#categories .title > .fa').click(function () {
      var parent = $(this).closest("li");
      parent.find("ul").toggle();
      parent.find(".title > .fa").toggleClass("fa-chevron-down fa-chevron-up");
    });

    $(".task .btn.details").click(function () {
      $(this).closest(".task").find(".details-content").toggle();
      $(this).toggleClass("active");
    });
  });
});