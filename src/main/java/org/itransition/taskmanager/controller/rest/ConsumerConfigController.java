package org.itransition.taskmanager.controller.rest;

import lombok.RequiredArgsConstructor;
import org.itransition.taskmanager.dto.ConsumerConfigDto;
import org.itransition.taskmanager.service.dto.ConsumerConfigService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;


import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/consumers/{id}/config",
        produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class ConsumerConfigController {

    private final ConsumerConfigService consumerConfigService;

    @GetMapping("{id}")
    public ConsumerConfigDto getConsumerConfig(@PathVariable("id") final Long id) {
        return consumerConfigService.findById(id);
    }

    @PutMapping("{id}")
    public ConsumerConfigDto updateConsumerConfig(final @PathVariable("id") Long consumerId,
                                      @Valid @RequestBody final ConsumerConfigDto consumerConfigDto) {

        return consumerConfigService.updateById(consumerId, consumerConfigDto);
    }
}
