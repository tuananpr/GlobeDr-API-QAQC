package com.apis.globedr.model.request.voucher;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoadCategoriesRQ {
    @JsonAlias({"nameVN"})
    private String name;
}
