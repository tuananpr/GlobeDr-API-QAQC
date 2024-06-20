package com.apis.globedr.model.response.product;

import io.qameta.allure.AllureId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Shop {
    private int session;
    private String username;
    private int shop_id;
    private String shop_title;
    private String shop_domain;
    private String shop_back_url;
    private int order_no;
    private int order_cash_amount;
    private String order_ship_date;
    private int order_ship_days;
    private String order_description;
    private String notify_url;
    private double validity_time;
    private Customer customer;
}
