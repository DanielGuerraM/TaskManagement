package com.TaskManagement.TaskManagementApp.entity;

import jakarta.persistence.*;
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
        @Column(length = 50)
        private String title;
        @Column(length = 250)
        private String description;
        @Column(name = "is_completed")
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
        this.created_at = LocalDateTime.now();
    }

    @PreUpdate
    public void setUpdated_at() {
        this.updated_at = LocalDateTime.now();
    }
    public Task() { }

    private Task(builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.description = builder.description;
        this.is_completed = builder.isCompleted;
    }
}