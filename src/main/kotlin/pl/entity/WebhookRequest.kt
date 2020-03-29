package pl.entity

data class WebhookRequest(
    var originalDetectIntentRequest: OriginalDetectIntentRequest? = null,
    var queryResult: QueryResult? = null,
    var responseId: String? = null,
    var session: String?
)

data class QueryResult(
    var action: String? = null,
    var allRequiredParamsPresent: Boolean? = null,
    var diagnosticInfo: Map<String, Any>? = null,
    var fulfillmentMessages: List<Any>? = null,
    var fulfillmentText: String? = null,
    var intent: Intent? = null,
    var intentDetectionConfidence: Float? = null,
    var languageCode: String? = null,
    var outputContexts: List<Context>? = null,
    var parameters: Map<String, Any>? = null,
    var queryText: String? = null,
    var speechRecognitionConfidence: Float? = null,
    var webhookPayload: Map<String, Any>? = null,
    var webhookSource: String? = null
)

data class Context(
    var lifespanCount: Int? = null,
    var name: String? = null,
    var parameters: Map<String, Any>? = null
)

data class Intent(
    var action: String? = null,
    var defaultResponsePlatforms: List<String>? = null,
    var displayName: String? = null,
    var events: List<String>? = null,
    var followupIntentInfo: List<Any>? = null,
    var inputContextNames: List<String>? = null,
    var isFallback: Boolean? = null,
    var messages: List<IntentMessage>? = null,
    var mlDisabled: Boolean? = null,
    var name: String? = null,
    var outputContexts: List<Context>? = null,
    var parameters: List<IntentParameter>? = null,
    var parentFollowupIntentName: String? = null,
    var priority: Int? = null,
    var resetContexts: Boolean? = null,
    var rootFollowupIntentName: String? = null,
    var trainingPhrases: List<Any>? = null,
    var webhookState: String? = null
)

data class IntentParameter(
    var defaultValue: String? = null,
    var displayName: String? = null,
    var entityTypeDisplayName: String? = null,
    var isList: Boolean? = null,
    var mandatory: Boolean? = null,
    var name: String? = null,
    var prompts: List<String>? = null,
    var value: String? = null
)

data class IntentMessage(
    var basicCard: Any? = null,
    var card: Any? = null,
    var carouselSelect: Any? = null,
    var image: Any? = null,
    var linkOutSuggestion: Any? = null,
    var listSelect: Any? = null,
    var payload: Map<String, Any>? = null,
    var platform: String? = null,
    var quickReplies: Any? = null,
    var simpleResponses: Any? = null,
    var suggestions: Any? = null,
    var text: Any? = null
)

data class OriginalDetectIntentRequest(
    var payload: Map<String, Any>? = null,
    var source: String? = null
)
