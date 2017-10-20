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

    $('#categories ul > li > ul').hide();

    $('#categories .title > .fa').click(function(){
      var parent = $(this).closest("li");
      parent.find("ul").toggle();
//      $(this).toggleClass("fa-chevron-down fa-chevron-up");
      parent.find(".title > .fa").toggleClass("fa-chevron-down fa-chevron-up");
    });
  });
});