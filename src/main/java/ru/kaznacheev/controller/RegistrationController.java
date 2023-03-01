package ru.kaznacheev.controller;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.kaznacheev.dto.request.RegistrationDto;
import ru.kaznacheev.dto.response.RegistrationResultDto;
import ru.kaznacheev.entity.Client;
import ru.kaznacheev.service.RegistrationService;
import ru.kaznacheev.service.SupportiveService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/clients")
@Validated
@AllArgsConstructor
public class RegistrationController {

    private RegistrationService registrationService;
    private SupportiveService supportiveService;
    private ModelMapper modelMapper;

    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public RegistrationResultDto registration(@Valid @RequestBody RegistrationDto registrationDto) {
        Client client = modelMapper.map(registrationDto, Client.class);
        String secretKey = registrationService.registration(client);
        return new RegistrationResultDto(secretKey, "Ключ будет активирован после подтверждения email. " +
                "Для активации перейдите по ссылке в письме");
    }

    @GetMapping("/activation")
    public String activate(@RequestParam String key) {
        Client client = supportiveService.getClient(key);
        return registrationService.activate(client);
    }

}
