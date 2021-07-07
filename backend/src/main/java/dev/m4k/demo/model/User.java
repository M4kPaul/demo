package dev.m4k.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "Users")
public class User implements Serializable {

  private static final long serialVersionUID = -8463445156552249802L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "USER_ID")
  private Long id;

  @Column(name = "USER_NAME")
  private String username;

  @JsonIgnore
  private String password;

  @Column(name = "created_date", nullable = false, updatable = false)
  @Temporal(TemporalType.TIMESTAMP)
  protected Date createdDate;

  @Column(name = "enabled", columnDefinition = "boolean")
  private boolean enabled = true;

  @JsonIgnore
  @ManyToMany
  @JoinTable(name = "user_role", joinColumns = { @JoinColumn(name = "USER_ID") }, inverseJoinColumns = { @JoinColumn(name = "ROLE_ID") })
  private Set<Role> roles;

  @JsonIgnore
  @OneToMany(fetch = FetchType.EAGER,targetEntity = Task.class, cascade = CascadeType.ALL)
  @JoinColumn(name = "fk_user_task", referencedColumnName = "USER_ID")
  private List<Task> tasks = new ArrayList<>();
}
