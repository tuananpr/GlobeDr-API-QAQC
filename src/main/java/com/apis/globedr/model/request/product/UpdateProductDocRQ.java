package com.apis.globedr.model.request.product;

import com.apis.globedr.model.general.file.FileFactory;
import com.apis.globedr.model.general.file.ImageFile;
import com.apis.globedr.model.step.product.ProductMS;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateProductDocRQ {
    private String productSig;
    private String docSig;
    private String orgSig;

    @JsonUnwrapped
    ImageFile file;

}
