/*
const Stomp = require('@stomp/stompjs');

class WebSocket {
    constructor(received_messages, connected, server) {
        this.received_messages = received_messages
        this.connected = connected
        this.server = server
    }
    /!*received_messages = [];
    connected = false;
    server = "http://127.0.0.1:8082";
*!/
    connect(friendIds) {
        if (this.isConnected()) {
            return;
        }
        const stompClient = new Stomp.Client("ws://127.0.0.1:8080/messages");

        //this.stompClient = client.connect()//Stomp.client("ws://127.0.0.1:8080/messages");
        stompClient.connect(
            {},
            frame => {
                this.connected = true;

                if (friendIds !== null) {
                    friendIds.forEach(id =>
                        stompClient.subscribe("/topic/channel/" + id, tick => {
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
    };

    send(channelId, payload) {
        if (!this.isConnected()) {
            this.connect(null)
        }
        console.log(JSON.stringify(payload));
        this.stompClient.send("/app/channel/" + channelId, JSON.stringify(payload))
    };

    joinChannel(channelId, payload) {
        if (!this.isConnected()) {
            this.connect(null);
        }
        console.log(JSON.stringify(payload));
        this.stompClient.subscribe("/topic/channel/" + channelId, tick => {
            console.log(tick);
            this.received_messages.push(JSON.parse(tick.body).content);
        })
    };

    leaveChannel(channelId, payload) {
        if (!this.isConnected()) {
            this.connect(null);
        }
        console.log(JSON.stringify(payload));
        this.stompClient.send("/app/channel/" + channelId, JSON.stringify(payload))
        this.stompClient.unsubscribe("/topic/channel/" + channelId).then(tick => {
            console.log(tick);
            this.received_messages.push(JSON.parse(tick.body).content);
        })
    };

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

module.exports = WebSocket*/
//