package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {
    private int UserId;
    private String UserName;
    private String Phone;
    private String Email;
    private String PasswordUsr;
    private int RoleId;
    private String RoleName;
}
