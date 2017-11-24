$(document).ready(function () {
  tinymce.init({
    selector: '#description',
    height: 100,
    menubar: false,
    plugins: [
      'advlist autolink lists link image charmap print preview  textcolor',
      'searchreplace visualblocks code fullscreen',
      'insertdatetime media table contextmenu paste code'
    ],
    toolbar: 'insert | undo redo |  formatselect | bold italic backcolor  | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | removeformat',
  });
});