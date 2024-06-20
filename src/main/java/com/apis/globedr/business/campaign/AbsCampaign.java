package com.apis.globedr.business.campaign;

import com.apis.globedr.model.request.chat.UserUnReadRQ;
import com.apis.globedr.model.response.chat.LoadCampaignsRS;
import com.apis.globedr.model.step.chat.ChatMS;
import com.rest.core.response.Response;

import java.util.List;

public abstract class AbsCampaign extends AbsSendMessage{

    public List<LoadCampaignsRS> loadCampaigns(ChatMS body) {
        return chatApi.loadCampaigns(body);
    }

    public Response loadUserUnreadCampaign(ChatMS body) {
        LoadCampaignsRS msg = loadCampaigns(body).stream().findFirst().orElse(null);
        UserUnReadRQ request = new UserUnReadRQ();
        request.setOrgSig(body.getOrgSig());
        request.setMsgSig(msg.getMsgSig());
        return chatApi.loadUserUnRead(request);
    }

    public Response loadUserUnClickCampaign(ChatMS body) {
        LoadCampaignsRS msg = loadCampaigns(body).stream().findFirst().orElse(null);
        UserUnReadRQ request = new UserUnReadRQ();
        request.setOrgSig(body.getOrgSig());
        request.setMsgSig(msg.getMsgSig());
        return chatApi.loadUserUnClicked(request);
    }

    public Response loadUserClickCampaign(ChatMS body) {
        LoadCampaignsRS msg = loadCampaigns(body).stream().findFirst().orElse(null);
        UserUnReadRQ request = new UserUnReadRQ();
        request.setOrgSig(body.getOrgSig());
        request.setMsgSig(msg.getMsgSig());
        return chatApi.loadUserClicked(request);
    }
}
