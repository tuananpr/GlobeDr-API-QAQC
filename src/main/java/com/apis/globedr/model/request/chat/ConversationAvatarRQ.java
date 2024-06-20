package com.apis.globedr.model.request.chat;

import com.apis.globedr.model.general.file.ImageFile;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConversationAvatarRQ {
    private String conversationSig;
    @JsonUnwrapped
    ImageFile file;


}
