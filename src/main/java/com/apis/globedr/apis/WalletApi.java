package com.apis.globedr.apis;

import com.apis.globedr.constant.API;
import com.apis.globedr.model.request.wallet.UsePointNotiRQ;
import com.apis.globedr.model.request.wallet.GetPointIntructRQ;
import com.apis.globedr.model.request.wallet.TransPointRQ;
import com.apis.globedr.model.request.wallet.UsedPointHistoryDetailRQ;
import com.rest.core.RestCore;
import com.rest.core.response.Response;

public class WalletApi extends BaseApi {

    private WalletApi() {
    }

    private static WalletApi instant;

    public static WalletApi getInstant() {
        if (instant == null) instant = new WalletApi();
        return instant;
    }


    public Response points () {
        return RestCore.given().url(API.Wallet.POINTS()).auth(token).get().send();
    }

    public Response getPointIntruct (Object body) {
        return RestCore.given().url(API.Wallet.GET_POINT_INTRUCT()).auth(token).body(body, GetPointIntructRQ.class).get().send();
    }

    public Response transPoint(Object body) {
        return RestCore.given().url(API.Wallet.TRANS_POINT()).auth(token).bodyEncrypt(body, TransPointRQ.class).post().send();
    }

    public Response usePointNoti(Object body) {
        return RestCore.given().url(API.Wallet.USE_POINT_NOTI()).auth(token).bodyEncrypt(body, UsePointNotiRQ.class).post().send();
    }

    public Response usedPointHistoryDetail(Object body) {
        return RestCore.given().url(API.Wallet.USED_POINT_HISTORY_DETAIL()).auth(token).params(body, UsedPointHistoryDetailRQ.class).get().send();
    }

}
