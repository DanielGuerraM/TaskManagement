package com.TaskManagement.TaskManagementApp.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table
public class Category {
    @Id
    private long id;
    @Column(length = 50)
    private String name;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    @OneToOne(mappedBy = "category", cascade = CascadeType.ALL)
    private Task task;

    public static class builder {
        private long id;
        @Column(length = 50)
        private String name;

        public builder id(long id) {
            this.id = id;
            return this;
        }

        public builder name(String name) {
            this.name = name;
            return this;
        }

        public Category build() {
            return new Category(this);
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Category() { }

    private Category(builder builder) {
        this.id = builder.id;
        this.name = builder.name;
    }
}