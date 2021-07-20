package com.fccalendar.core.domain;

import com.fccalendar.core.domain.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor
public class User extends BaseEntity {
    private String name;
    private String email;
    private String password;
    private LocalDate birthday;

    @Builder
    public User(Long id, String name, String email, String password, LocalDate birthday) {
        super(id);
        this.name = name;
        this.email = email;
        this.password = password;
        this.birthday = birthday;
    }

    public UserDto toDto() {
        return new UserDto(super.getId(), name, email, birthday);
    }
}
