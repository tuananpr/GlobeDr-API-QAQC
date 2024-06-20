package com.apis.globedr.business.changelog;

import com.apis.globedr.apis.ChangelogApi;
import com.apis.globedr.model.request.changelog.LogsRQ;
import com.apis.globedr.model.response.changelog.ChangelogRS;
import com.apis.globedr.model.response.changelog.NewRS;
import com.apis.globedr.model.step.changelog.ChangelogMS;
import com.rest.core.response.Response;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public abstract class ChangelogBus {
    ChangelogApi changelogApi = ChangelogApi.getInstant();

    protected abstract ChangelogMS prepare(ChangelogMS body);

    public NewRS create(ChangelogMS body) {
        body = prepare(body);
        return changelogApi.create(body);
    }

    public NewRS update(ChangelogMS body) {
        body = prepare(body);
        return changelogApi.update(body);
    }

    public List<ChangelogRS> list(ChangelogMS body) {
        return changelogApi.list(body);
    }

    public List<String> getChangelogSigs(ChangelogMS body) {
        return list(body).stream().map(c->c.getLogSig()).collect(Collectors.toList());
    }


    public Response remove(ChangelogMS body) {
        return changelogApi.remove(body);
    }

    public Response logs(LogsRQ body) {
        return changelogApi.logs(body);
    }


}
