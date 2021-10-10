package com.north47.offersdashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Offer {
    @NotNull
    private String offerName;
    @NotNull
    private String currency;
    @NotNull
    private Integer price;
}
