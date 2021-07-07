package dev.m4k.demo.dto;

import dev.m4k.demo.validator.PasswordMatches;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import org.springframework.boot.autoconfigure.info.ProjectInfoProperties.Build;

@Data
@PasswordMatches
public class RegisterRequest {

  private Long userID;

  @NotEmpty
  @Size(max = 64)
  private String username;

  @Size(min = 6, message = "{Size.userDto.password")
  private String password;

  @NotEmpty
  private String matchingPassword;

  public RegisterRequest(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public static Builder getBuilder() {
    return new Builder();
  }

  public static class Builder {

    private String username;
    private String password;

    public Builder addUsername(final String username) {
      this.username = username;
      return this;
    }

    public Builder addPassword(final String password) {
      this.password = password;
      return this;
    }

    public RegisterRequest build() {
      return new RegisterRequest(username, password);
    }
  }

}
