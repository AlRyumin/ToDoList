$(document).ready(function () {
  $(function () {
    $("#datepicker").datepicker({
      minDate: 0,
      dateFormat: 'dd-mm-yy'
    });

    $('#select-category').selectize({
      sortField: {
        field: 'text',
        direction: 'asc'
      }
    });
  });
});