package com.apis.globedr.model.request.topdeal;

import com.apis.globedr.model.general.file.FileFactory;
import com.apis.globedr.model.general.file.ImageFile;
import com.apis.globedr.model.step.product.ProductMS;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UploadPromotionIconRQ {
    private String promotionSig;

    @JsonUnwrapped
    ImageFile file;

    public UploadPromotionIconRQ setFile(String pathFile) {
        this.file = (ImageFile) FileFactory.getFile(pathFile);
        return this;
    }
}
