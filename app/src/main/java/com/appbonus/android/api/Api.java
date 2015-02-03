package com.appbonus.android.api;

import android.content.Context;

import com.appbonus.android.api.model.LoginRequest;
import com.appbonus.android.api.model.RegisterRequest;
import com.appbonus.android.api.model.ResetPasswordRequest;
import com.appbonus.android.api.model.SimpleRequest;
import com.appbonus.android.api.model.UserRequest;
import com.appbonus.android.model.Offer;
import com.appbonus.android.model.WithdrawalRequest;
import com.appbonus.android.model.api.BalanceWrapper;
import com.appbonus.android.model.api.DataWrapper;
import com.appbonus.android.model.api.HistoryWrapper;
import com.appbonus.android.model.api.LoginWrapper;
import com.appbonus.android.model.api.OfferWrapper;
import com.appbonus.android.model.api.OffersWrapper;
import com.appbonus.android.model.api.QuestionsWrapper;
import com.appbonus.android.model.api.ReferralsDetailsWrapper;
import com.appbonus.android.model.api.ReferralsHistoryWrapper;
import com.appbonus.android.model.api.SimpleResult;
import com.appbonus.android.model.api.UserWrapper;

import java.io.Serializable;

public interface Api extends Serializable {
    static final String HOST_URI = "http://appbonus-staging.herokuapp.com/";
    static final String API_SUFX = "api";
    static final String API_VERSION = "v1";

    static final String SUFX_SIGNIN = "signin";
    static final String SUFX_SIGNUP = "signup";
    static final String SUFX_RESET_PASSWORD = "reset_password";

    static final String SUFX_MY = "my";
    static final String SUFX_BALANCE = "balance";
    static final String SUFX_CONFIRM_PHONE = "confirm_phone";

    static final String SUFX_FAQ = "faq";

    /*
     *  POST /api/v1/signup
        AUTH no
        ROLE no-user
        PARAMS user: {email: "email@email.com",password: "password", country: "russia|ukraine|belarus", phone: "xxxxxxxxx", device_id: "ID"}
        RESPONSE
          {
            "user": user.as_json, "auth_token": "xxxxxxxxxxxxxxxxxxxx"
          }
     */
    LoginWrapper registration(RegisterRequest request) throws Throwable;

    /*
     *  POST /api/v1/signin
        AUTH no
        ROLE no-user
        PARAMS user: {email: "email@email.com",password: "password"}
        RESPONSE
          {
            "user": user.as_json, "auth_token": "xxxxxxxxxxxxxxxxxxxx"
          }
     */
    LoginWrapper login(LoginRequest loginRequest) throws Throwable;

    /*
     *  POST /api/v1/reset_password
        AUTH no
        ROLE no-user
        PARAMS user: {email: "email@email.com"}
        RESPONSE
          {
            success: true
          }
     */
    SimpleResult resetPassword(ResetPasswordRequest request) throws Throwable;

    /*
     *  GET /api/v1/my
        PARAMS auth_token: "xxxxxxxxxxxxxxxxxxxx"
        AUTH required
        ROLE any
        RESPONSE
          {
            user: {
              id: 1
              email: "email@email.com"
              phone: "7777777777",
              country: "russia|ukraine|belarus",
              balance: XXX,
              iphone: true|false,
              ipad: true|false,
              android: true,
              referrer_id: null,
              notify: true|false,
              notify_sound: '',
              phone_confirmed: true|false,
              auth_services: [{
                id: 15,
                user_id: 19,
                provider: "vkontakte",
                url: "http://vk.com/kelion"
              }]
            }
          }
     */
    UserWrapper readProfile(SimpleRequest request) throws Throwable;

    /*
     *  GET /api/v1/my/history
        PARAMS auth_token: "xxxxxxxxxxxxxxxxxxxx"
        AUTH required
        ROLE any
        RESPONSE
          {
            meta: {
              per_page: 10,
              current_page: 1,
              total_pages: 10,
              total_count: 101
            },
            history: [
              {
                amount: XXX
                created_at: "2014-02-28T23:31:17.503+04:00"
                held: false
                id: XXX
                operation_type: "withdrawal"
                user_id: XX,
                offer_title: 'Clash of Clans',
                offer_icon: 'http://....'
              }, {...}
              ...]
     */
    HistoryWrapper readHistory(Context context, String authToken, Long page) throws Throwable;

    /*
     *  GET /api/v1/my/referrals_history
        PARAMS auth_token: "xxxxxxxxxxxxxxxxxxxx"
        AUTH required
        ROLE any
        RESPONSE
          {
            meta: {
              per_page: 10,
              current_page: 1,
              total_pages: 10,
              total_count: 101
            },
            referrals_history: [
                {
                  amount: XXX
                   created_at: "2014-02-28T23:31:17.503+04:00"
                   held: false
                   id: XXX
                   operation_type: "withdrawal"
                   user_id: XX,
                   offer_title: 'Clash of Clans',
                   offer_icon: 'http://....'
                  initiator: {
                    id: 63049
                    email: email@email.com
                  }
                }
                ...
              ]
     */
    ReferralsHistoryWrapper readReferralsHistory(Context context, String authToken, Long page) throws Throwable;

    /*
     *  GET /api/v1/my/balance
        PARAMS auth_token: "xxxxxxxxxxxxxxxxxxxx"
        AUTH required
        ROLE any
        RESPONSE
          {
            balance: {
              active_balance: 1079
              held_balance: 0
              pending_withdrawal: 0  // Баланс на вывод ждущий подтверждения
              referrals_profit: 0
              tasks_profit: 1100
              withdrawal_sum: 253
            }
          }
     */
    BalanceWrapper readBalance(SimpleRequest request) throws Throwable;

