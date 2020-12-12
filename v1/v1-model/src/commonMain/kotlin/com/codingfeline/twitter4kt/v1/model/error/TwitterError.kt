/**
 * Copyright 2020 Shimizu Yasuhiro (yshrsmz)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.codingfeline.twitter4kt.v1.model.error

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Suppress("unused")
@Serializable
public data class TwitterError(
    @SerialName("code") public val rawCode: Int,
    @SerialName("message") public val message: String,
) {
    public val code: Code
        get() = Code.from(rawCode)

    override fun toString(): String {
        return "TwitterError(rawCode=$rawCode, code=$code, message=$message)"
    }

    /**
     * https://developer.twitter.com/ja/docs/basics/response-codes
     */
    public enum class Code(public val value: Int) {
        InvalidCoordinates(3),
        NoLocationWithTheIP(13),
        NoUserMatched(17),
        CouldNotAuthenticate(32),
        PageNotFound(34),
        CanNotReportYourself(36),
        ParameterMissing(38),
        InvalidAttachmentUrl(44),
        UserNotFound(50),
        UserSuspended(63),
        YourAccountSuspended(64),
        V1ApiIsNoLongerActive(68),
        ActionNotPermitted(87),
        RateLimitExceeded(88),
        InvalidOrExpiredToken(89),
        SSLRequired(92),
        CannotAccessDM(93),
        UnableToVerifyCredential(99),
        AccountUpdateFailed(99),
        OverCapacity(130),
        InternalError(131),
        TimestampOutOfBounds(135),
        AlreadyFavorited(144),
        CanNotSendDMToNonFollower(150),
        ErrorSendingDM(160),
        UnableToFollow(161),
        CanNotSeeStatus(179),
        DailyStatusUpdateLimit(185),
        StatusIsTooLong(186),
        DuplicateStatus(187),
        InvalidUrlParameter(195),
        SpamReportLimit(205),
        DMNotOpen(214),
        BadAuthenticationData(215),
        RestrictedAuthentication(220),
        RequestMightBeAutomated(226),
        UserMustVerifyLogin(231),
        EndpointRetired(251),
        CanNotPerformWriteAction(261),
        CanNotMuteYourself(271),
        NotHaveBeenMuted(272),
        AnimatedGIFNotAllowed(323),
        InvalidMediaIds(324),
        MediaIdNotFound(325),
        TemporarilyLocked(326),
        AlreadyRetweeted(327),
        CanNotSendDM(349),
        DMTooLong(354),
        SubscriptionAlreadyExists(355),
        CanNotReply(385),
        MultipleAttachmentTypes(386),
        InvalidUrl(407),
        CallbackUrlNotApproved(415),
        InvalidApplication(416),
        DesktopAppOnlySupportOob(417),
        Unknown(-1);

        public companion object {
            public fun from(rawCode: Int): Code {
                return values().firstOrNull { it.value == rawCode } ?: Unknown
            }
        }
    }
}
