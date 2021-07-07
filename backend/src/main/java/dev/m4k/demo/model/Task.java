package dev.m4k.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Table(name = "Tasks")
public class Task {

  private static final long serialVersionUID = -3957216589946768390L;

  public Task(String description, String modifiedDate) {
    this.description = description;
    this.modifiedDate = modifiedDate;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @Column(name = "description", columnDefinition = "TEXT")
  private String description;

  @Column(name = "done", columnDefinition = "boolean")
  private boolean isDone;

  protected String modifiedDate;
}
