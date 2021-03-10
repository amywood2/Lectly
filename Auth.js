const crypto = require("crypto");
const jwt = require("jsonwebtoken");
const connection = require("../common/Connection");


exports.register = (request,response)=>{
    let name = request.body.name;
    let email = request.body.email;
    let password = request.body.password;
    let passwordhash =  generateHash(password);
    var regvalue = {
        "name":name,
        "email":email,
        "password":passwordhash.hash,
        "salt":passwordhash.salt,
    }
    connection.query("select * from organization where email=? or phoneno = ?",[email,phoneno],(error,result)=>{
        let res = {};
        let stat;
        if(error)
        {
            res.data = null;
            res.fullerror = error;
            res.message = "cannot able to register try again";
            stat = 500;


            response.status(stat).json(res);
        }
}


function generateHash(password)
{
  let salt =  crypto.randomBytes(16).toString('hex');
  let hash = crypto.pbkdf2Sync(password,salt,1000,64,'sha512').toString('hex');
  return {
    "salt" : salt,
    "hash" : hash
  }
}


function generateWebToken(name,email,phone,saltval,addressdata)
{
  var expiry = new Date();
  expiry.setDate(expiry.getDate()+7);
  return jwt.sign({
    email:email,
    name:name,
    phone:phone,
    exp:parseInt(expiry.getTime()/1000),
    address:addressdata
  },saltval);
}