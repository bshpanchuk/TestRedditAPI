package com.bshpanchuk.testyalantis.common

import android.content.Context
import com.bshpanchuk.testyalantis.domain.model.Credentials
import org.xmlpull.v1.XmlPullParser

/**
 *  Auxiliary class for Android-Reddit-OAuth2
 */
object RedditAuthUtils {

    private const val FILE_NAME = "credentials"
    private const val FILE_DEFTYPE = "xml"

    fun loadCredentials(context: Context): Credentials {

        val isTravis = System.getenv("TRAVIS") != null && System.getenv("TRAVIS")!!.toBoolean()
        if (isTravis) {
            return loadCredentialsFromTravis()
        }

        val credentialsId = context.resources.getIdentifier(FILE_NAME, FILE_DEFTYPE, context.packageName)
        if (credentialsId != 0) {
            return loadCredentialsFromXmlFile(context)
        }

        throw IllegalStateException("Cannot find credentials in Travis nor .xml file!")
    }

    private fun loadCredentialsFromTravis(): Credentials {

        val clientId = System.getenv("clientId")
        val redirectUrl = System.getenv("redirectUrl")

        val scriptClientId = System.getenv("scriptClientId")
        val scriptClientSecret = System.getenv("scriptClientSecret")

        val username = System.getenv("username")
        val password = System.getenv("password")

        val scopes = ArrayList<String>()
        scopes.addAll(System.getenv("scopes")?.split(',') ?: emptyList())

        return Credentials(
            clientId = clientId!!,
            redirectUrl = redirectUrl!!,

            scriptClientId = scriptClientId!!,
            scriptClientSecret = scriptClientSecret!!,
            username = username!!,
            password = password!!,

            scopes
        )
    }

    private fun loadCredentialsFromXmlFile(context: Context): Credentials {

        val credentialsId = context.resources.getIdentifier(FILE_NAME, FILE_DEFTYPE, context.packageName)
        val xpp = context.resources.getXml(credentialsId)

        var clientId = ""
        var redirectUrl = ""

        var scriptClientId = ""
        var scriptClientSecret = ""
        var username = ""
        var password = ""

        val scopes = ArrayList<String>()

        while (xpp.eventType != XmlPullParser.END_DOCUMENT) {

            when (xpp.eventType) {

                XmlPullParser.START_TAG -> {

                    when (xpp.name) {
                        "clientId" -> clientId = xpp.nextText()
                        "redirectUrl" -> redirectUrl = xpp.nextText()
                        "scope" -> scopes.add(xpp.nextText())
                        "scriptClientId" -> scriptClientId = xpp.nextText()
                        "scriptClientSecret" -> scriptClientSecret = xpp.nextText()
                        "username" -> username = xpp.nextText()
                        "password" -> password = xpp.nextText()
                    }
                }
            }

            xpp.next()
        }

        return Credentials(
            clientId,
            redirectUrl,

            scriptClientId,
            scriptClientSecret,
            username,
            password,

            scopes
        )
    }
}
