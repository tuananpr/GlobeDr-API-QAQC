package com.apis.globedr.model.step.connection;

import com.apis.globedr.model.request.chat.LoadConversationsRQ;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoadConversationMS extends LoadConversationsRQ {
}
