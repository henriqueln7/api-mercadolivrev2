package com.mercadolivre.apimlv2.usecases.detailsproduct;

import com.mercadolivre.apimlv2.domain.Opinion;

class OpinionResponse {

    public final String title;
    public final String description;
    public final String opinionatorEmail;
    public final int score;

    public OpinionResponse(Opinion opinion) {
        title = opinion.getTitle();
        description = opinion.getDescription();
        opinionatorEmail = opinion.getOpinionator().getLogin();
        score = opinion.getScore();
    }
}
