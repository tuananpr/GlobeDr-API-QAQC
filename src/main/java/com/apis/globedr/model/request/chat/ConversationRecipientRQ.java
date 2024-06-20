package com.apis.globedr.model.request.chat;

import com.apis.globedr.model.general.Recipient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConversationRecipientRQ {
        private List<Recipient> recipients;
        private Integer viewerType;
        private String conversationSig;
}
