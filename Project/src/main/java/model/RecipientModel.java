package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipientModel {
    private int RecipientId;
    private String RecipientName;
    private String RecipientPhone;
}
