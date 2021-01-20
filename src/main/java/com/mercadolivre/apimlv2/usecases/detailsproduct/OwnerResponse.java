package com.mercadolivre.apimlv2.usecases.detailsproduct;

import com.mercadolivre.apimlv2.domain.User;

class OwnerResponse {

    public final String email;

    public OwnerResponse(User owner) {
        this.email = owner.getLogin();
    }
}
