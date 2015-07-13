package com.stevesie.adwordstographite

import com.google.api.ads.common.lib.auth.OfflineCredentials

object AdWordsToGraphite {

  def main(args: Array[String]): Unit = {
    println("AdWords to Graphite")

    val oAuth2Credential = (new OfflineCredentials.Builder)
      .forApi(OfflineCredentials.Api.ADWORDS)
      .fromFile
      .build
      .generateCredential

    println("Credentials are:")
    println(oAuth2Credential)
  }
}
