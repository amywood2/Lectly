const mysql = require(“mysql”);
  var connection = mysql.createConnection(
    {
        host : “ip address”,
        user : “username”,
        password : “password”,
        database : “database_name”
    }
  );
  connection.connect((err)=>{
    if(err)
    {
      console.log(err);
    }
    else{
        console.log(“connected”);
    }
  });
  module.exports = connection;