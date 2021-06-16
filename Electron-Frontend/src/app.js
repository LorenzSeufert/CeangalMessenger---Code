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

let friends = {}

let textChannels = []

function joinMessage(senderId, sender) {
    return {
        type: "JOIN",
        content: "",
        senderId: senderId,
        sender: sender
    }
}

const webSocket = {
    received_messages: [],
    connected: false,
    server: "http://127.0.0.1:8082",

    connect: function (friendIds) {
        if (this.isConnected()) {
            return;
        }
        this.stompClient = Stomp.client("ws://127.0.0.1:8082/messages");
        this.stompClient.connect(
            {},
            frame => {
                this.connected = true;

                if (friendIds !== null) {
                    friendIds.forEach(id =>
                        this.stompClient.subscribe("/topic/channel/" + id, tick => {
                            console.log(tick);
                            this.received_messages.push(JSON.parse(tick.body).content);
                        })
                    )
                }

                console.log("Connected" + frame);
            },
            error => {
                console.log(error);
                this.connected = false;
            }
        );
    },

    send: function (channelId, payload) {
        if (!this.isConnected()) {
            this.connect(null)
        }
        console.log(JSON.stringify(payload));
        this.stompClient.send("/app/channel/" + channelId, JSON.stringify(payload))
    },

    joinChannel: function (channelId, payload) {
        if (!this.isConnected()) {
            this.connect(null);
        }
        console.log(JSON.stringify(payload));
        this.stompClient.subscribe("/topic/channel/" + channelId, tick => {
            console.log(tick);
            this.received_messages.push(JSON.parse(tick.body).content);
        })
    },

    leaveChannel: function (channelId, payload) {
        if (!this.isConnected()) {
            this.connect(null);
        }
        console.log(JSON.stringify(payload));
        this.stompClient.send("/app/channel/" + channelId, JSON.stringify(payload))
        this.stompClient.unsubscribe("/topic/channel/" + channelId).then(tick => {
            console.log(tick);
            this.received_messages.push(JSON.parse(tick.body).content);
        })
    },

    isConnected() {
        if (this.connected === true) {
            console.log("Already connected to Websocket.");
            return true;
        } else {
            console.log("Not Connected to Websocket.");
            return false;
        }
    }
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

        let payload = {
            username: req.body.username,
            password: pw,
            email: req.body.email,
            birthdate: req.body.birthdate,
            description: req.body.description
        }

        axios.post(apiUrl + "/user/createUser", payload).then(function (response) {

            res.render("loginPage",{
                error: "success",
                errorMessage: "User creating was successful!"
            })
        }).catch(function (error){
            if (error.response.data === "Email and Username already exists"){
                res.render("signupPage",{
                    error: "error",
                    errorMessage: "Username and Email already exist! Please use something else!",
                    email: "",
                    password1: "",
                    password2: "",
                    username: "",
                    description: req.body.description
                })
            }else if (error.response.data === "Username already exists"){
                res.render("signupPage",{
                    error: "error",
                    errorMessage: "Username already exist! Please use something else!",
                    email: req.body.email,
                    password1: "",
                    password2: "",
                    username: "",
                    description: req.body.description
                })
            }else if (error.response.data === "Email already exists"){
                res.render("signupPage",{
                    error: "error",
                    errorMessage: "Email already exist! Please use something else!",
                    email: "",
                    password1: "",
                    password2: "",
                    username: req.body.username,
                    description: req.body.description
                })
            }else{
                res.render("signupPage",{
                    error: "error",
                    errorMessage: "Something is not working! Please try again",
                    email: req.body.email,
                    password1: "",
                    password2: "",
                    username: req.body.username,
                    description: req.body.description
                })
            }
        })
    }
});

app.get("/editProfile", function (req,res){
   res.render("editProfilePage",{
       error: "false",
       errorMessage: "",
       username: user.username
   })
});

app.get("/profile", function (req,res){
    res.render("profilePage",{
        error: "false",
        errorMessage: "",
        username: user.username,
        email: user.email,
        birthdate: user.birthdate,
        description: user.description
    })
});

app.get("/logout", function (req,res){

    axios.delete(apiUrl + "/user/logout",{
        headers: {
            "id": sessionId
        }
    }).then(function (response) {
        sessionId = ""

        res.render("loginPage",{
            error: "success",
            errorMessage: "Logged out successfully!"
        })
    }).catch(function (error){
        res.render("profilePage",{
            error: "error",
            errorMessage: "Something went wrong. Please try again!",
            username: user.username,
            email: user.email,
            birthdate: user.birthdate,
            description: user.description
        })
    })
});

