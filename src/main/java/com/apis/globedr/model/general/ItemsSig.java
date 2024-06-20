package com.apis.globedr.model.general;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ItemsSig {
    private String itemSig;

    public ItemsSig(String itemSig) {
        this.itemSig = itemSig;
    }
}
