var express = require('express');
var bodyParser = require('body-parser');
var crypto = require('crypto');
var Pusher = require('pusher');

var app = express();
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

var pusher = new Pusher({
  appId      : process.env.PUSHER_APP_ID,
  key        : process.env.PUSHER_APP_KEY,
  secret     : process.env.PUSHER_APP_SECRET,
  encrypted  : true,
});