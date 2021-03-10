package pl.kubaretip.cpo.api.service.impl;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import pl.kubaretip.cpo.api.constants.StatusConstants;
import pl.kubaretip.cpo.api.domain.OrderStatus;
import pl.kubaretip.cpo.api.exception.StatusResourceException;
import pl.kubaretip.cpo.api.repository.StatusRepository;
import pl.kubaretip.cpo.api.service.OrderStatusService;

@Service
public class OrderStatusServiceImpl implements OrderStatusService {

    private final StatusRepository statusRepository;

    public OrderStatusServiceImpl(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    @Override
    public OrderStatus getOrderStatus(StatusConstants status) {
        var createdStatus = statusRepository.findByNameIgnoreCaseAndLocaleIgnoreCase(status.name(),
                LocaleContextHolder.getLocale().toLanguageTag())
                .orElseThrow(() -> new StatusResourceException("exception.status.notFound"));
        var orderStatus = new OrderStatus();
        orderStatus.setStatus(createdStatus);
        return orderStatus;
    }

}