    /*
     *  POST /api/v1/my/register_device
        PARAMS auth_token: "xxxxxxxxxxxxxxxxxxxx", device_token: "TOKEN"
        AUTH required
        ROLE any
        RESPONSE
          {
            data: "ok"
          }
     */
    DataWrapper registerDevice(Context context, String authToken, String deviceToken) throws Throwable;

    /*
     *  POST /api/v1/my/unregister_device
        PARAMS auth_token: "xxxxxxxxxxxxxxxxxxxx", device_token: "TOKEN"
        AUTH required
        ROLE any
        RESPONSE
          {
            data: "ok"
          }
     */
    DataWrapper unregisterDevice(Context context, String authToken, String deviceToken) throws Throwable;

    /*
     *  POST /api/v1/my/withdrawal
        PARAMS auth_token: "xxxxxxxxxxxxxxxxxxxx", withdrawal_request: {request_type:"phone|qiwi",amount:"XXXX"}
        AUTH required
        ROLE any
        RESPONSE
          {
            data: "ok|fail",
            info: errors if fails
          }
     */
    DataWrapper makeWithdrawal(Context context, String authToken, WithdrawalRequest request) throws Throwable;

    /*
     *  PATCH /api/v1/my/confirm_phone
        PARAMS -
        AUTH required
        ROLE any
        RESPONSE
          {
            data: "ok|fail",
            info: errors if fails
          }
     */
    DataWrapper confirmPhone(SimpleRequest request) throws Throwable;

    /*
     *  PATCH /api/v1/my
        PARAMS auth_token: "xxxxxxxxxxxxxxxxxxxx", user: {... user_attribures ...}
        AUTH required
        ROLE any
        RESPONSE
          {
            "user": user.as_json
          }
     */
    UserWrapper writeProfile(UserRequest request) throws Throwable;

    /*
     *  POST /api/v1/vk_auth
        AUTH no
        ROLE no-user
        PARAMS user: {email: "email@email.com",vk_token: "TOKEN"}
        RESPONSE
          {
            "user": user.as_json, "auth_token": "xxxxxxxxxxxxxxxxxxxx"
          }
     */
    LoginWrapper vkRegister(Context context, String mail, String vkToken) throws Throwable;

    LoginWrapper vkLogin(Context context, String vkToken) throws Throwable;

    /*
     *  DELETE /api/v1/vk_auth
        PARAMS no
        AUTH required
        ROLE no-user
        RESPONSE
          {
            success: true
          }
     */
    SimpleResult vkExit(Context context) throws Throwable;

    /*
     *  GET /api/v1/offers
        PARAMS auth_token: "xxxxxxxxxxxxxxxxxxxx", page: Number
        AUTH required
        ROLE any
        RESPONSE
          {
            meta: {
              per_page: 10,
              current_page: 1,
              total_pages: 10,
              total_count: 101
            },
            offers: [
              ...,
              {
                description: "Самое очаровательное приложение всех времен «Заботливые мишки»! "
                icon: "http://a4.mzstatic.com/us/r30/Purple/v4/db/4c/7d/db4c7d49-6cd7-50ef-fd91-742069551ffd/mzl.zutfkxcr.175x175-75.jpg"
                id: 64
                reward: 7
                title: "Заботливые мишки [iPad]",
                done: true|false
              },
              ...
            ]
          }
     */
    OffersWrapper getOffers(Context context, String authToken, Long page) throws Throwable;

    /*
     *  GET /api/v1/offers/:id
        PARAMS auth_token: "xxxxxxxxxxxxxxxxxxxx"
        AUTH required
        ROLE any
        RESPONSE
          {
            offer: {
              [
                {
                  description: "Самое очаровательное приложение всех времен «Заботливые мишки»! "
                  download_link: "/download/15/64"
                  icon: "http://a4.mzstatic.com/us/r30/Purple/v4/db/4c/7d/db4c7d49-6cd7-50ef-fd91-742069551ffd/mzl.zutfkxcr.175x175-75.jpg"
                  id: 64
                  reward: 7
                  done: true|false,
                  title: "Заботливые мишки [iPad]"
                },
                ...
              ]
            }
          }
     */
    OfferWrapper showOffer(Context context, String authToken, Offer offer) throws Throwable;


    /*
     *  GET /api/v1/faq
        PARAMS auth_token: "xxxxxxxxxxxxxxxxxxxx"
        AUTH required
        ROLE any
        RESPONSE
          {
            questions: [...{
              id: 1,
              text: 'За что начисляется баланс?',
              answer: "Чтобы баланс начислился...."
            }...
            ]
          }
     */
    QuestionsWrapper getFaq(SimpleRequest request) throws Throwable;

    /*
     *  GET /api/v1/my/referrals_details
        PARAMS auth_token: "xxxxxxxxxxxxxxxxxxxx"
        AUTH required
        ROLE any
        RESPONSE
          {
            referrals_details: {
              user_id: 7143,
              referrals_profit: "55.0",
              levels: {
                {
                  count: 23,
                  profit: "55.0"
                },
                {
                  count: 0,
                  profit: "0.0"
                },
                {
                  count: 0,
                  profit: "0.0"
                }
              }
            }
          }
     */
    ReferralsDetailsWrapper readReferralsDetails(Context context, String authToken) throws Throwable;
}
