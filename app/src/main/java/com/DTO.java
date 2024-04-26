package com;

public class DTO {


    public DTO(String topic, String link) {
        this.topic = topic;
        this.link = link;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    String topic;
    String link;




}
