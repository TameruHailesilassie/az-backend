package com.softech.ehr.dto.response;

import com.softech.ehr.model.base.ModelBase;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class AuthDto extends ModelBase {

    private static final long serialVersionUID = 7431193836933783650L;
    private UserAuthDto userData;
    private String accessToken;
}
