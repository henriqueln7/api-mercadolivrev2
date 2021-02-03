package com.mercadolivre.apimlv2.shared.fakesystems;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class NotaFiscalRequest {
    @NotBlank
    public final String idCompra;
    @NotNull
    public final Long idComprador;

    public NotaFiscalRequest(String idCompra, @NotNull Long idComprador) {
        this.idCompra = idCompra;
        this.idComprador = idComprador;
    }


    @Override
    public String toString() {
        return "NotaFiscalRequest{" + "idCompra='" + idCompra + '\'' + ", idComprador=" + idComprador + '}';
    }
}
