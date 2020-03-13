logın olabılmek ıcın bu sekıld bır request atmalısınız
burada ben auth kullandım.  logın ıcın basıc authatıcate key lasım burada  client id: todo  ,  secret ise:secret   
btoa(`todo:secret`)  size birtane basic auth key generate ediyor bu login icin onemli,

getToken() {

  const payload = 'username=' + encodeURIComponent(username) + '&password=' + encodeURIComponent(password) + '&grant_type=password';
    
    const basicAuthHeader = btoa(`todo:secret`);
    const options = {
      headers: new HttpHeaders()
        .set('Content-Type', 'application/x-www-form-urlencoded')
        .append('Authorization', `Basic  ${basicAuthHeader}`)
    };
    return this.httpSvc.post('localhost:9190/oauth/token', payload, options);
  }
}



register icin auth keye gerek yok,  

register() {

  const payload = {
  "id": 0,
  "name": "string",
  "password": "string",
  "surname": "string",
  "username": "string"
  };
    
    const options = {
      headers: new HttpHeaders()
        .set('Content-Type', 'application/json');
    };
    return this.httpSvc.post('localhost:9190/users/register', payload, options);
  }
}


add,  delete gibi islemlerde artik  login olduktan sonra size birtane response donecek bu response icinde token bilgisi var
buradaki  token cok onemli,  bu token ile islem yapabilirsiniz, Spring tarafina token gondereceginiz icin spring sizin kim oldugunuzu bununla bilebilecek.
Ayrica username yada user id gibi bir bilgiye ihtiyac duymuyor.  token type "bearer"  
token yazilirken  "bearer <size verilen token>"   olarak kullaniyorsunuz,  bearerden sonra mutlaka bir bosluk vardir.

addTODO() {

  const payload = {
    "description": "string",
  "id": 0,
  "parentTodoId": 0,
  "title": "string"
  };
    
    const token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1ODQwNDg4ODQsInVzZXJfbmFtZSI6InN0cmluZyIsImp0aSI6IjMxZmE2YjFlLTEzMDQtNDA0Yi04YzdlLWY0MmM4OGFlNzVmYyIsImNsaWVudF9pZCI6InRvZG8iLCJzY29wZSI6WyJyZWFkIiwid3JpdGUiXX0.n6l5XkWhJ1MFOd1ozVTULLz-3BrNf4Y8RMumcziVu1g";
    const options = {
      headers: new HttpHeaders()
        .set('Content-Type', 'application/json')
        .append('Authorization', `bearer  ${token}`)
    };
    return this.httpSvc.post('localhost:9190/todo/add', payload, options);
  }
}