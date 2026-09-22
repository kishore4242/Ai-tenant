package com.aitenant.web_service.model.mongo;

import com.aitenant.web_service.model.History;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document(collection = "ai_tenant_history")
@Getter
@Setter
public class ChatHistory {
    @Id
    private String id;

    @Field("tenant_id")
    private String tenantId;

    @Field("user_id")
    private String userId;

    @Field("chat_id")
    private String chatId;

    @Field("title")
    private String title;

    @Field("messages")
    private List<History> history;

    public ChatHistory(){}

    private ChatHistory(Builder builder){
        this.tenantId = builder.tenantId;
        this.chatId = builder.chatId;
        this.userId = builder.userId;
        this.title = builder.title;
        this.history = builder.histories;
    }
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder{
        private String tenantId;
        private String userId;
        private String chatId;
        private String title;
        private List<History> histories;
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

        public Builder history(List<History> histories){
            this.histories = histories;
            return this;
        }

        public Builder chatId(String chatId){
            this.chatId = chatId;
            return this;
        }

        public ChatHistory build(){
            return new ChatHistory(this);
        }

    }
}
