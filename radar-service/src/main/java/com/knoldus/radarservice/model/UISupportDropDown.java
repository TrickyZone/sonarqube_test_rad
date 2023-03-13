package com.knoldus.radarservice.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * this data model contains ui support drop down data.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UISupportDropDown {
    private List<String> dropDownData;
}
