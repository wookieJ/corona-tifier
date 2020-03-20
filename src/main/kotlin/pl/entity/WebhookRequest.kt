package pl.entity

data class WebhookRequest(
    var originalDetectIntentRequest: OriginalDetectIntentRequest?,
    var queryResult: QueryResult?,
    var responseId: String?,
    var session: String?
)

data class QueryResult(
    var action: String?,
    var allRequiredParamsPresent: Boolean?,
    var diagnosticInfo: Map<String, Any>?,
    var fulfillmentMessages: List<Any>?,
    var fulfillmentText: String?,
    var intent: Intent?,
    var intentDetectionConfidence: Float?,
    var languageCode: String?,
    var outputContexts: List<Context>?,
    var parameters: Map<String, Any>?,
    var queryText: String?,
    var speechRecognitionConfidence: Float?,
    var webhookPayload: Map<String, Any>?,
    var webhookSource: String?
)

data class Context(
    var lifespanCount: Int?,
    var name: String?,
    var parameters: Map<String, Any>?
)

data class Intent(
    var action: String?,
    var defaultResponsePlatforms: List<String>?,
    var displayName: String?,
    var events: List<String>?,
    var followupIntentInfo: List<Any>?,
    var inputContextNames: List<String>?,
    var isFallback: Boolean?,
    var messages: List<IntentMessage>?,
    var mlDisabled: Boolean?,
    var name: String?,
    var outputContexts: List<Context>?,
    var parameters: List<IntentParameter>?,
    var parentFollowupIntentName: String?,
    var priority: Int?,
    var resetContexts: Boolean?,
    var rootFollowupIntentName: String?,
    var trainingPhrases: List<Any>?,
    var webhookState: String?
)

data class IntentParameter(
    var defaultValue: String?,
    var displayName: String?,
    var entityTypeDisplayName: String?,
    var isList: Boolean?,
    var mandatory: Boolean?,
    var name: String?,
    var prompts: List<String>?,
    var value: String?
)

data class IntentMessage(
    var basicCard: Any?,
    var card: Any?,
    var carouselSelect: Any?,
    var image: Any?,
    var linkOutSuggestion: Any?,
    var listSelect: Any?,
    var payload: Map<String, Any>?,
    var platform: String?,
    var quickReplies: Any?,
    var simpleResponses: Any?,
    var suggestions: Any?,
    var text: Any?
)

data class OriginalDetectIntentRequest(
    var payload: Map<String, Any>?, var source: String?
)
