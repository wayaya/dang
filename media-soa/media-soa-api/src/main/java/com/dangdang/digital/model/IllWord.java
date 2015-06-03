package com.dangdang.digital.model;

import java.io.Serializable;

public class IllWord implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer illegalWordId;

    private Integer type;

    private String words;

    public Integer getIllegalWordId() {
        return illegalWordId;
    }

    public void setIllegalWordId(Integer illegalWordId) {
        this.illegalWordId = illegalWordId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words == null ? null : words.trim();
    }
}