package ru.netology.homework.model;

public class Post implements Cloneable {
    private long id;
    private String content;
    private boolean isPublicSend;

    @Override
    public Post clone() {
        try {
            return (Post) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", postInput='" + content + '\'' +
                ", isPublicSend=" + isPublicSend +
                '}';
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isPublicSend() {
        return isPublicSend;
    }

    public void setPublicSend(boolean publicSend) {
        isPublicSend = publicSend;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}
