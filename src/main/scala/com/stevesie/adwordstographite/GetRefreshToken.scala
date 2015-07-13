package com.stevesie.adwordstographite

import java.io.{InputStreamReader, BufferedReader}

import com.google.api.ads.common.lib.auth.GoogleClientSecretsBuilder
import com.google.api.ads.common.lib.auth.GoogleClientSecretsBuilder.Api
import com.google.api.client.googleapis.auth.oauth2.{GoogleCredential, GoogleAuthorizationCodeFlow}
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.common.collect.Lists

//instructions: https://github.com/googleads/googleads-java-lib/wiki/Using-OAuth2.0
//https://github.com/googleads/googleads-java-lib/blob/master/examples/adwords_axis/src/main/java/adwords/axis/auth/GetRefreshToken.java
object GetRefreshToken {

  val SCOPE = "https://www.googleapis.com/auth/adwords"
  val CALLBACK_URL = "urn:ietf:wg:oauth:2.0:oob"

  def main(args: Array[String]): Unit = {

    val clientSecrets = (new GoogleClientSecretsBuilder)
      .forApi(Api.ADWORDS)
      .fromFile
      .build

    val authorizationFlow = new GoogleAuthorizationCodeFlow.Builder(
      new NetHttpTransport,
      new JacksonFactory,
      clientSecrets,
      Lists.newArrayList(SCOPE))
      // Set the access type to offline so that the token can be refreshed.
      // By default, the library will automatically refresh tokens when it
      // can, but this can be turned off by setting
      // api.adwords.refreshOAuth2Token=false in your ads.properties file.
      .setAccessType("offline").build

    val authorizeUrl =
      authorizationFlow.newAuthorizationUrl.setRedirectUri(CALLBACK_URL).build
    println("Paste this url in your browser: \n" + authorizeUrl + '\n')

    // Wait for the authorization code.
    println("Type the code you received here: ")
    val authorizationCode = new BufferedReader(new InputStreamReader(System.in)).readLine

    // Authorize the OAuth2 token.
    val tokenRequest =
      authorizationFlow.newTokenRequest(authorizationCode)
    tokenRequest.setRedirectUri(CALLBACK_URL)
    val tokenResponse = tokenRequest.execute

    // Create the OAuth2 credential.
    val credential = (new GoogleCredential.Builder)
      .setTransport(new NetHttpTransport)
      .setJsonFactory(new JacksonFactory)
      .setClientSecrets(clientSecrets)
      .build

    // Set authorized credentials.
    credential.setFromTokenResponse(tokenResponse)

    printf("Your refresh token is: %s\n", credential.getRefreshToken)

    // Enter the refresh token into your ads.properties file.
    printf("In your ads.properties file, modify:\n\napi.adwords.refreshToken=%s\n",
      credential.getRefreshToken)
  }
}
