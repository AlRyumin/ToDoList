$(document).ready(function () {
  $(function () {
    var dateFormat = 'dd-mm-yy';
    $("#datepicker").datepicker({
      minDate: 0,
      dateFormat: dateFormat
    });

    if ($('#datepicker').val() === "") {
      $('#datepicker').datepicker('setDate', new Date());
    }

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

    $("#tasks .filters h3").click(function () {
      $("#tasks .filters-content").toggle();
      $(this).find(".fa").toggleClass("fa-chevron-down fa-chevron-up");
    });
  });
});