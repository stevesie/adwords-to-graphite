package com.stevesie.adwordstographite

import com.google.api.ads.adwords.axis.factory.AdWordsServices
import com.google.api.ads.adwords.axis.utils.v201506.SelectorBuilder
import com.google.api.ads.adwords.axis.v201506.cm.{CampaignPage, CampaignServiceInterface}
import com.google.api.ads.adwords.lib.client.AdWordsSession
import com.google.api.ads.adwords.lib.selectorfields.v201506.cm.CampaignField
import com.google.api.ads.common.lib.auth.OfflineCredentials
import com.google.api.ads.common.lib.auth.OfflineCredentials.Api

object AdWordsToGraphite {

  val PAGE_SIZE = 100

  def main(args: Array[String]): Unit = {
    println("AdWords to Graphite")

    val oAuth2Credential = (new OfflineCredentials.Builder)
      .forApi(Api.ADWORDS)
      .fromFile()
      .build()
      .generateCredential

    val session = (new AdWordsSession.Builder)
      .fromFile()
      .withOAuth2Credential(oAuth2Credential)
      .build

    val adWordsServices = new AdWordsServices

    val campaignService =
      adWordsServices.get(session, classOf[CampaignServiceInterface])

    val builder = new SelectorBuilder

    var offset = 0

    var selector = builder
      .fields(CampaignField.Id, CampaignField.Name)
      .orderAscBy(CampaignField.Name)
      .offset(offset)
      .limit(PAGE_SIZE)
      .build

    var page: CampaignPage = null
    do {
      // Get all campaigns.
      page = campaignService.get(selector)

      // Display campaigns.
      if (page.getEntries != null) {
        page.getEntries.foreach { campaign =>
          System.out.println("Campaign with name \"" + campaign.getName + "\" and id \""
            + campaign.getId + "\" was found.");
        }
      } else {
        System.out.println("No campaigns were found.")
      }

      offset += PAGE_SIZE
      selector = builder.increaseOffsetBy(PAGE_SIZE).build
    } while (offset < page.getTotalNumEntries)
  }
}
