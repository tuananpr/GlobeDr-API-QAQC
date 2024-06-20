package com.apis.globedr.model.step.changelog;

import com.apis.globedr.model.general.file.File;
import com.apis.globedr.model.general.file.FileFactory;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangelogMS {
    private String name;
    private String country;
    private Integer language;
    private String startDate;
    private String endDate;
    private String imgBase64;
    private String image;
    private String orgSig;
    private Integer linkId ; // default = 0
    private Integer linkType; // default = 0, enums.ChangeLogLinkType
    private String linkName; // name of Article, Voucher to get linkId
    private String linkSig;
    private String imgFileExt;
    private Integer status = 2 ; // default = 2
    private String logSig;
    private List<String> logSigs;

    @JsonProperty("image")
    public void setImage(String path) {
        if(path != null){
            File file = FileFactory.getFile(path);
            if (file.isExisted()) {
                setImgBase64(file.toBase64());
                setImgFileExt(file.getExtension());
                this.image = null;
            }
        }else{
            this.image = null;
        }
    }

    @JsonProperty("image")
    public String getImage() {
        return this.image;
    }
}
