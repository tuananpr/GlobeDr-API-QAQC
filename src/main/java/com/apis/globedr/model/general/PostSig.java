package com.apis.globedr.model.general;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostSig {
    private String postSig;

    public PostSig(String postSig) {
        this.postSig = postSig;
    }
}
