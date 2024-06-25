package com.joaoxstone.stoneroyale.core.http

object ErrorResponses {

    const val NOT_FOUND = 404
    const val INTERNAL_SERVER_ERROR = 500
    const val BAD_REQUEST = 400
    const val REQUEST_TIMEOUT = 408

    fun getStatusCodeMessage(statusCode: Int): String {
        return when (statusCode) {
            NOT_FOUND -> "Não foi encontrado"
            INTERNAL_SERVER_ERROR -> "Erro ao acessar recurso, tente novamente mais tarde"
            BAD_REQUEST -> "Erro ao acessar recurso,verifique a conexão com a internet"
            REQUEST_TIMEOUT -> "Tempo esgotado, tente novamente"
            else -> "Erro ao acessar recurso, tente novamente mais tarde "
        }
    }
}