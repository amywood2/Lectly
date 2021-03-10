const crypto = require("crypto");
const jwt = require("jsonwebtoken");
const connection = require("../common/Connection");


exports.register = (request,response)=>{
    let name = request.body.name;
    let email = request.body.email;
    let phoneno = request.body.phone;
    let password = request.body.password;
    let buildno = request.body.buildno;
    let area = request.body.area;
    let district = request.body.district;
    let state = request.body.state;
    let country = request.body.country;
    let pincode = request.body.pincode;
    let address = buildno +","+area+","+district+","+state+","+country+","+pincode;
    let passwordhash =  generateHash(password);
    var regvalue = {
        "name":name,
        "email":email,
        "phoneno":phoneno,
        "buildno":buildno,
        "area":area,
        "district":district,
        "state":state,
        "country":country,
        "pincode":pincode,
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
        else if(result.length > 0)
        {
            res.data = null;
            res.fullerror = null;
            res.message = "organization already exists";
            stat = 400;

            response.status(stat).json(res);
        }
        else{
            let tablename = "organization";
            connection.query("SHOW TABLE STATUS WHERE name = ?",[tablename],(e,r)=>{
                if(e)
                {
                    res.data = null;
                    res.fullerror = e;
                    res.message = "cannot able to get data";
                    stat = 500;

                    response.status(stat).json(res);
                }
                else if(r.length > 0)
                {
                    connection.query("insert into organization set ?",regvalue,(err,res)=>{
                    if(err)
                    {
                        res.data = null;
                        res.fullerror = err;
                        res.message = "cannot able to register try again";
                        stat = 500;
                    }
                    else{
                        res.data = generateWebToken(name,email,phoneno,passwordhash.salt,address);
                        res.fullerror = null;
                        res.message = "success";
                        stat = 200;
                    }


                    response.status(stat).json(res);




                    });
                }

            });
        }
    });
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