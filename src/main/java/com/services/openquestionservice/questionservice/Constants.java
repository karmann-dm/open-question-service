package com.services.openquestionservice.questionservice;

public enum  Constants {
    DEFAULT_VOTING_TEXT("Проголосуйте за предложенные варианты");

    private final String text;

    Constants(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
