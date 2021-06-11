const express = require("express");
const bodyParser = require("body-parser");
const ejs = require("ejs");
const path = require("path");

const app = express();

app.use(express.static(__dirname + "/public"));
app.set('views', path.join(__dirname, '../src/views'));
app.set("view engine", "ejs");
app.use(bodyParser.urlencoded({ extended: true }));
app.disable('x-powered-by');


app.get("/", function (req,res){
    res.render("loginPage",{
        error: false
    })
});

app.post("/login", function (req, res){
    console.log(req.body.username)
    console.log(req.body.password)
    res.render("loginPage",{
        error: 'Something went wrong'
    })
});

app.get("/signup", function (req, res){
    res.render("signupPage")
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

app.get("/editProfile", function (req,res){
   res.render("editProfilePage")
});

app.get("/profile", function (req,res){
    res.render("profilePage")
});

app.get("/friends", function (req,res){
    res.render("friendPage")
});

app.get("/chats", function (req,res){
    res.render("chatPage")
});

app.post("/saveData", function (req,res){
    console.log(req.body.newUsername)
    console.log(req.body.newEmail)
    console.log(req.body.newDescription)
});

app.post("/deleteAccount", function (req,res){
    console.log("DELETE ACCOUNT")
});

app.listen(3841, function () {
    console.log("Server started on port 3841.");
});