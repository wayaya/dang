package com.dangdang.digital.model;

public class BookAuthor {
    private Long id;

    private Long digestId;

    private Long authorId;
    
    //构建展示字段
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDigestId() {
		return digestId;
	}

	public void setDigestId(Long digestId) {
		this.digestId = digestId;
	}

	public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}