package io.mishkav.app_client.serviceException

/**
 * Свой ServiceException, чтобы можно было выкидывать при создании Intent'а
 *
 * @author Ворожцов Михаил
 */
class ServiceException(message: String) : Exception(message)