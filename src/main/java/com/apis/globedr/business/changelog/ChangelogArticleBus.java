package com.apis.globedr.business.changelog;

import com.apis.globedr.enums.ChangeLogLinkType;
import com.apis.globedr.apis.ForumManagerApi;
import com.apis.globedr.model.step.changelog.ChangelogMS;

public class ChangelogArticleBus extends ChangelogBus {

    @Override
    protected ChangelogMS prepare(ChangelogMS body){
        Integer id = ForumManagerApi.getInstant().loadCategoryWithPost(0)
                .stream()
                .flatMap(listCategory->listCategory.getList()
                        .stream()
                        .filter(post->post.getTitle().equalsIgnoreCase(body.getLinkName()))
                        .map(post->post.getPostId())
                ).findFirst().orElse(null);


        body.setLinkId(id);
        body.setLinkName(null);
        body.setLinkType(ChangeLogLinkType.Article.value());
        return body;
    }

}
