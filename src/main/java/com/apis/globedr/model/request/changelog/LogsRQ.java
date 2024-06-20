package com.apis.globedr.model.request.changelog;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LogsRQ {
    private String country;
    private Integer language;
    private boolean excludeAppLog;

}