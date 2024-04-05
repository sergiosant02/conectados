package com.sergiosantiago.conectados.dtos;

import java.util.Set;
import java.util.stream.Collectors;

import com.sergiosantiago.conectados.models.User;
import com.sergiosantiago.conectados.models.enums.Gender;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long id;
    private String name;
    private String lastName;
    private String picture;
    private String email;
    private String password;
    private String role;
    private Boolean enabled;
    private Set<Long> roomInIds;
    private Set<Long> roomOwnerIds;
    private Set<Long> noteIds;
    private Set<Long> productIds;
    private Set<Long> productCategoryIds;
    private Gender gender;

    public static UserDTO from(User user) {
        return new UserDTO(user.getId(), user.getName(), user.getLastName(), user.getPicture(), user.getEmail(),
                user.getPassword(),
                user.getRole(), user.getEnabled(),
                user.getRoomIn().stream().map(e -> e.getId()).collect(Collectors.toSet()),
                user.getRoomOwner().stream().map(e -> e.getId()).collect(Collectors.toSet()),
                user.getNotes().stream().map(e -> e.getId()).collect(Collectors.toSet()),
                user.getProducts().stream().map(e -> e.getId()).collect(Collectors.toSet()),
                user.getProductCategories().stream().map(e -> e.getId()).collect(Collectors.toSet()), user.getGender());
    }
}
