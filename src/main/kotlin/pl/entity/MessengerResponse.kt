package pl.entity

data class MessengerResponse(
    var recipient: Recipient,
    var message: Message
)

data class Recipient(
    var id: String
)

data class Message(
    var text: String
)
