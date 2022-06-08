package com.softech.ehr.dto.request;
import com.softech.ehr.model.base.ModelBase;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AuthPostDto extends ModelBase {
    private static final long serialVersionUID = 7151443507829405471L;
    @NotNull(message = "Username must be provided")
    private String username;
    @NotNull(message = "Password must be provided")
    private String password;
    private String device;
}
