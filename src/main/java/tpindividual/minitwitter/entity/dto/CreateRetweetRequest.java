package tpindividual.minitwitter.entity.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateRetweetRequest {

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Original Tweet ID is required")
    private Long originalTweetId;

    public CreateRetweetRequest() {}

    public CreateRetweetRequest(Long userId, Long originalTweetId) {
        this.userId = userId;
        this.originalTweetId = originalTweetId;
    }

}