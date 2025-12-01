package tpindividual.minitwitter.entity.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class UserResponse {
    private Long id;
    private String userName;

    public UserResponse() {}

    public UserResponse(Long id, String userName) {
        this.id = id;
        this.userName = userName;
    }

}