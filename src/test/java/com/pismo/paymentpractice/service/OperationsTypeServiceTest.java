package com.pismo.paymentpractice.service;

import com.pismo.paymentpractice.domain.OperationsType;
import com.pismo.paymentpractice.exception.NotFoundErrorException;
import com.pismo.paymentpractice.repository.OperationsTypeRepository;
import com.pismo.paymentpractice.repository.entity.OperationTypesModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class OperationsTypeServiceTest {
    private OperationsTypeService operationsTypeService;

    @MockBean
    private OperationsTypeRepository operationsTypeRepository;

    @BeforeEach
    void init() {
        this.operationsTypeService = new OperationsTypeService(operationsTypeRepository);
    }

    @Test
    void whenTryGetExistedOperationTypeWithValidIdThenReturnCorrespondentOperationType() {
        when(operationsTypeRepository.findById(anyInt())).thenReturn(
                Optional.of(new OperationTypesModel(1, OperationsType.CASH_PURCHASES)));

        var result = operationsTypeService.getOperationTypeById(OperationsType.CASH_PURCHASES.getValue());

        assertEquals(OperationsType.CASH_PURCHASES, result);
        verify(operationsTypeRepository, times(1)).findById(anyInt());
    }

    @Test
    void whenTryGetOneOperationTypeWithInvalidIdThenThrowNotFoundException() {
        when(operationsTypeRepository.findById(anyInt())).thenThrow(
                new NotFoundErrorException("Error Finding Operation Type by Id"));

        assertThrows(NotFoundErrorException.class, () -> operationsTypeService.getOperationTypeById(123));

        verify(operationsTypeRepository, times(1)).findById(anyInt());
    }
}