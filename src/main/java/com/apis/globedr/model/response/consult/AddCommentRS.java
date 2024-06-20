package com.apis.globedr.model.response.consult;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AddCommentRS {
    private String postSig;
    private Integer postId;
    List<Comment> comments;
}
