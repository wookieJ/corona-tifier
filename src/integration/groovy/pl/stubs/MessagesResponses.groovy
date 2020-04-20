package pl.stubs

class MessagesResponses {

    static String successfullySentMessageResponse(def recipientId, def messageId) {
        """
        {
          "recipient_id": "${recipientId}",
          "message_id": "${messageId}"
        }
        """
    }
}
