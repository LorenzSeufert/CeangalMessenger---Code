const express = require("express");
const bodyParser = require("body-parser");
const ejs = require("ejs");
const path = require("path");
const hash = require("crypto");
const axios = require("axios");

const app = express();

app.use(express.static(__dirname + "/public"));
app.set("views", path.join(__dirname, "../src/views"));
app.set("view engine", "ejs");
app.use(bodyParser.urlencoded({ extended: true }));
app.disable("x-powered-by");

const apiUrl = "http://localhost:8080/api"

let sessionId = "";

let user = {
    id: 0,
    username: "",
    password: "",
    email: "",
    birthdate: "",
    description: ""
}


app.get("/", function (req,res){
    res.render("loginPage",{
        error: "false",
        errorMessage: ""
    })
});

app.post("/login", function (req, res){
    let notEmpty = true;

    if(req.body.email === ""){
        res.render("loginPage",{
            error: "info",
            errorMessage: "Missing email!"
        })
        notEmpty = false
    }
    if(req.body.password === ""){
        res.render("loginPage",{
            error: "info",
            errorMessage: "Missing password!"
        })
        notEmpty = false
    }
    if(notEmpty){

        let payload = {
            email: (req.body.email).toString(),
            password: hash.createHash('sha256').update(req.body.password).digest('hex')
        }

        axios.post(apiUrl + "/user/login", payload).then(function (response) {
            sessionId = response.headers["sessionid"]
            user = response.data

            res.render("profilePage",{
                error: "false",
                errorMessage: "",
                username: user.username,
                email: user.email,
                birthdate: user.birthdate,
                description: user.description
            })
        }).catch(function (error){
            if (error.response.status === 404){
                res.render("loginPage",{
                    error: "error",
                    errorMessage: "Username not found!"
                })
            }else if (error.response.status === 403){
                res.render("loginPage",{
                    error: "error",
                    errorMessage: "Login credentials not matching!"
                })
            }else{
                res.render("loginPage",{
                    error: "error",
                    errorMessage: "Something is not working!"
                })
            }
        })
    }
});

app.get("/signup", function (req, res){
    res.render("signupPage",{
        error: "false",
        errorMessage: "",
        email: "",
        password1: "",
        password2: "",
        username: "",
        description: ""
    })
});

app.post("/signup", function (req,res){
    let isValid = true;

    if(req.body.email === ""){
        res.render("signupPage",{
            error: "info",
            errorMessage: "Missing email!",
            email: req.body.email,
            password1: req.body.password1,
            password2: req.body.password2,
            username: req.body.username,
            description: req.body.description
        })
        isValid = false
    }
    if(req.body.password1 === ""){
        res.render("signupPage",{
            error: "info",
            errorMessage: "Missing password!",
            email: req.body.email,
            password1: req.body.password1,
            password2: req.body.password2,
            username: req.body.username,
            description: req.body.description
        })
        isValid = false
    }
    if(req.body.password2 === ""){
        res.render("signupPage",{
            error: "info",
            errorMessage: "Missing password confirmation!",
            email: req.body.email,
            password1: req.body.password1,
            password2: req.body.password2,
            username: req.body.username,
            description: req.body.description
        })
        isValid = false
    }
    if(req.body.username === ""){
        res.render("signupPage",{
            error: "info",
            errorMessage: "Missing username!",
            email: req.body.email,
            password1: req.body.password1,
            password2: req.body.password2,
            username: req.body.username,
            description: req.body.description
        })
        isValid = false
    }
    if(req.body.birthdate === ""){
        res.render("signupPage",{
            error: "info",
            errorMessage: "Missing birthdate!",
            email: req.body.email,
            password1: req.body.password1,
            password2: req.body.password2,
            username: req.body.username,
            description: req.body.description
        })
        isValid = false
    }
    if(req.body.description === ""){
        res.render("signupPage",{
            error: "info",
            errorMessage: "Missing description!",
            email: req.body.email,
            password1: req.body.password1,
            password2: req.body.password2,
            username: req.body.username,
            description: req.body.description
        })
        isValid = false
    }
    if (req.body.password1 !== req.body.password2){
        res.render("signupPage",{
            error: "error",
            errorMessage: "Your passwords are not matching!",
            email: req.body.email,
            password1: "",
            password2: "",
            username: req.body.username,
            description: req.body.description
        })
        isValid = false
    }
    if(isValid){
        let pw = hash.createHash('sha256').update(req.body.password1).digest('hex')


        res.render("loginPage",{
            error: "false",
            errorMessage: ""
        })
    }
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