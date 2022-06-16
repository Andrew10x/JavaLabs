package model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConstantsModel {
    private int id;
    private String Name;
    private float MinValue;
    private float MinPrice;
    private float Coef;
}
