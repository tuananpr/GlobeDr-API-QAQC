package com.apis.globedr.services.database.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WalletTransPointRule {
    private Integer id;
    private Integer amount;
    private Integer pointActivity;
    private String name;
    private String descriptionHtml;
    private String onDate;
    private String fromDate;
    private String toDate;


}
