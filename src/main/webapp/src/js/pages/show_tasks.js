
jQuery(document).ready(function ()
{
  var dateFormat = 'DD-MM-YYYY';

  $('#date-range').dateRangePicker(
          {
            format: dateFormat,
            language: 'en',
          })
          .bind('datepicker-change', function (event, obj)
          {
            setInputDate(obj);
            $(".date-picker-wrapper .apply-btn").trigger("click");
          });
});

function setInputDate(obj) {
  $("input[name='start_date']").val(obj.date1.getDate() + "-" + (obj.date1.getMonth() + 1) + "-" + obj.date1.getFullYear());
  $("input[name='end_date']").val(obj.date2.getDate() + "-" + (obj.date2.getMonth() + 1) + "-" + obj.date2.getFullYear());
}