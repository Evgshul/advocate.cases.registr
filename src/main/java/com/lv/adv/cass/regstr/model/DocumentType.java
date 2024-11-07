package com.lv.adv.cass.regstr.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DocumentType {
    AGREEMENT("Agreement"),
    CONTRACT("Contract");

    private final String documentType;

    @Override
    public String toString() {
        return documentType;
    }
}
