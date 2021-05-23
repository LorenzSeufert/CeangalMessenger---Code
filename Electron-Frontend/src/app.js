const express = require("express");
const bodyParser = require("body-parser");
const ejs = require("ejs");
const path = require("path");

const app = express();

app.use(express.static(__dirname + "/public"));
app.set('views', path.join(__dirname, '../src/views'));
app.set("view engine", "ejs");
app.use(bodyParser.urlencoded({ extended: true }));



app.get("/", function (req,res){
    res.render("login")
});

app.post("/login", function (req, res){
    console.log(req.body.username)
    console.log(req.body.password)
    res.render("profile")
});

app.get("/signup", function (req, res){
    res.render("signup")
});

app.post("/signup", function (req,res){
    // console.log(req.body.email)
    // console.log(req.body.password1)
    // console.log(req.body.password2)
    // console.log(req.body.username)
    // console.log(req.body.birthdate)
    // console.log(req.body.description)
    console.log(req)
});

app.listen(3841, function () {
    console.log("Server started on port 3841.");
});