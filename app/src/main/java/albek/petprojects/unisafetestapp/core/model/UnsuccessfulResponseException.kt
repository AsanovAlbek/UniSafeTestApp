package albek.petprojects.unisafetestapp.core.model

class UnsuccessfulResponseException(
    message: String? = null,
    cause: Throwable? = null
) : Exception(message, cause)