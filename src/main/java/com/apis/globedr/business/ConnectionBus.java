package com.apis.globedr.business;

import com.apis.globedr.apis.ConnectionApi;
import com.apis.globedr.model.request.connection.LoadConnectionsRQ;
import com.apis.globedr.model.request.connection.LoadFollowedOrgsRQ;
import com.apis.globedr.model.response.org.OrgRS;
import com.apis.globedr.model.step.connection.ConnectionMS;

import com.apis.globedr.model.step.connection.InvitationMS;
import com.apis.globedr.model.response.connection.ConnectionDetailRS;
import com.apis.globedr.model.step.org.OrgMS;
import com.rest.core.response.Response;
import java.util.List;
import java.util.stream.Collectors;


public class ConnectionBus extends AbsBus{


    public ConnectionBus() {

    }

    public List<ConnectionDetailRS> searchUser(ConnectionMS body) {
        return ConnectionApi.getInstant().searchUser(body);
    }

    public void requestConnection(ConnectionMS body) {
        searchUser(body).forEach(ConnectionApi.getInstant()::requestConnection);
    }

    public List<ConnectionDetailRS> loads(String name) {
        return ConnectionApi.getInstant().loadConnections(ConnectionMS.builder().name(name).build());
    }

    public List<ConnectionDetailRS> loads(ConnectionMS body) {
        return ConnectionApi.getInstant().loadConnections(body);
    }

    public List<ConnectionDetailRS> loads() {
        return ConnectionApi.getInstant().loadConnections(new LoadConnectionsRQ());
    }


    public List<ConnectionDetailRS> loadRequest() {
        return ConnectionApi.getInstant().loadRequestConnection(new ConnectionMS());
    }

    public void accept() {
        loadRequest().forEach(ConnectionApi.getInstant()::acceptConnection);
    }

    public void decline() {
        loadRequest().forEach(ConnectionApi.getInstant()::declineConnection);
    }

    public void accept(ConnectionMS body) {
        loadRequest().forEach(detail -> {
            if (body.getName() != null && detail.getUser().getUserName().equalsIgnoreCase(body.getName())) {
                ConnectionApi.getInstant().acceptConnection(detail);
            }
        });
    }

    public void decline(ConnectionMS body) {
        loadRequest().forEach(detail -> {
            if (body.getName() != null && detail.getUser().getUserName().equalsIgnoreCase(body.getName())) {
                ConnectionApi.getInstant().declineConnection(detail);
            }
        });
    }

    public Response countRequest() {
        return ConnectionApi.getInstant().countRequestConnection();
    }


    public List<OrgRS> searchOrgs(ConnectionMS body) {
        return ConnectionApi.getInstant().searchOrgs(body.getName());
    }

    public void followOrg(ConnectionMS body) {
        searchOrgs(body).forEach(org -> {
            OrgMS orgMs = OrgMS.builder().orgSig(org.getOrgSig()).isFollow(true).build();
            ConnectionApi.getInstant().followOrg(orgMs);
        });
    }

    public void unfollowOrg(ConnectionMS body) {
        searchOrgs(body).forEach(org -> {
            OrgMS orgMs = OrgMS.builder().orgSig(org.getOrgSig()).isFollow(false).build();
            ConnectionApi.getInstant().followOrg(orgMs);
        });
    }

    public void remove(ConnectionMS body) {
        searchUser(body).forEach(ConnectionApi.getInstant()::removeConnection);
    }

    public Response invite(InvitationMS body) {
        return ConnectionApi.getInstant().invite(body);
    }


    public List<OrgRS> loadFollowOrgsToShare(String subSig, LoadFollowedOrgsRQ body) {
        body.setSubSig(subSig);
        return loadFollowOrgs(body);
    }

    public List<OrgRS> loadFollowOrgsByName(String name) {
        LoadFollowedOrgsRQ body =  new LoadFollowedOrgsRQ();
        body.setName(name);
        return loadFollowOrgs(body);
    }

    public List<OrgRS> loadFollowOrgs() {
        return loadFollowOrgs(new LoadFollowedOrgsRQ());
    }

    public List<OrgRS> loadFollowOrgs(Object body) {
        return ConnectionApi.getInstant().loadFollowOrgs(body);
    }


}
