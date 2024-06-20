package com.apis.globedr.business.changelog;

import com.apis.globedr.model.step.changelog.ChangelogMS;

public class ChangelogNormalBus extends ChangelogBus {
    @Override
    protected ChangelogMS prepare(ChangelogMS body){
        body.setLinkId(0);
        body.setLinkType(0);
        return body;
    }

}
