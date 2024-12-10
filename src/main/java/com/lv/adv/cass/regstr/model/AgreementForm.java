package com.lv.adv.cass.regstr.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AgreementForm {
    WRITTEN("written"),
    ORAL("oral");

    private final String documentForm;

    @Override
    public String toString() {
        return documentForm;
    }
}