app.get("/friends", function (req,res){

    axios.get(apiUrl + "/user/getFriends",{
        headers: {
            "id": sessionId
        }
    }).then(function (response) {
        friends = response.data

        res.render("friendPage", {
            error: "false",
            errorMessage: "false",
            friends: friends
        })

    }).catch(function (error){
        res.render("profilePage",{
            error: "false",
            errorMessage: "",
            username: user.username,
            email: user.email,
            birthdate: user.birthdate,
            description: user.description
        })
    })
});

app.post("/addFriend", function (req,res){

    axios.get(apiUrl + "/user/addFriend",{
        headers: {
            "id": sessionId,
            "friendName": req.body.friendToAdd
        }
    }).then(function (response) {
        updateFriends().then(function (){

            res.render("friendPage",{
                error: "success",
                errorMessage: "Friend successfully added!",
                friends: friends
            })
        }).then(function (response){
            let friendId = ""

            for(var i = 0; i < friends.length; i++)
            {
                if(friends[i].username === req.body.friendToAdd)
                {
                    friendId = friends[i].id;
                    break
                }
            }

            createTextChannel(req.body.friendToAdd, friendId).then(function (response) {
                console.log("Text channel created!")
            })
        })
    }).catch(function (error){
        res.render("friendPage",{
            error: "error",
            errorMessage: "Username not found. Could not add as friend!",
            friends: friends
        })
    })


});

function createTextChannel(friend,friendId){
    return new Promise(function (fulfill, reject){

        let payload = {
            name: friend + " - " + user.username,
            usersName: [
                friend, user.username
            ],
            messages: [
               joinMessage(user.id,user.username), joinMessage(friendId,friend)
            ]
        }

        axios.post(apiUrl + "/textChannel/create",payload)
            .then(function (response) {
            textChannels.push(response.data)
            fulfill(response.data)
        }).catch(function (error){
            reject(error)
        })
    });
}

app.post("/removeFriend", function (req,res){

    axios.delete(apiUrl + "/user/removeFriend",{
        headers: {
            "id": sessionId,
            "friendName": req.body.deleteName
        }
    }).then(function (response) {
        updateFriends().then( function () {
            res.render("friendPage",{
                error: "success",
                errorMessage: "Friend successfully removed!",
                friends: friends
            })
        })
    }).catch(function (error){
        res.render("friendPage",{
            error: "error",
            errorMessage: "Something went wrong! Please try again!",
            friends: friends
        })
    })
});

function updateFriends(){
    return new Promise(function (fulfill, reject){
        axios.get(apiUrl + "/user/getFriends",{
            headers: {
                "id": sessionId
            }
        }).then(function (response) {
            friends = response.data
            fulfill(response.data)
        }).catch(function (error){
            reject(error)
        })
    });
}

app.get("/chats", function (req,res){
    res.render("chatPage", {
        textChannels: textChannels
    })
});

app.post("/openChat", function (req,res){
    console.log(req.body.chatWith)
    res.render("chat")
});

app.post("/saveData", function (req,res){

    let payload = {
        username: req.body.newUsername,
        password: "",
        email: req.body.newEmail,
        birthdate: "",
        description: req.body.newDescription
    }

    axios.put(apiUrl + "/user/editUser", payload, {
        headers: {
            "id": sessionId
        }
    }).then(function (response) {
        user = response.data

        res.render("editProfilePage",{
            error: "success",
            errorMessage: "Profile updated successfully!",
            username: user.username
        })
    }).catch(function (error){

        if (error.response.data === "Email and Username already exists"){
            res.render("editProfilePage",{
                error: "error",
                errorMessage: "Username and Email already exist! Please use something else!",
                username: user.username
            })
        }else if (error.response.data === "Username already exists"){
            res.render("editProfilePage",{
                error: "error",
                errorMessage: "Username already exist! Please use something else!",
                username: user.username
            })
        }else if (error.response.data === "Email already exists"){
            res.render("editProfilePage",{
                error: "error",
                errorMessage: "Email already exist! Please use something else!",
                username: user.username
            })
        }else{
            res.render("editProfilePage",{
                error: "error",
                errorMessage: "Something went wrong. Please try again!",
                username: user.username
            })
        }
    })
});

app.post("/deleteAccount", function (req,res){

    axios.delete(apiUrl + "/user/deleteUser",{
        headers: {
            "id": sessionId
        }
    }).then(function (response) {
        user = {
            id: 0,
            username: "",
            password: "",
            email: "",
            birthdate: "",
            description: ""
        }

        sessionId = ""

        res.render("loginPage",{
            error: "success",
            errorMessage: "User deleted successfully!"
        })
    }).catch(function (error){
        res.render("editProfilePage",{
            error: "error",
            errorMessage: "Something went wrong. Please try again!",
            username: user.username
        })
    })
});

app.listen(3841, function () {
    console.log("Server started on port 3841.");
});