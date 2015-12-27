window.onload = function () {
  var isbn = getQueryParam('isbn');
  if (isbn) {
    $.ajax({
      url: baseUrl + '/get/' + isbn,
      dataType: 'json',
      success: function (book) {
        for (var key in tableHeaderMapper) {
          document.querySelector('input[name="' + key + '"]').value = book[tableHeaderMapper[key]];
        }
        var isbnInput = document.querySelector('input[name="ISBN"]');
        isbnInput.disabled = true;
      }
    });
  }

  var form = document.querySelector('.form');
  form.addEventListener('submit', function (e) {
    e.preventDefault();
    var formElements = e.target.elements;
    var book = {};
    for (var i = 0; i < formElements.length - 1; ++i) {
      book[tableHeaderMapper[formElements[i].name]] = formElements[i].value;
    }
    if (isbn) {
      httpRequest('PUT', baseUrl + '/update', function () {
        location.href = '/index.html';
      }, book);
    } else {
      httpRequest('POST', baseUrl + '/add', function () {
        location.href = '/index.html';
      }, book);
    }
  });
};
