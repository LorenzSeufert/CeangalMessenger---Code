import Stomp from "webstomp-client";

export const webSocket = {
    received_messages: [],
    connected: false,
    server: "http://127.0.0.1:8082",
    connect: function(url) {
        if (this.isConnected()) {
            return;
        }
        this.stompClient = Stomp.client("ws://127.0.0.1:8082" + url);
        this.stompClient.connect(
            { Authorization: `Bearer ${token}` },
            frame => {
                this.connected = true;
                console.log("Connected" + frame);
                this.stompClient.subscribe("/topic/chat", tick => {
                    console.log(tick);
                    this.received_messages.push(JSON.parse(tick.body).content);
                });
            },
            error => {
                console.log(error);
                this.connected = false;
            }
        );
    },
    send: function(url, payload) {
        if(!this.isConnected()) {
            return;
        }
        console.log(JSON.stringify(payload));
        this.stompClient.send("/app" + url, JSON.stringify(payload))
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