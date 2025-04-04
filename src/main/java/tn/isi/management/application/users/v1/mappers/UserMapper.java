package tn.isi.management.application.users.v1.mappers;

import tn.isi.management.application.users.v1.models.UserResponse;
import tn.isi.management.domain.entities.User;

public abstract class UserMapper {

    public static UserResponse userToUserResponse(User user ) {
        return UserResponse.builder()
                .login(user.getLogin())
                .roleName(user.getRole().getName())
                .build();
    }

}
