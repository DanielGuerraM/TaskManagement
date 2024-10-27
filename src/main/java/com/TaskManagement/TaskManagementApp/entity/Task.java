package com.TaskManagement.TaskManagementApp.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "task")
public class Task {
    @Id
    private long id;
    private String title;
    private String description;
    private boolean is_completed;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    @OneToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public static class builder {
        private long id;
        private String title;
        private String description;
        private boolean is_completed;

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

        public builder isCompleted(boolean isCompleted) {
            this.is_completed = isCompleted;
            return this;
        }

        public Task build() {
            return new Task(this);
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isIs_completed() {
        return is_completed;
    }

    public void setIs_completed(boolean is_completed) {
        this.is_completed = is_completed;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    @PrePersist
    public void setCreated_at() {
        this.created_at = LocalDateTime.now();
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    @PreUpdate
    public void setUpdated_at() {
        this.updated_at = LocalDateTime.now();
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Task() { }

    public Task(builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.description = builder.description;
        this.is_completed = builder.is_completed;
    }
}