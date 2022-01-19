package fr.newqcmplus.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Password {

    private int id;
    private String oldPassword;
    private String newPassword;
    private String newPasswordConfirmation;

}
