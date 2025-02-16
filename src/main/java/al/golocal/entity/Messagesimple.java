package al.golocal.entity;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Messagesimple {
    private String senderName;

    private String receiverName;

    private String message;

    private String media;

    private Status status;
    private String mediaType;

}

