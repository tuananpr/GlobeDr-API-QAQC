package com.apis.globedr.model.response.article;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import lombok.experimental.Accessors;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class PostMsgRS {
    private Integer msgId;
    @JsonAlias({"msgSignature", "msgSig"})
    private String msgSig;
    @JsonAlias({"postMsgSignature", "postMsgSig"})
    private String postMsgSig;
    private String msg;
    private String msgContent;
    private Boolean isLike;
}
