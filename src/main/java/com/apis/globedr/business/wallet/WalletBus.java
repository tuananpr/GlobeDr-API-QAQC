package com.apis.globedr.business.wallet;

import com.apis.globedr.apis.WalletApi;
import com.apis.globedr.constant.API;
import com.apis.globedr.model.request.wallet.UsePointNotiRQ;
import com.apis.globedr.model.request.wallet.UsedPointHistoryDetailRQ;
import com.apis.globedr.model.step.wallet.WalletMS;
import com.rest.core.RestCore;
import com.rest.core.response.Response;

public class WalletBus{

    WalletApi walletApi = WalletApi.getInstant();

    public Response points(){
        return walletApi.points();
    }

    public Response loadRewardPointIntruct(WalletMS body){
        return walletApi.getPointIntruct(body);
    }

    public Response loadPointsHistory(WalletMS body){
        return walletApi.transPoint(body);
    }



    public Response usePointNoti(UsePointNotiRQ body) {
        return walletApi.usePointNoti(body);
    }

    public Response usedPointHistoryDetail(Object body) {
        return walletApi.usedPointHistoryDetail(body);
    }
}
