package com.pismo.paymentpractice.service;

import com.pismo.paymentpractice.domain.OperationsType;
import com.pismo.paymentpractice.exception.NotFoundErrorException;
import com.pismo.paymentpractice.repository.OperationsTypeRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
public class OperationsTypeService {
    private static final Logger logger = LogManager.getLogger(OperationsTypeService.class.getName());
    private final OperationsTypeRepository operationsTypeRepository;

    public OperationsTypeService(OperationsTypeRepository operationsTypeRepository) {
        this.operationsTypeRepository = operationsTypeRepository;
    }

    public OperationsType getOperationTypeById(Integer operationsTypeId) {
        var response = operationsTypeRepository.findById(operationsTypeId);

        return response.orElseThrow(
                        () -> {
                            logger.error(format("Operation type with id %d doesnt exists.", operationsTypeId));
                            throw new NotFoundErrorException(format("Operation type with id %d doesnt exists.",
                                    operationsTypeId));
                        })
                .getDescription();
    }
}
