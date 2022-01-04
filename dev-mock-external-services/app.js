var express = require("express");
var app = express();
app.use(express.json())

function makeid(length) {
    var result           = '';
    var characters       = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
    var charactersLength = characters.length;
    for ( var i = 0; i < length; i++ ) {
        result += characters.charAt(Math.floor(Math.random() * charactersLength));
    }
    return result;
}

function sleep(seconds) 
{
  var e = new Date().getTime() + (seconds * 1000);
  while (new Date().getTime() <= e) {}
}

app.listen(3000, () => {
    console.log("Server running on port 3000");
});

app.post("/payment", (req, res, next) => {
    console.info(req.body);

    sleep(5);
    res.json(
        {
            hash: makeid(40)
        }
    );
});

app.post("/booking-service", (req, res) => {
    console.info(req.body);
    res.json(
        {
            hash: makeid(20), 
            httpSessionId: req.body.httpSessionId
        }
    );
});