package com.apis.globedr.apis;



import com.apis.globedr.model.request.connection.*;
import com.apis.globedr.model.response.connection.*;
import com.apis.globedr.model.response.org.OrgRS;
import com.apis.globedr.model.step.connection.InvitationMS;
import com.apis.globedr.model.step.org.OrgMS;
import com.apis.globedr.constant.API;
import com.fasterxml.jackson.core.type.TypeReference;
import com.rest.core.RestCore;
import com.rest.core.response.Response;

import java.util.List;

public class ConnectionApi extends BaseApi {


    private ConnectionApi() {
    }

    private static ConnectionApi instant;

    public static ConnectionApi getInstant() {
        if (instant == null) instant = new ConnectionApi();
        return instant;
    }


    public Response requestConnection(Object body) {
        return RestCore.given().url(API.Connection.REQUEST_CONNECTION()).auth(token).body(body, RequestConnectionsRQ.class).post().send();
    }


    public Response acceptConnection(Object body) {
        return RestCore.given().url(API.Connection.ACCEPT_CONNECTION()).auth(token).body(body, AcceptConnectionRQ.class).post().send();
    }

    public Response declineConnection(Object body) {
        // same object with AcceptConnectionRQ
        return RestCore.given().url(API.Connection.DECLINE_CONNECTION()).auth(token).body(body, AcceptConnectionRQ.class).post().send();
    }


    public Response countRequestConnection() {
        return RestCore.given().url(API.Connection.COUNT_REQUEST_CONNECTION()).auth(token).get().send();
    }


    public List<ConnectionDetailRS> loadConnections(Object body) {
        return RestCore.given().url(API.Connection.LOAD_CONNECTIONS()).auth(token)
                .body(body, LoadConnectionsRQ.class).post().send()
                .extractAsModels("data.list", ConnectionDetailRS.class );
    }

    public List<ConnectionDetailRS> loadRequestConnection(Object body) {
        return RestCore.given().url(API.Connection.LOAD_REQUEST_CONNECTION()).auth(token)
                .body(body, LoadRequestConnectionsRQ.class).post().send()
                .extractAsModels("data.list", ConnectionDetailRS.class );
    }


    public List<OrgRS> loadFollowOrgs(Object body) {
        return RestCore.given().url(API.Connection.LOAD_FOLLOW_ORGS()).auth(token)
                .body(body, LoadFollowedOrgsRQ.class).post().send()
                .extractAsModels("data.list", OrgRS.class);
    }



    public Response followOrg(Object body) {
        return RestCore.given().url(API.Connection.FOLLOW_ORG()).auth(token).body(body, FollowOrgRQ.class).post().send();
    }

    public List<OrgRS> searchOrgs(String name) {
        OrgMS org = new OrgMS();
        org.setName(name);
        return searchOrgs(org);
    }


    public List<OrgRS> searchOrgs(Object body) {
        // same object with LoadFollowedOrgsRQ
        return RestCore.given().url(API.Connection.SEARCH_ORGS()).auth(token).body(body, LoadFollowedOrgsRQ.class).post().send()
                .extractAsModels("data.list",OrgRS.class);
    }

    public List<ConnectionDetailRS> searchUser(Object body) {
        return RestCore.given().url(API.Connection.SEARCH_USER()).auth(token)
                .body(body, SearchUserRQ.class).post().send()
                .extractAsModels("data.list", ConnectionDetailRS.class );
    }

    public Response removeConnection(Object body) {
        return RestCore.given().url(API.Connection.REMOVE_CONNECTION_USER()).auth(token)
                .body(body, RemoveConnectionUserRQ.class).delete().send();
    }

    public Response invite(Object body) {
        return RestCore.given().url(API.Connection.INVITATION()).auth(token).body(body, InvitationMS.class).post().send();
    }


}
