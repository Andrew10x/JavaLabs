package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderModel {
    private int Id;
    private int UserId;
    private int DirectionId;
    private String DateOrd;
    private int StatusId;
    private float WeightOrd;
    private int LengthOrd;
    private int WidthOrd;
    private int HeightOrd;
    private int TypeId;
    private int RecipientId;
    private float SumInsured;
    private String Adress;
    private float DeliveryCost;
}
