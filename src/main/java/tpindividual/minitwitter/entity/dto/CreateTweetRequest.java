package tpindividual.minitwitter.entity.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateTweetRequest {

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Text is required")
    @Size(min = 1, max = 280, message = "Tweet text must be between 1 and 280 characters")
    private String text;

    public CreateTweetRequest() {}

    public CreateTweetRequest(Long userId, String text) {
        this.userId = userId;
        this.text = text;
    }

}