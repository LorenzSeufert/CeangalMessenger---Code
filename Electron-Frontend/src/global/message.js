export const message = {
    chatMessage: function (textMessage) {
        return {
            id: 0,
            type: "CHAT",
            content: textMessage,
            senderId: 0,//TODO
            sender: "" //TODO
        }
    },
    joinMessage: function () {
        return {
            id: 0,
            type: "JOIN",
            content: "",
            senderId: 0, //TODO
            sender: "" //TODO
        }
    },
    leaveMessage: function () {
        return {
            id: 0,
            type: "LEAVE",
            content: "",
            senderId: 0, //TODO
            sender: "" //TODO
        }
    }

}