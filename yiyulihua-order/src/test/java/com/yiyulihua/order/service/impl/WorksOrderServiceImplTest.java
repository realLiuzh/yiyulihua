package com.yiyulihua.order.service.impl;

import com.yiyulihua.common.result.Result;
import com.yiyulihua.common.to.OrderTo;
import com.yiyulihua.common.utils.PageUtils;
import com.yiyulihua.common.vo.ParticipantWorksVo;
import com.yiyulihua.order.feign.WorksClient;
import com.yiyulihua.order.util.OrderConstants;
import com.yiyulihua.order.util.RedisIdWorker;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@SpringBootTest
public class WorksOrderServiceImplTest {

    @Mock
    private WorksClient mockWorksClient;
    @Mock
    private RedisIdWorker mockRedisIdWorker;

    @Autowired
    private WorksOrderServiceImpl worksOrderServiceImplUnderTest;


    @Test
    public void testCreateOrder() {
        // Setup
        final OrderTo order = new OrderTo();
        order.setWorksId(0);
        order.setQuotePrice(new BigDecimal("0.00"));
        order.setTitle("orderTitle");

        when(mockRedisIdWorker.nextId("order:works:no")).thenReturn(0L);
        doReturn(Result.success()).when(mockWorksClient).updateNumber(0);

        // Run the test
        final Long result = worksOrderServiceImplUnderTest.createOrder(order);

        // Verify the results
        assertThat(result).isEqualTo(0L);
    }

    @Test
    public void testCreateOrder_WorksClientReturnsNoItem() {
        // Setup
        final OrderTo order = new OrderTo();
        order.setWorksId(0);
        order.setQuotePrice(new BigDecimal("0.00"));
        order.setTitle("orderTitle");

        when(mockRedisIdWorker.nextId("order:works:no")).thenReturn(0L);
        doReturn(Result.success()).when(mockWorksClient).updateNumber(0);

        // Run the test
        final Long result = worksOrderServiceImplUnderTest.createOrder(order);

        // Verify the results
        assertThat(result).isEqualTo(0L);
    }

    @Test
    public void testCreateOrder_WorksClientReturnsError() {
        // Setup
        final OrderTo order = new OrderTo();
        order.setWorksId(0);
        order.setQuotePrice(new BigDecimal("0.00"));
        order.setTitle("orderTitle");

        when(mockRedisIdWorker.nextId("order:works:no")).thenReturn(0L);

        // Run the test
        final Long result = worksOrderServiceImplUnderTest.createOrder(order);

        // Verify the results
        assertThat(result).isEqualTo(0L);
    }

    @Test
    public void testUpdateOrderStatusByOrderNo() {
        // Setup
        // Run the test
        worksOrderServiceImplUnderTest.updateOrderStatusByOrderNo("2208141076362644",
                OrderConstants.PAID,
                OrderConstants.REJECTED);

        // Verify the results
    }
}
