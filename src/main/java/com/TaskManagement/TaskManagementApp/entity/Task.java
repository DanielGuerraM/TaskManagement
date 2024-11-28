package com.TaskManagement.TaskManagementApp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "task")
public class Task {
    @Id
    private long id;
    private String title;
    private String description;
    private boolean is_completed;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @NotNull
    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @NotNull
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public static class builder {
        private long id;
        private String title;
        private String description;
        private User user;
        private Category category;
        private boolean isCompleted;

        public builder id(long id) {
            this.id = id;
            return this;
        }

        public builder title(String title) {
            this.title = title;
            return this;
        }

        public builder description(String description) {
            this.description = description;
            return this;
        }

        public builder user(User user) {
            this.user = user;
            return this;
        }

        public builder category(Category category) {
            this.category = category;
            return this;
        }

        public builder isCompleted(boolean isCompleted) {
            this.isCompleted = isCompleted;
            return this;
        }

        public Task build() {
            return new Task(this);
        }
    }

    @PrePersist
    public void setCreated_at() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void setUpdated_at() {
        this.updatedAt = LocalDateTime.now();
    }
    public Task() { }

    private Task(builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.description = builder.description;
        this.user = builder.user;
        this.category = builder.category;
        this.is_completed = builder.isCompleted;
    }
}