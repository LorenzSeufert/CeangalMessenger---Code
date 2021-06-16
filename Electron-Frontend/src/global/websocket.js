import Stomp from "webstomp-client";

export const webSocket = {
    received_messages: [],
    connected: false,
    server: "http://127.0.0.1:8082",

    connect: function(friendIds) {
        if (this.isConnected()) {
            return;
        }
        this.stompClient = Stomp.client("ws://127.0.0.1:8082/messages");
        this.stompClient.connect(
            { Authorization: `Bearer ${token}` },
            frame => {
                this.connected = true;

                if(friendIds !== null) {
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

    send: function(channelId, payload) {
        if(!this.isConnected()) {
            this.connect(null)
        }
        console.log(JSON.stringify(payload));
        this.stompClient.send("/app/channel/" + channelId, JSON.stringify(payload))
    },

    joinChannel: function(channelId, payload) {
        if(!this.isConnected()) {
            this.connect(null);
        }
        console.log(JSON.stringify(payload));
        this.stompClient.subscribe("/topic/channel/" + channelId, tick => {
            console.log(tick);
            this.received_messages.push(JSON.parse(tick.body).content);
        })
    },

    leaveChannel: function(channelId, payload) {
        if(!this.isConnected()) {
            this.connect(null);
        }
        console.log(JSON.stringify(payload));
        this.stompClient.send("/app/channel/" + channelId, JSON.stringify(payload))
        this.stompClient.unsubscribe("/topic/channel/" + channelId)
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