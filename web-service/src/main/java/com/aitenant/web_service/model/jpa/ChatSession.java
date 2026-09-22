package com.aitenant.web_service.model.jpa;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Setter
@Table(name = "chat_history")
@Entity
@Getter
public class ChatSession extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tenant_id", nullable = false)
    private String tenantId;

    @Column(name ="user_id", nullable = false)
    private String userId;

    @Column(name ="chat_id", nullable = false, unique = true)
    private String chatId;

    @Column(name ="title", nullable = false)
    private String title;

    public ChatSession(){}

    private ChatSession(Builder builder){
        this.tenantId = builder.tenantId;
        this.chatId = builder.chatId;
        this.userId = builder.userId;
        this.title = builder.title;
    }
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder{
        private String tenantId;
        private String userId;
        private String chatId;
        private String title;
        public Builder(){
        }

        public Builder tenantId(String tenantId){
            this.tenantId = tenantId;
            return this;
        }

        public Builder userId(String userId){
            this.userId = userId;
            return this;
        }

        public Builder title(String title){
            this.title = title;
            return this;
        }

        public Builder chatId(String chatId){
            this.chatId = chatId;
            return this;
        }

        public ChatSession build(){
            return new ChatSession(this);
        }

    }
}
