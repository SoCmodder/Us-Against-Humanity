function newGameVal(form) {
  var e = form.elements;

  if(e['password'].value != e['password_confirm'].value) {
    $("#passwordError").show();
    e['password'].value = '';
    e['password_confirm'].value = '';
    return false;
  }
  return true;
}