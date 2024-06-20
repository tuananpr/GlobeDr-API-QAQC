package com.apis.globedr.model.request.chat;


import com.apis.globedr.model.general.file.FileFactory;
import com.apis.globedr.model.general.file.ImageFile;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Data;

@Data
public class UploadMsgDocsRQ {
    private String createDate;
    private String conversationSig;
    private Integer msgType;
    private Integer senderType;

    @JsonUnwrapped
    ImageFile files;


    public void setFiles(String pathFile){
        this.files = (ImageFile) FileFactory.getFile(pathFile);
    }

}
