package fr.newqcmplus.entity;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class Password {

    private int id;

    @NotBlank(message = "{input.not.blank}")
    private String oldPassword;

    @NotBlank(message = "{input.not.blank}")
    private String newPassword;

    @NotBlank(message = "{input.not.blank}")
    private String newPasswordConfirmation;

}
