package com.github.wakingrufus.mastodon.client

import com.github.wakingrufus.mastodon.account.createAccountConfig
import com.github.wakingrufus.mastodon.account.createAccountState
import com.github.wakingrufus.mastodon.config.AccountConfig
import com.github.wakingrufus.mastodon.config.ConfigurationHandler
import com.github.wakingrufus.mastodon.config.FileConfigurationHandler
import com.github.wakingrufus.mastodon.data.AccountState
import com.github.wakingrufus.mastodon.data.OAuthModel
import com.sys1yagi.mastodon4j.MastodonClient
import com.sys1yagi.mastodon4j.api.Scope
import com.sys1yagi.mastodon4j.api.entity.Notification
import com.sys1yagi.mastodon4j.api.entity.auth.AccessToken
import com.sys1yagi.mastodon4j.api.method.Accounts
import com.sys1yagi.mastodon4j.api.method.Apps
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}
fun getOAuthUrl(oauthModel: OAuthModel): String {
    val apps = Apps(oauthModel.client)
    return apps.getOAuthUrl(
            clientId = oauthModel.appRegistration.clientId,
            scope = Scope(Scope.Name.ALL),
            redirectUri = oauthModel.appRegistration.redirectUri)
}

fun completeOAuth(oAuth: OAuthModel,
                  onComplete: (AccountState) -> Unit,
                  configHandler: ConfigurationHandler = FileConfigurationHandler(),
                  accountStateCreator: (MastodonClient, (Notification) -> Unit) -> AccountState = ::createAccountState,
                  accountConfigCreator: (Accounts, String, String, String, String) -> AccountConfig = ::createAccountConfig,
                  accessTokenBuilder: (OAuthModel) -> AccessToken = ::getAccessToken,
                  accountClientCreator: (String, String) -> MastodonClient = ::createAccountClient) {
    val accessToken = accessTokenBuilder.invoke(oAuth)
    val accountClient = accountClientCreator.invoke(oAuth.client.getInstanceName(), accessToken.accessToken)
    val accountState = accountStateCreator.invoke(accountClient, {System.out.println(it.toString())})
    configHandler.saveConfig(configHandler.addAccountToConfig(
            configHandler.readFileConfig(),
            accountConfigCreator.invoke(
                    Accounts(accountClient),
                    accessToken.accessToken,
                    oAuth.appRegistration.clientId,
                    oAuth.appRegistration.clientSecret,
                    accountClient.getInstanceName())))
    onComplete.invoke(accountState)
}