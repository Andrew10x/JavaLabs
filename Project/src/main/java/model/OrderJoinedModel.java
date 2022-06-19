package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderJoinedModel {
    private int Id;
    private String DateOrd;
    private int StatusId;
    private float WeightOrd;
    private int LengthOrd;
    private int WidthOrd;
    private int HeightOrd;
    private float SumInsured;
    private String Address;
    private float DeliveryCost;
    private String UserName;
    private String Phone;
    private String Email;
    private String CityFrom;
    private String CityTo;
    private String StatusName;
    private String TypeName;
    private String RecipientName;
    private String RecipientPhone;
}
