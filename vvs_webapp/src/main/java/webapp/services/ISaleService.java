package webapp.services;

public interface ISaleService {

    SalesDTO getSaleByCustomerVat(int vat) throws ApplicationException;

    SalesDTO getAllSales() throws ApplicationException;

    void addSale(int customerVat) throws ApplicationException;

    void updateSale(int id) throws ApplicationException;

    SalesDeliveryDTO getSalesDeliveryByVat(int vat) throws ApplicationException;
    
    int addSaleDelivery(int sale_id, int addr_id) throws ApplicationException;
} 