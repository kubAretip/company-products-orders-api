package pl.kubaretip.cpo.api.service.impl;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import pl.kubaretip.cpo.api.constants.StatusConstants;
import pl.kubaretip.cpo.api.domain.Order;
import pl.kubaretip.cpo.api.domain.OrderStatus;
import pl.kubaretip.cpo.api.exception.StatusResourceException;
import pl.kubaretip.cpo.api.repository.StatusRepository;
import pl.kubaretip.cpo.api.service.OrderStatusService;
import pl.kubaretip.cpo.api.util.Translator;

@Service
public class OrderStatusServiceImpl implements OrderStatusService {

    private final StatusRepository statusRepository;
    private final Translator translator;

    public OrderStatusServiceImpl(StatusRepository statusRepository,
                                  Translator translator) {
        this.statusRepository = statusRepository;
        this.translator = translator;
    }

    @Override
    public OrderStatus getOrderStatus(StatusConstants status) {
        var createdStatus = statusRepository.findByNameIgnoreCaseAndLocaleIgnoreCase(status.name(),
                LocaleContextHolder.getLocale().toLanguageTag())
                .orElseThrow(() -> new StatusResourceException(translator.translate("status.notFound.resource")));
        var orderStatus = new OrderStatus();
        orderStatus.setStatus(createdStatus);
        return orderStatus;
    }


    public void saveOrderStatus(Order order, StatusConstants status){
        var createdStatus = statusRepository.findByNameIgnoreCaseAndLocaleIgnoreCase(status.name(),
                LocaleContextHolder.getLocale().toLanguageTag())
                .orElseThrow(() -> new StatusResourceException(translator.translate("status.notFound.resource")));
        var orderStatus = new OrderStatus();
        orderStatus.setStatus(createdStatus);
        orderStatus.setOrder(order);

    }


}
