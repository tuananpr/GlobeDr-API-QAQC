package com.apis.globedr.model.request.topdeal;


import com.apis.globedr.model.general.file.FileFactory;
import com.apis.globedr.model.general.file.ImageFile;
import com.apis.globedr.model.general.file.PdfFile;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UploadPromotionFileRQ{
    private String promotionSig;

    @JsonUnwrapped
    PdfFile file;


    public UploadPromotionFileRQ setFile(String pathFile) {
        this.file = (PdfFile) FileFactory.getFile(pathFile);
        return this;
    }
}
