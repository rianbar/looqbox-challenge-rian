package com.looqbox.challenge.model.response;

public class HighlightResponse {
    String name;
    String highlight;

    public HighlightResponse(String name, String highlight) {
        this.name = name;
        this.highlight = highlight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHighlight() {
        return highlight;
    }

    public void setHighlight(String highlight) {
        this.highlight = highlight;
    }
}
