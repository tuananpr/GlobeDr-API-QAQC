package com.apis.globedr.business.changelog;

import com.apis.globedr.enums.ChangeLogLinkType;
import com.apis.globedr.apis.ForumManagerApi;
import com.apis.globedr.model.step.article.ArticleMS;
import com.apis.globedr.model.step.changelog.ChangelogMS;

public class ChangelogAppVersionBus extends ChangelogBus {

    @Override
    protected ChangelogMS prepare(ChangelogMS info) {
        ArticleMS articleMS = new ArticleMS();
        articleMS.setType(47);
        articleMS.setPostTitleMsg(info.getLinkName());


        Integer id = ForumManagerApi.getInstant().loadSystemPosts(articleMS).extractAsInts("$..postId").get(0);
        info.setLinkId(id);
        info.setLinkType(ChangeLogLinkType.AppVersion.value());
        info.setLinkName(null);
        return info;
    }


}
