package com.apis.globedr.model.request.system;

import com.apis.globedr.enums.PostType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetSystemPostRQ {
    private Integer postType;

    public GetSystemPostRQ setPostType(Object value){
        this.postType = PostType.value(value);
        return this;
    }
}
