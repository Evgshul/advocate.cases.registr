package com.lv.adv.cass.regstr.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CaseType {

    CRIMINAL("Criminal"),
    CIVIL("Civil"),
    ADMINISTRATIVE("Administrative");

    private final String casesCategories;

    @Override
    public String toString() {
        return casesCategories;
    }
}
