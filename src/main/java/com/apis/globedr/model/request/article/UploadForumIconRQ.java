package com.apis.globedr.model.request.article;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.File;

@Getter
@Setter
@NoArgsConstructor
public class UploadForumIconRQ {

    private File file;
    @JsonAlias({"orgSig", "orgSignature"})
    private String OrgSignature;
    @JsonAlias({"postSig", "postSignature"})
    private String PostSignature;
}
