package ru.kaznacheev.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JacksonXmlRootElement(localName = "Secret_key")
public class SecretKeyDto {

    @JsonProperty("secret_key")
    @NotBlank(message = "Секретный ключ не может быть пустым")
    private String secretKey;

}
