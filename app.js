const express = require("express");
const bodyparser = require("body-parser");
const authentication = require("../Authentication/Auth");


const app = express();
app.use(bodyparser.json());
app.use(bodyparser.urlencoded({extended:false}));


app.use((req, res, next) => {
    res.setHeader("Access-Control-Allow-Origin", "*");
    res.setHeader(
      "Access-Control-Allow-Headers",
      "Origin, X-Requested-With, Content-Type, Accept"
    );
    res.setHeader(
      "Access-Control-Allow-Methods",
      "GET, POST, PATCH, DELETE, OPTIONS"
    );
    next();
  });


app.post("/register",authentication.register);


module.exports = app;