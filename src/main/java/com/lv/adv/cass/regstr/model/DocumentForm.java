package com.lv.adv.cass.regstr.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DocumentForm {
    WRITTEN("written"),
    ORAL("oral");

    private final String documentForm;

    @Override
    public String toString() {
        return documentForm;
    }
}
